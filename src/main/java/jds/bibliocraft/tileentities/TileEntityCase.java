package jds.bibliocraft.tileentities;

import jds.bibliocraft.blocks.BlockCase;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCase extends BiblioTileEntity
{
	public boolean openLid = false;;
    public boolean showText = false;
    public boolean hasRS;
    public int WoolColour = 0;

	public TileEntityCase()
	{
		super(2, true);
	}

	public boolean hasRedstoneBlock()
	{
		ItemStack rsblock = getStackInSlot(0);
		if (rsblock != null)
		{
			if (Block.isEqualTo(Block.getBlockFromItem(rsblock.getItem()),Blocks.redstone_block))
			{
				return true;
			}
		}

		return false;
	}

	public boolean setInnerCover(ItemStack carpet, EntityPlayer player)
	{
		if (carpet != null && carpet.getItem() == Item.getItemFromBlock(Blocks.carpet) && getStackInSlot(1) == null)
		{
            this.WoolColour = carpet.getItemDamage();
			ItemStack carpetCopy = carpet.copy();
			carpetCopy.stackSize = (1);
			setInventorySlotContents(1, carpetCopy);
			carpet.stackSize = (carpet.stackSize - 1);
			if (carpet.stackSize == 0)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
			else
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, carpet);
			}
			getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}
		return false;
	}
	public void removeInnerCover()
	{
		setInventorySlotContents(1, null);
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public boolean hasRedStone()
	{
		return hasRS;
	}

	public void setShowText(boolean show)
	{
		showText = show;
	}
	public boolean getShowText()
	{
		return showText;
	}

	public boolean setCaseSlot(ItemStack stack)
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
		if (inventory[0] != null)
		{
			return true;
		}
		else
		{
			return false;
		}
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

    public void setOpenLid(boolean open)
    {
    	this.openLid = open;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public boolean getOpenLid()
    {
    	return this.openLid;
    }

    public int getWoolColour()
    {
    	return this.WoolColour;
    }

//	@Override
//	public String getName()
//	{
//		return BlockCase.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{
		boolean redtest = hasRS;
		hasRS = hasRedstoneBlock();
		if (redtest != hasRS)
		{
			updateSurroundingBlocks(BlockCase.instance);
		}
	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.openLid = nbt.getBoolean("LidOpen");
		this.hasRS = nbt.getBoolean("hasRedstone");
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
    	nbt.setBoolean("LidOpen", openLid);
    	nbt.setBoolean("hasRedstone", hasRS);
		return nbt;
	}

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }

//	@Override
//	public ITextComponent getDisplayName()
//	{
//		ITextComponent chat = new ChatComponentText(getName());
//		return chat;
//	}
//
//	@Override
//	public boolean isEmpty()
//	{
//		return false;
//	}

}
