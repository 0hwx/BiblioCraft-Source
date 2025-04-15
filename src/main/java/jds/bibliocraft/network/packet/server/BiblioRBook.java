package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityFancyWorkbench;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

// TODO: review. I removed the `id` part of the packet as it seems only to send the player ID, which we know on the server.
// Leading on, merge this and BiblioRBookLoad. I didn't notice earlier and it sounds like a pain to do now :P
public class BiblioRBook implements IMessage {
    int posX;
    int posY;
    int posZ;
    public BiblioRBook() {

    }

    public BiblioRBook(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
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

    public static class Handler implements IMessageHandler<BiblioRBook, IMessage> {

        @Override
        public IMessage onMessage(BiblioRBook message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    World world = player.worldObj;
                    // TODO: check pos range to plr
                    TileEntity tile = world.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityFancyWorkbench) {
                        TileEntityFancyWorkbench bench = (TileEntityFancyWorkbench) tile;
                        bench.setBookGrid(player.getEntityId());
                    }
                }
            return null;
        }

    }
}
