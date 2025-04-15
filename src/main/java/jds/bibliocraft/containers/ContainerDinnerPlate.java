package jds.bibliocraft.containers;

import jds.bibliocraft.slots.SlotFood;
import jds.bibliocraft.tileentities.TileEntityDinnerPlate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerDinnerPlate extends Container
{
	protected TileEntityDinnerPlate plateTile;
	protected SlotFood foodSlot;

	public ContainerDinnerPlate(InventoryPlayer inventoryPlayer, TileEntityDinnerPlate tile)
	{
		plateTile = tile;
		addSlotToContainer(this.foodSlot = new SlotFood(this, plateTile, 0, 80, 45));
		addSlotToContainer(this.foodSlot = new SlotFood(this, plateTile, 1, 35, 26));
		addSlotToContainer(this.foodSlot = new SlotFood(this, plateTile, 2, 125, 26));
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return plateTile.isUseableByPlayer(player);
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
			Item toolTest = stack.getItem();


			if (slot < 3)
			{
				if (!this.mergeItemStack(stackInSlot, 3, 39, true))  // changing 9 to 6
				{
					return null;
				}
			}
			//places it into the tileEntity is possible since its in the player inventory

			else if (foodSlot.isItemValid(stack) && !this.mergeItemStack(stackInSlot, 0, 3, false))
			{
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
