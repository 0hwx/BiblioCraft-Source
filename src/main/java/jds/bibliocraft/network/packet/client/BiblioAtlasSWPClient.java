package jds.bibliocraft.network.packet.client;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.network.packet.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiblioAtlasSWPClient implements IMessage {
    ItemStack atlas;

    public BiblioAtlasSWPClient() {

    }

    public BiblioAtlasSWPClient(ItemStack atlas) {
        this.atlas = atlas;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.atlas = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.atlas);
    }

    public static class Handler implements IMessageHandler<BiblioAtlasSWPClient, IMessage>
    {

        @Override
        public IMessage onMessage(BiblioAtlasSWPClient message, MessageContext ctx)
        {
            Minecraft.getMinecraft().func_152344_a(() ->
            {
            	handleAtlas(message.atlas);
            });
            return null;
        }

    }

    @SideOnly(Side.CLIENT)
    public static void handleAtlas(ItemStack atlas)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        player.rotationPitch = 50.0f;
        Utils.openMapGUI(Minecraft.getMinecraft().thePlayer, atlas);
    }
}
