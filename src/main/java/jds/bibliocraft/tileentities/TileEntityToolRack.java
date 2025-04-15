package jds.bibliocraft.tileentities;

import jds.bibliocraft.Config;
import jds.bibliocraft.blocks.BlockToolRack;
import jds.bibliocraft.helpers.EnumVertPosition;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityToolRack extends BiblioTileEntity
{
	public int showWText = 0;

	public TileEntityToolRack()
	{
		super(4, true);
	}

	public boolean checkSlot(int slot)
    {
    	if (getStackInSlot(slot) != null)
    	{
    		return true;
    	}
    	return false;
    }

    public ItemStack getTool(int slot)
    {
    	ItemStack tool = inventory[slot];
    	if(tool != null)
    	{
    		return tool;
    	}
    	else
    	{
    		return null;
    	}
    }

    public void removeTool(int slot)
    {
    	if (inventory[slot] != null)
    	{
    		setInventorySlotContents(slot, null);
            getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);    	}
    }

    public boolean addTool(int slot, ItemStack stack)
    {
    	if (inventory[slot] == null)
    	{
    		setInventorySlotContents(slot, stack);
            getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
            return true;
    	}
    	else
    	{
    		return false;
    	}
    }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		Item stackitem = itemstack.getItem();
		if (stackitem != null)
		{
			if (isItemTool(stackitem, itemstack))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isItemTool(Item tool, ItemStack stack)
	{
		String toolName = tool.getItemStackDisplayName(stack);
		String toolcodeName = tool.toString();
		//String displayName = stackTest.getItemDisplayName(stack);
		if (tool instanceof ItemTool || tool instanceof ItemSword || tool instanceof ItemBow || tool instanceof ItemHoe || tool instanceof ItemFishingRod || tool instanceof ItemShears || tool instanceof ItemFlintAndSteel || Config.testToolValidity(toolName, toolcodeName))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

//	@Override
//	public String getName()
//	{
//		return BlockToolRack.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack) {		setVertPosition(EnumVertPosition.WALL);}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt) {}

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
