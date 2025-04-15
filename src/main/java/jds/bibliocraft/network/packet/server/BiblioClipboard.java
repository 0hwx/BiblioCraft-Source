package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityClipboard;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioClipboard implements IMessage {
    int posX, posY, posZ;
    int updatePos;

    public BiblioClipboard() {

    }

    public BiblioClipboard(int x, int y, int z, int updatePos) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.updatePos = updatePos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.updatePos = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeInt(this.updatePos);
    }

    public static class Handler implements IMessageHandler<BiblioClipboard, IMessage> {

        @Override
        public IMessage onMessage(BiblioClipboard message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    TileEntity tile = player.worldObj.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityClipboard) {
                        TileEntityClipboard clipboard = (TileEntityClipboard) tile;
                        clipboard.updateClipboardFromPlayerSelection(message.updatePos);
                    }
                }
            return null;
        }

    }
}
