package jds.bibliowoods.botania;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Mod(modid=BiblioWoodsBotania.MODID, name="BiblioWoodBotania", version=BiblioWoodsBotania.VERSION, dependencies="after:bibliocraft; after:Botania")

public class BiblioWoodsBotania
{
    public static final String MODID = "bibliowoodsbotania";
    public static final String VERSION = "2.0";
    public static CreativeTabs creativeTab;

	public boolean modloaded = Loader.isModLoaded("Botania");
	public boolean biblioLoaded = Loader.isModLoaded("bibliocraft");

	@Mod.Instance(MODID)
	public static BiblioWoodsBotania instance;

	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		modloaded = Loader.isModLoaded("Botania");
		biblioLoaded = Loader.isModLoaded("bibliocraft");

		if (biblioLoaded && modloaded)
		{
			// TODO these work, but I am not sure how excited I am about botania?, maybe I could ask twitter what blocks i hsould do?
			Block planks = Block.getBlockFromName("Botania:shimmerwoodPlanks");
			Block planks2 = Block.getBlockFromName("Botania:livingwood");
			Block planks3 = Block.getBlockFromName("Botania:dreamwood");
			Block planks5 = Block.getBlockFromName("Botania:livingrock");
//            System.out.println("testBlock" + planks);
//            System.out.println("testBlock" + planks2);
//            System.out.println("testBlock" + planks3);
//            System.out.println("testBlock" + planks5);
			testBlock(planks);
			testBlock(planks2);
			testBlock(planks3);
			testBlock(planks5);
		}
		else
		{
			FMLLog.warning("BiblioWoods Botania edition failed to load");
			FMLLog.warning("Is BiblioCraft loaded?   "+biblioLoaded);
			FMLLog.warning("Is Botania loaded?   "+modloaded);
		}
	}

	public void testBlock(Block block)
	{
		if (block != null)
		{
			for (int i = 0; i < 16; i++)
			{
				ItemStack stack = new ItemStack(block, 1, i);
//				if (stack != null)
//					System.out.println("stack " + i + "   = " + stack.getDisplayName() + "   unlocalized = " + stack.getUnlocalizedName());
			}
		}
	}
}
