package jds.bibliocraft.network.packet.client;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import jds.bibliocraft.items.ItemBigBook;
import jds.bibliocraft.items.ItemClipboard;
import jds.bibliocraft.items.ItemRecipeBook;
import jds.bibliocraft.network.packet.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioDeskOpenGui implements IMessage {
    int posX;
    int posY;
    int posZ;
    ItemStack book;
    boolean canCraft;

    public BiblioDeskOpenGui() {

    }

    public BiblioDeskOpenGui(int x, int y, int z, ItemStack book, boolean canCraft) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.book = book;
        this.canCraft = canCraft;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.book = ByteBufUtils.readItemStack(buf);
        this.canCraft = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        ByteBufUtils.writeItemStack(buf, this.book);
        buf.writeBoolean(this.canCraft);
    }

    public static class Handler implements IMessageHandler<BiblioDeskOpenGui, IMessage> {

        @Override
        public IMessage onMessage(BiblioDeskOpenGui message, MessageContext ctx) {
            ItemStack book = message.book;
            boolean canCraft = message.canCraft;
            if (book != null) {
                final Item signedtest = book.getItem();
                Minecraft.getMinecraft().func_152344_a(() -> {

                	handleBook(book, message.posX, message.posY, message.posZ, signedtest, canCraft);

                });
            }
            return null;
        }
    }

    @SideOnly(Side.CLIENT)
    public static void handleBook(ItemStack book, int x, int y, int z, Item signedtest, boolean canCraft)
    {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        if (signedtest instanceof ItemWritableBook) {
            Utils.openWritingGUI(player, book, x, y, z, false);
            // signedtest.onItemRightClick(book, world, player);
        }
        if (signedtest instanceof ItemWritableBook) {
            Utils.openWritingGUI(player, book, x, y, z, true);
        }
        if (signedtest instanceof ItemClipboard) {
            Utils.openClipboardGUI(book, false, x, y, z);
        }
        if (signedtest instanceof ItemBigBook) {
            Utils.openBigBookGUI(book, x, y, z, player.getDisplayName());
        }
        if (signedtest instanceof ItemRecipeBook) {
            Utils.openRecipeBookGUI(book, x, y, z, -1, canCraft);
        }
        if (Loader.isModLoaded("thaumcraft") && book.toString().contains("thaumonomicon")) {
            signedtest.onItemRightClick(book, player.getEntityWorld(), player);
        }
        if (Loader.isModLoaded("tailcraft") && book.toString().contains("railcraft.routing.table")) {
            signedtest.onItemRightClick(book, player.getEntityWorld(), player);
        }
        if (Loader.isModLoaded("craftguide") && book.toString().contains("craftguide")) {
            signedtest.onItemRightClick(book, player.getEntityWorld(), player);
        }
        if (Loader.isModLoaded("botania") && book.getUnlocalizedName().contentEquals("item.lexicon")) {
            // System.out.println(book.getUnlocalizedName());
            // signedtest.onItemRightClick(book, world, player);
            // doesnt work
        }
    }
}
