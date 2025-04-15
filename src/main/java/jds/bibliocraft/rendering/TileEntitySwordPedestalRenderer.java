package jds.bibliocraft.rendering;

import jds.bibliocraft.tileentities.BiblioTileEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class TileEntitySwordPedestalRenderer extends TileEntityBiblioRenderer
{

	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
		renderSlotItem(tile.getStackInSlot(0), 0.5, 0.6, 0.5, 0.9f);

	}

	@Override
	public void additionalGLStuffForItemStack()
	{
		GL11.glRotatef(135, 0.0f, 0.0f, 1.0f);
	}

}
