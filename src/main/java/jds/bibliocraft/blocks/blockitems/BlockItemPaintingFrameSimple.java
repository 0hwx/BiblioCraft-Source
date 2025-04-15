package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import jds.bibliocraft.blocks.BlockPaintingFrameSimple;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;


public class BlockItemPaintingFrameSimple extends BiblioWoodBlockItem
{
	public static final BlockItemPaintingFrameSimple instance = new BlockItemPaintingFrameSimple(BlockPaintingFrameSimple.instance);

	public BlockItemPaintingFrameSimple(Block block)
	{
		super(block, BlockPaintingFrameSimple.name);
		setUnlocalizedName(BlockPaintingFrameSimple.name);
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(StatCollector.translateToLocal("paintingFrame.2tier"));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
}
