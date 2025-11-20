package jds.bibliowoods.natura;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import jds.bibliocraft.blocks.BlockBookcase;
import jds.bibliocraft.blocks.BlockCase;
import jds.bibliocraft.blocks.BlockToolRack;
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
//		biblioLoaded = Loader.isModLoaded("bibliocraft");

		if (modloaded)
		{
			Block planks1 = Block.getBlockFromName("Natura:planks");
			Block slabs1 = Block.getBlockFromName("Natura:plankSlab1");
			Block slabs2 = Block.getBlockFromName("Natura:plankSlab2");
			Block[] planks = {planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1, planks1};
			int[] plankID = {0,1,2,3,4,5,6,7,8,9,10,11,12};
			Block[] slabs = {slabs1, slabs1, slabs1, slabs1, slabs1, slabs1, slabs1, slabs1, slabs2, slabs2, slabs2, slabs2,slabs2};
			int[] slabID = {0,1,2,3,4,5,6,7,0,1,2,3,4};

			String[] textures = {
                "natura:eucalyptus_planks",
                "natura:sakura_planks",
                "natura:ghostwood_planks",
                "natura:redwood_planks",
                "natura:bloodwood_planks",
                "natura:hopseed_planks",
                "natura:maple_planks",
                "natura:silverbell_planks",
                "natura:purpleheart_planks",
                "natura:tiger_planks",
                "natura:willow_planks",
                "natura:darkwood_planks",
                "natura:fusewood_planks"
			};
			ItemStack icon = new ItemStack(BlockToolRack.instance, 1, 6);

			NBTTagCompound tags = new NBTTagCompound();
			tags.setString("renderTexture", "natura:tiger_planks");
			icon.setTagCompound(tags);
            System.out.println("icon nbt = " + icon.getTagCompound());
			creativeTab = new BiblioWoodHelperTab("bibliowoodnaturatab", textures, icon);
			for (int i = 0; i < textures.length; i++)
			{
                RegisterCustomFramedBlocks reg = new RegisterCustomFramedBlocks(textures[i]);
                ItemStack plank = new ItemStack(planks[i], 1, plankID[i]);
                ItemStack slab = new ItemStack(slabs[i], 1, slabID[i]);
				reg.registerRecipies(plank, slab);
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
