package jds.bibliocraft.rendering;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelClipboard;
import jds.bibliocraft.models.ModelFramedChest;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import jds.bibliocraft.blocks.BiblioWoodBlock.EnumWoodType;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityFramedChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class TileEntityFramedChestRenderer extends TileEntityBiblioRenderer
{
//	private IBakedModel smallLid;
//	private IBakedModel largeLidLeft;
//	private IBakedModel largeLidRight;
//	private IBakedModel latch;
    private ModelFramedChest model = new ModelFramedChest();
    private String customTextureString = "none";
	private EnumWoodType wood = EnumWoodType.OAK;
	private ResourceLocation modelLocation = new ResourceLocation("bibliocraft:block/framedchest.obj");
	private Block state;

	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
		if (tile instanceof TileEntityFramedChest)
		{
			TileEntityFramedChest chest = (TileEntityFramedChest)tile;
			if (state == null)
			{
				state = chest.getWorldObj().getBlock(chest.xCoord, chest.yCoord, chest.zCoord);
			}
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
            initModels(chest);
            bindTexture(CommonProxy.TEST_BLOCK);
            this.model.renderFramedChest();


			float lid = chest.getPrevLidAngle() + (chest.getLidAngle() - chest.getPrevLidAngle()) * tick;
			lid = 1.0F - lid;
			lid = 1.0F - lid * lid * lid;
			lid = lid * 90.0f;
			//System.out.println(chest.getLidAngle());
//			if (chest.getIsDouble())
//			{
//				if (chest.getIsLeft())
//				{
//					renderPart(largeLidLeft, 1.0, 0.625, 0.05, lid);
//					renderPart(latch, 1.5, 0.625, 0.05, lid);
//				}
//				else
//				{
//					renderPart(largeLidRight, 1.0, 0.625, 0.05, lid);
//				}
//			}
//			else
//			{
//				renderPart(smallLid, 1.0, 0.625, 0.05, lid);
//				renderPart(latch, 1.0, 0.625, 0.05, lid);
//			}
			renderSlotItem(chest.getLabelStack(), 0.5, 0.23, 0.93, 0.5f);
            GL11.glPopMatrix();
		}

	}

	private ResourceLocation initModels(TileEntityFramedChest chest)
	{
		wood = EnumWoodType.getEnum(chest.getBlockMetadata());
		//customTextureString = ;
		switch (wood)
		{
			case OAK: {customTextureString = "minecraft:blocks/planks_oak"; break;}
			case SPRUCE: {customTextureString = "minecraft:blocks/planks_spruce"; break;}
			case BIRCH: {customTextureString = "minecraft:blocks/planks_birch"; break;}
			case JUNGLE: {customTextureString = "minecraft:blocks/planks_jungle"; break;}
			case ACACIA: {customTextureString = "minecraft:blocks/planks_acacia"; break;}
			case DARKOAK: {customTextureString = "minecraft:blocks/planks_big_oak"; break;}
			case FRAME:
			{
				if (chest.getCustomTextureString().contains("none") || chest.getCustomTextureString().contains("minecraft:white"))
				{
					customTextureString = "bibliocraft:blocks/frame";
				}
				else
				{
					customTextureString = chest.getCustomTextureString();
				}
				break;
			}
			default: {customTextureString = "minecraft:blocks/planks_oak"; break;}
		}

//		IModel model = null;
//		try
//		{
//			model = ModelLoaderRegistry.getModel(modelLocation);
//		}
//		catch (Exception e)
//		{
//
//			model = ModelLoaderRegistry.getMissingModel();
//		}
//		model = model.process(ImmutableMap.of("flip-v", "true"));
//		List<String> smallPart = new ArrayList<String>();
//		smallPart.add("small_lid");
//		List<String> largeLeftPart = new ArrayList<String>();
//		largeLeftPart.add("large_lid_left");
//		List<String> largeRightPart = new ArrayList<String>();
//		largeRightPart.add("large_lid_right");
//		List<String> latchPart = new ArrayList<String>();
//		latchPart.add("latch");
//		OBJModel.OBJState smallState = new OBJModel.OBJState(smallPart, true);
//		OBJModel.OBJState largeLeftState = new OBJModel.OBJState(largeLeftPart, true);
//		OBJModel.OBJState largeRightState = new OBJModel.OBJState(largeRightPart, true);
//		OBJModel.OBJState latchState = new OBJModel.OBJState(latchPart, true);
//		smallLid = model.bake(smallState,  Attributes.DEFAULT_BAKED_FORMAT, textureGetter);
//		largeLidLeft = model.bake(largeLeftState,  Attributes.DEFAULT_BAKED_FORMAT, textureGetter);
//		largeLidRight = model.bake(largeRightState,  Attributes.DEFAULT_BAKED_FORMAT, textureGetter);
//		latch = model.bake(latchState,  Attributes.DEFAULT_BAKED_FORMAT, textureGetter);
        return null;
    }

	protected Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>()
	{
		@Override
		public TextureAtlasSprite apply(ResourceLocation location)
		{
			String returnValue = location.toString();
			if (returnValue.contentEquals("minecraft:blocks/planks_oak"))
			{
				returnValue = customTextureString;
			}
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(returnValue);
		}
	};


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
//		for (BakedQuad quad :  model.getQuads(null, null, 0))
//		{
//			LightUtil.renderQuadColor(worldRenderer, quad, 0xFFFFFFFF);
//		}
//		tessellator.draw();
//		RenderHelper.enableStandardItemLighting();
//		GL11.glPopMatrix();
//	}
}
