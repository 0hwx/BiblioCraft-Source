package jds.bibliocraft.slots;

import cpw.mods.fml.common.Loader;
import jds.bibliocraft.containers.ContainerAtlas;
import jds.bibliocraft.items.ItemAtlas;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotAtlasMap extends Slot
{

	private ContainerAtlas atlasContainer;

	public SlotAtlasMap(ContainerAtlas container, IInventory iInventory, int x, int y, int z)
	{
		super(iInventory, x, y, z);
		atlasContainer = container;
	}

	@Override
	public int getSlotStackLimit()
    {
        return 1;
    }

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return isAtlasMapItemValid(stack);
	}

	public boolean isAtlasMapItemValid(ItemStack stack)
	{
		//System.out.println("called");
		//atlasContainer.updatedSlots = true;
		if (stack != null)
		{
			Item stuff = stack.getItem();

			if (stuff instanceof ItemAtlas)
			{
				return false;
			}

			if (stuff ==Items.filled_map || stuff == Items.map)
			{
				return true;
			}

			if (Loader.isModLoaded("terrafirmacraft") && Loader.isModLoaded("BiblioWoodsTFC") && stuff == Items.paper)
			{
				return true;
			}
		}
		return false;
	}
}
