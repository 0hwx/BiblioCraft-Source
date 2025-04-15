package jds.bibliocraft.tileentities;

import jds.bibliocraft.blocks.BlockMarkerPole;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMarkerPole extends BiblioTileEntity
{
	//public int direction = 0;

	public TileEntityMarkerPole()
	{
		super(0, false);
		this.setRenderBoxAdditionalSize(2);
	}
//
//	@Override
//	public String getName()
//	{
//		return BlockMarkerPole.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack) { }

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		//this.direction = nbt.getInteger("direction");
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
		//nbt.setInteger("direction", direction);
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
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }
}
