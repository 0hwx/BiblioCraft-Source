package jds.bibliocraft.network.packet.client;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import jds.bibliocraft.helpers.BiblioSortingHelper;
import jds.bibliocraft.helpers.SortedListItem;
import jds.bibliocraft.network.packet.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioStockLog implements IMessage {
    NBTTagCompound tags;

    public BiblioStockLog() {

    }

    public BiblioStockLog(NBTTagCompound tags) {
        this.tags = tags;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.tags = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.tags);
    }

    public static class Handler implements IMessageHandler<BiblioStockLog, IMessage> {

        @Override
        public IMessage onMessage(BiblioStockLog message, MessageContext ctx) {
            Minecraft.getMinecraft().func_152344_a(() -> {
                NBTTagCompound tags = message.tags;
                if (tags != null) {
                    NBTTagList comp = tags.getTagList("compasses", Constants.NBT.TAG_COMPOUND);
                    int[] compasses = { -1, -1, -1, -1, -1, -1, -1, -1 };
                    ItemStack[] compassStacks = { null, null, null, null,
                            null, null, null, null };

                    for (int i = 0; i < comp.tagCount(); i++) {
                        NBTTagCompound compTag = comp.getCompoundTagAt(i);
                        if (compTag != null) {
                            String invSlotName = "slot" + i;
                            int compSlot = compTag.getInteger(invSlotName);
                            compasses[i] = compSlot;
                            if (compSlot != -1) {
                                compassStacks[i] = ItemStack.loadItemStackFromNBT(compTag);
                            }
                        }
                    }

                    NBTTagList alphaTagList = tags.getTagList("alphaList", Constants.NBT.TAG_COMPOUND);
                    final ArrayList<SortedListItem> alphaList = BiblioSortingHelper
                            .convertNBTTagListToArrayList(alphaTagList);
                    NBTTagList quanaTagList = tags.getTagList("quantaList", Constants.NBT.TAG_COMPOUND);
                    final ArrayList<SortedListItem> quantaList = BiblioSortingHelper
                            .convertNBTTagListToArrayList(quanaTagList);
                    final int[] finalCompasses = compasses;
                    final ItemStack[] finalCompassStacks = compassStacks;
                    final String title = tags.getString("title");
                    // openCatalogGUI(player, alphaList, quantaList, compassStacks, compasses,
                    // tags.getString("title"));
                    Minecraft.getMinecraft().func_152344_a(new Runnable() {

                        @Override
                        public void run() {
                            Utils.openCatalogGUI(Minecraft.getMinecraft().thePlayer, alphaList, quantaList,
                                    finalCompassStacks,
                                    finalCompasses, title);
                        }
                    });
                }
            });
            return null;
        }

    }
}
