//package jds.bibliocraft.rendering;
//
//import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
//import jds.bibliocraft.api.render.IISBRH;
//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.RenderBlocks;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.world.IBlockAccess;
//
//public class CustomISBRHRender implements ISimpleBlockRenderingHandler {
//    @Override
//    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
//        if (block instanceof IISBRH) {
//            IISBRH customRenderBlock = (IISBRH) block;
//            customRenderBlock.renderInventoryBlock(block, metadata, modelId, renderer, Tessellator.instance);
//        }
//    }
//
//    @Override
//    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
//        if (block instanceof IISBRH) {
//            IISBRH customRenderBlock = (IISBRH) block;
//            return customRenderBlock.renderWorldBlock(world, x, y, z, block, modelId, renderer, Tessellator.instance);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean shouldRender3DInInventory(int modelId) {
//        return true;
//    }
//
//    @Override
//    public int getRenderId() {
//        return IISBRH.RenderId;
//    }
//}
