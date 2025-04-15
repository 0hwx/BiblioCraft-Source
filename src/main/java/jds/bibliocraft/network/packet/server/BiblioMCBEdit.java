package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.Config;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityDesk;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioMCBEdit implements IMessage {
    int posX;
    int posY;
    int posZ;
    int currentPage;
    ItemStack book;

    public BiblioMCBEdit() {

    }

    public BiblioMCBEdit(int posX, int posY, int posZ, int currentPage, ItemStack book) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.currentPage = currentPage;
        this.book = book;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.currentPage = buf.readInt();
        this.book = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeInt(this.currentPage);
        ByteBufUtils.writeItemStack(buf, this.book);
    }

    public static class Handler implements IMessageHandler<BiblioMCBEdit, IMessage> {

        @Override
        public IMessage onMessage(BiblioMCBEdit message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (message.book != null) {
                    if (Config.testBookValidity(message.book)) {
                        // TODO: distance from player to position
                        if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                            TileEntityDesk deskTile = (TileEntityDesk) player.worldObj.getTileEntity(message.posX, message.posY, message.posZ);
                            if (deskTile != null) {
                                deskTile.overwriteWrittenBook(message.book);
                                deskTile.setCurrentPage(message.currentPage);
                            }
                        }
                    }
                }
            return null;
        }

    }
}
