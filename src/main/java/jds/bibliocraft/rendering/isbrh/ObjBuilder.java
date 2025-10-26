package jds.bibliocraft.rendering.isbrh;

import jds.bibliocraft.helpers.EnumShiftPosition;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.models.BiblioModels;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexRotationFacing;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTransform;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTransformComposite;
import jds.bibliocraft.rendering.isbrh.Vertex.VertexTranslation;
import jds.bibliocraft.utils.math.Vector.Vector3d;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

import static jds.bibliocraft.rendering.isbrh.TechneUtil.getInterpolatedU;
import static jds.bibliocraft.rendering.isbrh.TechneUtil.getInterpolatedV;
import static jds.bibliocraft.rendering.isbrh.TechneUtil.makeShiftTransform;
import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

public class ObjBuilder {


    public WavefrontObject wavefrontObject;
    public Tessellator tessellator;
    public IBlockAccess world;
    public int x;
    public int y;
    public int z;
    public ForgeDirection angle = NORTH;
    public EnumVertPosition vert = EnumVertPosition.FLOOR;
    public EnumShiftPosition shift = EnumShiftPosition.NO_SHIFT;

    public ObjBuilder(WavefrontObject wavefrontObject, Tessellator tessellator, IBlockAccess world, int x, int y, int z) {
        this.wavefrontObject = wavefrontObject;
        this.tessellator = tessellator;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ObjBuilder setModel(WavefrontObject wavefrontObject) {
        this.wavefrontObject = wavefrontObject;
        return this;
    }

    public ObjBuilder getModel(BiblioModels model) {
        this.wavefrontObject = model.getModel();
        return this;
    }

    public ObjBuilder startTess() {
        this.tessellator.startDrawingQuads();
        this.tessellator.setColorOpaque_F(1, 1, 1);
        return this;
    }

    public ObjBuilder endTess() {
        this.tessellator.draw();
        return this;
    }




    public void setPostion(BiblioTileEntity tile) {
        this.shift = tile.getShiftPosition();
        this.vert = tile.getVertPosition();
        this.angle = tile.getAngle();
    }

    public void renderPartWithOrientation(String partName, IIcon icon) {
        for (GroupObject part : this.wavefrontObject.groupObjects) {
            if (part.name.equalsIgnoreCase(partName)) {
                renderWithOrientation(part, icon, this.tessellator, this.shift, this.vert, this.angle, this.world, this.x, this.y, this.z);
            }
        }
    }

    public void renderWithOrientation(GroupObject group, IIcon icon, Tessellator tes,
                                      EnumShiftPosition shift, EnumVertPosition vert,
                                      ForgeDirection rotation, IBlockAccess world, int x, int y, int z) {

        // === Setup transform center ===
        Vector3d center = new Vector3d(0.5, 0.5, 0.5);
        List<VertexTransform> transforms = new ArrayList<>();

//        // === Handle vertical orientation ===
//        if (vert == EnumVertPosition.CEILING) {
//            // Flip upside-down around X-axis
//            transforms.add(new VertexRotation(Math.PI, new Vector3d(1, 0, 0), center));
//        } else if (vert == EnumVertPosition.WALL) {
//            // Tilt forward 90 degrees to mount on wall
//            transforms.add(new VertexRotation(Math.PI / 2, new Vector3d(1, 0, 0), center));
//        }

        // === Handle facing rotation with VertexRotationFacing ===
        VertexRotationFacing facingRot = new VertexRotationFacing(WEST);
        facingRot.setRotation(rotation);
        transforms.add(facingRot);

        // === Handle optional shift ===
        VertexTranslation shiftTrans = makeShiftTransform(shift, rotation);
        if (shiftTrans != null) {
            transforms.add(shiftTrans);
        }

        // === Combine all transforms ===
        VertexTransformComposite finalTransform = new VertexTransformComposite(transforms);

        // === Render model with combined transforms ===
        renderWithIcon(group, icon, null, tes, world, x, y, z, finalTransform, true);
    }

    public void renderWithIcon(GroupObject go, IIcon icon, IIcon override, Tessellator tes, IBlockAccess world,
                               int x, int y, int z, VertexTransform vt, boolean isBRH) {

        for (Face f : go.faces) {
            // Copy face normal and apply transformation once per face
            ForgeDirection normal = getNormalFor(f.faceNormal, angle);

            if (vt != null) {
                if (vt instanceof VertexRotationFacing) {
                    normal = ((VertexRotationFacing) vt).rotate(normal);
                }
                if (vt instanceof VertexTransformComposite) {
                    for (VertexTransform xform : ((VertexTransformComposite) vt).xforms) {
                        if (xform instanceof VertexRotationFacing) {
                            normal = ((VertexRotationFacing) xform).rotate(normal);
                        }
                    }
                }
            }

            // Set face normal for lighting
            tes.setNormal(f.faceNormal.x, f.faceNormal.y, f.faceNormal.z);

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
            if (isBRH && world != null) {
                int neighborX = x + normal.offsetX;
                int neighborY = y + normal.offsetY;
                int neighborZ = z + normal.offsetZ;

                Block neighbor = world.getBlock(neighborX, neighborY, neighborZ);
                int brightness;

                // If neighbor is opaque, fallback to current block brightness
                if (neighbor.getLightOpacity() >= 255) {
                    brightness = world.getBlock(x, y, z).getMixedBrightnessForBlock(world, x, y, z);
                } else {
                    brightness = neighbor.getMixedBrightnessForBlock(world, neighborX, neighborY, neighborZ);
                }

                tes.setBrightness(brightness);
            }

            for (int i = 0; i < f.vertices.length; i++) {
                Vertex vert = f.vertices[i];
                Vector3d v = new Vector3d(vert);

                if (vt != null) {
                    vt.apply(v);
                }

                // Set color multiplier
                if (isBRH) {
                    int c = (int) (0xFF * RenderUtil.getColorMultiplierForFace(normal));
                    tes.setColorOpaque(c, c, c);
                }

                // Handle texture
                if (override != null) {
                    // Compute UVs based on face-local axes
                    double u = dotProduct(v, right);
                    double vCoord = dotProduct(v, down);

                    // Clamp UVs
                    u = u - Math.floor(u);
                    vCoord = vCoord - Math.floor(vCoord);

                    // Flip for certain faces to match Minecraft convention
                    if (normal == SOUTH || normal == WEST) u = 1 - u;
                    if (normal != ForgeDirection.UP && normal != DOWN) vCoord = 1 - vCoord;

                    tes.addVertexWithUV(v.x, v.y, v.z, override.getInterpolatedU(u * 16), override.getInterpolatedV(vCoord * 16));
                } else {
                    TextureCoordinate t = f.textureCoordinates[i];
                    tes.addVertexWithUV(v.x, v.y, v.z, getInterpolatedU(icon, t.u), getInterpolatedV(icon, t.v));
                }
            }
        }
    }

    // Helper to compute dot product for UV mapping
    private double dotProduct(Vector3d v, ForgeDirection dir) {
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
        if (n.y > 0) normal = ForgeDirection.UP;
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

}
