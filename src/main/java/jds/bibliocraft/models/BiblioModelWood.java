//package jds.bibliocraft.models;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.vecmath.Matrix4f;
//import javax.vecmath.Quat4f;
//import javax.vecmath.Vector3f;
//
//import com.google.common.base.Function;
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableMap;
//import com.google.common.collect.Lists;
//
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.client.renderer.texture.TextureMap;
//import net.minecraft.util.IIcon;
//import net.minecraft.util.ResourceLocation;
//
//import jds.bibliocraft.helpers.ModelCache;
//import net.minecraft.util.IIcon;
//import net.minecraftforge.client.model.AdvancedModelLoader;
//import net.minecraftforge.client.model.IModelCustom;
//import org.apache.commons.lang3.tuple.Pair;
//
//import jds.bibliocraft.blocks.base.BiblioWoodBlock;
//import jds.bibliocraft.blocks.base.BiblioWoodBlock.EnumWoodType;
//
//import jds.bibliocraft.states.TextureState;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.Direction;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.world.World;
//
//@SideOnly(Side.CLIENT)
//public abstract class BiblioModelWood implements IModelCustom {
//    private IModelCustom model = null;
//    private String modelLocation = " ";
//    private String textureLocation = "none";
//    private String customTextureLocation = "none";
//    private EnumWoodType wood = EnumWoodType.FRAME;
//    private ModelCache cache;
//    private boolean gotOBJ = false;
//    protected IIcon texture;
//
//    public BiblioModelWood(String modelLoc) {
//        this.modelLocation = modelLoc;
//        this.cache = new ModelCache();
//    }
//
//    private void setModel(int metadata, boolean isBlock, int attempt) {
//        if (this.model == null || (this.model != null && !this.model.getType().equals("obj"))) {
//            try {
//                this.model = AdvancedModelLoader.loadModel(new ResourceLocation(this.modelLocation));
//                gotOBJ = true;
//            } catch (Exception e) {
//                this.model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft:models/missing.obj"));
//                gotOBJ = false;
//                if (attempt < 6) {
//                    setModel(metadata, isBlock, attempt + 1);
//                    return;
//                }
//            }
//        }
//
//        if (metadata != -1) {
//            wood = EnumWoodType.getEnum(metadata);
//            loadAdditionalTextureData(metadata);
//        } else {
//            loadAdditionalTextureData(-1);
//        }
//
//        try {
//            switch (wood) {
//                case OAK:
//                    textureLocation = "textures/blocks/planks_oak";
//                    break;
//                case SPRUCE:
//                    textureLocation = "textures/blocks/planks_spruce";
//                    break;
//                case BIRCH:
//                    textureLocation = "textures/blocks/planks_birch";
//                    break;
//                case JUNGLE:
//                    textureLocation = "textures/blocks/planks_jungle";
//                    break;
//                case ACACIA:
//                    textureLocation = "textures/blocks/planks_acacia";
//                    break;
//                case DARKOAK:
//                    textureLocation = "textures/blocks/planks_big_oak";
//                    break;
//                case FRAME:
//                    if (customTextureLocation.contains("none") || customTextureLocation.contains("minecraft:white") || customTextureLocation.length() == 0) {
//                        textureLocation = "bibliocraft:textures/blocks/frame";
//                    } else {
//                        textureLocation = customTextureLocation;
//                    }
//                    break;
//                default:
//                    textureLocation = "textures/blocks/planks_oak";
//                    break;
//            }
//        } catch (NullPointerException e) {
//            System.out.println("Null pointer thrown on obtaining the texture " + e);
//        }
//
//        try {
//            if (cache.hasModel(textureLocation)) {
//                this.baseModel = cache.getCurrentMatch();
//            } else {
//                if (gotOBJ) {
//                    cache.addToCache(this.model, textureLocation);
//                }
//                this.baseModel = this.model;
//            }
//        } catch (NullPointerException e) {
//            System.out.println("null pointer exception thrown in attempt to load model(s) " + e);
//        }
//    }
//
//    public void loadAdditionalTextureData(int metadata) {
//    }
//
//    public abstract String getTextureLocation(String resourceLocation, String textureLocation);
//
//    public void registerIcons(IIconRegister register) {
//        this.texture = register.registerIcon(textureLocation);
//    }
//
//    public IIcon getIcon() {
//        return this.texture;
//    }
//
//    public boolean hasCustomInventoryRendering() {
//        return true;
//    }
//
//    public void renderItem() {
//        // Implement custom item rendering here
//    }
//
//    public void renderBlock() {
//        // Implement custom block rendering here
//    }
//
//    public IIcon getParticleIcon() {
//        try {
//            return this.texture != null ? this.texture :
//                ((TextureMap)Minecraft.getMinecraft().getTextureManager()
//                    .getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("textures/blocks/planks_oak");
//        } catch (NullPointerException e) {
//            return ((TextureMap)Minecraft.getMinecraft().getTextureManager()
//                .getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("textures/blocks/planks_oak");
//        }
//    }
//
//    protected void updateModel(ItemStack stack) {
//        if (stack != null) {
//            wood = EnumWoodType.getEnum(stack.getItemDamage());
//            customTextureLocation = "none";
//            NBTTagCompound tags = stack.getTagCompound();
//            if (tags != null && tags.hasKey("renderTexture")) {
//                customTextureLocation = tags.getString("renderTexture");
//            }
//            setModel(stack.getItemDamage(), false, 0);
//        }
//    }
//}
