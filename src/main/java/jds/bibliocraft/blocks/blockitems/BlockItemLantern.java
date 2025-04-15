package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.blocks.BlockLanternGold;
import jds.bibliocraft.blocks.BlockLanternIron;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockItemLantern extends ItemBlock
{

	private final static String[] lanternNames =
		{
			"whiteLantern",
			"lightGrayLantern",
			"grayLantern",
			"blackLantern",
			"redLantern",
			"orangeLantern",
			"yellowLantern",
			"limeLantern",
			"greenLantern",
			"cyanLantern",
			"lightBlueLantern",
			"blueLantern",
			"purpleLantern",
			"magentaLantern",
			"pinkLantern",
			"brownLantern"
		};

	private final static String[] candleColors =
	{
		I18n.format("lantern.candle0"),
		I18n.format("lantern.candle1"),
		I18n.format("lantern.candle2"),
		I18n.format("lantern.candle3"),
		I18n.format("lantern.candle4"),
		I18n.format("lantern.candle5"),
		I18n.format("lantern.candle6"),
		I18n.format("lantern.candle7"),
		I18n.format("lantern.candle8"),
		I18n.format("lantern.candle9"),
		I18n.format("lantern.candle10"),
		I18n.format("lantern.candle11"),
		I18n.format("lantern.candle12"),
		I18n.format("lantern.candle13"),
		I18n.format("lantern.candle14"),
		I18n.format("lantern.candle15")
	};

	public static final BlockItemLantern instanceGold = new BlockItemLantern(BlockLanternGold.instance, BlockLanternGold.name);
	public static final BlockItemLantern instanceIron = new BlockItemLantern(BlockLanternIron.instance, BlockLanternIron.name);

	public BlockItemLantern(Block block, String name)
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
        return lanternNames[itemstack.getItemDamage()];
    }

    @SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		boolean isIron = false;
		if (stack != null && stack.getItem() == Item.getItemFromBlock(BlockLanternIron.instance))
		{
			isIron = true;
		}
		int meta = stack.getItemDamage();
		tooltip.add(candleColors[meta]);
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
