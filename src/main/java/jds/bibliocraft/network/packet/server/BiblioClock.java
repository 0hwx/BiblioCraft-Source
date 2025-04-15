package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityClock;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioClock implements IMessage {
    NBTTagCompound tag;
    boolean tick;
    boolean chime;
    boolean rsout;
    boolean rspulse;
    int posX;
    int posY;
    int posZ;

    public BiblioClock() {

    }

    public BiblioClock(NBTTagCompound tag, boolean tick, boolean chime, boolean rsout, boolean rspulse, int posX, int posY, int posZ) {
        this.tag = tag;
        this.tick = tick;
        this.chime = chime;
        this.rsout = rsout;
        this.rspulse = rspulse;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.tag = ByteBufUtils.readTag(buf);
        this.tick = buf.readBoolean();
        this.chime = buf.readBoolean();
        this.rsout = buf.readBoolean();
        this.rspulse = buf.readBoolean();
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.tag);
        buf.writeBoolean(this.tick);
        buf.writeBoolean(this.chime);
        buf.writeBoolean(this.rsout);
        buf.writeBoolean(this.rspulse);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }

    public static class Handler implements IMessageHandler<BiblioClock, IMessage> {

        @Override
        public IMessage onMessage(BiblioClock message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    World world = player.worldObj;
                    int[] chimes = message.tag.getIntArray("chimes");
                    int[] redstone = message.tag.getIntArray("redstone");

                    TileEntity tile = world.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityClock) {
                        TileEntityClock clock = (TileEntityClock) tile;
                        clock.setSettingFromGui(chimes, redstone, message.tick, message.chime, message.rsout,
                                message.rspulse);
                    }
                }
            return null;
        }

    }
}
