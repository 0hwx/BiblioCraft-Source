package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.models.ModelBookcase;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;

public class TileEntityBookcaseRenderer extends TileEntityBiblioRenderer {
    private ModelBookcase Bookcase = new ModelBookcase();

    private int[] Count;
    private int meta;
    private int rotation;
    private  boolean createBookcase = false;

    public TileEntityBookcaseRenderer() {

    }
    @Override
    public void renderTileEntityAt(BiblioTileEntity tileEntity, double x, double y, double z, float tick) {
//        TileEntityBookcase tile = (TileEntityBookcase) tileEntity;
//        if (tile != null) {
//
//            this.Count = tile.getCheckedBooks();
//            this.meta = tile.getBlockMetadata();
//            GL11.glPushMatrix();
//            switch (this.getAngle()) {
//                case WEST: // south
//                    rotation = 0;
//                    xshift -= 0.5f;
//                    zshift -= 0.5f;
//                    break;
//                case SOUTH: // east
//                    rotation = 90;
//                    xshift -= 0.5f;
//                    zshift += 0.5f;
//                    break;
//                case EAST: // north
//                    rotation = 180;
//                    xshift += 0.5f;
//                    zshift += 0.5f;
//                    break;
//                case NORTH: // west
//                    rotation = 270;
//                    xshift += 0.5f;
//                    zshift -= 0.5f;
//                    break;
//            }
//            renderBookcase(x + xshift, y, z + zshift, rotation, this.meta, false);
//            GL11.glPopMatrix();
//        }
    }

//    public void renderBookcase(double x, double y, double z, double rotate, int meta, boolean renderAllBooks) {
//        GL11.glTranslated(x,y,z);
//        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
//        this.bindTexture(BiblioWoodRegistry.getResource(meta));
//        this.Bookcase.renderBookcase();
//        this.bindTexture(CommonProxy.BOOKCASE_BOOKS);
//        if (renderAllBooks) {
//            for (int i = 0; i < 16; i++) {
//                this.Bookcase.renderbook(i);
//            }
//        } else if (this.Count != null && this.Count.length == 16) {
//            for (int i = 0; i < 16; i++) {
//                if (this.Count[i] == 1) {
//                    this.Bookcase.renderbook(i);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//        switch (type) {
//            case INVENTORY:
//                renderBookcase(0, -0.5D, 0.3D,180.0D, item.getItemDamage(),true);
//                return;
//            case EQUIPPED_FIRST_PERSON:
//                renderBookcase(0.5D, 0, 0.5D, 90.0D, item.getItemDamage(),true);
//                return;
//            case EQUIPPED:
//                renderBookcase(0.5D, 0, 0.5D, 0, item.getItemDamage(),true);
//                return;
//            default:
//                renderBookcase(0, -0.5D, -0.25D, 0, item.getItemDamage(),true);
//        }
//    }
}
