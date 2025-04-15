package jds.bibliocraft.containers;

import jds.bibliocraft.slots.SlotDisc;
import jds.bibliocraft.tileentities.TileEntityDiscRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerDiscRack extends Container
{
	protected TileEntityDiscRack rackTile;
	protected SlotDisc discSlot;

	public ContainerDiscRack(InventoryPlayer inventoryPlayer, TileEntityDiscRack tile)
	{
		rackTile = tile;
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 0, 8, 34));
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 1, 26, 34));
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 2, 44, 34));
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 3, 62, 34));
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 4, 80, 34));
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 5, 98, 34));
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 6, 116, 34));
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 7, 134, 34));
		addSlotToContainer(this.discSlot = new SlotDisc(this, rackTile, 8, 152, 34));
		bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return rackTile.isUseableByPlayer(player);
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


			if (slot < 9)
			{
				if (!this.mergeItemStack(stackInSlot, 9, 45, true))  // changing 9 to 6
				{
					return null;
				}
			}
			//places it into the tileEntity is possible since its in the player inventory

			else if (discSlot.isItemValid(stack) && !this.mergeItemStack(stackInSlot, 0, 9, false))
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
