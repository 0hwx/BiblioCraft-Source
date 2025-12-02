package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.models.ModelFancyWorkbench;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;

public class TileEntityFancyWorkbenchRenderer extends TileEntityBiblioRenderer  {

    @Override
    public void renderTileEntityAt(BiblioTileEntity tileEntity, double x, double y, double z, float tick) {
            renderSlotItem(tileEntity.getStackInSlot(0), 0.2, 1.01, 0.4, 0.8f,90.0F);
        }

    @Override
    public void additionalGLStuffForItemStack () {}

}
