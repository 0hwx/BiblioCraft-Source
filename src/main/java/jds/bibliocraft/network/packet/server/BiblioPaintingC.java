package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityPainting;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioPaintingC implements IMessage {

    int posX;
    int posY;
    int posZ;
    int aspectX;
    int aspectY;
    public BiblioPaintingC() {

    }
    public BiblioPaintingC(int posX, int posY, int posZ, int aspectX, int aspectY) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.aspectX = buf.readInt();
        this.aspectY = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeInt(this.aspectX);
        buf.writeInt(this.aspectY);
    }
    public static class Handler implements IMessageHandler<BiblioPaintingC, IMessage> {

        @Override
        public IMessage onMessage(BiblioPaintingC message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    World world = player.worldObj;
                    TileEntity tile = world.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityPainting) {
                        TileEntityPainting painting = (TileEntityPainting) tile;
                        painting.setPacketAspectsUpdate(message.aspectX, message.aspectY);
                    }
                }
            return null;
        }

    }
}
