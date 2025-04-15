package jds.bibliocraft.rendering;

import java.util.ArrayList;
import java.util.List;

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
}
