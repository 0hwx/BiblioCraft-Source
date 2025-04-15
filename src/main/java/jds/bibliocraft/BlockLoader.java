package jds.bibliocraft;

import cpw.mods.fml.common.registry.GameRegistry;
import jds.bibliocraft.blocks.BlockArmorStand;
import jds.bibliocraft.blocks.BlockBell;
import jds.bibliocraft.blocks.BlockBookcase;
import jds.bibliocraft.blocks.BlockBookcaseCreative;
import jds.bibliocraft.blocks.BlockCase;
import jds.bibliocraft.blocks.BlockClipboard;
import jds.bibliocraft.blocks.BlockClock;
import jds.bibliocraft.blocks.BlockCookieJar;
import jds.bibliocraft.blocks.BlockDesk;
import jds.bibliocraft.blocks.BlockDinnerPlate;
import jds.bibliocraft.blocks.BlockDiscRack;
import jds.bibliocraft.blocks.BlockFancySign;
import jds.bibliocraft.blocks.BlockFancyWorkbench;
import jds.bibliocraft.blocks.BlockFramedChest;
import jds.bibliocraft.blocks.BlockFurniturePaneler;
import jds.bibliocraft.blocks.BlockLabel;
import jds.bibliocraft.blocks.BlockLampGold;
import jds.bibliocraft.blocks.BlockLampIron;
import jds.bibliocraft.blocks.BlockLanternGold;
import jds.bibliocraft.blocks.BlockLanternIron;
import jds.bibliocraft.blocks.BlockMapFrame;
import jds.bibliocraft.blocks.BlockMarkerPole;
import jds.bibliocraft.blocks.BlockPaintingFrameBorderless;
import jds.bibliocraft.blocks.BlockPaintingFrameFancy;
import jds.bibliocraft.blocks.BlockPaintingFrameFlat;
import jds.bibliocraft.blocks.BlockPaintingFrameMiddle;
import jds.bibliocraft.blocks.BlockPaintingFrameSimple;
import jds.bibliocraft.blocks.BlockPaintingPress;
import jds.bibliocraft.blocks.BlockPotionShelf;
import jds.bibliocraft.blocks.BlockPrintingPress;
import jds.bibliocraft.blocks.BlockSeat;
import jds.bibliocraft.blocks.BlockShelf;
import jds.bibliocraft.blocks.BlockSwordPedestal;
import jds.bibliocraft.blocks.BlockTable;
import jds.bibliocraft.blocks.BlockToolRack;
import jds.bibliocraft.blocks.BlockTypeWriter;
import jds.bibliocraft.blocks.BlockTypesettingTable;
import jds.bibliocraft.blocks.blockitems.BiblioWoodBlockItem;
import jds.bibliocraft.blocks.blockitems.BlockItemArmorStand;
import jds.bibliocraft.blocks.blockitems.BlockItemBookcase;
import jds.bibliocraft.blocks.blockitems.BlockItemBookcaseCreative;
import jds.bibliocraft.blocks.blockitems.BlockItemCase;
import jds.bibliocraft.blocks.blockitems.BlockItemClock;
import jds.bibliocraft.blocks.blockitems.BlockItemDesk;
import jds.bibliocraft.blocks.blockitems.BlockItemFancySign;
import jds.bibliocraft.blocks.blockitems.BlockItemFancyWorkbench;
import jds.bibliocraft.blocks.blockitems.BlockItemFramedChest;
import jds.bibliocraft.blocks.blockitems.BlockItemFurniturePaneler;
import jds.bibliocraft.blocks.blockitems.BlockItemLabel;
import jds.bibliocraft.blocks.blockitems.BlockItemLamp;
import jds.bibliocraft.blocks.blockitems.BlockItemLantern;
import jds.bibliocraft.blocks.blockitems.BlockItemMapFrame;
import jds.bibliocraft.blocks.blockitems.BlockItemPaintingFrameBorderless;
import jds.bibliocraft.blocks.blockitems.BlockItemPaintingFrameFancy;
import jds.bibliocraft.blocks.blockitems.BlockItemPaintingFrameFlat;
import jds.bibliocraft.blocks.blockitems.BlockItemPaintingFrameMiddle;
import jds.bibliocraft.blocks.blockitems.BlockItemPaintingFrameSimple;
import jds.bibliocraft.blocks.blockitems.BlockItemPotionShelf;
import jds.bibliocraft.blocks.blockitems.BlockItemSeat;
import jds.bibliocraft.blocks.blockitems.BlockItemShelf;
import jds.bibliocraft.blocks.blockitems.BlockItemSwordPedestal;
import jds.bibliocraft.blocks.blockitems.BlockItemTable;
import jds.bibliocraft.blocks.blockitems.BlockItemToolRack;
import jds.bibliocraft.blocks.blockitems.BlockItemTypewriter;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class BlockLoader
{
    public static String[] dyes =
    {
        "dyeWhite",
        "dyeLightGray",
        "dyeGray",
        "dyeBlack",
        "dyeRed",
        "dyeOrange",
        "dyeYellow",
        "dyeLime",
        "dyeGreen",
        "dyeCyan",
        "dyeLightBlue",
        "dyeBlue",
        "dyePurple",
        "dyeMagenta",
        "dyePink",
        "dyeBrown"
    };

    public static String[] dyes2 =
    {
        "dyeBlack",
        "dyeRed",
        "dyeGreen",
        "dyeBrown",
        "dyeBlue",
        "dyePurple",
        "dyeCyan",
        "dyeLightGray",
        "dyeGray",
        "dyePink",
        "dyeLime",
        "dyeYellow",
        "dyeLightBlue",
        "dyeMagenta",
        "dyeOrange",
        "dyeWhite"

    };

	public static int creativetabID = CreativeTabs.getNextID();

	public static final CreativeTabs biblioTab = new BiblioTab("BiblioCraft");
	public static CreativeTabs biblioLightsTab;// = new BiblioLightsTab("BiblioCraftLights");
	public static void initLightTab()
	{
		biblioLightsTab = new BiblioLightsTab("BiblioCraftLights");
	}

	/** The number of woods starting at 0 so easy use in loops.  */
	public final static int NUMBER_OF_WOODS = 6;



	/** BiblioBlocks */
	/*
	@ObjectHolder(BlockBookcase.name)
	public static final Block bookcase_block = BlockBookcase.instance;
	@ObjectHolder(BlockBookcase.name)
	public static final Item bookcase_item = BlockItemBookcase.instance;
	*/



	public static void initBlocks()
	{
		if (Config.enableBookcase)
		{
            GameRegistry.registerBlock(BlockBookcase.instance, BlockItemBookcase.class ,BlockBookcase.name);
            GameRegistry.registerBlock(BlockBookcaseCreative.instance, BlockItemBookcaseCreative.class, BlockBookcaseCreative.name);
		}
		if (Config.enableGenericshelf)
		{
            GameRegistry.registerBlock(BlockShelf.instance,BlockItemShelf.class, BlockShelf.name);
		}
		if (Config.enableTapemeasure)
		{
            GameRegistry.registerBlock(BlockMarkerPole.instance, BlockMarkerPole.name);
		}
		if (Config.enableClipboard)
		{
            GameRegistry.registerBlock(BlockClipboard.instance, BlockClipboard.name);
		}

		if (Config.enableLantern)
		{
//            GameRegistry.registerBlock(BlockLanternGold.instance,BlockItemLantern.class, BlockLanternGold.name);
//            GameRegistry.registerBlock(BlockLanternIron.instance,BlockItemLantern.class, BlockLanternIron.name);
		}
		if (Config.enableLamp)
		{
//            GameRegistry.registerBlock(BlockLampGold.instance, BlockItemLamp.class, BlockLampGold.name);
//            GameRegistry.registerBlock(BlockLampIron.instance, BlockItemLamp.class, BlockLampIron.name);
		}
		if (Config.enableFurniturePaneler)
		{
            GameRegistry.registerBlock(BlockFurniturePaneler.instance,BlockItemFurniturePaneler.class, BlockFurniturePaneler.name);
		}
		if (Config.enableFramedChest)
		{
            GameRegistry.registerBlock(BlockFramedChest.instance,BlockItemFramedChest.class, BlockFramedChest.name);
		}
		if (Config.enableFancySign)
		{
            GameRegistry.registerBlock(BlockFancySign.instance,BlockItemFancySign.class, BlockFancySign.name);
		}
		if (Config.enableFancyWorkbench)
		{
            GameRegistry.registerBlock(BlockFancyWorkbench.instance,BlockItemFancyWorkbench.class, BlockFancyWorkbench.name);
		}
		if (Config.enablePotionshelf)
		{
            GameRegistry.registerBlock(BlockPotionShelf.instance,BlockItemPotionShelf.class, BlockPotionShelf.name);
		}
		if (Config.enableToolrack)
		{
            GameRegistry.registerBlock(BlockToolRack.instance,BlockItemToolRack.class, BlockToolRack.name);
		}
		if (Config.enableWoodLabel)
		{
            GameRegistry.registerBlock(BlockLabel.instance,BlockItemLabel.class, BlockLabel.name);
		}
		if (Config.enableWritingdesk)
		{
            GameRegistry.registerBlock(BlockDesk.instance,BlockItemDesk.class, BlockDesk.name);
		}
		if (Config.enableTable)
		{
            GameRegistry.registerBlock(BlockTable.instance,BlockItemTable.class, BlockTable.name);
		}
		if (Config.enableSeat)
		{
            GameRegistry.registerBlock(BlockSeat.instance,BlockItemSeat.class, BlockSeat.name);
		}

		if (Config.enableClock)
		{
            GameRegistry.registerBlock(BlockClock.instance,BlockItemClock.class, BlockClock.name);
		}
		if (Config.enableWeaponcase)
		{
            GameRegistry.registerBlock(BlockCase.instance,BlockItemCase.class, BlockCase.name);
		}
		if (Config.enableMapFrame)
		{
            GameRegistry.registerBlock(BlockMapFrame.instance,BlockItemMapFrame.class, BlockMapFrame.name);
		}

		if (Config.enablePainting)
		{
            GameRegistry.registerBlock(BlockPaintingFrameFlat.instance,BlockItemPaintingFrameFlat.class, BlockPaintingFrameFlat.name);
            GameRegistry.registerBlock(BlockPaintingFrameSimple.instance,BlockItemPaintingFrameSimple.class, BlockPaintingFrameSimple.name);
            GameRegistry.registerBlock(BlockPaintingFrameMiddle.instance,BlockItemPaintingFrameMiddle.class, BlockPaintingFrameMiddle.name);
            GameRegistry.registerBlock(BlockPaintingFrameFancy.instance,BlockItemPaintingFrameFancy.class, BlockPaintingFrameFancy.name);
            GameRegistry.registerBlock(BlockPaintingFrameBorderless.instance,BlockItemPaintingFrameBorderless.class, BlockPaintingFrameBorderless.name);

            GameRegistry.registerBlock(BlockPaintingPress.instance, BlockPaintingPress.name);

		}
		if (Config.enableTypewriter)
		{
            GameRegistry.registerBlock(BlockTypeWriter.instance,BlockItemTypewriter.class, BlockTypeWriter.name);
		}
		if (Config.enableSwordPedestal)
		{
            GameRegistry.registerBlock(BlockSwordPedestal.instance,BlockItemSwordPedestal.class, BlockSwordPedestal.name);
		}
		if (Config.enableArmorstand)
		{
            GameRegistry.registerBlock(BlockArmorStand.instance,BlockItemArmorStand.class, BlockArmorStand.name);
		}
		if (Config.enableDeskBell)
		{
            GameRegistry.registerBlock(BlockBell.instance, BlockBell.name);
		}
		if (Config.enablePrintpressTypeMachine)
		{
            GameRegistry.registerBlock(BlockTypesettingTable.instance, BlockTypesettingTable.name);
		}
		if (Config.enableCookieJar)
		{
            GameRegistry.registerBlock(BlockCookieJar.instance, BlockCookieJar.name);
		}
		if (Config.enableDinnerPlate)
		{
            GameRegistry.registerBlock(BlockDinnerPlate.instance, BlockDinnerPlate.name);
		}
		if (Config.enableDiscRack)
		{
            GameRegistry.registerBlock(BlockDiscRack.instance, BlockDiscRack.name);
		}
	}

	public static void initBlockItemsRenderer()
	{
			if (Config.enableBookcase)
		{
            GameRegistry.registerBlock(BlockBookcase.instance, BlockItemBookcase.class ,BlockBookcase.name);
            GameRegistry.registerBlock(BlockBookcaseCreative.instance, BlockItemBookcaseCreative.class, BlockBookcaseCreative.name);
		}
//		if (Config.enableGenericshelf)
//		{
//            GameRegistry.registerBlock(BlockShelf.instance,BlockItemShelf.class, BlockShelf.name);
//		}
//		if (Config.enableTapemeasure)
//		{
//            GameRegistry.registerBlock(BlockMarkerPole.instance, BlockMarkerPole.name);
//		}
//		if (Config.enableClipboard)
//		{
//            GameRegistry.registerBlock(BlockClipboard.instance, BlockClipboard.name);
//		}
//
//		if (Config.enableLantern)
//		{
////            GameRegistry.registerBlock(BlockLanternGold.instance,BlockItemLantern.class, BlockLanternGold.name);
////            GameRegistry.registerBlock(BlockLanternIron.instance,BlockItemLantern.class, BlockLanternIron.name);
//		}
//		if (Config.enableLamp)
//		{
////            GameRegistry.registerBlock(BlockLampGold.instance, BlockItemLamp.class, BlockLampGold.name);
////            GameRegistry.registerBlock(BlockLampIron.instance, BlockItemLamp.class, BlockLampIron.name);
//		}
//		if (Config.enableFurniturePaneler)
//		{
//            GameRegistry.registerBlock(BlockFurniturePaneler.instance,BlockItemFurniturePaneler.class, BlockFurniturePaneler.name);
//		}
//		if (Config.enableFramedChest)
//		{
//            GameRegistry.registerBlock(BlockFramedChest.instance,BlockItemFramedChest.class, BlockFramedChest.name);
//		}
//		if (Config.enableFancySign)
//		{
//            GameRegistry.registerBlock(BlockFancySign.instance,BlockItemFancySign.class, BlockFancySign.name);
//		}
//		if (Config.enableFancyWorkbench)
//		{
//            GameRegistry.registerBlock(BlockFancyWorkbench.instance,BlockItemFancyWorkbench.class, BlockFancyWorkbench.name);
//		}
//		if (Config.enablePotionshelf)
//		{
//            GameRegistry.registerBlock(BlockPotionShelf.instance,BlockItemPotionShelf.class, BlockPotionShelf.name);
//		}
//		if (Config.enableToolrack)
//		{
//            GameRegistry.registerBlock(BlockToolRack.instance,BlockItemToolRack.class, BlockToolRack.name);
//		}
//		if (Config.enableWoodLabel)
//		{
//            GameRegistry.registerBlock(BlockLabel.instance,BlockItemLabel.class, BlockLabel.name);
//		}
//		if (Config.enableWritingdesk)
//		{
//            GameRegistry.registerBlock(BlockDesk.instance,BlockItemDesk.class, BlockDesk.name);
//		}
//		if (Config.enableTable)
//		{
//            GameRegistry.registerBlock(BlockTable.instance,BlockItemTable.class, BlockTable.name);
//		}
//		if (Config.enableSeat)
//		{
//            GameRegistry.registerBlock(BlockSeat.instance,BlockItemSeat.class, BlockSeat.name);
//		}
//
//		if (Config.enableClock)
//		{
//            GameRegistry.registerBlock(BlockClock.instance,BlockItemClock.class, BlockClock.name);
//		}
//		if (Config.enableWeaponcase)
//		{
//            GameRegistry.registerBlock(BlockCase.instance,BlockItemCase.class, BlockCase.name);
//		}
//		if (Config.enableMapFrame)
//		{
//            GameRegistry.registerBlock(BlockMapFrame.instance,BlockItemMapFrame.class, BlockMapFrame.name);
//		}
//
//		if (Config.enablePainting)
//		{
//            GameRegistry.registerBlock(BlockPaintingFrameFlat.instance,BlockItemPaintingFrameFlat.class, BlockPaintingFrameFlat.name);
//            GameRegistry.registerBlock(BlockPaintingFrameSimple.instance,BlockItemPaintingFrameSimple.class, BlockPaintingFrameSimple.name);
//            GameRegistry.registerBlock(BlockPaintingFrameMiddle.instance,BlockItemPaintingFrameMiddle.class, BlockPaintingFrameMiddle.name);
//            GameRegistry.registerBlock(BlockPaintingFrameFancy.instance,BlockItemPaintingFrameFancy.class, BlockPaintingFrameFancy.name);
//            GameRegistry.registerBlock(BlockPaintingFrameBorderless.instance,BlockItemPaintingFrameBorderless.class, BlockPaintingFrameBorderless.name);
//
//            GameRegistry.registerBlock(BlockPaintingPress.instance, BlockPaintingPress.name);
//
//		}
//		if (Config.enableTypewriter)
//		{
//            GameRegistry.registerBlock(BlockTypeWriter.instance,BlockItemTypewriter.class, BlockTypeWriter.name);
//		}
//		if (Config.enableSwordPedestal)
//		{
//            GameRegistry.registerBlock(BlockSwordPedestal.instance,BlockItemSwordPedestal.class, BlockSwordPedestal.name);
//		}
//		if (Config.enableArmorstand)
//		{
//            GameRegistry.registerBlock(BlockArmorStand.instance,BlockItemArmorStand.class, BlockArmorStand.name);
//		}
//		if (Config.enableDeskBell)
//		{
//            GameRegistry.registerBlock(BlockBell.instance, BlockBell.name);
//		}
//		if (Config.enablePrintpressTypeMachine)
//		{
//            GameRegistry.registerBlock(BlockTypesettingTable.instance, BlockTypesettingTable.name);
//		}
//		if (Config.enableCookieJar)
//		{
//            GameRegistry.registerBlock(BlockCookieJar.instance, BlockCookieJar.name);
//		}
//		if (Config.enableDinnerPlate)
//		{
//            GameRegistry.registerBlock(BlockDinnerPlate.instance, BlockDinnerPlate.name);
//		}
//		if (Config.enableDiscRack)
//		{
//            GameRegistry.registerBlock(BlockDiscRack.instance, BlockDiscRack.name);
//		}
	}
}
