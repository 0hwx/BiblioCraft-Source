package jds.bibliocraft;


import jds.bibliocraft.blocks.BlockBookcase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiblioTab extends CreativeTabs
{

	public BiblioTab(String name)
	{
		super(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{

//		if (Config.enableBookcase)
//		{
//			return Item.getItemFromBlock(BlockBookcase.instance);//Item.getItemFromBlock(BlockBookcase.instance);
//		}
//		/*
//		else if (Config.enableArmorstand)
//		{
//			return Item.getItemFromBlock(BlockLoader.armorStand);
//		}
//		else if (Config.enableGenericshelf)
//		{
//			return Item.getItemFromBlock(BlockLoader.genericShelf);
//		}
//		else if (Config.enableToolrack)
//		{
//			return Item.getItemFromBlock(BlockLoader.toolRack);
//		}
//		*/
//		else
//		{
//			return Item.getItemFromBlock(Blocks.bookshelf);
//		}
        return Item.getItemFromBlock(Blocks.bookshelf);
	}

}
