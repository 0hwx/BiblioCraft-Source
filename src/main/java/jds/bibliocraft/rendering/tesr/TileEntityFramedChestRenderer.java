package jds.bibliocraft.rendering.tesr;

import com.google.common.base.Function;
import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.blocks.base.BiblioWoodBlock.EnumWoodType;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityFramedChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TileEntityFramedChestRenderer extends TileEntityBiblioRenderer {

    private ResourceLocation modelLocation = new ResourceLocation("bibliocraft", "models/block/framedchest.obj");
    private IModelCustom chestModel = AdvancedModelLoader.loadModel(modelLocation);
    private String customTextureString = "none";
    private EnumWoodType wood = EnumWoodType.OAK;
    private TextureAtlasSprite chestSprite;

    @Override
    public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick) {
        if (!(tile instanceof TileEntityFramedChest chest)) return;

        getCustomTextureString(chest.getBlockMetadata(), chest.getCustomTextureString());

        float lid = chest.getPrevLidAngle() + (chest.getLidAngle() - chest.getPrevLidAngle()) * tick;
        lid = 1.0F - lid;
        lid = 1.0F - lid * lid * lid;
        lid = lid * 90.0f;

        if (chest.getIsDouble()) {
            if (chest.getIsLeft()) {
                renderPart("large_lid_left", 1.0, 0.625, 0.05, lid);
                renderPart("large_chest_left", 1.0, 0, 0, 0);

                bindTexture(CommonProxy.IRON);
                renderPart("latch", 1.5, 0.625, 0.05, lid);
            } else {
                renderPart("large_lid_right", 1.0, 0.625, 0.05, lid);
                renderPart("large_chest_right", 1.0, 0, 0, 0);
            }
        } else {
            renderPart("small_lid", 1.0, 0.625, 0.05, lid);
            renderPart("small_chest", 1.0, 0, 0, 0);

            bindTexture(CommonProxy.IRON);
            renderPart("latch", 1.0, 0.625, 0.05, lid);
        }

        renderSlotItem(chest.getLabelStack(), 0.5, 0.23, 0.93, 0.5f);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        if (type == ItemRenderType.ENTITY) {
            GL11.glTranslated(-0.5, 0.0, -0.5);
        }

        getCustomTextureFromStack(item);

        if (chestModel != null) {
            applySpriteUV();
            chestModel.renderPart("small_chest");

            GL11.glTranslated(0.05, 0.625, 0);
            chestModel.renderPart("small_lid");

            bindTexture(CommonProxy.IRON);
            chestModel.renderPart("latch");
            resetSpriteUV();
        }

        GL11.glPopMatrix();
    }

    private void getCustomTextureFromStack(ItemStack stack) {
        String textureName = "bibliocraft:frame"; // Default

        if (stack.hasTagCompound()) {
            NBTTagCompound tags = stack.getTagCompound();
            if (tags.hasKey("renderTexture")) {
                String savedTex = tags.getString("renderTexture");
                if (savedTex != null && !savedTex.isEmpty() && !savedTex.equals("none")) {
                    textureName = savedTex;
                }
            }
        }
        getCustomTextureString(stack.getItemDamage(), textureName);
    }

    private void getCustomTextureString(int meta, String TextureString) {
        wood = EnumWoodType.getEnum(meta);
        switch (wood) {
            case SPRUCE -> customTextureString = "minecraft:planks_spruce";
            case BIRCH -> customTextureString = "minecraft:planks_birch";
            case JUNGLE -> customTextureString = "minecraft:planks_jungle";
            case ACACIA -> customTextureString = "minecraft:planks_acacia";
            case DARKOAK -> customTextureString = "minecraft:planks_big_oak";
            case FRAME -> customTextureString = (TextureString.contains("none") || TextureString.isEmpty()) ? "bibliocraft:frame" : TextureString;
            default -> customTextureString = "minecraft:planks_oak";
        }

        bindChestTexture(customTextureString);
    }

    private void bindChestTexture(String customTextureString) {
        if (customTextureString == null || customTextureString.equals("none") || customTextureString.isEmpty()) {
            customTextureString = "minecraft:planks_oak";
        }

        chestSprite = Minecraft.getMinecraft()
            .getTextureMapBlocks()
            .getAtlasSprite(customTextureString);

        this.bindTexture(TextureMap.locationBlocksTexture);
    }

    private void renderPart(String modelpart, double x, double y, double z, float rotation) {
        switch (this.getAngle()) {
            case SOUTH -> {
                double tx = x;
                x = -z;
                z = tx;
            }
            case WEST -> {
                x *= -1;
                z *= -1;
            }
            case NORTH -> {
                double tx = x;
                x = z;
                z = -tx;
            }
            default -> {}
        }

        GL11.glPushMatrix();

        GL11.glTranslated(this.globalX + this.xshift + x, this.globalY + y, this.globalZ + this.zshift + z);
        GL11.glRotated(degreeAngle - 90.0f, 0.0F, 1.0F, 0.0F);
        GL11.glRotated(rotation, 0.0f, 0.0f, 1.0f);

        applySpriteUV();
        chestModel.renderPart(modelpart);
        resetSpriteUV();

        GL11.glPopMatrix();
    }

    private void applySpriteUV() {
        if (chestSprite == null) return;
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        GL11.glTranslatef(chestSprite.getMinU(), chestSprite.getMinV(), 0f);
        GL11.glScalef(chestSprite.getMaxU() - chestSprite.getMinU(), chestSprite.getMaxV() - chestSprite.getMinV(), 1f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    private void resetSpriteUV() {
        if (chestSprite == null) return;
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}
