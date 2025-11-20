package jds.bibliocraft.tileentities;

import jds.bibliocraft.CommonProxy;

import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBell extends BiblioTileEntity
{
	private int redstone = 0;
	private int counter = 0;

	public TileEntityBell()
	{
		super(0, false);
	}

//	@Override
//	public String getName()
//	{
//		return BlockBell.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack) { }

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt) {	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
		return nbt;
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
		return 0;
	}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

//    @Override
//	public ITextComponent getDisplayName()
//	{
//		ITextComponent chat = new ChatComponentText(getName());
//		return chat;
//	}

	@Override
    public void updateEntity()
	{
		if (counter >= 2)
		{
			int power = getWorldObj().getStrongestIndirectPower(xCoord, yCoord, zCoord);
			if (power > redstone)
			{
				getWorldObj().playSoundEffect(xCoord, yCoord, zCoord, CommonProxy.SOUND_DING, 1.0F, 1.0F);
			}
			redstone = power;
			counter = 0;
		}
		else
		{
			counter++;
		}
	}

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }
}
