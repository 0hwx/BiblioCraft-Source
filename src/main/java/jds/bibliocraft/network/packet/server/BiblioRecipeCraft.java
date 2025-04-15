package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.Config;
import jds.bibliocraft.items.ItemRecipeBook;
import jds.bibliocraft.network.packet.Utils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioRecipeCraft implements IMessage {
    ItemStack recipeBook;
    int inventorySlot;

    public BiblioRecipeCraft() {

    }

    public BiblioRecipeCraft(ItemStack recipeBook, int inventorySlot) {
        this.recipeBook = recipeBook;
        this.inventorySlot = inventorySlot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.recipeBook = ByteBufUtils.readItemStack(buf);
        this.inventorySlot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.recipeBook);
        buf.writeInt(this.inventorySlot);
    }

    public static class Handler implements IMessageHandler<BiblioRecipeCraft, IMessage> {

        @Override
        public IMessage onMessage(BiblioRecipeCraft message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                ItemStack recipeBook = message.recipeBook;
                int inventorySlot = message.inventorySlot;
                if (Config.enableRecipeBookCrafting) {
                    if (recipeBook != null && recipeBook.getItem() instanceof ItemRecipeBook) {
                        ItemStack[] bookGrid = new ItemStack[9];// new
                                                                                                              // ItemStack[9];
                        ItemStack resultStack = null;
                        NBTTagCompound nbt = recipeBook.getTagCompound();
                        if (nbt != null) {
                            NBTTagList tagList = nbt.getTagList("grid", Constants.NBT.TAG_COMPOUND);
                            bookGrid = new ItemStack[9];
                            for (int i = 0; i < 9; i++) {
                                NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
                                byte slot = tag.getByte("Slot");
                                if (slot >= 0 && slot < 9) {
                                    ItemStack nbtStack = ItemStack.loadItemStackFromNBT(tag);
                                    if (nbtStack != null) {
                                        bookGrid[slot] = nbtStack;
                                    }
                                }
                            }
                            NBTTagCompound resultTag = nbt.getCompoundTag("result");
                            if (resultTag != null) {
                                resultStack = ItemStack.loadItemStackFromNBT(resultTag);
                            }
                        }

                        if (resultStack != null) {
                            if (Utils.checkForValidRecipeIngredients(bookGrid, player, false)) {
                                Container contained = new Container() {
                                    @Override
                                    public boolean canInteractWith(EntityPlayer p_75145_1_) {
                                        return false;
                                    }
                                };
                                InventoryCrafting playerCraftMatrix = new InventoryCrafting(contained, 3, 3);
                                for (int i = 0; i < bookGrid.length; i++) {
                                    playerCraftMatrix.setInventorySlotContents(i, bookGrid[i]);
                                }

                                ItemStack result = CraftingManager.getInstance().findMatchingRecipe(playerCraftMatrix, player.worldObj);
                                if (result != null) {
                                    if (Utils.checkForValidRecipeIngredients(bookGrid, player, true)) // remove valid
                                                                                                      // ingredients
                                                                                                      // from
                                    // inventory
                                    {
                                        if (!(player.inventory.addItemStackToInventory(result.copy()))) {
                                            EntityItem entityItem = new EntityItem(player.worldObj, player.posX,
                                                    player.posY,
                                                    player.posZ,
                                                    new ItemStack(result.getItem(), result.stackSize,
                                                            result.getItemDamage()));
                                            if (result.hasTagCompound()) {
                                                entityItem.getEntityItem()
                                                        .setTagCompound(
                                                                (NBTTagCompound) result.getTagCompound().copy());
                                            }
                                            entityItem.motionX = 0;
                                            entityItem.motionY = 0;
                                            entityItem.motionZ = 0;
                                            player.worldObj.spawnEntityInWorld(entityItem);
                                        }
                                        Utils.sendARecipeBookTextPacket(player,
                                                result.getDisplayName() + " "
                                                        + I18n.format("gui.recipe.crafted"),
                                                inventorySlot);
                                    } else {
                                        Utils.sendARecipeBookTextPacket(player,
                                                I18n.format("gui.recipe.failed"),
                                                inventorySlot);
                                    }
                                    return message;
                                } else {
                                    Utils.sendARecipeBookTextPacket(player, I18n.format("gui.recipe.invalid"),
                                            inventorySlot);
                                    return message;
                                }
                            } else {
                                Utils.sendARecipeBookTextPacket(player, I18n.format("gui.recipe.missing"),
                                        inventorySlot);
                                return message;
                            }
                        } else {
                            Utils.sendARecipeBookTextPacket(player, I18n.format("gui.recipe.invalid"),
                                    inventorySlot);
                            return message;
                        }
                    }
                    Utils.sendARecipeBookTextPacket(player, I18n.format("gui.recipe.wrong"), inventorySlot);
                } else {
                    Utils.sendARecipeBookTextPacket(player, I18n.format("gui.recipe.disabled"),
                            inventorySlot);
                }

            return null;
        }
    }
}
