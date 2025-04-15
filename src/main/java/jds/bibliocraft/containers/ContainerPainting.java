package jds.bibliocraft.containers;

import jds.bibliocraft.items.ItemPaintingCanvas;
import jds.bibliocraft.slots.SlotPainting;
import jds.bibliocraft.tileentities.TileEntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPainting extends Container
{
	protected TileEntityPainting painting;
	protected SlotPainting canvasSlot;

	private boolean hasStack = false;
	//private String[] stringSizes = {"1x1","1x2"};

	public ContainerPainting(InventoryPlayer inventoryPlayer, TileEntityPainting tile)
	{
		this.painting = tile;
		addSlotToContainer(this.canvasSlot = new SlotPainting(this, this.painting, 0, 80, 89));
		bindPlayerInventory(inventoryPlayer);
		if (this.painting.getStackInSlot(0) != null)
		{
			this.hasStack = true;
		}
		else
		{
			this.hasStack = false;
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		//System.out.println("container update");
		if (this.painting.getStackInSlot(0) != null)
		{
			if (!hasStack)
			{
				this.hasStack = true;
				this.painting.setContainterUpdate(true);
				//System.out.println("has a stack");
			}
		}
		else
		{
			if (hasStack)
			{
				this.hasStack = false;
				this.painting.setContainterUpdate(true);
				//System.out.println("lost a stack");
			}
		}
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j+i*9+9, 8+j*18, 110+i*18));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8+i*18,168));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return painting.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		//this.painting.setContainterUpdate(true);
		//System.out.println("container update");
		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			if (slot == 0)
			{
				if (!this.mergeItemStack(stackInSlot, 1, 37, true))  // changing 9 to 6
				{
					return null;
				}
			}
			else
			{
				if ((!canvasCheck(stack) || this.painting.getStackInSlot(0) != null))
				{
					return null;
				}

				if (stack.stackSize == 1)
				{
					if (!this.mergeItemStack(stackInSlot, 0, 1, false))
					{
						return null;
					}

				}
				else
				{
					stack.stackSize = (1);
					this.mergeItemStack(stack, 0, 1, false);
					stackInSlot.stackSize = (stackInSlot.stackSize - 1);
                    return null;
				}
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

	private boolean canvasCheck(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemPaintingCanvas)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
