package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import jds.bibliocraft.blocks.BlockPaintingFrameFlat;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;


public class BlockItemPaintingFrameFlat extends BiblioWoodBlockItem
{
	public static final BlockItemPaintingFrameFlat instance = new BlockItemPaintingFrameFlat(BlockPaintingFrameFlat.instance);

	public BlockItemPaintingFrameFlat(Block block)
	{
		super(block, BlockPaintingFrameFlat.name);
		setUnlocalizedName(BlockPaintingFrameFlat.name);
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(StatCollector.translateToLocal("paintingFrame.1tier"));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
}
