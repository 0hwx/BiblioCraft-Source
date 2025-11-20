package jds.bibliocraft.tileentities;

import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityShelf extends BiblioTileEntity
{
	private boolean hasTop;

	public TileEntityShelf()
	{
		super(16, true);
	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{

	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.hasTop = nbt.getBoolean("hasTop");
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
		nbt.setBoolean("hasTop", this.hasTop);
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

    @Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
	}
    /*
	public boolean checkSlot(int slot)
    {
    	if (getStackInSlot(slot) != null)
    	{
    		return true;
    	}
    	return false;
    }

    public void removeStuff(int slot)
    {
    	ItemStack returnValue = null;
    	if (inventory[slot] != null)
    	{
    		ItemStack slotStack = getStackInSlot(slot);
    		setInventorySlotContents(slot, null);
    		getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
    		returnValue = slotStack;
    	}
    }

    public boolean addStuff(int slot, ItemStack stack)
    {
    	if (inventory[slot] == null)
    	{
    		setInventorySlotContents(slot, stack);
    		getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    */
    public void setTop(boolean noblock)
    {
    	hasTop = noblock;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public boolean getTop()
    {
    	return hasTop;
    }

//	@Override
//	public String getName()
//	{
//		return BlockShelf.name;
//	}
//
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
