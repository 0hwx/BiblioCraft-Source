package jds.bibliocraft.tileentities;

import jds.bibliocraft.blocks.BlockDinnerPlate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityDinnerPlate extends BiblioTileEntity
{

	public TileEntityDinnerPlate()
	{
		super(3, false);
	}


	public boolean isFoodHaveBowl(ItemStack food, World world, EntityPlayer player)
	{
		if (food.getItem() instanceof ItemFood)
		{
			ItemStack bowlTest = food.onFoodEaten(world, player);// this changed, but seems to work I think
			if (bowlTest != null)
			{
				if (bowlTest.getItem() == Items.bowl )//Item.bowlEmpty)  this might need some attention later
				{
					return true;
				}
			}
		}
		return false;
	}

	public int addFood(ItemStack newFood)
	{
		int addedFood = -1;
		ItemStack slot1Stack = getStackInSlot(0);
		ItemStack slot2Stack = getStackInSlot(1);
		ItemStack slot3Stack = getStackInSlot(2);
		if (slot1Stack == null)
		{
			setInventorySlotContents(0, newFood);
			return 0;
		}
		else if (slot1Stack.getItem() == newFood.getItem() && slot1Stack.getItemDamage() == newFood.getItemDamage())
		{
			if (slot1Stack.stackSize < slot1Stack.getMaxStackSize())
			{
				if ((slot1Stack.stackSize + newFood.stackSize) < slot1Stack.getMaxStackSize())
				{
					slot1Stack.stackSize =(slot1Stack.stackSize + newFood.stackSize);
					setInventorySlotContents(0, slot1Stack);
					return 0;
				}
				else
				{
					addedFood = (newFood.stackSize - (slot1Stack.getMaxStackSize() - slot1Stack.stackSize));
					slot1Stack.stackSize =(slot1Stack.getMaxStackSize());
					return addedFood;
				}
			}
		}

		if (slot2Stack == null)
		{
			setInventorySlotContents(1, newFood);
			return 0;
		}
		else if (slot2Stack.getItem() == newFood.getItem() && slot2Stack.getItemDamage() == newFood.getItemDamage())
		{
			if (slot2Stack.stackSize < slot2Stack.getMaxStackSize())
			{
				if ((slot2Stack.stackSize + newFood.stackSize) < slot2Stack.getMaxStackSize())
				{
					slot2Stack.stackSize =(slot2Stack.stackSize + newFood.stackSize);
					setInventorySlotContents(1, slot2Stack);
					return 0;
				}
				else
				{
					addedFood = (newFood.stackSize - (slot2Stack.getMaxStackSize() - slot2Stack.stackSize));
					slot2Stack.stackSize =(slot2Stack.getMaxStackSize());
					return addedFood;
				}
			}
		}

		if (slot3Stack == null)
		{
			setInventorySlotContents(2, newFood);
			return 0;
		}
		else if (slot3Stack.getItem() == newFood.getItem() && slot3Stack.getItemDamage() == newFood.getItemDamage())
		{
			if (slot3Stack.stackSize < slot3Stack.getMaxStackSize())
			{
				if ((slot3Stack.stackSize + newFood.stackSize) < slot3Stack.getMaxStackSize())
				{
					slot3Stack.stackSize =(slot3Stack.stackSize + newFood.stackSize);
					setInventorySlotContents(2, slot3Stack);
					return 0;
				}
				else
				{
					addedFood = (newFood.stackSize - (slot3Stack.getMaxStackSize() - slot3Stack.stackSize));
					slot3Stack.stackSize =(slot3Stack.getMaxStackSize());
					return addedFood;
				}
			}
		}

		return -1;
	}

	public boolean isPlateEmpty()
	{
		ItemStack slot1Stack = getStackInSlot(0);
		ItemStack slot2Stack = getStackInSlot(1);
		ItemStack slot3Stack = getStackInSlot(2);
		if (slot1Stack == null && slot2Stack == null && slot3Stack == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}



	public ItemStack getFood(int slot)
	{
		ItemStack newFood = getStackInSlot(slot).copy();
		ItemStack plateFood = getStackInSlot(slot);
		if (plateFood.stackSize > 1)
		{
			plateFood.stackSize =(plateFood.stackSize - 1);
			setInventorySlotContents(slot, plateFood);
		}
		else
		{
			setInventorySlotContents(slot, null);
		}
		newFood.stackSize =(1);
		return newFood;
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
		//   this has to do with buildcraft pipes / hoppers
		// this should only accept foods
		if (itemstack != null)
		{
			if (itemstack.getItem() instanceof ItemFood)
			{
				return true;
			}
		}
		return false;
	}


//	@Override
//	public String getName()
//	{
//		return BlockDinnerPlate.name;
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
