package jds.bibliocraft.network.packet.client;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.helpers.BiblioRenderHelper;
import jds.bibliocraft.network.BiblioNetworking;
import jds.bibliocraft.network.packet.server.BiblioPaneler;
import jds.bibliocraft.tileentities.TileEntityFurniturePaneler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioPanelerClient implements IMessage {
    ItemStack panels;
    int posX;
    int posY;
    int posZ;


    public BiblioPanelerClient() {

    }

    public BiblioPanelerClient(ItemStack panels, int posX, int posY, int posZ) {

        this.panels = panels;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.panels = ByteBufUtils.readItemStack(buf);
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.panels);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }

    public static class Handler implements IMessageHandler<BiblioPanelerClient, IMessage> {

        @Override
        public IMessage onMessage(BiblioPanelerClient message, MessageContext ctx) {
            Minecraft.getMinecraft().func_152344_a(() -> {
                EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
                String panelTextureName = "none";
                if (message.panels != null) {
                    panelTextureName = BiblioRenderHelper.getBlockTextureString(message.panels);
                }

                TileEntity tile = player.worldObj.getTileEntity(message.posX, message.posY, message.posZ);
                if (tile != null && tile instanceof TileEntityFurniturePaneler) {
                    TileEntityFurniturePaneler paneler = (TileEntityFurniturePaneler) tile;
                    paneler.setCustomCraftingTex(panelTextureName);
                }
                BiblioNetworking.INSTANCE.sendToServer(new BiblioPaneler(panelTextureName, message.posX, message.posY, message.posZ));
            });
            return null;
        }

    }
}
