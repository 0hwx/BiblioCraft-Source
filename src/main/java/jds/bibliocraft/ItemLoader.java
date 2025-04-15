package jds.bibliocraft;

import cpw.mods.fml.common.registry.GameRegistry;
import jds.bibliocraft.helpers.RecipeBiblioAtlas;
import jds.bibliocraft.items.ItemAtlas;
import jds.bibliocraft.items.ItemAtlasPlate;
import jds.bibliocraft.items.ItemBigBook;
import jds.bibliocraft.items.ItemChase;
import jds.bibliocraft.items.ItemClipboard;
import jds.bibliocraft.items.ItemDeathCompass;
import jds.bibliocraft.items.ItemDrill;
import jds.bibliocraft.items.ItemEnchantedPlate;
import jds.bibliocraft.items.ItemFramingBoard;
import jds.bibliocraft.items.ItemFramingSaw;
import jds.bibliocraft.items.ItemFramingSheet;
import jds.bibliocraft.items.ItemHandDrill;
import jds.bibliocraft.items.ItemLock;
import jds.bibliocraft.items.ItemMapTool;
import jds.bibliocraft.items.ItemNameTester;
import jds.bibliocraft.items.ItemPaintingCanvas;
import jds.bibliocraft.items.ItemPlate;
import jds.bibliocraft.items.ItemPlumbLine;
import jds.bibliocraft.items.ItemReadingGlasses;
import jds.bibliocraft.items.ItemRecipeBook;
import jds.bibliocraft.items.ItemRedstoneBook;
import jds.bibliocraft.items.ItemSeatBack;
import jds.bibliocraft.items.ItemSeatBack2;
import jds.bibliocraft.items.ItemSeatBack3;
import jds.bibliocraft.items.ItemSeatBack4;
import jds.bibliocraft.items.ItemSeatBack5;
import jds.bibliocraft.items.ItemSlottedBook;
import jds.bibliocraft.items.ItemStockroomCatalog;
import jds.bibliocraft.items.ItemTape;
import jds.bibliocraft.items.ItemTapeMeasure;
import jds.bibliocraft.items.ItemWaypointCompass;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.RecipeSorter;


public class ItemLoader
{
	public static Enchantment readingChant;
	public static Enchantment deathCompChant;




	public static void initItems()
	{

		// OBJ models
		if (Config.enableWaypointCompass)
		{
			GameRegistry.registerItem(ItemWaypointCompass.instance, ItemWaypointCompass.name);
		}
		if (Config.enableMapFrame)
		{
            GameRegistry.registerItem(ItemMapTool.instance, ItemMapTool.name);
		}
		if (Config.enableSeat)
		{
            GameRegistry.registerItem(ItemSeatBack.instance, ItemSeatBack.name);
            GameRegistry.registerItem(ItemSeatBack2.instance, ItemSeatBack2.name);
            GameRegistry.registerItem(ItemSeatBack3.instance, ItemSeatBack3.name);
            GameRegistry.registerItem(ItemSeatBack4.instance, ItemSeatBack4.name);
            GameRegistry.registerItem(ItemSeatBack5.instance, ItemSeatBack5.name);
		}

		// Vanilla models
		if (Config.enableStockroomCatalog)
		{
            GameRegistry.registerItem(ItemStockroomCatalog.instance, ItemStockroomCatalog.name);
		}

		if (Config.enablePlumbLine)
		{
            GameRegistry.registerItem(ItemPlumbLine.instance, ItemPlumbLine.name);

		}

		if (Config.enableFurniturePaneler)
		{
            GameRegistry.registerItem(ItemFramingSaw.instance, ItemFramingSaw.name);
            GameRegistry.registerItem(ItemFramingBoard.instance, ItemFramingBoard.name);
            GameRegistry.registerItem(ItemFramingSheet.instance, ItemFramingSheet.name);
		}

		if (Config.enableTesterItem)
		{
            GameRegistry.registerItem(ItemNameTester.instance, ItemNameTester.name);

		}

		if (Config.enableAtlas)
		{
            GameRegistry.registerItem(ItemAtlas.instance, ItemAtlas.name);
            GameRegistry.registerItem(ItemAtlasPlate.instance, ItemAtlasPlate.name);
		}

		if (Config.enableAtlas && Config.enableDeathCompass)
		{
            GameRegistry.registerItem(ItemDeathCompass.instance, ItemDeathCompass.name);
			//deathCompChant = new EnchantmentDeathCompass();
		}

		if (Config.enablePainting)
		{
            GameRegistry.registerItem(ItemPaintingCanvas.instance, ItemPaintingCanvas.name);

		}
		if (Config.enableBigBook)
		{
            GameRegistry.registerItem(ItemBigBook.instance, ItemBigBook.name);

		}

		if (Config.enableFancyWorkbench)
		{
            GameRegistry.registerItem(ItemRecipeBook.instance, ItemRecipeBook.name);
		}

		if (Config.enableSlottedBook)
		{
            GameRegistry.registerItem(ItemSlottedBook.instance, ItemSlottedBook.name);
		}

		if (Config.enableHandDrill)
		{
            GameRegistry.registerItem(ItemHandDrill.instance, ItemHandDrill.name);
		}

		if (Config.enableTapemeasure)
		{
            GameRegistry.registerItem(ItemTapeMeasure.instance, ItemTapeMeasure.name);
            GameRegistry.registerItem(ItemTape.instance, ItemTape.name);
		}
		if (Config.enablePrintpressTypeMachine)
		{
            GameRegistry.registerItem(ItemChase.instance, ItemChase.name);
            GameRegistry.registerItem(ItemPlate.instance, ItemPlate.name);
            GameRegistry.registerItem(ItemEnchantedPlate.instance, ItemEnchantedPlate.name);
		}
		if (Config.enableRedstonebook)
		{
            GameRegistry.registerItem(ItemRedstoneBook.instance, ItemRedstoneBook.name);
		}
		if (Config.enableReadingglasses)
		{
            GameRegistry.registerItem(ItemReadingGlasses.instance, ItemReadingGlasses.name);
		}
		if (Config.enableDrill)
		{
            GameRegistry.registerItem(ItemDrill.instance, ItemDrill.name);
		}
		if (Config.enableLock)
		{
            GameRegistry.registerItem(ItemLock.instance, ItemLock.name);
		}
		if (Config.enableClipboard)
		{
            GameRegistry.registerItem(ItemClipboard.instance, ItemClipboard.name);
		}
		//readingChant = new EnchantmentReading();
		//Enchantment.addToBookList(readingChant);


	}

//	public static void addRecipies(RegistryEvent.Register<IRecipe> event)
//	{
//
//		ItemStack enchantedbook = new ItemStack(Items.enchanted_book, 1, 0);
//		ItemStack enchantedreadingbook = new ItemStack(Items.enchanted_book, 1, 0);
//		if (Config.enableAtlas)
//		{
//			if (Config.enableDeathCompass && Config.enableWaypointCompass)
//			{
//				ResourceLocation regName = new ResourceLocation("bibliocraft:enchantedatlas");
//				ResourceLocation regNameb = new ResourceLocation("bibliocraft:enchantedatlasalt");
//				ItemStack waypointCompass = new ItemStack(ItemWaypointCompass.instance, 1, 0);
//				ItemStack enderPearl = new ItemStack(Items.ENDER_PEARL, 1, 0);
//				RecipeSorter.register("bibliocraft:enchantedatlas", RecipeBiblioAtlas.class, RecipeSorter.Category.SHAPED, "");
//				IRecipe recipea = RecipeBiblioAtlas.addAtlasEnchantRecipe(new ItemStack(ItemAtlas.instance, 1, 0), "PBP", "CAC", "PBP", 'P', enderPearl, 'B', enchantedbook, 'C', waypointCompass, 'A', new ItemStack(ItemAtlas.instance));
//				recipea.setUnlocalizedName(regName);
//				event.getRegistry().register(recipea);
//				IRecipe recipeb =  RecipeBiblioAtlas.addAtlasEnchantRecipe(new ItemStack(ItemAtlas.instance, 1, 0), "PCP", "BAB", "PCP", 'P', enderPearl, 'B', enchantedbook, 'C', waypointCompass, 'A', new ItemStack(ItemAtlas.instance));
//				recipeb.setUnlocalizedName(regNameb);
//				event.getRegistry().register(recipeb);
//			}
//		}
//
//	}
}
