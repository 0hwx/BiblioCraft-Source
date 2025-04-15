package jds.bibliocraft.tileentities;

import jds.bibliocraft.blocks.BlockFancySign;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityFancySign extends BiblioTileEntity
{
	public String[] text = new String[15];
	public int[] textScale = new int[15];
	public int numOfLines = 10;
	public int slot1Scale = 1;
	public int slot1Rot = 0;
	public int slot1X = 0;
	public int slot1Y = 0;
	public int slot2Scale = 1;
	public int slot2Rot = 0;
	public int slot2X = 0;
	public int slot2Y = 0;

	public TileEntityFancySign()
	{
		// place up to 4 items in sign to render on sign in assigned positions.? maybe. need a container for this.
		super(2, true);
		for (int n = 0; n<15; n++)
		{
			this.text[n] = "";
			this.textScale[n] = 1;
		}
	}

	public boolean doesSignContainUserData()
	{
		boolean value = false;
		for (int i = 0; i < this.inventory.length; i++)
		{
			if (this.inventory[i] != null)
			{
				value = true;
				break;
			}
		}
		if (value == false)
		{
			for (int i = 0; i < this.text.length; i++)
			{
				if (this.text[i] != null)
				{
					// remove all the spaces from text and THEN test the length, this will ensure spaces are irrelevent
					String thetext = this.text[i].replace(" ", "");
					if (thetext.length() > 0)
					{
						value = true;
						break;
					}
				}
			}
		}
		return value;
	}


	public void updateFromPacket(String[] texts, int[] scales, int numoflines, int s1scale, int s1rot, int s1x, int s1y, int s2scale, int s2rot, int s2x, int s2y)
	{
		this.text = texts;
		this.textScale = scales;
		this.numOfLines = numoflines;
		this.slot1Scale = s1scale;
		this.slot1Rot = s1rot;
		this.slot1X = s1x;
		this.slot1Y = s1y;
		this.slot2Scale = s2scale;
		this.slot2Rot = s2rot;
		this.slot2X = s2x;
		this.slot2Y = s2y;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
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
		return 1;
	}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return false;
	}


//	@Override
//	public String getName()
//	{
//		return BlockFancySign.name;
//	}


	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{
	}


	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.slot1Scale = nbt.getInteger("s1scale");
		this.slot1Rot = nbt.getInteger("s1rot");
		this.slot1X = nbt.getInteger("s1x");
		this.slot1Y = nbt.getInteger("s1y");
		this.slot2Scale = nbt.getInteger("s2scale");
		this.slot2Rot = nbt.getInteger("s2rot");
		this.slot2X = nbt.getInteger("s2x");
		this.slot2Y = nbt.getInteger("s2y");
		this.numOfLines = nbt.getInteger("numoflines");
		this.textScale = nbt.getIntArray("textscale");
		for (int n = 0; n<15; n++)
		{
			this.text[n] = nbt.getString("text"+n);
		}
	}


	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
    	nbt.setInteger("s1scale", this.slot1Scale);
    	nbt.setInteger("s1rot", this.slot1Rot);
    	nbt.setInteger("s1x", this.slot1X);
    	nbt.setInteger("s1y", this.slot1Y);
    	nbt.setInteger("s2scale", this.slot2Scale);
    	nbt.setInteger("s2rot", this.slot2Rot);
    	nbt.setInteger("s2x", this.slot2X);
    	nbt.setInteger("s2y", this.slot2Y);
    	nbt.setInteger("numoflines", this.numOfLines);
    	nbt.setIntArray("textscale", this.textScale);
    	for (int n = 0; n<15; n++)
    	{
    		nbt.setString("text"+n, this.text[n]);
    	}
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
