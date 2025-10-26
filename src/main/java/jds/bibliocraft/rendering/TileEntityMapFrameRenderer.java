package jds.bibliocraft.rendering;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityMapFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;

public class TileEntityMapFrameRenderer extends TileEntityBiblioRenderer
{
	private ResourceLocation modelLocation = new ResourceLocation("bibliocraft:block/mapframe.obj");
//	private IBakedModel pin;
//	private IBlockState state;

	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{

		ItemStack stack = tile.getStackInSlot(0);
//		if (pin == null)
//		{
//			//initModel();
//			List<String> part = new ArrayList<String>();
//			part.add("pin");
//			pin = initModel(part, modelLocation);
//		}

		if (stack != null && tile instanceof TileEntityMapFrame)
		{
			TileEntityMapFrame frame = (TileEntityMapFrame)tile;
//			if (state == null)
//			{
//				state = frame.getWorld().getBlockState(frame.getPos());
//			}
			renderFramedItemMap(stack, frame.mapRotation);
			for (int i = 0; i < frame.getPinXCoords().size(); i++)
			{
				float xValue = (Float)frame.getPinYCoords().get(i);
				float yValue = (Float)frame.getPinXCoords().get(i);
				switch (this.getVertPosition())
				{
					case FLOOR: { xValue = 1.0f - xValue; yValue = 1.0f - yValue; break; }
					case WALL:
					{
						if (this.getAngle() == ForgeDirection.SOUTH || this.getAngle() == ForgeDirection.EAST)
						{
							float tx = xValue;
							xValue = yValue;
							yValue = tx;
						}
						else
						{
							float tx = xValue;
							xValue = 1.0f - yValue;
							yValue = tx;
						}
						break;
					}
					case CEILING: { yValue = 1.0f - yValue; break; }
				}
				bindTexture(getColorTexture((Float)frame.getPinColors().get(i)));
//				renderPin(pin, xValue, yValue);
			}
		}
	}

	public ResourceLocation getColorTexture(float color)
	{
		int colorint = (int)color;
		switch (colorint)
		{
			case 0:return CommonProxy.BLACKWOOL;
			case 1:return CommonProxy.REDWOOL;
			case 2:return CommonProxy.GREENWOOL;
			case 3:return CommonProxy.LIMEWOOL;
			case 4:return CommonProxy.BROWNWOOL;
			case 5:return CommonProxy.BLUEWOOL;
			case 6:return CommonProxy.CYANWOOL;
			case 7:return CommonProxy.LBLUEWOOL;
			case 8:return CommonProxy.PURPLEWOOL;
			case 9:return CommonProxy.MAGENTAWOOL;
			case 10:return CommonProxy.PINKWOOL;
			case 11:return CommonProxy.YELOOWWOOL;
			case 12:return CommonProxy.ORANGEWOOL;
			case 13:return CommonProxy.GRAYWOOL;
			case 14:return CommonProxy.LGRAYWOOL;
			case 15:return CommonProxy.WHITEWOOL;
			default:return CommonProxy.REDWOOL;
		}
	}

//	private void renderPin(IBakedModel model, double x, double y)
//	{
//		double z = 0.0;
//		boolean isWall = this.getVertPosition() == EnumVertPosition.WALL;
//		if (!isWall)
//		{
//			z = y;
//			y = 0.0;
//		}
//
//		switch (this.getAngle())
//		{
//			case SOUTH:
//			{
//				double tx = x;
//				x = -z;
//				z = tx;
//				if (this.getVertPosition() == EnumVertPosition.FLOOR)
//				{
//					y = -y;
//					z = 1 - z;
//				}
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
//		GL11.glRotatef(degreeAngle, 0.0F, 1.0F, 0.0F);
//
//		switch (this.getVertPosition())
//		{
//			case FLOOR:
//			{
//				GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
//				break;
//			}
//			case WALL:
//			{
//				GL11.glRotatef(90.0f, 0.0f, -1.0f, 0.0f);
//				break;
//			}
//			case CEILING:
//			{
//				GL11.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
//				GL11.glTranslated(-1.0, 0.0, 0.0);
//				break;
//			}
//		}
//
//	    worldRenderer.begin(GL11.GL_QUADS, Attributes.DEFAULT_BAKED_FORMAT);
//	    for (BakedQuad quad :  model.getQuads(null, null, 0))
//		{
//			LightUtil.renderQuadColor(worldRenderer, quad, 0xFFFFFFFF);
//		}
//		tessellator.draw();
//		RenderHelper.enableStandardItemLighting();
//		GL11.glPopMatrix();
//	}

	private void renderFramedItemMap(ItemStack stack, int rotation)
    {
    	GL11.glPushMatrix();
    	float rot = 0.0f;
    	float xAdjust = 0.0f;
    	float yAdjust = 0.0f;
    	float zAdjust = 0.0f;
		switch (rotation)
    	{
    		case 0:
	    	{
	    		rot = 180.0f;
	    		switch (this.getVertPosition())
	    		{
	    			case FLOOR: { rot = 0.0f; zAdjust = 0.0f; break; }
	    			case WALL:
	    			{
	    				switch (this.getAngle())
	    				{
		    				case SOUTH: { yAdjust = 1.0f; zAdjust = -1.0f; break; }
		    				case WEST: { xAdjust = 1.0f; yAdjust = 1.0f; break; }
		    				case NORTH: { yAdjust = 1.0f; zAdjust = 1.0f; break; }
		    				case EAST: { xAdjust = -1.0f; yAdjust = 1.0f; break; }
		    				default: break;
	    				}
	    				break;
	    			}
	    			case CEILING: { rot = 0.0f; xAdjust = 0.0f; break; }
	    			default: break;
	    		}
	    		break;
	    	}
	    	case 1:
	    	{
	    		rot = 90.0f;
	    		switch (this.getVertPosition())
	    		{
	    			case FLOOR: { rot = 270.0f; xAdjust = 1.0f; zAdjust = 0.0f; break; }
	    			case WALL:
	    			{
	    				switch (this.getAngle())
	    				{
	    					case SOUTH: { rot = 270.0f; yAdjust = 1.0f; break; }
		    				case WEST: { rot = 270.0f; yAdjust = 1.0f; break; }
		    				case NORTH: { rot = 90.0f; yAdjust = 1.0f; break; }
		    				case EAST: { rot = 90.0f; yAdjust = 1.0f; break; }
		    				default: break;
	    				}
	    				break;
	    			}
	    			case CEILING: { rot = 90.0f; xAdjust = 1.0f; break; }
	    			default: break;
	    		}

	    		break;
	    	}
	    	case 2:
	    	{

	    		switch (this.getVertPosition())
	    		{
	    			case FLOOR: { rot = 180.0f; xAdjust = 1.0f; zAdjust = 1.0f; break; }
	    			case CEILING: { rot = 180.0f; xAdjust = 1.0f; zAdjust = -1.0f; break; }
	    			default: break;
	    		}
	    		break;
    		}
	    	case 3:
	    	{
	    		rot = 270.0f;
	    		switch (this.getVertPosition())
	    		{
	    			case FLOOR: { rot = 90.0f; zAdjust = 1.0f; break; }
	    			case WALL:
	    			{
	    				switch (this.getAngle())
	    				{
	    					case SOUTH: { rot = 90.0f; zAdjust = -1.0f; break; }
		    				case WEST: { rot = 90.0f; xAdjust = 1.0f; break; }
		    				case NORTH: { rot = 270.0f; zAdjust = 1.0f; break; }
		    				case EAST: { rot = 270.0f; xAdjust = -1.0f; break; }
		    				default: break;
	    				}
	    				break;
	    			}
	    			case CEILING: { rot = 270.0f; xAdjust = 0.0f; zAdjust = -1.0f; break; }
	    			default: break;
	    		}

	    		break;
	    	}
    	}

    	switch (this.getVertPosition())
		{
			case FLOOR:
			{
				GL11.glTranslated(this.globalX + xAdjust, this.globalY + 0.035f + yAdjust, this.globalZ + zAdjust);
				GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
				break;
			}
			case WALL:
			{
				switch (this.getAngle())
				{
					case SOUTH:
					{
						GL11.glTranslated(this.globalX + 0.965f + xAdjust, this.globalY + 0.0f + yAdjust, this.globalZ + 1.0 + zAdjust);
						GL11.glRotatef(rot, 1.0f, 0.0f, 0.0f);
						GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
						break;
						}
					case WEST:
					{
						GL11.glTranslated(this.globalX + xAdjust, this.globalY + 0.0f + yAdjust, this.globalZ + 0.965 + zAdjust);
						GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
						break;
					}
					case NORTH:
					{
						GL11.glTranslated(this.globalX + 0.035 + xAdjust, this.globalY + yAdjust + 0.0f, this.globalZ + zAdjust);
						GL11.glRotatef(rot, 1.0f, 0.0f, 0.0f);
						GL11.glRotatef(90.0f, 0.0f, -1.0f, 0.0f);
						break;
					}
					case EAST:
					{
						GL11.glTranslated(this.globalX + 1.0 + xAdjust, this.globalY + 0.0f + yAdjust, this.globalZ + 0.035 + zAdjust);
						GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
						GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
						break;
					}
					default: break;
				}

				break;
			}
			case CEILING:
			{

				GL11.glTranslated(this.globalX + xAdjust, this.globalY + 0.965 + yAdjust, this.globalZ + 1.0 + zAdjust);
				GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);

				break;
			}
		}
    	GL11.glScalef(0.00782f,  0.00782f,  0.00782f);
        RenderHelper.disableStandardItemLighting();
        MapData mapdata =Items.filled_map.getMapData(stack, this.mc.theWorld);
        if (mapdata != null)
        {
            this.mc.entityRenderer.getMapItemRenderer().func_148250_a(mapdata, false);
        }
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
    }

//	private void initModel()
//	{
//		IModel model = null;
//		try
//		{
//			model = ModelLoaderRegistry.setModel(modelLocation);
//		}
//		catch (Exception e)
//		{
//
//			model = ModelLoaderRegistry.getMissingModel();
//		}
//		model = model.process(ImmutableMap.of("flip-v", "true"));
//
//		List<String> part = new ArrayList<String>();
//		part.add("pin");
//		OBJModel.OBJState state = new OBJModel.OBJState(part, true);
//		pin = model.bake(state,  Attributes.DEFAULT_BAKED_FORMAT, textureGetter);
//	}

	protected Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>()
	{
		@Override
		public TextureAtlasSprite apply(ResourceLocation location)
		{
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/planks_oak");
		}
	};
}
