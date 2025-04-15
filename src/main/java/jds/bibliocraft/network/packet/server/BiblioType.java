package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityTypeMachine;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

// UNTESTED!!
public class BiblioType implements IMessage {
    String bookName;
    int posX;
    int posY;
    int posZ;
    // dummy constructor for FML
    public BiblioType() {

    }
    public BiblioType(String bookName, int posX, int posY, int posZ) {
        this.bookName = bookName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.bookName = ByteBufUtils.readUTF8String(buf);
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.bookName);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }
    public static class Handler implements IMessageHandler<BiblioType, IMessage> {

        @Override
        public IMessage onMessage(BiblioType message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    World world = player.worldObj;
                    TileEntity tile = world.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null) {
                        TileEntityTypeMachine typetile = (TileEntityTypeMachine) tile;
                        typetile.setBookname(message.bookName);
                    }
                }
            return null;
        }

    }
}
