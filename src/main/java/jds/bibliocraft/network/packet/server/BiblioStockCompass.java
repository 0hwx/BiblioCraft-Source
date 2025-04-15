package jds.bibliocraft.network.packet.server;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.items.ItemWaypointCompass;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioStockCompass implements IMessage {
    int slotNumber;
    String title;
    int x;
    int z;

    public BiblioStockCompass() {

    }

    public BiblioStockCompass(int slotNumber, String title, int x, int z) {
        this.slotNumber = slotNumber;
        this.title = title;
        this.x = x;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.slotNumber = buf.readInt();
        this.title = ByteBufUtils.readUTF8String(buf);
        this.x = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.slotNumber);
        ByteBufUtils.writeUTF8String(buf, this.title);
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    public static class Handler implements IMessageHandler<BiblioStockCompass, IMessage> {

        @Override
        public IMessage onMessage(BiblioStockCompass message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (message.slotNumber < player.inventory.getSizeInventory()) {
                    ItemStack compass = player.inventory.getStackInSlot(message.slotNumber);
                    if (compass != null && compass.getItem() instanceof ItemWaypointCompass) {
                        NBTTagCompound tags = compass.getTagCompound();
                        if (tags == null) {
                            tags = new NBTTagCompound();
                        }
                        tags.setInteger("XCoord", message.x);
                        tags.setInteger("ZCoord", message.z);
                        tags.setString("WaypointName", message.title);
                        compass.setTagCompound(tags);
                        player.inventory.setInventorySlotContents(message.slotNumber, compass);
                    }
                }
            return null;
        }

    }
}
