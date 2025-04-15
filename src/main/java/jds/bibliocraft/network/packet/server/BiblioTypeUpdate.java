package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityTypeMachine;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioTypeUpdate implements IMessage {
    int posX;
    int posY;
    int posZ;

    public BiblioTypeUpdate() {

    }

    public BiblioTypeUpdate(int x, int y, int z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }

    public static class Handler implements IMessageHandler<BiblioTypeUpdate, IMessage> {

        @Override
        public IMessage onMessage(BiblioTypeUpdate message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                // TODO: Check reach distance between block and player
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    TileEntity tile = player.worldObj.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityTypeMachine) {
                        TileEntityTypeMachine typeTile = (TileEntityTypeMachine) tile;
                        typeTile.booklistset();
                    }
                }
            return null;
        }

    }
}
