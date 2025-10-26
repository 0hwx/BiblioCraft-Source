package jds.bibliocraft.network.packet.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityMapFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioAtlasTGUI implements IMessage {
    ItemStack atlas;
    int posX;
    int posY;
    int posZ;

    public BiblioAtlasTGUI() {

    }

    public BiblioAtlasTGUI(ItemStack atlas, int posX, int posY, int posZ) {
        this.atlas = atlas;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.atlas = ByteBufUtils.readItemStack(buf);
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.atlas);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }

    public static class Handler implements IMessageHandler<BiblioAtlasTGUI, IMessage> {

        @Override
        public IMessage onMessage(BiblioAtlasTGUI message, MessageContext ctx) {
            Minecraft.getMinecraft().func_152344_a(() ->
            {
            	handleAtlas(message.atlas, message.posX, message.posY, message.posZ);
            });
            return null;
        }

    }

    @SideOnly(Side.CLIENT)
    public static void handleAtlas(ItemStack atlas, int posX, int posY, int posZ)
    {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        final TileEntityMapFrame tile = (TileEntityMapFrame) player.worldObj.getTileEntity(posX, posY, posZ);
        if (tile != null)
        {
            Utils.openWaypointTransferGUI(Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer, atlas, tile);
        }
    }
}
