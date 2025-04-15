package jds.bibliocraft.containers;

import jds.bibliocraft.Config;
import jds.bibliocraft.slots.SlotBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerBookcase extends Container
{

	protected IInventory tileEntity;
	protected SlotBook bookSlot;

	public ContainerBookcase (InventoryPlayer inventoryPlayer, TileEntity tile)
	{
		tileEntity = (IInventory)tile;

		// Notes from tutorial say that
		//the Slot constructor takes the IInventory and the slot number in that it binds to
        //and the x-y coordinates it resides on-screen

		for (int i = 0; i < 2; i++)  // this changes how many slots are on the y axis
		{
			for (int j = 0; j < 8; j++) // this changes how many slots are on the x axis
			{
				// to setup container limitations all I need to do is create a class that extends Slot
				//overwrite isItemValid() and getSlotStackLimit()
				// then initialize here with a private final SlotBook bookSlot;
				addSlotToContainer(this.bookSlot = new SlotBook(this, tileEntity, j+i*8, 17+j*18, 17+i*25));
				//addSlotToContainer(new Slot(tileEntity, j+i*8, 17+j*18, 17+i*25)); // from what I understand, the j+i+x bit, the x should match the x axis slots
			}

		}
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileEntity.isUseableByPlayer(player);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j+i*9+9, 8+j*18, 84+i*18));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8+i*18,142));
		}
	}
	/*
	 * It seems I can test if things are instances of armor or tools.
	 *
	 * stack.getItem() instanceof ItemArmor
	 *
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
//null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			//stackSizeTest.getItemStackLimit()
			//merges the item into player inventory since its in the tileEntity

			if (slot < 16) // changing 9 to 6
			{
				if (!this.mergeItemStack(stackInSlot, 16, 52, true))  // changing 9 to 6
				{
					return null;
				}
			}
			//places it into the tileEntity is possible since its in the player inventory

			else if (!Config.isBlock(stack) && Config.testBookValidity(stack) && !this.mergeItemStack(stackInSlot, 0, 16, false))  //&& stackTest.getItemStackLimit() == 1
			{
				//System.out.println(stackName);
				return null;
			}


			if (stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);
			} else
			{
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize)
			{
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

}
