package jds.bibliocraft.network.packet.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jds.bibliocraft.items.ItemAtlas;
import jds.bibliocraft.network.packet.Utils;
import jds.bibliocraft.tileentities.TileEntityMapFrame;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;


// Doesn't crash but I have no idea how to work the atlas so I am not sure if it's fully working!
public class BiblioAtlasWPT implements IMessage {
    boolean toMapFrame;
    int posX;
    int posY;
    int posZ;
    ItemStack atlasStack;

    // dummy constructor for FML
    public BiblioAtlasWPT() {

    }

    public BiblioAtlasWPT(boolean toMapFrame, int posX, int posY, int posZ, ItemStack atlasStack) {
        this.toMapFrame = toMapFrame;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.atlasStack = atlasStack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.toMapFrame = buf.readBoolean();
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.atlasStack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.toMapFrame);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        ByteBufUtils.writeItemStack(buf, this.atlasStack);
    }

    public static class Handler implements IMessageHandler<BiblioAtlasWPT, IMessage> {

        @Override
        public IMessage onMessage(BiblioAtlasWPT message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                if (Utils.hasPointLoaded(player, message.posX, message.posY, message.posZ)) {
                    TileEntity tile = player.worldObj.getTileEntity(message.posX, message.posY, message.posZ);
                    if (tile != null && tile instanceof TileEntityMapFrame && message.atlasStack != null
                            && message.atlasStack.getItem() instanceof ItemAtlas) {
                        TileEntityMapFrame frameTile = (TileEntityMapFrame) tile;
                        if (message.toMapFrame) {
                            transferWaypointsToMapFrame(frameTile, message.atlasStack);
                            // player.worldObj.markBlockForUpdate(frameTile.getPos());
                            frameTile.getWorldObj().markBlockForUpdate(frameTile.xCoord, frameTile.yCoord, frameTile.zCoord);
                        } else {
                            transferWaypointsToAtlas(frameTile, message.atlasStack, player);
                        }
                    }
                }
            return null;
        }

        private void transferWaypointsToMapFrame(TileEntityMapFrame frameTile, ItemStack atlasStack) {
            InventoryBasic inv = Utils.getInventory(atlasStack);
            NBTTagCompound atlasTags = atlasStack.getTagCompound();
            ItemStack mapStack = Utils.getCurrentMapStack(atlasStack);
            if (atlasTags != null && inv != null && mapStack != null && atlasTags.hasKey("maps")) {
                NBTTagList maps = atlasTags.getTagList("maps", Constants.NBT.TAG_COMPOUND);
                NBTTagCompound mapTag = null;
                String mapName = "Map_" + mapStack.getItemDamage();
                for (int n = 0; n < maps.tagCount(); n++) {
                    mapTag = maps.getCompoundTagAt(n);
                    if (mapTag != null && mapTag.hasKey("mapName")) {
                        if (mapTag.getString("mapName").contentEquals(mapName)) {
                            frameTile.addMapPinDataFromAtlas(mapTag);

                            for (int i = 0; i < frameTile.getRotation(); i++) {
                                frameTile.rotateWaypoints();
                            }

                        }
                    }
                }
            }

        }

        private void transferWaypointsToAtlas(TileEntityMapFrame frameTile, ItemStack atlasStack,
                EntityPlayerMP player) {
            InventoryBasic inv = Utils.getInventory(atlasStack);
            ItemStack newMap = frameTile.getStackInSlot(0);
            NBTTagCompound tags = atlasStack.getTagCompound();
            if (inv != null && tags != null) {
                String newMapName = "Map_" + newMap.getItemDamage();
                NBTTagList atlasMapsDatas = new NBTTagList();
                if (tags.hasKey("maps")) {
                    atlasMapsDatas = tags.getTagList("maps", Constants.NBT.TAG_COMPOUND);

                    for (int i = 0; i < atlasMapsDatas.tagCount(); i++) {
                        NBTTagCompound testTag = atlasMapsDatas.getCompoundTagAt(i);
                        if (testTag != null && testTag.hasKey("mapName")) {
                            String oldMap = testTag.getString("mapName");
                            if (oldMap.contentEquals(newMapName)) {
                                atlasMapsDatas.removeTag(i);
                            }
                        }
                    }
                }
                atlasMapsDatas.appendTag(Utils.getNewMapDataCompound(frameTile, newMapName));
                tags.setTag("maps", atlasMapsDatas);
                atlasStack.setTagCompound(tags);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, atlasStack);
            }
        }

    }
}
