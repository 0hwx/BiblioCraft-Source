package jds.bibliocraft.network.packet.server;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.netty.buffer.ByteBuf;
import jds.bibliocraft.items.ItemStockroomCatalog;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioStockTitle implements IMessage {
    String title;

    public BiblioStockTitle() {

    }

    public BiblioStockTitle(String title) {
        this.title = title;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.title = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.title);
    }

    public static class Handler implements IMessageHandler<BiblioStockTitle, IMessage> {

        @Override
        public IMessage onMessage(BiblioStockTitle message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                ItemStack stockroomcatalog = player.getHeldItem();
                if (stockroomcatalog != null && stockroomcatalog.getItem() instanceof ItemStockroomCatalog) {
                    NBTTagCompound tags = stockroomcatalog.getTagCompound();
                    if (tags == null) {
                        tags = new NBTTagCompound();
                    }
                    NBTTagCompound display = new NBTTagCompound();
                    display.setString("Name", ChatFormatting.WHITE + message.title);
                    tags.setTag("display", display);
                    stockroomcatalog.setTagCompound(tags);
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, stockroomcatalog);
                }
            return null;
        }

    }
}
