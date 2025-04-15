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

// UNTESTED
public class BiblioTypeFlag implements IMessage {
    String bookname;
    boolean newFlag;

    public BiblioTypeFlag() {

    }
    public BiblioTypeFlag(String bookname, boolean newFlag) {
        this.bookname = bookname;
        this.newFlag = newFlag;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.bookname = ByteBufUtils.readUTF8String(buf);
        this.newFlag = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.bookname);
        buf.writeBoolean(this.newFlag);
    }
    public static class Handler implements IMessageHandler<BiblioTypeFlag, IMessage> {

        @Override
        public IMessage onMessage(BiblioTypeFlag message, MessageContext ctx) {
                boolean isServer = FMLCommonHandler.instance().getSide() == Side.SERVER;
                FileUtil util = new FileUtil();
                if (!util.updatePublicFlag(!isServer, message.bookname, message.newFlag)) {
                    BiblioCraft.LOGGER.warn("Updating book flag for " + message.bookname + " failed");
                }
            return null;
        }

    }
}
