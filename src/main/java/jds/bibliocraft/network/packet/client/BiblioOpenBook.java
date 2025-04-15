package jds.bibliocraft.network.packet.client;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.gui.GuiRecipeBook;
import jds.bibliocraft.items.ItemRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiblioOpenBook implements IMessage {
    boolean canCraft;

    public BiblioOpenBook() {

    }

    public BiblioOpenBook(boolean canCraft) {
        this.canCraft = canCraft;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.canCraft = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.canCraft);
    }

    public static class Handler implements IMessageHandler<BiblioOpenBook, IMessage>
    {

        @Override
        public IMessage onMessage(BiblioOpenBook message, MessageContext ctx)
        {
            boolean canCraft = message.canCraft;
            Minecraft.getMinecraft().func_152344_a(() ->
            {
            	// this fixes it.
            	openBook(canCraft);
            });
            return null;
        }

    }

	@SideOnly(Side.CLIENT)
    public static void openBook(boolean canCraft)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        ItemStack stackMain = player.getHeldItem();

        if (stackMain.getItem() == ItemRecipeBook.instance)
        {
            Minecraft.getMinecraft().displayGuiScreen( new GuiRecipeBook(stackMain, false, 0, 0, 0, player.inventory.currentItem, canCraft));
        }
    }
}
