package jds.bibliocraft.tileentities;

import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class TileEntityArmorStand extends BiblioTileEntity
{
	public boolean helm = false;
	public boolean cuirass = false;
	public boolean greaves = false;
	public boolean boots = false;

	public int showArmorText = 0;

	private boolean isBottomStand = true;


	public TileEntityArmorStand()
	{
		super(4, true);
	}

	public void setIsBottomStand(boolean isBottom)
	{
		this.isBottomStand = isBottom;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean getIsBottomStand()
	{
		return this.isBottomStand;
	}

	public void checkArmorSlots()
	{
		ItemStack stackTest0 = getStackInSlot(0);
		if (stackTest0 != null)
		{
			helm = true;
		}
		else
		{
			helm = false;
		}

		ItemStack stackTest1 = getStackInSlot(1);
		if (stackTest1 != null)
		{
			cuirass = true;
		}
		else
		{
			cuirass = false;
		}

		ItemStack stackTest2 = getStackInSlot(2);
		if (stackTest2 != null)
		{
			greaves = true;
		}
		else
		{
			greaves = false;
		}

		ItemStack stackTest3 = getStackInSlot(3);
		if (stackTest3 != null)
		{
			boots = true;
		}
		else
		{
			boots = false;
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
		return 1;
	}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    public boolean getHelm()
    {
    	return helm;
    }
    public boolean getCuirass()
    {
    	return cuirass;
    }
    public boolean getGreaves()
    {
    	return greaves;
    }
    public boolean getBoots()
    {
    	return boots;
    }

    public boolean addArmor(ItemStack stack, int armorType)
    {
    	checkArmorSlots();
    	switch (armorType)
    	{
	    	case 0:{
	    		if (!helm)
	    		{
	    			setInventorySlotContents(0, stack);
	    			return true;
	    		}
	    		break;}
	    	case 1:{
	       		if (!cuirass)
	    		{
	    			setInventorySlotContents(1, stack);
	    			return true;
	    		}
	    		break;}
	    	case 2:{
	       		if (!greaves)
	    		{
	    			setInventorySlotContents(2, stack);
	    			return true;
	    		}
	    		break;}
	    	case 3:{
	       		if (!boots)
	    		{
	    			setInventorySlotContents(3, stack);
	    			return true;
	    		}
	    		break;}
	    	default:break;
    	}
    	return false;
    }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		if (!this.getIsBottomStand())
		{
			return false;
		}
		Item stackItem = itemstack.getItem();
		if (stackItem instanceof ItemArmor )
		{
			ItemArmor armorItem = (ItemArmor)stackItem;
			if (armorItem != null)
			{
				int armorType = armorItem.armorType;

				if (slot == 0 && armorType == 3)
				{
					return true;
				}
				else if (slot == 1 && armorType == 2)
				{
					return true;
				}
				else if (slot == 2 && armorType == 1)
				{
					return true;
				}
				else if (slot == 3 && armorType == 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
	}

//	@Override
//	public String getName()
//	{
//		return BlockArmorStand.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{
		checkArmorSlots();
	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.helm = nbt.getBoolean("helm");
		this.cuirass = nbt.getBoolean("cuirass");
		this.greaves = nbt.getBoolean("greaves");
		this.boots = nbt.getBoolean("boots");
		this.isBottomStand = nbt.getBoolean("isBottom");
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
		nbt.setBoolean("helm", this.helm);
		nbt.setBoolean("cuirass", this.cuirass);
		nbt.setBoolean("greaves", this.greaves);
		nbt.setBoolean("boots", this.boots);
		nbt.setBoolean("isBottom", this.isBottomStand);
		return nbt;
	}

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }

//	@Override
//	public IChatComponent getDisplayName()
//	{
//        IChatComponent chat = new ChatComponentText(getName());
//		return chat;
//	}
}
