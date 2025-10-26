package jds.bibliocraft.utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BiblioWoodRegistry {

    public static class WoodEntry {
        public final String name;
        public final String textureName;
        public final int meta;

        @SideOnly(Side.CLIENT)
        public IIcon icon;

        @SideOnly(Side.CLIENT)
        public ResourceLocation resource;

        public WoodEntry(String name, String domain, String path, String textureName, int meta) {
            this.name = name;
            this.textureName = textureName;
            this.meta = meta;
            this.resource = new ResourceLocation(domain, path + textureName +".png");
        }
    }

    // Map: meta -> WoodEntry
    private static final Map<Integer, WoodEntry> registeredWoods = new LinkedHashMap<>();

    // Fixed ranges per mod
    private static final int VANILLA_START = 0;
    private static final int BOP_START = 5;
    private static final int BOP_END = 21;
    private static final int FORESTRY_START = 22;
    private static final int FORESTRY_END = 51;
    private static final int BOTANIA_START = 52;
    private static final int BOTANIA_END = 55;

    public static void init() {
        // Vanilla woods
        addVanillaWood("oak", "minecraft", "textures/blocks/","planks_oak", VANILLA_START);
        addVanillaWood("spruce", "minecraft", "textures/blocks/","planks_spruce", VANILLA_START+1);
        addVanillaWood("birch", "minecraft", "textures/blocks/","planks_birch", VANILLA_START+2);
        addVanillaWood("jungle", "minecraft", "textures/blocks/","planks_jungle", VANILLA_START+3);

//        // Biomes O' Plenty woods
//        if (Loader.isModLoaded("BiomesOPlenty")) {
//            String[] bopTextures = {
//                "sacred_oak", "cherry", "umbran", "fir", "ethereal", "magic",
//                "mangrove", "palm", "redwood", "willow", "pine", "hellbark",
//                "jacaranda", "mahogany", "ebony", "eucalyptus"
//            };
//            for (int i = 0; i < bopTextures.length; i++) {
//                addWood("bop_" + bopTextures[i], "biomesoplenty:blocks/" + bopTextures[i] + "_planks", BOP_START + i);
//            }
//        }
//
//        // Forestry woods
        if (Loader.isModLoaded("Forestry")) {
            String[] forestry = {"acacia","balsa","baobab","cherry","citrus", "chestnut", "cocobolo", "ebony", "giganteum", "greenheart", "ipe", "kapok", "larch","lime","mahoe","maple","mahogany","padauk","palm","papaya","pine","plum","poplar","sequoia","teak","walnut","wenge","willow","zebrawood"};
            for (int i = 0; i < forestry.length; i++) {
                addWood("forestry_" + forestry[i],"forestry", "textures/blocks/", "wood/planks." + forestry[i], FORESTRY_START + i);
            }
        }
//
        // Botania woods
        if (Loader.isModLoaded("Botania")) {
            addWood("botania_dreamwood", "botania", "textures/blocks/","dreamwood1", BOTANIA_START + 1);
            addWood("botania_livingwood", "botania", "textures/blocks/","livingwood1", BOTANIA_START + 2);
            addWood("botania_shimmerwood", "botania", "textures/blocks/", "shimmerwoodPlanks", BOTANIA_START + 3);
//            String[] botania = {"dreamwood", "livingwood", "shimmerwood", "manawood"};
//            for (int i = 0; i < botania.length; i++) {
//                addWood("botania_" + botania[i], "botania:" + botania[i] + "1", BOTANIA_START + i);
//            }
        }
    }

    private static void addVanillaWood(String name, String domain, String path, String textureName, int meta) {
        addWood(name, domain, path, textureName ,meta);
    }

    private static void addWood(String name, String domain, String path, String textureName, int meta) {
        registeredWoods.put(meta, new WoodEntry(name, domain, path, textureName , meta));
    }

    public static Map<Integer, WoodEntry> getRegisteredWoods() {
        return registeredWoods;
    }

    public static WoodEntry getWood(int meta) {
        return registeredWoods.get(meta);
    }

    // === Client-side texture registration ===
    @SideOnly(Side.CLIENT)
    public static void registerIcons(IIconRegister register) {
        for (WoodEntry entry : registeredWoods.values()) {
            entry.icon = register.registerIcon(entry.resource.getResourceDomain() + ":" + entry.textureName);
        }
    }

    @SideOnly(Side.CLIENT)
    public static IIcon getIcon(int meta) {
        WoodEntry entry = getWood(meta);
        return (entry != null && entry.icon != null) ? entry.icon : null;
    }
    @SideOnly(Side.CLIENT)
    public static ResourceLocation getResource(int meta) {
        WoodEntry entry = getWood(meta);
        if (entry == null) return null;
        return (entry != null) ? entry.resource : null;
    }
}

