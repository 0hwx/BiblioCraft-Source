package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityMapFrame;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioMapPin implements IMessage {
    int posX;
    int posY;
    int posZ;
    float xPin;
    float yPin;
    String name;
    int colour;
    int pinNum;
    boolean remove;
    boolean edit;
    public BiblioMapPin() {

    }
    public BiblioMapPin(int posX, int posY, int posZ, float xPin, float yPin, String name, int colour, int pinNum, boolean remove, boolean edit) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.xPin = xPin;
        this.yPin = yPin;
        this.name = name;
        this.colour = colour;
        this.pinNum = pinNum;
        this.remove = remove;
        this.edit = edit;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.xPin = buf.readFloat();
        this.yPin = buf.readFloat();
        this.name = ByteBufUtils.readUTF8String(buf);
        this.colour = buf.readInt();
        this.pinNum = buf.readInt();
        this.remove = buf.readBoolean();
        this.edit = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeFloat(this.xPin);
        buf.writeFloat(this.yPin);
        ByteBufUtils.writeUTF8String(buf, this.name);
        buf.writeInt(this.colour);
        buf.writeInt(this.pinNum);
        buf.writeBoolean(this.remove);
        buf.writeBoolean(this.edit);
    }
    public static class Handler implements IMessageHandler<BiblioMapPin, IMessage> {

        @Override
        public IMessage onMessage(BiblioMapPin message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    World world = player.worldObj;
                    TileEntity tile = world.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityMapFrame) {
                        TileEntityMapFrame mapFrame = (TileEntityMapFrame) tile;
                        if (!message.remove) {
                            if (!message.edit) {
                                mapFrame.addPinCoords(message.xPin, message.yPin, message.name, message.colour);
                            } else {
                                mapFrame.editPinData(message.name, message.colour, message.pinNum);
                            }
                        } else {
                            mapFrame.removePin(message.pinNum);
                        }
                    }
                }
            return null;
        }

    }
}
