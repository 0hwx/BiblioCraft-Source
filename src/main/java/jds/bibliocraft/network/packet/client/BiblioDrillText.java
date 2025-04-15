package jds.bibliocraft.network.packet.client;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.items.ItemDrill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioDrillText implements IMessage {
    String displayText;
    public BiblioDrillText() {

    }
    public BiblioDrillText(String displayText) {
        this.displayText = displayText;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.displayText = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.displayText);
    }
    public static class Handler implements IMessageHandler<BiblioDrillText, IMessage> {

        @Override
        public IMessage onMessage(BiblioDrillText message, MessageContext ctx) {
            Minecraft.getMinecraft().func_152344_a(() -> {
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                ItemStack playerhand = player.getHeldItem();
                if (playerhand != null && playerhand.getItem() instanceof ItemDrill)
                {
                    ItemDrill drill = (ItemDrill)playerhand.getItem();
                    drill.updateFromPacket(message.displayText);
                }
            });
            return null;
        }

    }
}
