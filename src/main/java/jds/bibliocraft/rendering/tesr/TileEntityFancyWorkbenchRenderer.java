package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.models.ModelFancyWorkbench;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;

public class TileEntityFancyWorkbenchRenderer extends TileEntityBiblioRenderer  {
    private ModelFancyWorkbench fancyWorkbench = new ModelFancyWorkbench();
    private int[] Count;
    private int meta;
    private int rotation;

    @Override
    public void renderTileEntityAt(BiblioTileEntity tileEntity, double x, double y, double z, float tick) {

//        TileEntityFancyWorkbench tile = (TileEntityFancyWorkbench) tileEntity;
//        if (tile != null) {
//            this.Count = tile.getBookArray();
//            this.meta = tile.getBlockMetadata();
//            GL11.glPushMatrix();
//            switch (this.getAngle()) {
//                case WEST: // south
//                    rotation = 0;
//                    break;
//                case SOUTH: // east
//                    rotation = 90;
//                    break;
//                case EAST: // north
//                    rotation = 180;
//                    break;
//                case NORTH: // west
//                    rotation = 270;
//                    break;
//            }
//            renderTable(x + 0.5D, y, z + 0.5D, rotation, this.meta,false);
//
//            GL11.glPopMatrix();
//        }
            renderSlotItem(tileEntity.getStackInSlot(0), 0.2, 1.01, 0.4, 0.8f,90.0F);
        }

    @Override
    public void additionalGLStuffForItemStack () {}

//    public void renderTable(double x, double y, double z, double rotate, int meta, boolean renderAllBooks) {
//        GL11.glTranslated(x,y,z);
//        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
//        this.bindTexture(CommonProxy.CRAFTINGTOP);
//        this.fancyWorkbench.rendertop();
//        this.bindTexture(CommonProxy.WORKBENCH_SIDES);
//        this.fancyWorkbench.rendersides();
//        this.bindTexture(BiblioWoodRegistry.getResource(meta));
//        this.fancyWorkbench.renderbench();
//        this.bindTexture(CommonProxy.BOOKCASE_BOOKS);
//        if (renderAllBooks) {
//            for (int i = 0; i < 8; i++) {
//                this.fancyWorkbench.renderbook(i);
//            }
//        } else if (this.Count != null && this.Count.length == 8) {
//            for (int i = 0; i < 8; i++) {
//                if (this.Count[i] == 1) {
//                    this.fancyWorkbench.renderbook(i);
//                }
//            }
//        }
//    }
//
//
//    @Override
//    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
//        return true;
//    }
//
//    @Override
//    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
//        return true;
//    }
//
//    @Override
//    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//        switch (type) {
//            case INVENTORY:
//                renderTable(0, -0.5D, 0,180.0D, item.getItemDamage(),true);
//                return;
//            case EQUIPPED_FIRST_PERSON:
//                renderTable(0.5D, 0, 0.5D, 90.0D, item.getItemDamage(),true);
//                return;
//            case EQUIPPED:
//                renderTable(0.5D, 0, 0.5D, 0, item.getItemDamage(),true);
//                return;
//            default:
//                renderTable(0, -0.5D, 0, 0, item.getItemDamage(),true);
//        }
//    }
}
