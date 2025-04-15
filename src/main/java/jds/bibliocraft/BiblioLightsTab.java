package jds.bibliocraft;

import jds.bibliocraft.blocks.BlockLampGold;
import jds.bibliocraft.blocks.BlockLanternGold;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiblioLightsTab extends CreativeTabs
{
	public BiblioLightsTab(String name)
	{
		super(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
//		if (Config.enableLantern)
//		{
//			return Item.getItemFromBlock(BlockLanternGold.instance);
//		}
//		else if (Config.enableLamp)
//		{
//			return Item.getItemFromBlock(BlockLampGold.instance);
//		}
//		else
//		{
//			return Item.getItemFromBlock(Blocks.glowstone);
//		}
        return Item.getItemFromBlock(Blocks.glowstone);
	}

}
