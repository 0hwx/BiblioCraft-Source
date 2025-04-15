package jds.bibliocraft.containers;

import jds.bibliocraft.slots.SlotWeaponCase;
import jds.bibliocraft.tileentities.TileEntityCase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerWeaponCase extends Container
{

	protected TileEntityCase tileEntity;
	protected SlotWeaponCase weaponSlot;

	public ContainerWeaponCase(InventoryPlayer inventoryPlayer, TileEntityCase tile)
	{
		tileEntity = tile;

		addSlotToContainer(this.weaponSlot = new SlotWeaponCase(this, tileEntity, 0, 80, 36));

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


			if (slot < 1)
			{
				if (!this.mergeItemStack(stackInSlot, 1, 37, true))  // changing 9 to 6
				{
					return null;
				}
			}
			//places it into the tileEntity is possible since its in the player inventory

			else if (!this.mergeItemStack(stackInSlot, 0, 1, false))
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

	/*
	public boolean isItemTool(Item tool, ItemStack stack)
	{
		String toolName = tool.getItemDisplayName(stack);
		String bookName = tool.getLocalItemName(stack);
		if (tool instanceof ItemTool || tool instanceof ItemSword || tool instanceof ItemBow || tool instanceof ItemHoe || tool instanceof ItemFishingRod || tool instanceof ItemShears || tool instanceof ItemFlintAndSteel || Config.testToolValidity(toolName) || Config.testBookValidity(bookName) || Config.testCaseValidity(toolName))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	*/
}
