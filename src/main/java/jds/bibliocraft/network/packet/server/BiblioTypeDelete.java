package jds.bibliocraft.network.packet.server;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.helpers.FileUtil;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class BiblioTypeDelete implements IMessage {
    String bookname;
    public BiblioTypeDelete() {

    }
    public BiblioTypeDelete(String bookname) {
        this.bookname = bookname;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.bookname = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.bookname);
    }
    public static class Handler implements IMessageHandler<BiblioTypeDelete, IMessage> {

        @Override
        public IMessage onMessage(BiblioTypeDelete message, MessageContext ctx) {
                FileUtil util = new FileUtil();
                boolean isServer = FMLCommonHandler.instance().getSide() == Side.SERVER;
                if (util.deleteBook(!isServer, message.bookname)) {
                    BiblioCraft.LOGGER.info(message.bookname + " has been deleted FOREVER!");
                } else {
                    BiblioCraft.LOGGER.warn("Deletion of " + message.bookname + " failed.");
                    // FMLLog.warning
                }
            return null;
        }

    }
}
