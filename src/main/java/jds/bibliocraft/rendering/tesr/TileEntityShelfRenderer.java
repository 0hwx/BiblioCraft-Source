package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.tileentities.base.BiblioTileEntity;

public class TileEntityShelfRenderer extends TileEntityBiblioRenderer {

    @Override
    public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick) {

        this.renderSlotItem(tile.getStackInSlot(0), 0.25,0.6875,0.25, 1f);
        this.renderSlotItem(tile.getStackInSlot(1), 0.75, 0.6875, 0.25, 1f);
        this.renderSlotItem(tile.getStackInSlot(2), 0.25, 0.1875, 0.25, 1f);
        this.renderSlotItem(tile.getStackInSlot(3), 0.75, 0.1875, 0.25, 1f);
    }


//    // to render block in a bigger size but not other items
//    public void isBlock(BiblioTileEntity tile) {
//        for (int i = 0; i < tile.getSizeInventory(); i++) {
//            ItemStack stack = tile.getStackInSlot(i);
//            // this is from RenderItem
//            if (stack != null && stack.getItemSpriteNumber() == 1 && stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType())) {
//                this.renderSlotItem(stack, 0.25 + (i % 2) * 0.5, 0.66 - (i / 2) * 0.49, 0.25, 1.5f);
//            } else {
//                this.renderSlotItem(stack, 0.25 + (i % 2) * 0.5, 0.66 - (i / 2) * 0.49, 0.25, 0.9f);
//            }
//        }
//    }

}
