package jds.bibliocraft.rendering;

import java.util.ArrayList;
import java.util.List;

import jds.bibliocraft.Config;
import jds.bibliocraft.helpers.EnumShiftPosition;
import jds.bibliocraft.tileentities.TileEntityFancySign;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import jds.bibliocraft.helpers.BiblioEnums.EnumBiblioPaintings;
import jds.bibliocraft.helpers.PaintingUtil;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityPaintPress;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityPainting.EnumArt;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TileEntityPaintPressRenderer extends TileEntityBiblioRenderer
{
	private ResourceLocation modelLocation = new ResourceLocation("bibliocraft:block/paintpress.obj");
	private EnumArt[] vanillaArtList = EnumArt.values();
	private EnumBiblioPaintings[] biblioArtList = EnumBiblioPaintings.values();
//	private IBakedModel lid;
//	private IBakedModel canvas;
	private ItemStack painting = null;
	private ItemStack prevPainting = null;
	public static final ResourceLocation PAINTINGCANVAS = new ResourceLocation("bibliocraft", "textures/paintings/canvas.png");
	public static final ResourceLocation PAINTINGSHEET = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
	private String paintingTitle = "blank";
	private String paintingString = "bibliocraft:paintings/canvas";
	private boolean updatePainting = true;
	private Block state;

	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
		TileEntityPaintPress press = (TileEntityPaintPress)tile;
		if (state == null)
		{
			state = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
		}
		paintingTitle = press.getPaintingTitle();
		painting = tile.getStackInSlot(0);
//		if (lid == null)
//		{
//			List<String> lidPart = new ArrayList<String>();
//			lidPart.add("lid");
//			lid = initModel(lidPart, modelLocation);
//		}

		paintingString = "bibliocraft:paintings/canvas";
		if (painting != null)
		{
			if (press.getPaintingType() == 0)
			{
				for (int i = 0; i < this.biblioArtList.length; i++)
				{
					if (this.paintingTitle.contentEquals(this.biblioArtList[i].title))
					{
						paintingString = this.biblioArtList[i].paintingTexturesStrings[0][0];
					}
				}
			}
			else if (press.getPaintingType() == 2)
			{
				if (PaintingUtil.customArtNames != null && PaintingUtil.customArtNames.length > 0 && PaintingUtil.customArtResources != null)
				{
					for (int i = 0; i < PaintingUtil.customArtNames.length; i++)
					{
						if (this.paintingTitle.contentEquals(PaintingUtil.customArtNames[i]))
						{
							paintingString = PaintingUtil.customArtResourceStrings[i];
						}
					}
				}
			}
		}
		List<String> canvasPart  = new ArrayList<String>();
		canvasPart.add("painting");
//		canvas = initModel(canvasPart, modelLocation);
		prevPainting = painting;


		bindTexture(TextureMap.locationBlocksTexture);
//		renderPart(lid, 1.0, 0.42, 0.0, -press.lidAngle);

		if (painting != null)
		{
			if (press.getPaintingType() == 1)
			{
				bindTexture(PAINTINGSHEET);
				GL11.glPushMatrix();
				RenderHelper.disableStandardItemLighting();
				for (int i = 0; i<vanillaArtList.length; i++)
				{
					if (this.paintingTitle.contentEquals(vanillaArtList[i].title))
					{
						drawVanillaPainting(x, y, z, i);
					}
				}
				RenderHelper.enableStandardItemLighting();
				GL11.glPopMatrix();
			}
			else if (press.getPaintingType() == 2)
			{
				GL11.glPushMatrix();
				RenderHelper.disableStandardItemLighting();
				for (int i = 0; i < PaintingUtil.customArtNames.length; i++)
				{
					if (this.paintingTitle.contentEquals(PaintingUtil.customArtNames[i]))
					{
						bindTexture(PaintingUtil.customArtResources[i]);
						drawCustomPainting(x, y, z, i);
					}
				}
				RenderHelper.enableStandardItemLighting();
				GL11.glPopMatrix();
			}
			else
			{
//				renderPart(canvas, 1.0, 0.0, 0.0, 0.0f);
			}
		}
	}

	@Override
	public String getTextureString(ResourceLocation location)
	{
		String output = location.toString();
		if (output.contains("canvas"))
		{
			output = paintingString;
		}
		return output;
	}

	public void drawCustomPainting(double x, double y, double z, int e) /// hmmm
	{
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        float x1 = 0.0f;
        float x2 = 1.0f;
        float y1 = 0.0f;
        float y2 = 1.0f;
		switch (this.getAngle())
		{
			case SOUTH:
			{
                tessellator.addVertexWithUV(x+0.5+0.4, y+0.5-0.126, z+0.5-0.4, x1, y1);
                tessellator.addVertexWithUV(x+0.5-0.4, y+0.5-0.126, z+0.5-0.4, x1, y2);
                tessellator.addVertexWithUV(x+0.5-0.4, y+0.5-0.126, z+0.5+0.4, x2, y2);
                tessellator.addVertexWithUV(x+0.5+0.4, y+0.5-0.126, z+0.5+0.4, x2, y1);
				break;
			}
			case WEST:
			{
                tessellator.addVertexWithUV(x+0.5+0.4, y+0.5-0.126, z+0.5-0.4,x1, y2);
                tessellator.addVertexWithUV(x+0.5-0.4, y+0.5-0.126, z+0.5-0.4,x2, y2);
                tessellator.addVertexWithUV(x+0.5-0.4, y+0.5-0.126, z+0.5+0.4,x2, y1);
                tessellator.addVertexWithUV(x+0.5+0.4, y+0.5-0.126, z+0.5+0.4,x1, y1);
				break;
			}
			case NORTH:
			{
                tessellator.addVertexWithUV(x+0.5+0.4, y+0.5-0.126, z+0.5-0.4,x2, y2);
                tessellator.addVertexWithUV(x+0.5-0.4, y+0.5-0.126, z+0.5-0.4,x2, y1);
                tessellator.addVertexWithUV(x+0.5-0.4, y+0.5-0.126, z+0.5+0.4,x1, y1);
                tessellator.addVertexWithUV(x+0.5+0.4, y+0.5-0.126, z+0.5+0.4,x1, y2);
				break;
			}
			case EAST:
			{
                tessellator.addVertexWithUV(x+0.5+0.4, y+0.5-0.126, z+0.5-0.4,x2, y1);
                tessellator.addVertexWithUV(x+0.5-0.4, y+0.5-0.126, z+0.5-0.4,x1, y1);
                tessellator.addVertexWithUV(x+0.5-0.4, y+0.5-0.126, z+0.5+0.4,x1, y2);
                tessellator.addVertexWithUV(x+0.5+0.4, y+0.5-0.126, z+0.5+0.4,x2, y2);
				break;
			}
			default: break;
		}
		tessellator.draw();
	}
	// */
	public void drawVanillaPainting(double i, double j, double k, int x)
	{
        float x1 = (float)(vanillaArtList[x].offsetX) / 256.0F;
        float x2 = (float)(vanillaArtList[x].offsetX + vanillaArtList[x].sizeX) / 256.0F;
        float y1 = (float)(vanillaArtList[x].offsetY) / 256.0F;
        float y2 = (float)(vanillaArtList[x].offsetY + vanillaArtList[x].sizeY) / 256.0F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
		switch (this.getAngle())
		{
			case SOUTH:
			{
                tessellator.addVertexWithUV(i+0.5+0.4, j+0.5-0.126, k+0.5-0.4, x1, y1);
                tessellator.addVertexWithUV(i+0.5-0.4, j+0.5-0.126, k+0.5-0.4, x1, y2);
                tessellator.addVertexWithUV(i+0.5-0.4, j+0.5-0.126, k+0.5+0.4, x2, y2);
                tessellator.addVertexWithUV(i+0.5+0.4, j+0.5-0.126, k+0.5+0.4, x2, y1);
				break;
			}
			case WEST:
			{
                tessellator.addVertexWithUV(i+0.5+0.4, j+0.5-0.126, k+0.5-0.4,x1, y2);
                tessellator.addVertexWithUV(i+0.5-0.4, j+0.5-0.126, k+0.5-0.4,x2, y2);
                tessellator.addVertexWithUV(i+0.5-0.4, j+0.5-0.126, k+0.5+0.4,x2, y1);
                tessellator.addVertexWithUV(i+0.5+0.4, j+0.5-0.126, k+0.5+0.4,x1, y1);
				break;
			}
			case NORTH:
			{
                tessellator.addVertexWithUV(i+0.5+0.4, j+0.5-0.126, k+0.5-0.4,x2, y2);
                tessellator.addVertexWithUV(i+0.5-0.4, j+0.5-0.126, k+0.5-0.4,x2, y1);
                tessellator.addVertexWithUV(i+0.5-0.4, j+0.5-0.126, k+0.5+0.4,x1, y1);
                tessellator.addVertexWithUV(i+0.5+0.4, j+0.5-0.126, k+0.5+0.4,x1, y2);
				break;
			}
			case EAST:
			{
                tessellator.addVertexWithUV(i+0.5+0.4, j+0.5-0.126, k+0.5-0.4,x2, y1);
                tessellator.addVertexWithUV(i+0.5-0.4, j+0.5-0.126, k+0.5-0.4,x1, y1);
                tessellator.addVertexWithUV(i+0.5-0.4, j+0.5-0.126, k+0.5+0.4,x1, y2);
                tessellator.addVertexWithUV(i+0.5+0.4, j+0.5-0.126, k+0.5+0.4,x2, y2);
				break;
			}
			default: break;
		}
		tessellator.draw();
	}


//	private void renderPart(IBakedModel model, double x, double y, double z, float rotation)
//	{
//		switch (this.getAngle())
//		{
//			case SOUTH:
//			{
//				double tx = x;
//				x = -z;
//				z = tx;
//				break;
//			}
//			case WEST:
//			{
//				x *= -1;
//				z *= -1;
//				break;
//			}
//			case NORTH:
//			{
//				double tx = x;
//				x = z;
//				z = -tx;
//				break;
//			}
//			case EAST:
//			{
//				break;
//			}
//			default: break;
//		}
//
//		GL11.glPushMatrix();
//		RenderHelper.disableStandardItemLighting();
//		GL11.glTranslated(this.globalX + this.xshift + x, this.globalY + y, this.globalZ + this.zshift + z);
//		GL11.glRotatef(degreeAngle - 90.0f, 0.0F, 1.0F, 0.0F);
//		GL11.glRotatef(rotation, 0.0f, 0.0f, 1.0f);
//	    worldRenderer.begin(GL11.GL_QUADS, Attributes.DEFAULT_BAKED_FORMAT);
//	    for (BakedQuad quad :  model.getQuads(null, null, 0))
//		{
//			LightUtil.renderQuadColor(worldRenderer, quad, 0xFFFFFFFF);
//		}
//		tessellator.draw();
//		RenderHelper.enableStandardItemLighting();
//		GL11.glPopMatrix();
//	}

    public static class TileEntityFancySignRenderer extends TileEntityBiblioRenderer
    {
        /*
        private int s1scale = 1;
        private int s1rot = 0;
        private int s1x = 0;
        private int s1y = 0;
        private int s2scale = 1;
        private int s2rot = 0;
        private int s2x = 0;
        private int s2y = 0;
        */
        private int slotx = 0;
        private int sloty = 0;
        private int slotScale = 1;
        private int slotRot = 0;
        private ItemStack slot;

        private int[] textscale = new int[15];
        //private String[] text = new String[15];
        //private int numOfLines = 15;
        private int linespace = 0;
        private int scaledLinesNumber[] = {16, 11, 7, 5, 4, 3};
        private float[] scalesText = {1.0f, 1.5f, 2.0f, 3.0f, 4.0f, 5.0f};
        private float[] antiScalesText = {(1.0f/scalesText[0]),(1.0f/scalesText[1]),(1.0f/scalesText[2]),(1.0f/scalesText[3]),(1.0f/scalesText[4]),(1.0f/scalesText[5])};
        private double[] xScaleOffset = {0.0, 0.008, 0.016, 0.024, 0.032, 0.04, 0.048, 0.056, 0.064};
        private double[] yScaleOffset = {0.0, 0.02, 0.0415, 0.062, 0.083, 0.104, 0.124, 0.146, 0.167};
        private float[] itemScale = {0.16f, 0.285f, 0.41f, 0.535f, 0.66f, 0.785f, 0.91f, 1.035f, 1.0f};
        private int textLine = 0;

        @Override
        public void renderTileEntityAt(BiblioTileEntity tileEntity, double x, double y, double z, float tick)
        {
            if (tileEntity instanceof TileEntityFancySign)
            {
                float[] itemScaleTEST = 	{0.08f, 0.16f, 	0.24f, 	0.31f, 0.39f, 0.47f, 0.55f, 0.64f, 0.71f};
                double[] xScaleOffsetTEST = {0.02, 	0.015, 	0.023, 	0.029, 0.031, 0.018, 0.022, 0.03,  0.03};
                double[] yScaleOffsetTEST = {0.008, 	0.01, 	0.02, 	0.025, 0.03,  0.03,  0.04,  0.05,  0.05};
                TileEntityFancySign tile = (TileEntityFancySign)tileEntity;
                textscale = tile.textScale;
                slot = tile.getStackInSlot(0);
                slotx = tile.slot1X;
                sloty = tile.slot1Y;
                slotScale = tile.slot1Scale;
                slotRot = tile.slot1Rot;
                renderSlotItem(slot,
                        0.503f + slotx * 0.004f + xScaleOffsetTEST[slotScale],
                        0.445f - sloty * 0.004f - yScaleOffsetTEST[slotScale],
                        0.06,
                        itemScaleTEST[slotScale]);
                slot = tile.getStackInSlot(1);
                slotx = tile.slot2X;
                sloty = tile.slot2Y;
                slotScale = tile.slot2Scale;
                slotRot = tile.slot2Rot;
                renderSlotItem(slot,
                        0.503f + slotx * 0.004f + xScaleOffsetTEST[slotScale],
                        0.445f - sloty * 0.004f - yScaleOffsetTEST[slotScale],
                        0.06,
                        itemScaleTEST[slotScale]);
                linespace = 0;

                for (int n = 0; n < tile.numOfLines; n++)
                {
                    textLine = n;
                    renderText(tile.text[n], 0.047, 0.737 - linespace * 0.004, 0.4325);
                    linespace += (int)8*(16.0f/scaledLinesNumber[tile.textScale[n]]);
                }
            }
        }

        @Override
        public void additionalGLStuffForItemStack()
        {
            if (getShiftPosition() == EnumShiftPosition.HALF_SHIFT)
            {
                GL11.glTranslated(0.0f, 0.0f, -0.18f);
            }
            else if (getShiftPosition() == EnumShiftPosition.FULL_SHIFT)
            {
                GL11.glTranslated(0.0f, 0.0f, -0.425f);
            }
            GL11.glScalef(1.0f, 1.0f, 0.01f);
            if (Config.isBlock(slot))
            {
                if (slotRot == 0)
                {
                    GL11.glRotatef(45, -0.52f, 1.0f, -0.2f);
                }
                else if (slotRot == 2)
                {
                    GL11.glRotatef(-45, 0.52f, 1.0f, -0.2f);
                }
            }
            else
            {
                if (slotRot == 0)
                {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
            }

        }

        @Override
        public void additionalGLStuffForText()
        {
            GL11.glScaled(scalesText[textscale[textLine]] * 0.89, scalesText[textscale[textLine]] * 0.89, 1.0);
        }
    }
}
