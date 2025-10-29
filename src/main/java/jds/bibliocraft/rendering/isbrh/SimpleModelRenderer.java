package jds.bibliocraft.rendering.isbrh;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;
import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.api.render.IISBRH;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

//@ThreadSafeISBRH(perThread = false)
public class SimpleModelRenderer implements ISimpleBlockRenderingHandler {

    public SimpleModelRenderer() {
    }

    @Override
    public int getRenderId() {
        return IISBRH.RenderId;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        final Tessellator tess = Tessellator.instance;
        if (block instanceof IISBRH) {
            IISBRH customRenderBlock = (IISBRH) block;
            return customRenderBlock.renderWorldBlock(world, x, y, z, block,tess);
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
