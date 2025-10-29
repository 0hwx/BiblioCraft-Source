package jds.bibliocraft.rendering;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelFancyWorkbench;
import jds.bibliocraft.models.ModelPotionShelf;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityPotionShelf;
import jds.bibliocraft.utils.BiblioWoodRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;


public class TileEntityPotionShelfRenderer extends TileEntityBiblioRenderer
{
    private ModelPotionShelf potionShelf = new ModelPotionShelf();
    private int meta;
    private int rotation;
    @Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
//        TileEntityPotionShelf tilePotionShelf = (TileEntityPotionShelf) tile;
//        if (tilePotionShelf != null)
//        {
//            this.meta = tilePotionShelf.getBlockMetadata();
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
//            renderLabel(x + xshift, y, z + zshift, rotation, this.meta);
//            GL11.glPopMatrix();
//        }
		renderSlotItem(tile.getStackInSlot(0), 0.16, 0.8055, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(1), 0.38, 0.8055, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(2), 0.595, 0.8055, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(3), 0.815, 0.8055, 0.15, 0.6f);

		renderSlotItem(tile.getStackInSlot(4), 0.16, 0.495, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(5), 0.38, 0.495, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(6), 0.595, 0.495, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(7), 0.815, 0.495, 0.15, 0.6f);

		renderSlotItem(tile.getStackInSlot(8),  0.16, 0.18, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(9),  0.38, 0.18, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(10), 0.595, 0.18, 0.15, 0.6f);
		renderSlotItem(tile.getStackInSlot(11), 0.815, 0.18, 0.15, 0.6f);
	}
//
//    public void renderLabel(double x, double y, double z, double rotate, int meta) {
//        GL11.glTranslated(x, y, z);
//        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
////        mc.renderEngine.bindTexture(BiblioWoodRegistry.getResource(meta));
//        potionShelf.renderLabel();
//    }
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
//                renderLabel(0, -0.5D, 0.3D,180.0D, item.getItemDamage());
//                return;
//            case EQUIPPED_FIRST_PERSON:
//                renderLabel(0.5D, 0, 0.5D, 90.0D, item.getItemDamage());
//                return;
//            case EQUIPPED:
//                renderLabel(0.5D, 0, 0.5D, 0, item.getItemDamage());
//                return;
//            default:
//                renderLabel(0, -0.5D, -0.25D, 0, item.getItemDamage());
//        }
//    }
}
