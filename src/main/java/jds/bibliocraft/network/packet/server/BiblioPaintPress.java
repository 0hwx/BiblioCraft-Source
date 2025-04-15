package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityPaintPress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioPaintPress implements IMessage {
    int posX;
    int posY;
    int posZ;
    int artType;
    String artName;
    boolean applyToCanvas;

    public BiblioPaintPress() {

    }

    public BiblioPaintPress(int posX, int posY, int posZ, int artType, String artName, boolean applyToCanvas) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.artType = artType;
        this.artName = artName;
        this.applyToCanvas = applyToCanvas;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.artType = buf.readInt();
        this.artName = ByteBufUtils.readUTF8String(buf);
        this.applyToCanvas = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeInt(this.artType);
        ByteBufUtils.writeUTF8String(buf, this.artName);
        buf.writeBoolean(this.applyToCanvas);
    }

    public static class Handler implements IMessageHandler<BiblioPaintPress, IMessage> {

        @Override
        public IMessage onMessage(BiblioPaintPress message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                World world = player.worldObj;
                TileEntity tile = world.getTileEntity(message.posX, message.posY, message.posZ);
                if (tile != null && tile instanceof TileEntityPaintPress) {
                    TileEntityPaintPress press = (TileEntityPaintPress) tile;
                    press.setSelectedPainting(message.artType, message.artName);
                    if (message.applyToCanvas) {
                        press.setCycle(true);
                    }
                }
            }
            return null;
        }

    }
}
