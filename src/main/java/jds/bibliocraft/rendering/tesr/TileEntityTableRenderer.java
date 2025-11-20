package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityTable;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class TileEntityTableRenderer extends TileEntityBiblioRenderer
{
	float xAdjust = 0.0f;
	float yAdjust = 0.0f;

	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
		if (tile instanceof TileEntityTable)
		{
			TileEntityTable table = (TileEntityTable)tile;
			ItemStack stack = table.getStackInSlot(0);
			xAdjust = table.getSlotX() * 90.0f;
			yAdjust = table.getSlotY() * 90.0f;
			if (table.getHasMap())
			{
				renderItemMap(stack, 0.1f, 0.0f, 0.1f, 1.0f);
			}
			else
			{
				renderSlotItem(stack, 0.5, 1.14, 0.5, 0.75f);
			}
		}

	}

	@Override
	public void additionalGLStuffForItemStack()
	{
		GL11.glRotatef(xAdjust, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(yAdjust, 0.0f, 1.0f, 0.0f);
	}

}
