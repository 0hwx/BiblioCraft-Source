package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import jds.bibliocraft.blocks.BlockPaintingFrameMiddle;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class BlockItemPaintingFrameMiddle extends BiblioWoodBlockItem
{
	public static final BlockItemPaintingFrameMiddle instance = new BlockItemPaintingFrameMiddle(BlockPaintingFrameMiddle.instance);

	public BlockItemPaintingFrameMiddle(Block block)
	{
		super(block, BlockPaintingFrameMiddle.name);
		setUnlocalizedName(BlockPaintingFrameMiddle.name);
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(StatCollector.translateToLocal("paintingFrame.3tier"));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
}
