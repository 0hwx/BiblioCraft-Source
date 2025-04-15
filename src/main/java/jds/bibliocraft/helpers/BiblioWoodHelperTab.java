package jds.bibliocraft.helpers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BiblioWoodHelperTab  extends CreativeTabs
{
	private String[] textureString;
	private Item icon;
	public BiblioWoodHelperTab(String name, String[] textures, Item icon)
	{
		super(name);
		this.textureString = textures;
		this.icon = icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return this.icon;

	}

    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List<ItemStack> list)
    {
    	for (int i = 0; i < textureString.length; i++)
    	{
        	RegisterCustomFramedBlocks reg = new RegisterCustomFramedBlocks(textureString[i]);
        	for (int j = 0; j < reg.getFramedBlockList().size(); j++)
        	{
        		if (reg.getEnableList()[j])
        			list.set(i, new ItemStack(reg.getFramedBlockList().get(j).getItem()));
        	}
    	}
    }
}
