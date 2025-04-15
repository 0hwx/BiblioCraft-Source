package jds.bibliocraft.rendering;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelToolRack;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityToolRack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityToolRackRenderer extends TileEntityBiblioRenderer implements IItemRenderer
{
    private ModelToolRack toolRack = new ModelToolRack();
    private int meta;
    private int rotation;
	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
        TileEntityToolRack toolRack = (TileEntityToolRack) tile;
        if (toolRack != null)
        {
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
            renderToolRack(x + 0.5D, y, z + 0.5D, rotation, this.meta);
            GL11.glPopMatrix();
        }
		renderSlotItem(tile.getStackInSlot(0), 0.3, 0.55, 0.9, 0.6f);
		renderSlotItem(tile.getStackInSlot(1), 0.675, 0.55, 0.9, 0.6f);
		renderSlotItem(tile.getStackInSlot(2), 0.3, 0.175, 0.9, 0.6f);
		renderSlotItem(tile.getStackInSlot(3), 0.675, 0.175, 0.9, 0.6f);
	}

    public void renderToolRack(double x, double y, double z, double rotate, int meta) {
        GL11.glTranslated(x, y, z);
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        mc.renderEngine.bindTexture(CommonProxy.VanilaPlankTexture(meta));
        toolRack.renderbase();
        mc.renderEngine.bindTexture(CommonProxy.IRON);
        toolRack.rendernubs();
    }

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderToolRack(0, -0.5D, -0.3D,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON:
                renderToolRack(0.5D, 0, 0.5D, 90.0D, item.getItemDamage());
                return;
            case EQUIPPED:
                renderToolRack(0.5D, 0, 1D, 0, item.getItemDamage());
                return;
            default:
                renderToolRack(0.5D, 0, 0.5D, 0, item.getItemDamage());
        }
    }
}
