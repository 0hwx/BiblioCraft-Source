package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import jds.bibliocraft.blocks.BlockPaintingFrameBorderless;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;


public class BlockItemPaintingFrameBorderless extends BiblioWoodBlockItem
{
	public static final BlockItemPaintingFrameBorderless instance = new BlockItemPaintingFrameBorderless(BlockPaintingFrameBorderless.instance);

	public BlockItemPaintingFrameBorderless(Block block)
	{
		super(block, BlockPaintingFrameBorderless.name);
		setUnlocalizedName(BlockPaintingFrameBorderless.name);
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(StatCollector.translateToLocal("paintingFrame.0tier"));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
}
