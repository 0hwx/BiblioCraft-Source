package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.blocks.BlockLampGold;
import jds.bibliocraft.blocks.BlockLampIron;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockItemLamp extends ItemBlock
{
	private final static String[] lampNames =
	{
		"whiteLamp",
		"lightGrayLamp",
		"grayLamp",
		"blackLamp",
		"redLamp",
		"orangeLamp",
		"yellowLamp",
		"limeLamp",
		"greenLamp",
		"cyanLamp",
		"lightBlueLamp",
		"blueLamp",
		"purpleLamp",
		"magentaLamp",
		"pinkLamp",
		"brownLamp"
	};

	private final static String[] hoodColors =
	{
		I18n.format("lamp.hood0"),
		I18n.format("lamp.hood1"),
		I18n.format("lamp.hood2"),
		I18n.format("lamp.hood3"),
		I18n.format("lamp.hood4"),
		I18n.format("lamp.hood5"),
		I18n.format("lamp.hood6"),
		I18n.format("lamp.hood7"),
		I18n.format("lamp.hood8"),
		I18n.format("lamp.hood9"),
		I18n.format("lamp.hood10"),
		I18n.format("lamp.hood11"),
		I18n.format("lamp.hood12"),
		I18n.format("lamp.hood13"),
		I18n.format("lamp.hood14"),
		I18n.format("lamp.hood15")
	};

	public static final BlockItemLamp instanceGold = new BlockItemLamp(BlockLampGold.instance, BlockLampGold.name);
	public static final BlockItemLamp instanceIron = new BlockItemLamp(BlockLampIron.instance, BlockLampIron.name);

	public BlockItemLamp(Block block, String name)
	{
		super(block);
		setHasSubtypes(true);
		setUnlocalizedName(name);
	}

	@Override
	public int getMetadata(int damageValue)
	{
		return damageValue;
	}

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return lampNames[itemstack.getItemDamage()];
    }

    @SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{

		boolean isIron = false;
		if (stack != null && stack.getItem() == Item.getItemFromBlock(BlockLampIron.instance))
		{
			isIron = true;
		}
		int meta = stack.getItemDamage();
		tooltip.add(hoodColors[meta]);
		if (isIron)
		{
			tooltip.add(I18n.format("lighting.metalIron"));
		}
		else
		{
			tooltip.add(I18n.format("lighting.metalGold"));
		}
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
}
