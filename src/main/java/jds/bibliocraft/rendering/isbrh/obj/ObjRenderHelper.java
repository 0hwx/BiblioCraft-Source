package jds.bibliocraft.rendering.isbrh.obj;

import jds.bibliocraft.helpers.EnumShiftPosition;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexRotationFacing;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTransform;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTransformComposite;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTranslation;
import jds.bibliocraft.utils.math.Vector.Vector3d;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.common.util.ForgeDirection;


import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

public class ObjRenderHelper {


    public static void renderWithIcon(GroupObject group, IIcon icon, IIcon override,  Tessellator tess,
                               ObjContext ctx, VertexTransform transform, boolean isBRH, boolean lockUV) {

        double angle = getRotationAngle(transform);

        // Convert the angle (0, PI/2, PI, 3*PI/2) to rotation steps (0, 1, 2, 3)
        // We divide by ROTATION_AMOUNT (PI/2) and round to the nearest integer.
        // Ensure you have ROTATION_AMOUNT defined or use Math.PI / 2
        final double ROTATION_AMOUNT = Math.PI / 2;
        int rotationSteps = (int)Math.round(angle / ROTATION_AMOUNT);

        // The rotation applied to the model is rotationSteps % 4
        rotationSteps %= 4;

        // Calculate the counter-rotation steps
        int counterRotation = (4 - rotationSteps) % 4;


        for (Face f : group.faces) {
            // Copy face normal and apply transformation once per face
            ForgeDirection normal = getNormalFor(f.faceNormal, ctx.facing);

            if (transform != null) {
                if (transform instanceof VertexRotationFacing rotationFacing) {
                    normal = rotationFacing.rotate(normal);
                }
                if (transform instanceof VertexTransformComposite composite) {
                    for (VertexTransform xform : composite.xforms) {
                        if (xform instanceof VertexRotationFacing rotationFacing) {
                            normal = rotationFacing.rotate(normal);
                        }
                    }
                }
            }

            // Set face normal for lighting
            tess.setNormal(f.faceNormal.x, f.faceNormal.y, f.faceNormal.z);

            // Compute right and down directions for UV mapping
            ForgeDirection right, down;
            switch (normal) {
                case UP:    right = EAST; down = SOUTH; break;
                case DOWN:  right = EAST; down = NORTH; break;
                case NORTH: right = EAST; down = DOWN; break;
                case SOUTH: right = WEST; down = DOWN; break;
                case EAST:  right = SOUTH; down = DOWN; break;
                case WEST:  right = NORTH; down = DOWN; break;
                default:    right = EAST; down = SOUTH; break;
            }

            // Set brightness if needed
            if (isBRH && ctx.world != null) {
                int neighborX = (int) (ctx.x + normal.offsetX);
                int neighborY = (int) (ctx.y + normal.offsetY);
                int neighborZ = (int) (ctx.z + normal.offsetZ);

                Block neighbor = ctx.world.getBlock(neighborX, neighborY, neighborZ);
                int brightness;

                // If neighbor is opaque, fallback to current block brightness
                if (neighbor.getLightOpacity() >= 255) {
                    brightness = ctx.world.getBlock((int) ctx.x, (int) ctx.y, (int) ctx.z).getMixedBrightnessForBlock(ctx.world, (int) ctx.x, (int) ctx.y, (int) ctx.z);
                } else {
                    brightness = neighbor.getMixedBrightnessForBlock(ctx.world, neighborX, neighborY, neighborZ);
                }

                tess.setBrightness(brightness);
            }

            for (int i = 0; i < f.vertices.length; i++) {
                Vertex vert = f.vertices[i];
                Vector3d vec = new Vector3d(vert);

                if (transform != null) {
                    transform.apply(vec);
                }

                // Set color multiplier
                if (isBRH) {
                    int c = (int) (0xFF * getColorMultiplierForFace(normal));
                    tess.setColorOpaque(c, c, c);
                }

                // Compute UVs
                double interpX , interpY;
                if (override != null) {
                    // Use world-aligned projection for tiled override icon (like vanilla block faces)
                    // u/vec are computed from vertex position dotted with right/down axes
                    interpX = dotProduct(vec, right);
                    interpY = dotProduct(vec, down);

                    // invert or flip to match vanilla face conventions as your original code did
                    if (normal == SOUTH || normal == WEST) interpX = 1.0 - interpX;
                    if (normal != ForgeDirection.UP && normal != ForgeDirection.DOWN) interpY = 1.0 - interpY;

                    // Finally sample using override icon. For override we use pixel-based interpolation: *16 (tile units).
                    tess.addVertexWithUV(vec.x, vec.y, vec.z,
                        override.getInterpolatedU(interpX * 16.0),
                        override.getInterpolatedV(interpY * 16.0));

                } else {
                    TextureCoordinate t = f.textureCoordinates[i];
                    double u = t.u;
                    double v = t.v;

                    // NEW LOGIC: Apply the pre-calculated counter-rotation
                    if (lockUV) {
                        // Only counter-rotate if the rotation is non-zero (i.e., rotationSteps != 0)
                        if (rotationSteps != 0) {
                            double[] rotatedUV = rotateUV(u, v, counterRotation);
                            u = rotatedUV[0];
                            v = rotatedUV[1];
                        }
                    }
                    tess.addVertexWithUV(vec.x, vec.y, vec.z, getInterpolatedU(icon, u), getInterpolatedV(icon, v));
                }
            }
        }
    }

    private static double getRotationAngle(VertexTransform transform) {
        if (transform == null) {
            return 0;
        }

        if (transform instanceof VertexRotationFacing rotationFacing) {
            return rotationFacing.getAngle();
        }

        if (transform instanceof VertexTransformComposite composite) {
            for (VertexTransform xform : composite.xforms) {
                if (xform instanceof VertexRotationFacing rotationFacing) {
                    return rotationFacing.getAngle();
                }
            }
        }
        // If no specific rotation facing transform is found, assume 0 rotation.
        return 0;
    }

    /**
     * rotateUV performs rotation of UV coordinates by 0/90/180/270 degrees clockwise.
     * Note: rotation parameter k:
     *  0 -> 0°
     *  1 -> 90° clockwise
     *  2 -> 180°
     *  3 -> 270° clockwise
     *
     * We expect u,v in [0,1). Result is also in [0,1).
     */
    private static double[] rotateUV(double u, double v, int rot) {
        return switch (rot & 3) {
            case 1 -> new double[]{wrap01(v), wrap01(1 - u)};        // 90° CW
            case 2 -> new double[]{wrap01(1 - u), wrap01(1 - v)};    // 180°
            case 3 -> new double[]{wrap01(1 - v), wrap01(u)};        // 270°
            default -> new double[]{wrap01(u), wrap01(v)};           // 0°
        };
    }

    // Fractional part in [0,1)
    private static double fractional(double x) {
        double f = x - Math.floor(x);
        // handle -0.0
        return f < 0 ? (f + 1.0) : f;
    }

    // Ensure value ends in [0,1)
    private static double wrap01(double x) {
        return fractional(x);
    }

    // Helper to compute dot product for UV mapping
    private static double dotProduct(Vector3d v, ForgeDirection dir) {
        switch (dir) {
            case UP: return v.y;
            case DOWN: return -v.y;
            case NORTH: return -v.z;
            case SOUTH: return v.z;
            case EAST: return v.x;
            case WEST: return -v.x;
            default: return 0;
        }
    }

    public static ForgeDirection getNormalFor(Vertex n, ForgeDirection facing) {
        ForgeDirection normal = ForgeDirection.UNKNOWN;

        // Step 1: Determine raw normal
        if (n.y > 0) normal = UP;
        else if (n.y < 0) normal = DOWN;
        else if (n.z > 0) normal = SOUTH;
        else if (n.z < 0) normal = NORTH;
        else if (n.x > 0) normal = EAST;
        else if (n.x < 0) normal = WEST;

        // Step 2: Rotate horizontal normals based on facing
        if (normal == NORTH || normal == SOUTH ||
            normal == EAST || normal == WEST) {

            normal = rotateHorizontal(normal, facing);
        }

        return normal;
    }

    private static ForgeDirection rotateHorizontal(ForgeDirection normal, ForgeDirection facing) {
        // Maps the rotation of horizontal normals for each facing
        switch (facing) {
            case NORTH: return normal; // No rotation
            case SOUTH:
                switch (normal) {
                    case NORTH: return SOUTH;
                    case SOUTH: return NORTH;
                    case EAST:  return WEST;
                    case WEST:  return EAST;
                }
            case EAST:
                switch (normal) {
                    case NORTH: return EAST;
                    case SOUTH: return WEST;
                    case EAST:  return SOUTH;
                    case WEST:  return NORTH;
                }
            case WEST:
                switch (normal) {
                    case NORTH: return WEST;
                    case SOUTH: return EAST;
                    case EAST:  return NORTH;
                    case WEST:  return SOUTH;
                }
            default: return normal;
        }
    }

    // reducing texture positioning error by working in double and not multiplying
    // and dividing by 16.
    public static double getInterpolatedV(IIcon icon, double offset) {
        return icon.getMinV() + (icon.getMaxV() - icon.getMinV()) * offset;
    }

    public static double getInterpolatedU(IIcon icon, double offset) {
        return icon.getMinU() + (icon.getMaxU() - icon.getMinU()) * offset;
    }

    public static VertexTranslation makeShiftTransform(EnumShiftPosition shift, ForgeDirection rotation) {
        double shiftAmount = 0;
        switch (shift) {
            case HALF_SHIFT: shiftAmount = 0.25; break;
            case FULL_SHIFT: shiftAmount = 0.5; break;
            default: return null;
        }

        Vector3d offset = new Vector3d(0, 0, 0);
        switch (rotation) {
            case SOUTH: offset.x -= shiftAmount; break;
            case NORTH: offset.x += shiftAmount; break;
            case WEST:  offset.z -= shiftAmount; break;
            case EAST:  offset.z += shiftAmount; break;
            default: break;
        }

        return new VertexTranslation(offset);
    }

    public static float getColorMultiplierForFace(ForgeDirection face) {
        if (face == ForgeDirection.UP) {
            return 1;
        }
        if (face == ForgeDirection.DOWN) {
            return 0.5f;
        }
        if (face.offsetX != 0) {
            return 0.6f;
        }
        return 0.8f; // z
    }
}

