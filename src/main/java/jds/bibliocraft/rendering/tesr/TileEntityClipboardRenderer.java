package jds.bibliocraft.rendering.tesr;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelClipboard;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityClipboard;
import org.lwjgl.opengl.GL11;

public class TileEntityClipboardRenderer extends TileEntityBiblioRenderer
{
    private ModelClipboard model = new ModelClipboard();
    private int angle = 0;
    private int button0state = 0;
    private int button1state = 0;
    private int button2state = 0;
    private int button3state = 0;
    private int button4state = 0;
    private int button5state = 0;
    private int button6state = 0;
    private int button7state = 0;
    private int button8state = 0;
    private int button9state = 0;
    private double textSpacing = -0.0658;
    private float boxSpacing = -0.0658F;

	@Override
	public void renderTileEntityAt(BiblioTileEntity tileEntity, double x, double y, double z, float tick)
	{
		TileEntityClipboard tile = (TileEntityClipboard)tileEntity;
        if (tile != null) {
            this.angle = tile.getAngleID();
            this.button0state = tile.button0state;
            this.button1state = tile.button1state;
            this.button2state = tile.button2state;
            this.button3state = tile.button3state;
            this.button4state = tile.button4state;
            this.button5state = tile.button5state;
            this.button6state = tile.button6state;
            this.button7state = tile.button7state;
            this.button8state = tile.button8state;
        }

        GL11.glPushMatrix();
        GL11.glTranslated(x + (double)0.5F, y, z + (double)0.5F);
        switch (this.angle) {
            case 0:
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 1:
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 2:
            default:
                break;
            case 3:
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                break;
        }

        GL11.glTranslated((double)-0.5F, (double)0.0F, (double)-0.5F);
        this.bindTexture(CommonProxy.CLIPBOARD_BLOCK);
        this.model.renderClipboard();
        switch (this.button0state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox1c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox1x();
                break;
        }


        switch (this.button1state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox2c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox2x();
        }


        switch (this.button2state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox3c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox3x();
        }


        switch (this.button3state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox4c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox4x();
        }


        switch (this.button4state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox5c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox5x();
        }


        switch (this.button5state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox6c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox6x();
        }


        switch (this.button6state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox7c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox7x();
        }


        switch (this.button7state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox8c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox8x();
        }


        switch (this.button8state) {
            case 1:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_CHECK);
                this.model.renderbox9c();
                break;
            case 2:
                this.bindTexture(CommonProxy.CLIPBOARD_BOX_X);
                this.model.renderbox9x();
        }

        GL11.glPopMatrix();
        if (tile != null)
		{
			renderText(tile.titletext, 0.037, 0.825, 0.27);
			renderText(tile.button0text, 0.037, 0.76, 0.222);
			renderText(tile.button1text, 0.037, 0.76+(1*textSpacing), 0.222);
			renderText(tile.button2text, 0.037, 0.76+(2*textSpacing), 0.222);
			renderText(tile.button3text, 0.037, 0.76+(3*textSpacing), 0.222);
			renderText(tile.button4text, 0.037, 0.76+(4*textSpacing), 0.222);
			renderText(tile.button5text, 0.037, 0.76+(5*textSpacing), 0.222);
			renderText(tile.button6text, 0.037, 0.76+(6*textSpacing), 0.222);
			renderText(tile.button7text, 0.037, 0.76+(7*textSpacing), 0.222);
			renderText(tile.button8text, 0.037, 0.76+(8*textSpacing), 0.222);
			String pageNum = ""+tile.currentPage;
			if (tile.currentPage > 9)
			{
				renderText(pageNum, 0.037, 0.17, 0.03);
			}
			else
			{
				renderText(pageNum, 0.037, 0.17, 0.02);
			}
		}
	}
}
