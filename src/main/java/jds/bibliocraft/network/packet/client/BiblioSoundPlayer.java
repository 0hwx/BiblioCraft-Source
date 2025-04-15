package jds.bibliocraft.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.client.event.sound.SoundEvent;


public class BiblioSoundPlayer implements IMessage
{

    int positionX;
    int positionY;
    int positionZ;
	float volume;
	float pitch;
	String theSound; // it might be better to just pass along an id to grab a reference to a preloaded sound, but well see if this works first, it's simpler.

	public BiblioSoundPlayer()
	{

	}

	public BiblioSoundPlayer(String sound, int x, int y, int z, float vol, float pit)
	{
		this.theSound = sound;
        this.positionX = x;
        this.positionY = y;
        this.positionZ = z;
		this.volume = vol;
		this.pitch = pit;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.theSound = ByteBufUtils.readUTF8String(buf);
        this.positionX = buf.readInt();
        this.positionY = buf.readInt();
        this.positionZ = buf.readInt();
		this.volume = buf.readFloat();
		this.pitch = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, theSound);
        buf.writeInt(positionX);
        buf.writeInt(positionY);
        buf.writeInt(positionZ);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
	}

    public static class Handler implements IMessageHandler<BiblioSoundPlayer, IMessage>
    {
        @Override
        public IMessage onMessage(BiblioSoundPlayer message, MessageContext ctx)
        {

            Minecraft.getMinecraft().func_152344_a(() ->
            {
            	PlaySound(message.theSound, message.positionX, message.positionY, message.positionZ, message.volume, message.pitch);
            });
			return null;
        }
    }

    public static void PlaySound(String soundString, int x, int y, int z, float vol, float pit)
    {
    	Minecraft.getMinecraft().theWorld.playSound(x, y, z, soundString,  vol, pit, false);
    }

}
