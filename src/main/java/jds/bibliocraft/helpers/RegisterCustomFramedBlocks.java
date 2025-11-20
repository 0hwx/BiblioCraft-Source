package jds.bibliocraft.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import jds.bibliocraft.Config;
import jds.bibliocraft.blocks.BlockArmorStand;
import jds.bibliocraft.blocks.BlockBookcase;
import jds.bibliocraft.blocks.BlockBookcaseCreative;
import jds.bibliocraft.blocks.BlockCase;
import jds.bibliocraft.blocks.BlockClock;
import jds.bibliocraft.blocks.BlockDesk;
import jds.bibliocraft.blocks.BlockFancySign;
import jds.bibliocraft.blocks.BlockFancyWorkbench;
import jds.bibliocraft.blocks.BlockFramedChest;
import jds.bibliocraft.blocks.BlockFurniturePaneler;
import jds.bibliocraft.blocks.BlockLabel;
import jds.bibliocraft.blocks.BlockMapFrame;
import jds.bibliocraft.blocks.BlockPaintingFrameBorderless;
import jds.bibliocraft.blocks.BlockPaintingFrameFancy;
import jds.bibliocraft.blocks.BlockPaintingFrameFlat;
import jds.bibliocraft.blocks.BlockPaintingFrameMiddle;
import jds.bibliocraft.blocks.BlockPaintingFrameSimple;
import jds.bibliocraft.blocks.BlockPotionShelf;
import jds.bibliocraft.blocks.BlockSeat;
import jds.bibliocraft.blocks.BlockShelf;
import jds.bibliocraft.blocks.BlockTable;
import jds.bibliocraft.blocks.BlockToolRack;
import jds.bibliocraft.items.ItemFramingSaw;
import jds.bibliocraft.items.ItemSeatBack;
import jds.bibliocraft.items.ItemSeatBack2;
import jds.bibliocraft.items.ItemSeatBack3;
import jds.bibliocraft.items.ItemSeatBack4;
import jds.bibliocraft.items.ItemSeatBack5;
import jds.bibliocraft.recipe.FramedRecipeRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RegisterCustomFramedBlocks
{
    private final ArrayList<ItemStack> texturedBlockList;
    private final Map<FramedBlockType, ItemStack> texturedStackMap; // For easy recipe access
    private final String textureString;

	public RegisterCustomFramedBlocks(String texture)
	{
        this.textureString = texture;
        this.texturedBlockList = new ArrayList<>();
        this.texturedStackMap = new HashMap<>();

        // Create the NBT tag *once*
        NBTTagCompound tags = new NBTTagCompound();
        tags.setString("renderTexture", this.textureString);

        // Iterate over the new enum
        for (FramedBlockType type : FramedBlockType.VALUES)
        {
            // Check the config flag directly from the enum
            if (type.isEnabled())
            {
                // Create the base stack (Item + meta)
                ItemStack Stack = type.createBaseStack();
                // Apply the NBT tag
                Stack.setTagCompound((NBTTagCompound) tags.copy());

                this.texturedBlockList.add(Stack);
                this.texturedStackMap.put(type, Stack);
            }
        }
    }

	public ArrayList<ItemStack> getFramedBlockList()
	{
		return this.texturedBlockList;
	}

    /**
     * Gets a specific textured item, or null if it was disabled.
     * Used for recipe registration.
     */
    public ItemStack getStack(FramedBlockType type)
    {
        return this.texturedStackMap.get(type);
    }

	public void registerRecipies(ItemStack plank, ItemStack slab) {
        WoodRegistryEntry recipeStrings = new WoodRegistryEntry(slab.getUnlocalizedName(), plank.getUnlocalizedName(), this.textureString, true);

        ItemStack stick = new ItemStack(Items.stick, 1, 0);
        ItemStack whiteWool = new ItemStack(Blocks.wool, 1, 0);
        ItemStack saw = new ItemStack(ItemFramingSaw.instance, 1, 0);
        ItemStack ironIngot = new ItemStack(Items.iron_ingot, 1, 0);
        ItemStack goldIngot = new ItemStack(Items.gold_ingot, 1, 0);
        ItemStack vanclock = new ItemStack(Items.clock, 1, 0);
        ItemStack sign = new ItemStack(Items.sign, 1, 0);
        ItemStack paper = new ItemStack(Items.paper, 1, 0);
        ItemStack craftingBench = new ItemStack(Blocks.crafting_table, 1, 0);
        ItemStack feather = new ItemStack(Items.feather, 1, 0);
        ItemStack emptyBottle = new ItemStack(Items.glass_bottle, 1, 0);
        ItemStack glassPane = new ItemStack(Blocks.glass, 1, 0);
        ItemStack torch = new ItemStack(Blocks.torch, 1, 0);
        ItemStack woodPP = new ItemStack(Blocks.wooden_pressure_plate, 1, 0);

        // --- This is now much safer and more readable than using get(0) ---
        ItemStack workbench = getStack(FramedBlockType.FANCY_WORKBENCH);
        ItemStack bookcase = getStack(FramedBlockType.BOOKCASE);

        if (workbench != null && bookcase != null)
        {
            Map<Character, Object> map = new HashMap<>();
            map.put('I', "dyeBlack");
            map.put('T', craftingBench);
            map.put('F', feather);
            map.put('S', slab);
            map.put('B', bookcase);
            FramedRecipeRegistry.defineAndRegisterRecipe(workbench, recipeStrings, new String[] { "ITF", "SBS", "SSS" }, map);
            FramedRecipeRegistry.defineAndRegisterRecipe(workbench, recipeStrings, new String[] { "FTI", "SBS", "SSS" }, map);
        }


        if (bookcase != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('X', plank);
            map.put('Y', slab);
            FramedRecipeRegistry.defineAndRegisterRecipe(bookcase, recipeStrings, new String[]{"XYX", "XYX", "XYX"}, map);
        }

        ItemStack framedChest = getStack(FramedBlockType.FRAMED_CHEST);
        ItemStack label = getStack(FramedBlockType.LABEL);
        if (framedChest != null && label != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('S', plank);
            map.put('L', label);
            FramedRecipeRegistry.defineAndRegisterRecipe(framedChest, recipeStrings, new String[]{"SSS", "SLS", "SSS"}, map);
        }

        ItemStack paneler = getStack(FramedBlockType.PANELER);
        if (paneler != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('S', slab);
            map.put('F', saw);
            map.put('P', plank);
            map.put('I', ironIngot);
            FramedRecipeRegistry.defineAndRegisterRecipe(paneler, recipeStrings, new String[]{"IFI", "SSS", "PPP"}, map);
        }

        ItemStack shelf = getStack(FramedBlockType.SHELF);
        if (shelf != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('X', plank);
            map.put('Y', slab);
            FramedRecipeRegistry.defineAndRegisterRecipe(shelf, recipeStrings, new String[]{"YYY", " X ", "YYY"}, map);
        }

        ItemStack toolRack = getStack(FramedBlockType.TOOL_RACK);
        if (toolRack != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('X', ironIngot);
            map.put('Y', slab);
            FramedRecipeRegistry.defineAndRegisterRecipe(toolRack, recipeStrings, new String[]{"YYY", "YXY", "YYY"}, map);
        }

        ItemStack potionShelf = getStack(FramedBlockType.POTION_SHELF);
        if (potionShelf != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('X', plank);
            map.put('Y', slab);
            map.put('B', emptyBottle);
            FramedRecipeRegistry.defineAndRegisterRecipe(potionShelf, recipeStrings, new String[]{"YYY", "XBX", "YYY"}, map);
        }

        ItemStack clock = getStack(FramedBlockType.CLOCK);
        if (clock != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('S', slab);
            map.put('C', vanclock);
            map.put('T', stick);
            map.put('G', goldIngot);
            FramedRecipeRegistry.defineAndRegisterRecipe(clock, recipeStrings, new String[]{"SCS", "STS", "SGS"}, map);
        }

        ItemStack borderlessFrame = getStack(FramedBlockType.PAINTING_FRAME_BORDERLESS);
        if (borderlessFrame != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('T', stick);
            map.put('S', slab);
            FramedRecipeRegistry.defineAndRegisterRecipe(borderlessFrame, recipeStrings, new String[]{"TST", "SSS", "TST"}, map);

            ItemStack flatFrame = getStack(FramedBlockType.PAINTING_FRAME_FLAT);
            if (flatFrame != null) {
                Map<Character, Object> map2 = new HashMap<>();
                map2.put('B', borderlessFrame);
                map2.put('S', slab);
                FramedRecipeRegistry.defineAndRegisterRecipe(flatFrame, recipeStrings, new String[]{"SSS", "SBS", "SSS"}, map2);
            }

            ItemStack simpleFrame = getStack(FramedBlockType.PAINTING_FRAME_SIMPLE);
            if (simpleFrame != null) {
                Map<Character, Object> map3 = new HashMap<>();
                map3.put('T', stick);
                map3.put('S', slab);
                map3.put('B', borderlessFrame);
                FramedRecipeRegistry.defineAndRegisterRecipe(simpleFrame, recipeStrings, new String[]{"TST", "SBS", "TST"}, map3);
            }

            ItemStack middleFrame = getStack(FramedBlockType.PAINTING_FRAME_MIDDLE);
            if (middleFrame != null) {
                Map<Character, Object> map4 = new HashMap<>();
                map4.put('T', stick);
                map4.put('S', slab);
                map4.put('B', borderlessFrame);
                FramedRecipeRegistry.defineAndRegisterRecipe(middleFrame, recipeStrings, new String[]{"TST", "TBT", "TST"}, map4);
            }

            ItemStack fancyFrame = getStack(FramedBlockType.PAINTING_FRAME_FANCY);
            if (fancyFrame != null) {
                Map<Character, Object> map5 = new HashMap<>();
                map5.put('T', stick);
                map5.put('B', borderlessFrame);
                FramedRecipeRegistry.defineAndRegisterRecipe(fancyFrame, recipeStrings, new String[]{"TTT", "TBT", "TTT"}, map5);
            }
        }

        ItemStack caseBlock = getStack(FramedBlockType.CASE);
        if (caseBlock != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('X', whiteWool);
            map.put('Y', slab);
            map.put('Z', glassPane);
            FramedRecipeRegistry.defineAndRegisterRecipe(caseBlock, recipeStrings, new String[]{"YZY", "YXY", "YYY"}, map);
        }

        ItemStack desk = getStack(FramedBlockType.DESK);
        if (desk != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('T', torch);
            map.put('F', feather);
            map.put('X', slab);
            map.put('Y', plank);
            FramedRecipeRegistry.defineAndRegisterRecipe(desk, recipeStrings, new String[]{"T F", "XXX", "Y Y"}, map);
        }

        ItemStack mapFrame = getStack(FramedBlockType.MAP_FRAME);
        if (mapFrame != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('S', stick);
            map.put('X', slab);
            FramedRecipeRegistry.defineAndRegisterRecipe(mapFrame, recipeStrings, new String[]{"SSS", "SXS", "SSS"}, map);
        }

        if (label != null) { // 'label' was defined earlier
            Map<Character, Object> map = new HashMap<>();
            map.put('Y', slab);
            FramedRecipeRegistry.defineAndRegisterRecipe(label, recipeStrings, new String[]{"YYY", "YYY"}, map);
        }

        ItemStack armorStand = getStack(FramedBlockType.ARMOR_STAND);
        if (armorStand != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('X', slab);
            map.put('Y', stick);
            FramedRecipeRegistry.defineAndRegisterRecipe(armorStand, recipeStrings, new String[]{" Y ", " Y ", "XXX"}, map);
        }

        ItemStack table = getStack(FramedBlockType.TABLE);
        if (table != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('X', slab);
            map.put('Y', plank);
            FramedRecipeRegistry.defineAndRegisterRecipe(table, recipeStrings, new String[]{"XXX", " Y ", " Y "}, map);
        }

        ItemStack fancySign = getStack(FramedBlockType.FANCY_SIGN);
        if (fancySign != null && label != null) {
            // This recipe looks shapeless
            // I'm assuming FramedRecipeRegistry has an overloaded method for shapeless
            FramedRecipeRegistry.defineAndRegisterRecipe(fancySign, recipeStrings, new Object[]{label, slab, paper});
        }

        ItemStack seat = getStack(FramedBlockType.SEAT);
        ItemStack seatBack = getStack(FramedBlockType.SEAT_BACK);
        ItemStack seatBack2 = getStack(FramedBlockType.SEAT_BACK_2);
        ItemStack seatBack3 = getStack(FramedBlockType.SEAT_BACK_3);
        ItemStack seatBack4 = getStack(FramedBlockType.SEAT_BACK_4);
        ItemStack seatBack5 = getStack(FramedBlockType.SEAT_BACK_5);

        if (seatBack != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('W', whiteWool);
            map.put('S', slab);
            map.put('T', stick);
            FramedRecipeRegistry.defineAndRegisterRecipe(seatBack, recipeStrings, new String[]{" W ", " S ", "T T"}, map);
        }

        if (seatBack2 != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('W', whiteWool);
            map.put('S', slab);
            map.put('T', stick);
            FramedRecipeRegistry.defineAndRegisterRecipe(seatBack2, recipeStrings, new String[]{"TWT", "TST", "T T"}, map);
        }

        if (seatBack4 != null) {
            Map<Character, Object> map = new HashMap<>();
            map.put('W', whiteWool);
            map.put('S', slab);
            map.put('T', stick);
            FramedRecipeRegistry.defineAndRegisterRecipe(seatBack4, recipeStrings, new String[]{"TWT", " S "}, map);
        }

        if (seatBack3 != null && seatBack2 != null) {
            FramedRecipeRegistry.defineAndRegisterRecipe(seatBack3, recipeStrings, new Object[]{slab, seatBack2});
        }

        if (seatBack5 != null && seatBack2 != null) {
            FramedRecipeRegistry.defineAndRegisterRecipe(seatBack5, recipeStrings, new Object[]{slab, slab, seatBack2});
        }

        if (seat != null) {
            // This one is a ShapedOreRecipe, which is different.
            // It needs to be registered with GameRegistry directly.
            // But we must make sure the output has the correct NBT!
            ItemStack nbtSeat = seat.copy(); // Use the NBT-tagged stack from our map
            GameRegistry.addRecipe(new ShapedOreRecipe(nbtSeat, true, new Object[]{" W ", " S ", "TPT", Character.valueOf('W'), whiteWool, Character.valueOf('S'), slab, Character.valueOf('T'), stick, Character.valueOf('P'), woodPP}));
        }
    }
    /**
     * Defines all 27 framed block/item types, their config flags.
     * This replaces the static parallel lists in RegisterCustomFramedBlocks.
     */
    public enum FramedBlockType {

        BOOKCASE(BlockBookcase.instance,  Config.enableBookcase),
        BOOKCASE_CREATIVE(BlockBookcaseCreative.instance,  Config.enableBookcase),
        FRAMED_CHEST(BlockFramedChest.instance,  Config.enableFramedChest),
        FANCY_WORKBENCH(BlockFancyWorkbench.instance,  Config.enableFancyWorkbench),
        PANELER(BlockFurniturePaneler.instance,  Config.enableFurniturePaneler),
        SHELF(BlockShelf.instance,  Config.enableGenericshelf),
        TOOL_RACK(BlockToolRack.instance,  Config.enableToolrack),
        POTION_SHELF(BlockPotionShelf.instance,  Config.enablePotionshelf),
        CLOCK(BlockClock.instance,  Config.enableClock),
        PAINTING_FRAME_BORDERLESS(BlockPaintingFrameBorderless.instance,  Config.enablePainting),
        PAINTING_FRAME_FLAT(BlockPaintingFrameFlat.instance,  Config.enablePainting),
        PAINTING_FRAME_SIMPLE(BlockPaintingFrameSimple.instance,  Config.enablePainting),
        PAINTING_FRAME_MIDDLE(BlockPaintingFrameMiddle.instance,  Config.enablePainting),
        PAINTING_FRAME_FANCY(BlockPaintingFrameFancy.instance,  Config.enablePainting),
        CASE(BlockCase.instance,  Config.enableWeaponcase),
        SEAT(BlockSeat.instance,  Config.enableSeat),
        DESK(BlockDesk.instance,  Config.enableWritingdesk),
        MAP_FRAME(BlockMapFrame.instance,  Config.enableMapFrame),
        LABEL(BlockLabel.instance,  Config.enableWoodLabel),
        ARMOR_STAND(BlockArmorStand.instance,  Config.enableArmorstand),
        TABLE(BlockTable.instance,  Config.enableTable),
        FANCY_SIGN(BlockFancySign.instance,  Config.enableFancySign),
        SEAT_BACK(ItemSeatBack.instance,  Config.enableSeat),
        SEAT_BACK_2(ItemSeatBack2.instance,  Config.enableSeat),
        SEAT_BACK_3(ItemSeatBack3.instance,  Config.enableSeat),
        SEAT_BACK_4(ItemSeatBack4.instance,  Config.enableSeat),
        SEAT_BACK_5(ItemSeatBack5.instance,  Config.enableSeat);

        private final Item item;
        private final boolean isEnabled;
        private static final FramedBlockType[] VALUES = values();


        FramedBlockType(Block block, boolean enabled) {
            this(Item.getItemFromBlock(block), enabled);
        }

        FramedBlockType(Item item, boolean enabled) {
            this.item = item;
            this.isEnabled = enabled;
        }

        /**
         * @return True if this block is enabled in the config.
         */
        public boolean isEnabled() {
            return this.isEnabled;
        }

        /**
         * @return A new base ItemStack (size 1, with metadata) for this type.
         */
        public ItemStack createBaseStack() {
            return new ItemStack(this.item, 1, 6);
        }
    }
}
