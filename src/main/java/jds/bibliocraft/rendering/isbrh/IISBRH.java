package jds.bibliocraft.rendering.isbrh;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public interface IISBRH {

    int RenderId = RenderingRegistry.getNextAvailableRenderId();

    /**
     * Called to render the block in the world.
     *
     * @param block The block instance
     * @param x Block x coordinate
     * @param y Block y coordinate
     * @param z Block z coordinate
     * @param tessellator The tessellator for drawing
     */
    boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block,Tessellator tessellator);
}
