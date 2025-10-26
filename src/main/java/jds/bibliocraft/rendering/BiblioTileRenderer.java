package jds.bibliocraft.rendering;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public abstract class BiblioTileRenderer<T extends TileEntity> extends TileEntitySpecialRenderer
    implements IItemRenderer {

    private RenderItem itemRenderer;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        GL11.glPushMatrix();
        renderBiblioTileEntityAt((T) tile, x, y, z, tick);
        GL11.glPopMatrix();
    }

    public abstract void renderBiblioTileEntityAt(T tile, double x, double y, double z, float tick);

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {}


    public void renderItemInWorld(ItemStack item, double x, double y, double z, float partialTicks) {
        renderItemInWorld(item, x, y, z, partialTicks, null, true,true, true);
    }

    public void renderItemInWorld(ItemStack item, double x, double y, double z, float partialTicks, Byte blockCount, boolean shouldBob, boolean shouldSpreadItems, boolean originalBlockCount) {
        EntityItem ei = new EntityItem(null, x, y, z, item);
        ei.age = (int) partialTicks;
        ei.hoverStart = 0;
        if (itemRenderer == null) {
            itemRenderer = new CustomRenderItem(originalBlockCount).SetBlockCount(blockCount).SetShouldBob(shouldBob).SetShouldSpreadItems(shouldSpreadItems);
        }
        this.itemRenderer.doRender(ei, x, y, z, 0.0F, 0.0F);
    }


    private static class CustomRenderItem extends RenderItem {


        private Byte blockCount;
        private boolean shouldBob;
        private boolean shouldSpreadItems;
        private boolean originalBlockCount;


        public CustomRenderItem(boolean originalBlockCount) {
            super();
            this.originalBlockCount = originalBlockCount;
            this.setRenderManager(RenderManager.instance);
        }

        public CustomRenderItem SetBlockCount(byte blockCount) {
            this.blockCount = blockCount;
            return this;
        }
        public CustomRenderItem SetShouldBob(boolean shouldBob) {
            this.shouldBob = shouldBob;
            return this;
        }
        public CustomRenderItem SetShouldSpreadItems(boolean shouldSpreadItems) {
            this.shouldSpreadItems = shouldSpreadItems;
            return this;
        }

        @Override
        public byte getMiniBlockCount(ItemStack stack, byte original) {
            return originalBlockCount ? original : blockCount;
        }

        @Override
        public boolean shouldBob() {
            return shouldBob;
        }

        @Override
        public boolean shouldSpreadItems() {
            return shouldSpreadItems;
        }
    }
}
