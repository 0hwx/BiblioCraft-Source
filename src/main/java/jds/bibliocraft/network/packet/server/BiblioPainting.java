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

public class BiblioPainting implements IMessage {
    int posX;
    int posY;
    int posZ;
    int corner;
    int scale;
    int res;
    int aspect;
    int rotation;
    int customAspectX;
    int customAspectY;
    boolean hideFrame;
    public BiblioPainting() {

    }
    public BiblioPainting(int posX, int posY, int posZ, int corner, int scale, int res, int aspect, int rotation, int customAspectX, int customAspectY, boolean hideFrame) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.corner = corner;
        this.scale = scale;
        this.res = res;
        this.aspect = aspect;
        this.rotation = rotation;
        this.customAspectX = customAspectX;
        this.customAspectY = customAspectY;
        this.hideFrame = hideFrame;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.corner = buf.readInt();
        this.scale = buf.readInt();
        this.res = buf.readInt();
        this.aspect = buf.readInt();
        this.rotation = buf.readInt();
        this.customAspectX = buf.readInt();
        this.customAspectY = buf.readInt();
        this.hideFrame = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeInt(this.corner);
        buf.writeInt(this.scale);
        buf.writeInt(this.res);
        buf.writeInt(this.aspect);
        buf.writeInt(this.rotation);
        buf.writeInt(this.customAspectX);
        buf.writeInt(this.customAspectY);
        buf.writeBoolean(this.hideFrame);
    }
    public static class Handler implements IMessageHandler<BiblioPainting, IMessage> {

        @Override
        public IMessage onMessage(BiblioPainting message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    World world = player.worldObj;
                    TileEntity tile = world.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityPainting) {
                        TileEntityPainting painting = (TileEntityPainting) tile;
                        painting.setHideFrame(message.hideFrame);
                        painting.setPacketUpdate(message.corner, message.scale, message.res, message.aspect, message.rotation, message.customAspectX, message.customAspectY);

                    }
                }
            return null;
        }

    }
}
