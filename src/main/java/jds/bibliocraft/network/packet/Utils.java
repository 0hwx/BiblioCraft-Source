package jds.bibliocraft.network.packet;

import java.util.ArrayList;
import java.util.List;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.gui.GuiAtlasMap;
import jds.bibliocraft.gui.GuiAtlasWaypointTransfer;
import jds.bibliocraft.gui.GuiBigBook;
import jds.bibliocraft.gui.GuiClipboard;
import jds.bibliocraft.gui.GuiRecipeBook;
import jds.bibliocraft.gui.GuiScreenBookDesk;
import jds.bibliocraft.gui.GuiStockCatalog;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.helpers.SortedListItem;
import jds.bibliocraft.items.ItemRecipeBook;
import jds.bibliocraft.network.BiblioNetworking;
import jds.bibliocraft.network.packet.client.BiblioRecipeText;
import jds.bibliocraft.tileentities.TileEntityMapFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.EnumFacing;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;

public class Utils {
    @SideOnly(Side.CLIENT)
    public static void openWritingGUI(EntityPlayer player, ItemStack book, int x, int y, int z, boolean signed) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBookDesk(player, book, signed, x, y, z));
    }

    @SideOnly(Side.CLIENT)
    public static void openClipboardGUI(ItemStack stack, boolean inInv, int x, int y, int z) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiClipboard(stack, inInv, x, y, z));
    }

    @SideOnly(Side.CLIENT)
    public static void openRecipeBookGUI(ItemStack stack, int x, int y, int z, int slot, boolean canCraft) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiRecipeBook(stack, true, x, y, z, slot, canCraft));
    }

    @SideOnly(Side.CLIENT)
    public static void openWaypointTransferGUI(World world, EntityPlayer player, ItemStack stack, TileEntityMapFrame tile) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiAtlasWaypointTransfer(world, player, stack, tile));
    }

    @SideOnly(Side.CLIENT)
    public static void openMapGUI(EntityPlayer player, ItemStack stack) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiAtlasMap(Minecraft.getMinecraft().theWorld, player, stack));
    }

    @SideOnly(Side.CLIENT)
    public static void openBigBookGUI(ItemStack stack, int x, int y, int z, String author) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBigBook(stack, false, x, y, z, author));
    }

    public static boolean checkForValidRecipeIngredients(ItemStack[] ingredients, EntityPlayerMP player,
                                                         boolean remove) {
        if (player.capabilities.isCreativeMode) {
            remove = false;
        }
        boolean[] passed = { false, false, false,
                false, false, false,
                false, false, false };
        ItemStack[] inventory = player.inventory.mainInventory;
        ItemStack[] playerInventory = inventory;
        ItemStack[] playerIngredients = new ItemStack[9];
        for (int i = 0; i < ingredients.length; i++) {
            playerIngredients[i] = ingredients[i];
        }
        ItemStack[] countedIngredients = new ItemStack[9];
        for (int i = 0; i < playerIngredients.length; i++) {
            ItemStack thing = playerIngredients[i];
            if (thing != null) {
                int count = 0;
                for (int n = 0; n < playerIngredients.length; n++) {
                    ItemStack subThing = playerIngredients[n];
                    if (subThing.getUnlocalizedName().equals(thing.getUnlocalizedName())) {
                        count++;
                        playerIngredients[n] = null;
                    }
                }
                thing.stackSize = (count);
                countedIngredients[i] = thing;
            }
        }
        for (int i = 0; i < countedIngredients.length; i++) {
            ItemStack ingredientItem = countedIngredients[i];
            if (ingredientItem != null
                    && !ingredientItem.getUnlocalizedName().contentEquals(null)) {
                for (int n = 0; n < playerInventory.length; n++) {
                    ItemStack inventoryItem = playerInventory[n];
                    if (inventoryItem != null
                            && inventoryItem.getUnlocalizedName().equals(ingredientItem.getUnlocalizedName())) {
                        if (inventoryItem.stackSize >= ingredientItem.stackSize) {
                            if (remove) {
                                inventoryItem.stackSize = (inventoryItem.stackSize - ingredientItem.stackSize);
                                if (inventoryItem.stackSize <= 0) {
                                    inventory[n] = null;
                                } else {
                                    inventory[n] = inventoryItem;
                                }
                            }
                            passed[i] = true;
                            break;
                        } else {
                            inventoryItem.stackSize = (ingredientItem.stackSize - inventoryItem.stackSize);
                            countedIngredients[i] = ingredientItem; //.set(i, ingredientItem);
                            if (remove) {
                                inventory[n] = null;
                            }
                        }
                    }
                }
            } else {
                passed[i] = true;
            }
        }
        boolean hasIngredients = true;
        for (int m = 0; m < passed.length; m++) {
            if (!passed[m]) {
                hasIngredients = false;
            }
        }
        if (player.capabilities.isCreativeMode) {
            hasIngredients = true;
        }
        return hasIngredients;
    }

    public static boolean checkIfValidPacketItem(String input) {
        // Make sure all this stuff can only open if in main hand. // TODO
        String validPacketItems[] = { "item.AtlasBook", "item.BigBook", "item.RecipeBook", "item.BiblioClipboard",
                "item.BiblioRedBook", "item.SlottedBook", "item.compass" };
        for (int i = 0; i < validPacketItems.length; i++) {
            if (validPacketItems[i].equals(input)) {
                return true;
            }
        }
        return false;
    }
    public static boolean hasPointLoaded(EntityPlayerMP player, int x, int y, int z) {
//        if (pos == null) {
//            BiblioCraft.LOGGER.error("Null position passed to load check by " + player.getDisplayName());
//        }
        WorldServer sworld = player.getServerForPlayer();
        PlayerManager chunkMap = sworld.getPlayerManager();
        return chunkMap.isPlayerWatchingChunk(player, x >> 4, z >> 4);
    }
	@SideOnly(Side.CLIENT)
	public static void openCatalogGUI(EntityPlayer player, ArrayList<SortedListItem> AlphaList,
			ArrayList<SortedListItem> QuantaList, ItemStack[] stacks, int[] compasses, String title) {
		Minecraft.getMinecraft()
				.displayGuiScreen(new GuiStockCatalog(player, AlphaList, QuantaList, stacks, compasses, title));
		// Minecraft.getMinecraft()AlphaList.
	}
    public static void sendARecipeBookTextPacket(EntityPlayerMP player, String text, int slot) {
        ItemStack currentBook = player.inventory.getStackInSlot(slot);
        if (currentBook != null) {
            if (currentBook.getItem() instanceof ItemRecipeBook) {
                BiblioNetworking.INSTANCE.sendTo(new BiblioRecipeText(text, slot), player);
                // ByteBuf buffer = Unpooled.buffer();
                // ByteBufUtils.writeUTF8String(buffer, text);
                // buffer.writeInt(slot);
                // BiblioCraft.ch_BiblioRecipeText.sendTo(new FMLProxyPacket(new PacketBuffer(buffer), "BiblioRecipeText"),
                //         player);
            }
        }
    }

    public static ItemStack getCurrentMapStack(ItemStack stack) {
        InventoryBasic inv = getInventory(stack);
        NBTTagCompound atlasTags = stack.getTagCompound();
        if (atlasTags != null) {
            int mapSlot = atlasTags.getInteger("mapSlot");
            if (mapSlot != -1) {
                ItemStack mapStack = inv.getStackInSlot(mapSlot);
                if (mapStack != null && mapStack.getItem() == Items.filled_map) {
                    return mapStack;
                }
            }
        }
        return null;
    }

    public static InventoryBasic getInventory(ItemStack stack) {
        NBTTagCompound tags = stack.getTagCompound();
        if (tags != null) {
            InventoryBasic atlasInventory = new InventoryBasic("AtlasInventory", true, 48);
            NBTTagList tagList = tags.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
                byte slot = tag.getByte("Slot");
                if (slot >= 0 && slot < atlasInventory.getSizeInventory()) {
                    ItemStack invStack = ItemStack.loadItemStackFromNBT(tag);
                    atlasInventory.setInventorySlotContents(slot, invStack);
                }
            }
            return atlasInventory;
        } else {
            return null;
        }
    }

    public static NBTTagCompound getNewMapDataCompound(TileEntityMapFrame tile, String newMapName) {
        int mapRotation = tile.getRotation();
        NBTTagCompound newMapData = new NBTTagCompound();
        newMapData.setString("mapName", newMapName);
        newMapData.setInteger("xCenter", tile.mapXCenter);
        newMapData.setInteger("zCenter", tile.mapZCenter);
        newMapData.setInteger("mapScale", tile.mapScale);
        ForgeDirection angle = tile.getAngle();
        EnumVertPosition vertAngle = tile.getVertPosition();
        int rotations = 0;
        switch (mapRotation) {
            case 1: {
                rotations = 3;
                break;
            }
            case 2: {
                rotations = 2;
                break;
            }
            case 3: {
                rotations = 1;
                break;
            }
        }
        ArrayList<Float> xPins = new ArrayList<>();
        xPins = (ArrayList<Float>) tile.xPin.clone();
        ArrayList<Float> yPins = new ArrayList<>();
        yPins = (ArrayList) tile.yPin.clone();

        for (int i = 0; i < rotations; i++) {
            ArrayList<Float> xCurrent = xPins;
            ArrayList<Float> yCurrent = yPins;
            if (((angle == ForgeDirection.SOUTH || angle == ForgeDirection.EAST) && vertAngle == EnumVertPosition.WALL)
                    || vertAngle == EnumVertPosition.CEILING) {
                xPins = yCurrent;
                yPins = xCurrent;
                yCurrent = yPins;
                for (int n = 0; n < xCurrent.size(); n++) {
                    yPins.set(n, 1 - (Float) yCurrent.get(n));
                }
            } else {
                xPins = yCurrent;
                yPins = xCurrent;
                xCurrent = xPins;
                for (int n = 0; n < xCurrent.size(); n++) {
                    xPins.set(n, 1 - (Float) xCurrent.get(n));
                }
            }
        }

        NBTTagList mapXPins = new NBTTagList();
        for (int i = 0; i < xPins.size(); i++) {
            float xpin = (Float) xPins.get(i);
            if (tile.getVertPosition() == EnumVertPosition.WALL
                    && (tile.getAngle() == ForgeDirection.WEST || tile.getAngle() == ForgeDirection.NORTH)) {
                xpin = 1.0f - xpin;
            }

            mapXPins.appendTag(new NBTTagFloat(xpin));
        }
        newMapData.setTag("xMapWaypoints", mapXPins);

        NBTTagList mapYPins = new NBTTagList();
        for (int i = 0; i < yPins.size(); i++) {
            float ypin = (Float) yPins.get(i);
            if (tile.getVertPosition() == EnumVertPosition.CEILING || tile.getVertPosition() == EnumVertPosition.WALL) {
                // ceiling
                ypin = 1.0f - ypin;
            }
            mapYPins.appendTag(new NBTTagFloat(ypin));
        }
        newMapData.setTag("yMapWaypoints", mapYPins);

        NBTTagList mapPinNames = new NBTTagList();
        for (int i = 0; i < tile.pinStrings.size(); i++) {
            mapPinNames.appendTag(new NBTTagString((String) tile.pinStrings.get(i)));
        }
        newMapData.setTag("MapWaypointNames", mapPinNames);

        NBTTagList mapPinColors = new NBTTagList();
        for (int i = 0; i < tile.pinColors.size(); i++) {
            mapPinColors.appendTag(new NBTTagFloat((Float) tile.pinColors.get(i)));
        }
        newMapData.setTag("MapWaypointColors", mapPinColors);
        return newMapData;
    }
}
