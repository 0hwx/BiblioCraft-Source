package jds.bibliocraft.helpers;

import codechicken.nei.api.API;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BiblioWoodHelperTab extends CreativeTabs {

    private final ItemStack icon;

    // This will store all the items for the tab, pre-calculated
    private final List<ItemStack> creativeTabItems = new ArrayList<ItemStack>();

    public BiblioWoodHelperTab(String name, String[] textures, ItemStack icon) {
        super(name);
        this.icon = icon;

        // This loop runs only ONCE during game startup
        for (String texture : textures)
        {
            // Create the registration helper
            RegisterCustomFramedBlocks reg = new RegisterCustomFramedBlocks(texture);

            for (ItemStack variantStack : reg.getFramedBlockList())
            {
                // 1. Add the item to our creative tab list
                this.creativeTabItems.add(variantStack);

                // 2. --- THIS IS THE FIX ---
                // Manually give this NBT variant to NEI
                if (true)
                {
                    API.addItemVariant(variantStack.getItem(), variantStack);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        return this.icon;
    }

    @Override
    public Item getTabIconItem() {
        // This is correct, getIconItemStack is the preferred method
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List<ItemStack> list) {
        // Now, we just add all the items from our pre-cached list.
        // This is super fast and has no bugs.
        list.addAll(this.creativeTabItems);
    }
}
