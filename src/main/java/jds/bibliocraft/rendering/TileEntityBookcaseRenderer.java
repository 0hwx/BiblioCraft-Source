package jds.bibliocraft.rendering;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelBookcase;
import jds.bibliocraft.models.ModelFancyWorkbench;
import jds.bibliocraft.tileentities.BiblioTileEntity;

import jds.bibliocraft.tileentities.TileEntityBookcase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityBookcaseRenderer extends TileEntityBiblioRenderer implements IItemRenderer {
    private ModelBookcase Bookcase = new ModelBookcase();

    private int[] Count;
    private int meta;
    private int rotation;
    private  boolean createBookcase = false;

    public TileEntityBookcaseRenderer() {

    }
    @Override
    public void renderTileEntityAt(BiblioTileEntity tileEntity, double x, double y, double z, float tick) {
        TileEntityBookcase tile = (TileEntityBookcase) tileEntity;
        if (tile != null) {

            this.Count = tile.getCheckedBooks();
            this.meta = tile.getBlockMetadata();
            GL11.glPushMatrix();
            switch (this.getAngle()) {
                case WEST: // south
                    rotation = 0;
                    break;
                case SOUTH: // east
                    rotation = 90;
                    break;
                case EAST: // north
                    rotation = 180;
                    break;
                case NORTH: // west
                    rotation = 270;
                    break;
            }
            renderBookcase(x + 0.5D, y, z + 0.5D, rotation, this.meta, false);
            GL11.glPopMatrix();
        }
    }

    public void renderBookcase(double x, double y, double z, double rotate, int meta, boolean renderAllBooks) {
        GL11.glTranslated(x,y,z);
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        this.bindTexture(CommonProxy.VanilaPlankTexture(meta));
        this.Bookcase.renderBookcase();
        this.bindTexture(CommonProxy.BOOKCASE_BOOKS);
        if (renderAllBooks) {
            for (int i = 0; i < 16; i++) {
                this.Bookcase.renderbook(i);
            }
        } else if (this.Count != null && this.Count.length == 16) {
            for (int i = 0; i < 16; i++) {
                if (this.Count[i] == 1) {
                    this.Bookcase.renderbook(i);
                }
            }
        }
    }


    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderBookcase(0, -0.5D, -0.3D,180.0D, item.getItemDamage(),true);
                return;
            case EQUIPPED_FIRST_PERSON:
                renderBookcase(0.5D, 0, 0.5D, 90.0D, item.getItemDamage(),true);
                return;
            case EQUIPPED:
                renderBookcase(0.5D, 0, 1D, 0, item.getItemDamage(),true);
                return;
            default:
                renderBookcase(0, 0, 0.5D, 0, item.getItemDamage(),true);
        }
    }
}
