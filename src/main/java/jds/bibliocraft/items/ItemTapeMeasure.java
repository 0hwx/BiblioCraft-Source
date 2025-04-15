package jds.bibliocraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.blocks.BlockMarkerPole;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.network.BiblioNetworking;
import jds.bibliocraft.network.packet.server.BiblioMeasure;
import jds.bibliocraft.tileentities.TileEntityMarkerPole;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


//import net.minecraft.network.packet.Packet;
//import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemTapeMeasure extends Item
{
	public static final String name = "tapeMeasure";
	public static final ItemTapeMeasure instance = new ItemTapeMeasure();

	private int firstMeasurex = 0;
	private int firstMeasurey = 0;
	private int firstMeasurez = 0;
	private int oldx = 0;
	private int oldy = 0;
	private int oldz = 0;
	private ForgeDirection oldface = ForgeDirection.NORTH;

	private int mode = 1;

	private int ticktime = 0;

	public ItemTapeMeasure()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setUnlocalizedName(name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
            ForgeDirection direction = ForgeDirection.getOrientation(side);
			if (firstMeasurex != 0 || firstMeasurey != 0 || firstMeasurez != 0)
			{

				int xdist = Math.abs(firstMeasurex - x);
				int ydist = Math.abs(firstMeasurey - y);
				int zdist = Math.abs(firstMeasurez - z);
				xdist++;
				zdist++;
				if (direction != ForgeDirection.DOWN && direction != ForgeDirection.UP)
				{
					ydist = ydist + 1;
				}
				int measurmentxz = (int) Math.sqrt((xdist*xdist)+(zdist*zdist));
				int measurmentxy = (int) Math.sqrt((xdist*xdist)+(ydist*ydist));
				int measurmentyz = (int) Math.sqrt((ydist*ydist)+(zdist*zdist));

				if (mode == 1)
				{
						if (zdist != 1)
						{
							player.addChatMessage(new ChatComponentText(I18n.format("tape.measurenorthsouth")+zdist)); //Measurement North/South =
						}
						if (xdist != 1)
						{
							player.addChatMessage(new ChatComponentText(I18n.format("tape.measureeastwest")+xdist)); //Measurement East/West =
						}
						if (ydist != 0)
						{
							player.addChatMessage(new ChatComponentText(I18n.format("tape.measureheight")+ydist)); //Measurement Height =
						}
						if (xdist == 1 && zdist == 1 && ydist == 0)
						{
							player.addChatMessage(new ChatComponentText(I18n.format("tape.nomeasure")));             //No Measurement
						}


				}
			   if (mode == 0)
				{
					if (ydist == 0)
					{
						player.addChatMessage(new ChatComponentText(I18n.format("tape.measure")+measurmentxz)); //Measurement =
					}
					else if (xdist == 0)
					{
						player.addChatMessage(new ChatComponentText(I18n.format("tape.measure")+measurmentyz));  //Measurement =
					}
					else if (zdist == 0)
					{
						player.addChatMessage(new ChatComponentText(I18n.format("tape.measure")+measurmentxy));  //Measurement =
					}
					else
					{
						int euclideon = (int) Math.sqrt((xdist*xdist)+(ydist*ydist)+(zdist*zdist));
						player.addChatMessage(new ChatComponentText(I18n.format("tape.measure")+euclideon));  //Measurement =
					}
				}
			    player.playSound(CommonProxy.SOUND_TAPE_CLOSE, 1.0F, 1.0F);
			    oldx = firstMeasurex;
			    oldy = firstMeasurey;
			    oldz = firstMeasurez;
				firstMeasurex = 0;
				firstMeasurey = 0;
				firstMeasurez = 0;
				sendPacket(false, oldx, oldy, oldz, oldface);
			}
			else
			{
				player.playSound(CommonProxy.SOUND_TAPE_OPEN, 1.0F, 1.0F);
				player.addChatMessage(new ChatComponentText(I18n.format("tape.startmeasure")));  //Starting Measurement.
				firstMeasurex = x;
				firstMeasurey = y;
				firstMeasurez = z;
				oldface = direction;
				sendPacket(true, firstMeasurex, firstMeasurey, firstMeasurez, direction);
				placeBlock(world, firstMeasurex, firstMeasurey, firstMeasurez, direction);
			}
		}
		return true;
	}

	public void sendPacket(boolean newOrOld, int i, int j, int k, ForgeDirection direction)
	{
        // ByteBuf buffer = Unpooled.buffer();
        try
        {
			BiblioNetworking.INSTANCE.sendToServer(new BiblioMeasure(i, j, k, newOrOld, direction.ordinal()));
        	// buffer.writeInt(i);
        	// buffer.writeInt(j);
        	// buffer.writeInt(k);
        	// buffer.writeBoolean(newOrOld);
        	// buffer.writeInt(direction.getIndex());
        	// BiblioCraft.ch_BiblioMeasure.sendToServer(new FMLProxyPacket(new PacketBuffer(buffer), "BiblioMeasure"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

	}

	private void placeBlock(World world, int x, int y, int z, ForgeDirection facing)
	{
		int xadj = 0;
		int yadj = 0;
		int zadj = 0;
		switch (facing)
		{
		case DOWN: yadj = -1; break;
		case UP: yadj = 1;  break;
		case NORTH: zadj = -1; break;
		case SOUTH: zadj = 1;  break;
		case WEST: xadj = -1; break;
		case EAST: xadj = 1;  break;
		default: xadj = 1; break;
		}

        int posX = x+xadj;
        int posY = y+yadj;
        int posZ = z+zadj;

		Block st = BlockMarkerPole.instance;
		world.setBlock(posX, posY, posZ, st);
		TileEntityMarkerPole poleTile = (TileEntityMarkerPole)world.getTileEntity(posX, posY, posZ);
		if (poleTile != null)
		{
			poleTile.setAngle(ForgeDirection.NORTH);
			if (facing == ForgeDirection.UP)
			{
				poleTile.setVertPosition(EnumVertPosition.FLOOR);
			}
			else if (facing == ForgeDirection.DOWN)
			{
				poleTile.setVertPosition(EnumVertPosition.CEILING);
			}
			else
			{
				switch (facing)
				{
					case NORTH: {facing = ForgeDirection.WEST; break;}
					case WEST: {facing = ForgeDirection.SOUTH; break;}
					case SOUTH: {facing = ForgeDirection.EAST; break;}
					case EAST: {facing = ForgeDirection.NORTH; break;}
					default: break;
				}
				poleTile.setAngle(facing);
				poleTile.setVertPosition(EnumVertPosition.WALL);
			}
			world.markBlockRangeForRenderUpdate(posX, posY, posZ, posX, posY, posZ);
		}
	}

	public void setMeasurments(boolean newOrOld, int i, int j, int k)
	{
		if (newOrOld)
		{
			firstMeasurex = i;
			firstMeasurey = j;
			firstMeasurez = k;
		}
		else
		{
			oldx = i;
			oldy = j;
			oldz = k;
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote)
		{
			if (mode == 0)
			{
				player.addChatMessage(new ChatComponentText(I18n.format("tape.mode0")));  //Switching mode to North/South, East/West, and height.
				mode = 1;
			}
			else if (mode == 1)
			{
				player.addChatMessage(new ChatComponentText(I18n.format("tape.mode1")));  //Switching mode to absolute measurment.
				mode = 0;
			}
		}
		return stack;
	}

	@Override
	 public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5)
	 {
		if (ticktime > 19)
		{
			NBTTagCompound tapem = itemstack.getTagCompound();
			if (tapem == null)
			{
				tapem = new NBTTagCompound();
				tapem.setInteger("distance", 0);
			}
			if(firstMeasurex != 0 || firstMeasurey != 0 || firstMeasurez != 0)
			{
				int currentx = (int)entity.posX;
				int currenty = (int)entity.posY;
				int currentz = (int)entity.posZ;
				if (currentx <  0)
				{
					currentx = currentx - 1;
				}
				if (currentz <  0)
				{
					currentz = currentz - 1;
				}
				int xdist;
				int ydist;
				int zdist;
				if (firstMeasurex > currentx)
				{
					xdist = Math.abs(firstMeasurex - currentx);
				}
				else
				{
					xdist = Math.abs(currentx - firstMeasurex);
				}
				if (firstMeasurey > currenty)
				{
					ydist = Math.abs(firstMeasurey - currenty);
				}
				else
				{
					ydist = Math.abs(currenty - firstMeasurey);
				}
				if (firstMeasurez > currentz)
				{
					zdist = Math.abs(firstMeasurez - currentz);
				}
				else
				{
					zdist = Math.abs(currentz - firstMeasurez);
				}

				xdist++;
				zdist++;
				int euclideon = (int) Math.sqrt((xdist*xdist)+(ydist*ydist)+(zdist*zdist));
				tapem.setInteger("distance", euclideon);
				itemstack.setTagCompound(tapem);
			}
			else
			{
				tapem.setInteger("distance", 0);
				itemstack.setTagCompound(tapem);
			}
			ticktime = 0;
		}
		else
		{
			ticktime++;
		}
	 }
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:tapemeasure");
    }
}
