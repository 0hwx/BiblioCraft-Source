package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import jds.bibliocraft.blocks.BlockPaintingFrameFancy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;


public class BlockItemPaintingFrameFancy extends BiblioWoodBlockItem
{
	public static final BlockItemPaintingFrameFancy instance = new BlockItemPaintingFrameFancy(BlockPaintingFrameFancy.instance);

	public BlockItemPaintingFrameFancy(Block block)
	{
		super(block, BlockPaintingFrameFancy.name);
		setUnlocalizedName(BlockPaintingFrameFancy.name);
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(StatCollector.translateToLocal("paintingFrame.4tier"));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
}
