package jds.bibliocraft.rendering.tesr;

import com.google.common.base.Function;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelFramedChest;
import net.minecraft.block.Block;
import org.lwjgl.opengl.GL11;

import jds.bibliocraft.blocks.base.BiblioWoodBlock.EnumWoodType;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityFramedChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class TileEntityFramedChestRenderer extends TileEntityBiblioRenderer {
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
    public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick) {
        if (tile instanceof TileEntityFramedChest) {
            TileEntityFramedChest chest = (TileEntityFramedChest) tile;
            if (state == null) {
                state = chest.getWorldObj().getBlock(chest.xCoord, chest.yCoord, chest.zCoord);
            }
            GL11.glPushMatrix();
//            GL11.glTranslated(0.5F, 1.0F,  -0.5F);
//            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            initModels(chest);
            this.bindTexture(new ResourceLocation(customTextureString));
            float lid = chest.getPrevLidAngle() + (chest.getLidAngle() - chest.getPrevLidAngle()) * tick;
            lid = 1.0F - lid;
            lid = 1.0F - lid * lid * lid;
            lid = lid * 90.0f;
            renderPart(chest, 1,0,0, lid);
//			System.out.println(chest.getLidAngle());
//			if (chest.getIsDouble())
//			{
//				if (chest.getIsLeft())
//				{
////					renderPart(largeLidLeft, 1.0, 0.625, 0.05, lid);
////					renderPart(latch, 1.5, 0.625, 0.05, lid);
//				}
//				else
//				{
////					renderPart(largeLidRight, 1.0, 0.625, 0.05, lid);
//				}
//			}
//			else
//			{
//                String[] lidPart = {"small_lid", "small_lid_item"};
//				renderPart(lidPart, 1,  0.625, 0.05, lid);
////				renderPart("small_lid", 1,   0.625,  0.05, lid);
////				renderPart("latch", 1,  0.625,  0.05, lid);
//			}
            renderSlotItem(chest.getLabelStack(), x, y + 0.23, z + 0.93, 0.5f);
            GL11.glPopMatrix();
        }

    }

    private void renderFramedChest(double x, double y, double z, double rotate) {
        this.model.SmallChest();
        GL11.glRotated(rotate, 1.0D, 0.0D, 0.0D);
        this.model.SmallLidItem();
        this.bindTexture(CommonProxy.IRON);
        this.model.latchItem();

    }

    private ResourceLocation initModels(TileEntityFramedChest chest) {
        wood = EnumWoodType.getEnum(chest.getBlockMetadata());
        //customTextureString = ;
        switch (wood) {
            case OAK: {
                customTextureString = "textures/blocks/planks_oak.png";
                break;
            }
            case SPRUCE: {
                customTextureString = "textures/blocks/planks_spruce.png";
                break;
            }
            case BIRCH: {
                customTextureString = "textures/blocks/planks_birch.png";
                break;
            }
            case JUNGLE: {
                customTextureString = "textures/blocks/planks_jungle.png";
                break;
            }
            case ACACIA: {
                customTextureString = "textures/blocks/planks_acacia.png";
                break;
            }
            case DARKOAK: {
                customTextureString = "textures/blocks/planks_big_oak.png";
                break;
            }
            case FRAME: {
                if (chest.getCustomTextureString().contains("none") || chest.getCustomTextureString().contains("minecraft:white")) {
                    customTextureString = "bibliocraft:textures/blocks/frame";
                } else {
                    customTextureString = chest.getCustomTextureString();
                }
                break;
            }
            default: {
                customTextureString = "textures/blocks/planks_oak.png";
                break;
            }
        }

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

    protected Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
        @Override
        public TextureAtlasSprite apply(ResourceLocation location) {
            String returnValue = location.toString();
            if (returnValue.contentEquals("minecraft:blocks/planks_oak")) {
                returnValue = customTextureString;
            }
            return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(returnValue);
        }
    };


    private void renderPart(TileEntityFramedChest chest, double x, double y, double z, float rotation) {
        // Handle rotation based on angle
        switch (this.getAngle()) {
            case SOUTH: {
                double tx = x;
                x = -z;
                z = tx;
                break;
            }
            case WEST: {
                x *= -1;
                z *= -1;
                break;
            }
            case NORTH: {
                double tx = x;
                x = z;
                z = -tx;
                break;
            }
            case EAST:
            default:
                break;
        }

        // Setup transform
        GL11.glTranslated(this.globalX + this.xshift + x, this.globalY + y, this.globalZ + this.zshift + z);
        GL11.glRotatef(degreeAngle - 90.0f, 0.0F, 1.0F, 0.0F);

        // Render chest components
        if (chest.getIsDouble()) {
            if (chest.getIsLeft()) {
                this.model.LargeChestLeft();
                GL11.glRotatef(rotation, 0.0f, 0.0f, 1.0f);
                GL11.glTranslated(0.05, 0.62, 0.0);
                this.model.LargeLidLeft();
            } else {
                this.model.LargeChestRight();
                GL11.glRotatef(rotation, 0.0f, 0.0f, 1.0f);
                GL11.glTranslated(0.05, 0.62, 0.0);
                this.model.LargeLidRight();
                GL11.glTranslated(0.0, 0.0, 0.5);
                this.bindTexture(CommonProxy.IRON);
                this.model.latch();
            }
        } else {
            this.model.SmallChest();
            GL11.glRotatef(rotation, 0.0f, 0.0f, 1.0f);
            this.model.SmallLidItem();
            this.bindTexture(CommonProxy.IRON);
            this.model.latchItem();
        }
    }
}
