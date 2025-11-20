package jds.bibliowoods.forestry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import jds.bibliocraft.blocks.BlockBookcase;
import jds.bibliocraft.helpers.BiblioWoodHelperTab;
import jds.bibliocraft.helpers.RegisterCustomFramedBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@Mod(modid=BiblioWoodsForestry.MODID, name="BiblioWoodForestry", version=BiblioWoodsForestry.VERSION, dependencies="after:bibliocraft; after:foresty")

public class BiblioWoodsForestry
{
    public static final String MODID = "bibliowoodsforestry";
    public static final String VERSION = "2.0";
    public static CreativeTabs creativeTab;

	public boolean modloaded = Loader.isModLoaded("Forestry");
	public boolean biblioLoaded = Loader.isModLoaded("bibliocraft");

	@Mod.Instance(MODID)
	public static BiblioWoodsForestry instance;

	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		modloaded = Loader.isModLoaded("Forestry");
		biblioLoaded = Loader.isModLoaded("bibliocraft");

		if (modloaded)
		{
			Block planks = Block.getBlockFromName("Forestry:planks");
			Block slabs = Block.getBlockFromName("Forestry:slabs");
			int[] plankID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
			int[] slabID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
			String[] textures = {
					"forestry:wood/planks.larch",
					"forestry:wood/planks.teak",
					"forestry:wood/planks.acacia",
					"forestry:wood/planks.lime",
					"forestry:wood/planks.chestnut",
					"forestry:wood/planks.wenge",
					"forestry:wood/planks.baobab",
					"forestry:wood/planks.sequoia",
					"forestry:wood/planks.kapok",
					"forestry:wood/planks.ebony",
					"forestry:wood/planks.mahogany",
					"forestry:wood/planks.balsa",
					"forestry:wood/planks.willow",
					"forestry:wood/planks.walnut",
					"forestry:wood/planks.greenheart",
					"forestry:wood/planks.cherry",
					"forestry:wood/planks.mahoe",
					"forestry:wood/planks.poplar",
					"forestry:wood/planks.palm",
					"forestry:wood/planks.papaya",
					"forestry:wood/planks.pine",
					"forestry:wood/planks.plum",
					"forestry:wood/planks.maple",
					"forestry:wood/planks.citrus",
					"forestry:wood/planks.giganteum",
					"forestry:wood/planks.ipe",
					"forestry:wood/planks.padauk",
					"forestry:wood/planks.cocobolo",
					"forestry:wood/planks.zebrawood"
			};

			ItemStack icon = new ItemStack(BlockBookcase.instance, 1, 6);
			NBTTagCompound tags = new NBTTagCompound();
			tags.setString("renderTexture", "forestry:wood/planks.cocobolo");
			icon.setTagCompound(tags);
			creativeTab = new BiblioWoodHelperTab("bibliowoodforestrytab", textures, icon);
			for (int i = 0; i < textures.length; i++)
			{
				RegisterCustomFramedBlocks reg = new RegisterCustomFramedBlocks(textures[i]);
                ItemStack plank = new ItemStack(planks, 1, plankID[i]);
                ItemStack slab = new ItemStack(slabs, 1, slabID[i]);
				reg.registerRecipies(plank, slab);
			}
		}
		else
		{
			FMLLog.warning("BiblioWoods Forestry edition failed to load");
			FMLLog.warning("Is BiblioCraft loaded?   "+biblioLoaded);
			FMLLog.warning("Is Forestry loaded?   "+modloaded);
		}
	}
}
