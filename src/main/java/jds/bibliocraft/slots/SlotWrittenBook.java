package jds.bibliocraft.slots;

import jds.bibliocraft.containers.ContainerWritingDesk;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;

public class SlotWrittenBook extends Slot
{
	final ContainerWritingDesk writingDesk;

	public SlotWrittenBook(ContainerWritingDesk deskContainer, IInventory iInventory, int i, int j, int k)
	{
		super(iInventory, i, j, k);
		this.writingDesk = deskContainer;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		String stackNameTest = stack.toString();
		Item stackTest = stack.getItem();

		if (stackTest instanceof ItemWritableBook || stackTest instanceof ItemEditableBook)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int getSlotStackLimit()
    {
        return 64;
    }
}

