package jds.bibliocraft.tileentities;

import jds.bibliocraft.blocks.BlockSwordPedestal;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySwordPedestal extends BiblioTileEntity
{
	public boolean showText = false;

	public TileEntitySwordPedestal()
	{
		super(1, false);
	}

	public void setShowText(boolean setter)
	{
		showText = setter;
	}
	public boolean getShowText()
	{
		return showText;
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
		// for use with pipes and hoppers and such
		if (itemstack != null)
		{
			if (itemstack.getItem() instanceof ItemSword || itemstack.getUnlocalizedName().toLowerCase().contains("sword") || itemstack.getUnlocalizedName().toLowerCase().contains("gt.metatool.01.0"))
			{
				if (itemstack.getItem() == Item.getItemFromBlock(BlockSwordPedestal.instance))
				{
					return false;
				}
				return true;
			}
		}
		return false;
	}

//	@Override
//	public String getName()
//	{
//		return BlockSwordPedestal.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{
		updateSurroundingBlocks(BlockSwordPedestal.instance);
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
