package jds.bibliocraft.tileentities;

import jds.bibliocraft.Config;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPotionShelf extends BiblioTileEntity
{
	public int showPotText = 0;

	public TileEntityPotionShelf()
	{
		super(12, true);
	}

	public boolean checkSlot(int slot)
    {
    	if (getStackInSlot(slot) != null)
    	{
    		return true;
    	}
    	return false;
    }

    public void setShowPotText(int pottext)
    {
    	showPotText = pottext;
    }
    public int getShowPotText()
    {
    	return showPotText;
    }

    public boolean addPotion(ItemStack pot, int slot)
    {
    	if (pot ==null && getStackInSlot(slot) != null)
    	{
    		setInventorySlotContents(slot, null);
    		return false;
    	}
    	if (pot != null && getStackInSlot(slot) == null)
    	{
    		setInventorySlotContents(slot, pot);
    		return true;

    	}
    	return false;
    }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		Item stackitem = itemstack.getItem();
		if (stackitem != null)
		{
			String stackName = stackitem.toString();
			String displayName = stackitem.getItemStackDisplayName(itemstack);
			if (Config.testPotionValidity(stackName, displayName, stackitem))
			{
				return true;
			}
		}
		return false;
	}

//	@Override
//	public String getName()
//	{
//		return BlockPotionShelf.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{

	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{

	}

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
		return 64;
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
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }
}
