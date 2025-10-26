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


public class TileEntityPotionShelfRenderer extends TileEntityBiblioRenderer implements IItemRenderer
{
    private ModelPotionShelf potionShelf = new ModelPotionShelf();
    private int meta;
    private int rotation;
    @Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
        TileEntityPotionShelf tilePotionShelf = (TileEntityPotionShelf) tile;
        if (tilePotionShelf != null)
        {
            this.meta = tilePotionShelf.getBlockMetadata();
            GL11.glPushMatrix();
            switch (this.getAngle()) {
                case WEST: // south
                    rotation = 0;
                    xshift -= 0.5f;
                    zshift -= 0.5f;
                    break;
                case SOUTH: // east
                    rotation = 90;
                    xshift -= 0.5f;
                    zshift += 0.5f;
                    break;
                case EAST: // north
                    rotation = 180;
                    xshift += 0.5f;
                    zshift += 0.5f;
                    break;
                case NORTH: // west
                    rotation = 270;
                    xshift += 0.5f;
                    zshift -= 0.5f;
                    break;
            }
            renderPotionShelf(x + xshift, y, z + zshift, rotation, this.meta);
            GL11.glPopMatrix();
        }
		renderSlotItem(tile.getStackInSlot(0), -0.35, 0.785, -0.25, 0.72f);
		renderSlotItem(tile.getStackInSlot(1), -0.125, 0.785, -0.24, 0.72f);
		renderSlotItem(tile.getStackInSlot(2), 0.1, 0.785, -0.25, 0.72f);
		renderSlotItem(tile.getStackInSlot(3), 0.325, 0.785, -0.24, 0.72f);

		renderSlotItem(tile.getStackInSlot(4), -0.35, 0.44, -0.24, 0.72f);
		renderSlotItem(tile.getStackInSlot(5), -0.125, 0.44, -0.25, 0.72f);
		renderSlotItem(tile.getStackInSlot(6), 0.1, 0.44, -0.24, 0.72f);
		renderSlotItem(tile.getStackInSlot(7), 0.325, 0.44, -0.25, 0.72f);

		renderSlotItem(tile.getStackInSlot(8),  -0.35, 0.1, -0.25, 0.72f);
		renderSlotItem(tile.getStackInSlot(9),  -0.125, 0.1, -0.24, 0.72f);
		renderSlotItem(tile.getStackInSlot(10), 0.1, 0.1, -0.25, 0.72f);
		renderSlotItem(tile.getStackInSlot(11), 0.325, 0.1, -0.24, 0.72f);
	}

    public void renderPotionShelf(double x, double y, double z, double rotate, int meta) {
        GL11.glTranslated(x, y, z);
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        mc.renderEngine.bindTexture(BiblioWoodRegistry.getResource(meta));
        potionShelf.renderPotionShelf();
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
                renderPotionShelf(0, -0.5D, 0.3D,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON:
                renderPotionShelf(0.5D, 0, 0.5D, 90.0D, item.getItemDamage());
                return;
            case EQUIPPED:
                renderPotionShelf(0.5D, 0, 0.5D, 0, item.getItemDamage());
                return;
            default:
                renderPotionShelf(0, -0.5D, -0.25D, 0, item.getItemDamage());
        }
    }
}
