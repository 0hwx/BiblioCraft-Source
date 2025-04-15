package jds.bibliocraft.containers;

import jds.bibliocraft.tileentities.TileEntityFramedChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFramedChest extends Container
{

	protected TileEntityFramedChest tileEntity;
	protected TileEntityFramedChest tileEntity2;

	public ContainerFramedChest (InventoryPlayer inventoryPlayer, TileEntityFramedChest tile, TileEntityFramedChest tile2)
	{
		this.tileEntity = tile;
		this.tileEntity2 = tile2;
		IInventory inventory = (IInventory)tileEntity;

		if (this.tileEntity2 != null && this.tileEntity2.getIsDouble())
		{

			if (this.tileEntity.getIsLeft())
			{
		        for (int j = 0; j < 3; ++j)
		        {
		            for (int k = 0; k < 9; ++k)
		            {
		                this.addSlotToContainer(new Slot(tileEntity, k + j * 9, 8 + k * 18, 18 + j * 18));
		            }
		        }
		        for (int j = 0; j < 3; ++j)
		        {
		            for (int k = 0; k < 9; ++k)
		            {
		                this.addSlotToContainer(new Slot(tileEntity2, k + j * 9, 8 + k * 18, 18+(3*18) + j * 18));
		            }
		        }
			}
			else
			{
		        for (int j = 0; j < 3; ++j)
		        {
		            for (int k = 0; k < 9; ++k)
		            {
		                this.addSlotToContainer(new Slot(tileEntity2, k + j * 9, 8 + k * 18, 18 + j * 18));
		            }
		        }
		        for (int j = 0; j < 3; ++j)
		        {
		            for (int k = 0; k < 9; ++k)
		            {
		                this.addSlotToContainer(new Slot(tileEntity, k + j * 9, 8 + k * 18, 18+(3*18) + j * 18));
		            }
		        }
			}


			bindPlayerInventory(inventoryPlayer, true);
		}
		else
		{
	        for (int j = 0; j < 3; ++j)
	        {
	            for (int k = 0; k < 9; ++k)
	            {
	                this.addSlotToContainer(new Slot(tileEntity, k + j * 9, 8 + k * 18, 18 + j * 18));
	            }
	        }
			bindPlayerInventory(inventoryPlayer, false);
		}

	}

	public TileEntityFramedChest getMainTile()
	{
		return this.tileEntity;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileEntity.isUseableByPlayer(player);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer, boolean doubled)
	{
		int  diff = 0;
		if (doubled)
		{
			diff = 54;
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j+i*9+9, 8+j*18, diff+85+i*18));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8+i*18, 143+diff));
		}
	}

	@Override
    public void onContainerClosed(EntityPlayer player)
    {
        InventoryPlayer inventoryplayer = player.inventory;

        if (inventoryplayer.getItemStack() != null)
        {
        	player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
            inventoryplayer.setItemStack((ItemStack)null);
        }

        if (this.tileEntity != null)
        {
        	this.tileEntity.addUsingPlayer(false);
        }
        if (this.tileEntity2 != null)
        {
        	this.tileEntity2.addUsingPlayer(false);
        }
    }

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (this.tileEntity2 != null && this.tileEntity2.getIsDouble())
			{
				if (slot < 54)
				{
					if (!this.mergeItemStack(stackInSlot, 54, 90, true))
					{
						return null;
					}
				}

				else if (!this.mergeItemStack(stackInSlot, 0, 54, false))
				{
					return null;
				}
			}
			else
			{
				if (slot < 27)
				{
					if (!this.mergeItemStack(stackInSlot, 27, 63, true))
					{
						return null;
					}
				}

				else if (!this.mergeItemStack(stackInSlot, 0, 27, false))
				{
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
}
