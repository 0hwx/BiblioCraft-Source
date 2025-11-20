package jds.bibliocraft.tileentities;

import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDiscRack extends BiblioTileEntity
{
	public boolean wallRotated = false;

	public TileEntityDiscRack()
	{
		super(9, false);
	}

	/*
	public boolean addDisc(int slot, ItemStack newDisc)
	{
		ItemStack slotStack = getStackInSlot(slot);
		if (slotStack == null)
		{
			setInventorySlotContents(slot, newDisc);
			return true;
		}
		else
		{
			return false;
		}
	}
	*/
	public void setWallRotation()
	{
		if (wallRotated)
		{
			wallRotated = false;
		}
		else
		{
			wallRotated = true;
		}
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public boolean getWallRotation()
	{
		return wallRotated;
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

    @Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		//    this has to do with buildcraft pipes / hoppers
		if (itemstack != null)
		{
			if (itemstack.getItem() instanceof ItemRecord || itemstack.getUnlocalizedName().contains("disc") || itemstack.getUnlocalizedName().contains("disk"))
			{
				return true;
			}
		}
		return false;
	}

//	@Override
//	public String getName()
//	{
//		return BlockDiscRack.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack) {}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.wallRotated = nbt.getBoolean("wallRotated");
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
		nbt.setBoolean("wallRotated", wallRotated);
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
