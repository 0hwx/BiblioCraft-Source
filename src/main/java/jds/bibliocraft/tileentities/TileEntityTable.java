package jds.bibliocraft.tileentities;

import java.util.Iterator;
import java.util.List;

import jds.bibliocraft.Config;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class TileEntityTable extends BiblioTileEntity
{
//	public boolean leg1 = false;
//	public boolean leg2 = false;
//	public boolean leg3 = false;
//	public boolean leg4 = false;
//	public boolean top1 = true;
//	public boolean top2 = true;
//	public boolean top3 = true;
//	public boolean top4 = true;
//	public boolean exps1 = false;
//	public boolean exps2 = false;
//	public boolean exps3 = false;
//	public boolean exps4 = false;
//	public boolean monoleg = true;
	public int slotxangle = 0;
	public int slotyangle = 0;
	public boolean hasMap = false;
	private int counter = 1;
	public EntityItemFrame fauxFrame;// = new EntityItemFrame(this.worldObj);
	public int redstonePassthrough = 0;

	public boolean isVanilla = true;

    // This single variable replaces all the leg/top/side booleans
    // 0=none, 1=one, 2=straight, 3=curve, 4=three, 5=four
    private int connectionState = 0;

	public TileEntityTable()
	{
        // Slot 0: Main item (or map)
        // Slot 1: Table cloth
        // Slot 2: Carpet
		super(3, true);
	}

    public int getConnectionState() {
        return this.connectionState;
    }

    public void setConnectionState(int state) {
        this.connectionState = state;
    }

	public boolean getHasMap()
	{
		return hasMap;
	}

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    public boolean setTableSlot(ItemStack stack)
	{
		boolean hasStack;
		if (stack == null)
		{
			if (isSlotFull())
			{
				setInventorySlotContents(0, null);
			}
			hasStack = false;
			return hasStack;
		}

		if (!isSlotFull())
		{
			setInventorySlotContents(0, stack);
			hasStack = true;
		}
		else
		{
			hasStack = false;
		}
		return hasStack;
	}

	public boolean isSlotFull()
	{
		if (getStackInSlot(0) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isClothSlotFull()
	{
		if (getStackInSlot(1)!= null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isCarpetFull()
	{
		if (getStackInSlot(2) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public ItemStack getCaseStack()
	{
		if (isSlotFull())
		{
			return getStackInSlot(0);
		}
		else
		{
			return null;
		}
	}

	public int setTableCloth(ItemStack stack)
	{
		int stacksize = 0;
		if (stack == null)
		{
			if(isClothSlotFull())
			{
				setInventorySlotContents(1, null);
			}
			stacksize = -1;
			return stacksize;
		}

		if (!isClothSlotFull())
		{
			if (stack.stackSize > 1)
			{
				stacksize = stack.stackSize - 1;
			}
			else
			{
				stacksize = 0;
			}
			ItemStack carpetpiece = stack.copy();
			carpetpiece.stackSize = (1);
			setInventorySlotContents(1, carpetpiece);
		}
		else
		{
			stacksize = stack.stackSize;
		}

		return stacksize;
	}
	public int getClothMetaData()
	{
		if(isClothSlotFull())
		{
			ItemStack carpet = getStackInSlot(1);
			return carpet.getItemDamage();
		}
		else
		{
			return -1;
		}
	}

	public int setCarpet(ItemStack stack)
	{
		int stacksize = 0;
		if (stack == null)
		{
			if(isCarpetFull())
			{
				setInventorySlotContents(2, null);
			}
			stacksize = -1;
			return stacksize;
		}

		if (!isCarpetFull())
		{
			if (stack.stackSize > 1)
			{
				stacksize = stack.stackSize - 1;
			}
			else
			{
				stacksize = 0;
			}
			ItemStack carpetpiece = stack.copy();
			carpetpiece.stackSize = (1);
			setInventorySlotContents(2, carpetpiece);
		}
		else
		{
			stacksize = stack.stackSize;
		}
		return stacksize;
	}

	public int getCarpetMetaData()
	{
		if(isCarpetFull())
		{
			ItemStack carpet = getStackInSlot(2);
			return carpet.getItemDamage();
		}
		else
		{
			return -1;
		}
	}

	public void setSlotX(int angle)
	{
		slotxangle = angle;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public void setSlotY(int angle)
	{
		slotyangle = angle;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public int getSlotX()
	{
		return slotxangle;
	}
	public int getSlotY()
	{
		return slotyangle;
	}

	public void setFrame(World world)
	{
		fauxFrame = new EntityItemFrame(world);
	}

	@Override
	public void updateEntity()
	{
		if (!this.worldObj.isRemote)
		{
			if (counter >= Config.mapUpdateRate)
			{
				counter = 1;
				if (hasMap)
				{
					if (this.fauxFrame == null)
					{
						if (this.worldObj != null)
						{
							this.setFrame(this.worldObj);
						}
						return;
					}
					List players = this.worldObj.playerEntities;
					ItemStack mapstack = getStackInSlot(0);

					mapstack.setItemFrame(fauxFrame);
					MapData mapdata =Items.filled_map.getMapData(mapstack, this.worldObj);

		            Iterator iterator = players.iterator();
		            while (iterator.hasNext())
		            {
		                 EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
		                Items.filled_map.updateMapData(getWorldObj(), entityplayermp, mapdata);
	                     byte[] packet = mapdata.getUpdatePacketData(mapstack, getWorldObj(), entityplayermp);
	                     if (packet != null)
	                     {
//	                         entityplayermp.playerNetServerHandler.sendPacket(packet);
	                     }
		            }
				}
			}
			else
			{
				counter++;
			}
		}

	}

//	@Override
//	public String getName()
//	{
//		return BlockTable.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{
		if(stack != null)
		{
			if (stack.getItem() ==Items.filled_map)
			{
				hasMap = true;
			}
			else
			{
				hasMap = false;
			}
		}
		else
		{
			hasMap = false;
		}
	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.slotxangle = nbt.getInteger("slotxangle");
		this.slotyangle = nbt.getInteger("slotyangle");
		this.hasMap = nbt.getBoolean("hasMap");
		this.isVanilla = nbt.getBoolean("isVanilla");
        this.connectionState = nbt.getInteger("ConnState");
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
    	nbt.setInteger("slotxangle", slotxangle);
    	nbt.setInteger("slotyangle", slotyangle);
    	nbt.setBoolean("hasMap", hasMap);
    	nbt.setBoolean("isVanilla", this.isVanilla);
        nbt.setInteger("ConnState", this.connectionState);
		return nbt;
	}

//	@Override
//	public ITextComponent getDisplayName()
//	{
//		ITextComponent chat = new ChatComponentText(getName());
//		return chat;
//	}

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }
}
