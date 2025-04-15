package jds.bibliocraft.containers;

import jds.bibliocraft.slots.SlotFancySign;
import jds.bibliocraft.tileentities.TileEntityFancySign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerFancySign extends Container
{
	private TileEntityFancySign tileEntity;
	protected SlotFancySign slot;

	public ContainerFancySign(InventoryPlayer inventoryPlayer, TileEntityFancySign tile)
	{
		tileEntity = tile;
		addSlotToContainer(this.slot = new SlotFancySign(this, tileEntity, 0, 194, 154));
		addSlotToContainer(this.slot = new SlotFancySign(this, tileEntity, 1, 222, 154));
		bindPlayerInventory(inventoryPlayer);
		//this.
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
				addSlotToContainer(new Slot(inventoryPlayer, j+i*9+9, 20+j*18, 159+i*18));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 20+i*18,217));
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


			if (slot < 2)
			{
				if (!this.mergeItemStack(stackInSlot, 2, 38, true))
				{
					return null;
				}
			}

			else if (!this.mergeItemStack(stackInSlot, 0, 2, false))
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
