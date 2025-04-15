package jds.bibliowoods.natura;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import jds.bibliocraft.blocks.BlockBookcase;
import jds.bibliocraft.blocks.BlockCase;
import jds.bibliocraft.helpers.BiblioWoodHelperTab;
import jds.bibliocraft.helpers.RegisterCustomFramedBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


@Mod(modid=BiblioWoodsNatura.MODID, name="BiblioWoodNatura", version=BiblioWoodsNatura.VERSION, dependencies="after:bibliocraft; after:Natura")

public class BiblioWoodsNatura
{
    public static final String MODID = "bibliowoodsnatura";
    public static final String VERSION = "2.0";
    public static CreativeTabs creativeTab;

	public boolean modloaded = Loader.isModLoaded("Natura");
	public boolean biblioLoaded = Loader.isModLoaded("bibliocraft");

	@Mod.Instance(MODID)
	public static BiblioWoodsNatura instance;

	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		modloaded = Loader.isModLoaded("Natura");
		biblioLoaded = Loader.isModLoaded("bibliocraft");

		if (biblioLoaded && modloaded)
		{
			Block planks1 = Block.getBlockFromName("Natura:planks");
			Block slabs1 = Block.getBlockFromName("Natura:plankSlab1");
			Block slabs2 = Block.getBlockFromName("Natura:plankSlab2");
			Block[] planks = {planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1};
			int[] plankID = {0,1,2,3,4,5,6,7,8,9,10,11,12};
			Block[] slabs = {slabs1, slabs1, slabs1, slabs1, slabs1, slabs1, slabs1, slabs1, slabs2, slabs2, slabs2, slabs2,slabs2};
			int[] slabID = {0,1,2,3,4,5,6,7,0,1,2,3,4};

			String[] textures = {
					"natura:blocks/maple_planks",
					"natura:blocks/silverbell_planks",
					"natura:blocks/purpleheart_planks",
					"natura:blocks/tiger_planks",
					"natura:blocks/willow_planks",
					"natura:blocks/eucalyptus_planks",
					"natura:blocks/hopseed_planks",
					"natura:blocks/sakura_planks",
					"natura:blocks/redwood_planks",
					"natura:blocks/ghostwood_planks",
					"natura:blocks/bloodwood_planks",
					"natura:blocks/darkwood_planks",
					"natura:blocks/fusewood_planks"
			};
			ItemStack icon = new ItemStack(BlockCase.instance, 1, 6);
			NBTTagCompound tags = new NBTTagCompound();
			tags.setString("renderTexture", "natura:blocks/tiger_planks");
			icon.setTagCompound(tags);
			creativeTab = new BiblioWoodHelperTab("bibliowoodnaturatab", textures, icon.getItem());
			for (int i = 0; i < textures.length; i++)
			{
                ItemStack plank = new ItemStack(planks[i], 1, plankID[i]);
                ItemStack slab = new ItemStack(slabs[i], 1, slabID[i]);
				RegisterCustomFramedBlocks reg = new RegisterCustomFramedBlocks(textures[i]);
				reg.registerRecipies(plank, slab);
                    System.out.println("plank " + i + "   = " + plank.getDisplayName() + "   unlocalized = " + plank.getUnlocalizedName());
                    System.out.println("slab " + i + "   = " + slab.getDisplayName() + "   unlocalized = " + slab.getUnlocalizedName());
			}

		}
		else
		{
			FMLLog.warning("BiblioWoods Natura edition failed to load");
			FMLLog.warning("Is BiblioCraft loaded?   "+biblioLoaded);
			FMLLog.warning("Is Natura loaded?   "+modloaded);
		}
	}
}
