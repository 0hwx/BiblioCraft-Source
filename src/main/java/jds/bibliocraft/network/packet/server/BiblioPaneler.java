package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityFurniturePaneler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioPaneler implements IMessage {
    String texName;
    int posX;
    int posY;
    int posZ;

    public BiblioPaneler() {

    }
    public BiblioPaneler(String texName, int posX, int posY, int posZ) {
        this.texName = texName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.texName = ByteBufUtils.readUTF8String(buf);
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.texName);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }
    public static class Handler implements IMessageHandler<BiblioPaneler, IMessage> {

        @Override
        public IMessage onMessage(BiblioPaneler message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    TileEntity tile = player.worldObj.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityFurniturePaneler) {
                        TileEntityFurniturePaneler paneler = (TileEntityFurniturePaneler) tile;
                        paneler.setCustomCraftingTex(message.texName);
                    }
                }
            return null;
        }

    }
}
