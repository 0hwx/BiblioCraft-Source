package jds.bibliocraft.rendering.isbrh.obj;

import jds.bibliocraft.helpers.EnumShiftPosition;
import jds.bibliocraft.helpers.EnumVertPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * ObjContext
 * -----------
 * A lightweight data container describing the render context for OBJ-based models.
 *
 * This provides all necessary information — position, facing, and alignment — for
 * rendering models either in the world or in off-world contexts such as GUI or inventory.
 *
 * <p><strong>Note:</strong> {@link #world} may be {@code null} when rendering
 * outside the world (e.g., inventory or static previews).
 */
public class ObjContext {

    /** The world the model is rendered in, or null for non-world contexts. */
    public final IBlockAccess world;

    /** The world-space or render-space position of the model. */
    public final double x, y, z;

    /** The direction the model is facing (e.g., NORTH, SOUTH, EAST, WEST). */
    public final ForgeDirection facing;

    /** The vertical position/orientation (e.g., FLOOR, WALL, CEILING). */
    public final EnumVertPosition vert;

    /**
     * The horizontal offset of the model along the X or Z axis.
     * <p>
     * Determines how far the model is shifted within the block:
     * NO_SHIFT — base position (1/3),
     * HALF_SHIFT — halfway (2/3),
     * FULL_SHIFT — full offset (3/3).
     */
    public final EnumShiftPosition shift;

    /**
     * Constructs a new rendering context for an OBJ model.
     *
     * @param world  The block access reference (nullable).
     *               Can be {@code null} for GUI or inventory rendering.
     * @param x      The X position in world or render space.
     * @param y      The Y position in world or render space.
     * @param z      The Z position in world or render space.
     * @param facing The facing direction of the model.
     * @param vert   The vertical orientation (floor, wall, ceiling).
     * @param shift  The positional shift or offset.
     */
    public ObjContext(IBlockAccess world, double x, double y, double z,
                      ForgeDirection facing, EnumVertPosition vert, EnumShiftPosition shift) {
        this.world = world;   // May be null if rendering outside world
        this.x = x;
        this.y = y;
        this.z = z;
        this.facing = facing;
        this.vert = vert;
        this.shift = shift;
    }

    public ObjContext(IBlockAccess world, double x, double y, double z) {
        this.world = world;   // May be null if rendering outside world
        this.x = x;
        this.y = y;
        this.z = z;
        this.facing = ForgeDirection.WEST;
        this.vert = EnumVertPosition.WALL;
        this.shift = EnumShiftPosition.NO_SHIFT;
    }
}

