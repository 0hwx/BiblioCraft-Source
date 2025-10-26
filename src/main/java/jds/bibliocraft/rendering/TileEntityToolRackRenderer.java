package jds.bibliocraft.rendering;


import jds.bibliocraft.tileentities.BiblioTileEntity;

public class TileEntityToolRackRenderer extends TileEntityBiblioRenderer
{
	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
        renderSlotItem(tile.getStackInSlot(0), 0.275, 0.615, 0.455, 0.8f);
        renderSlotItem(tile.getStackInSlot(1), 0.715, 0.615, 0.455, 0.8f);
        renderSlotItem(tile.getStackInSlot(2), 0.275, 0.175, 0.455, 0.8f);
        renderSlotItem(tile.getStackInSlot(3), 0.715, 0.175, 0.455, 0.8f);
	}
}
