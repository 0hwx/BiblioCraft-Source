package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.models.ModelCase;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityCase;
import org.lwjgl.opengl.GL11;


public class TileEntityCaseRenderer extends TileEntityBiblioRenderer
{
    private ModelCase model = new ModelCase();
    private int degreeAngle;
    private boolean openLid;
    private int meta;
    private int WoolColor;
	@Override
	public void renderTileEntityAt(BiblioTileEntity tileEntity, double x, double y, double z, float tick) {

        TileEntityCase tile = (TileEntityCase)tileEntity;
        this.openLid = tile.getOpenLid();
        this.meta = tile.getBlockMetadata();
        this.WoolColor = tile.getWoolColour();
        if (tile != null) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + (double) 0.5F, y, z + (double) 0.5F);
        switch (this.getAngle()) {
            case NORTH: // west
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                break;
            case SOUTH: //east
                GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
                break;
            case WEST://south
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                break;
            case EAST://north
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;
        }
        GL11.glTranslated(0.5F, 1.0F,  -0.5F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
//        this.bindTexture(WoolColourTexture(this.WoolColor));
        this.model.rendercase_inside();
//        this.bindTexture(BiblioWoodRegistry.getResource(meta));
        this.model.rendercase_bottom();

        if (this.openLid)
        {
//            this.bindTexture(BiblioWoodRegistry.getResource(meta));
            this.model.renderlid_wood_open();
            this.bindTexture(CommonProxy.GLASS);
            this.model.renderlid_glass_open();
            this.bindTexture(CommonProxy.IRON);
            this.model.renderlid_latch_open();
        }
        else
        {
//            this.bindTexture(BiblioWoodRegistry.getResource(meta));
            this.model.rendercase_lid_wood();
            this.bindTexture(CommonProxy.GLASS);
            this.model.rendercase_lid_glass();
            this.bindTexture(CommonProxy.IRON);
            this.model.rendercase_lid_latch();
        }
        GL11.glPopMatrix();
    }
		renderSlotItem(tile.getStackInSlot(0), 0,0,0, 0.5f);
        tile.getRenderBoundingBox();
	}

	@Override
	public void additionalGLStuffForItemStack()
	{
		if (getVertPosition() == EnumVertPosition.FLOOR)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslated(0.0, -0.3, 0.3);
		}
	}

}
