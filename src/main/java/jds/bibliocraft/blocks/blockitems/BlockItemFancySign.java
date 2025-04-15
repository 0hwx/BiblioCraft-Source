package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import jds.bibliocraft.blocks.BlockFancySign;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlockItemFancySign extends BiblioWoodBlockItem
{
	public static final BlockItemFancySign instance = new BlockItemFancySign(BlockFancySign.instance);

	public BlockItemFancySign(Block block)
	{
		super(block, BlockFancySign.name);
        setUnlocalizedName(BlockFancySign.name);
	}

	@Override
    public List<String> addAdditionalInformation(ItemStack stack, EntityPlayer world, List<String> tooltip)
    {
        NBTTagCompound tags = stack.getTagCompound();
        if (tags != null && tags.hasKey("textscale"))
        {
        	tooltip.add("This sign contains user data.");
        }
    	return tooltip;
    }
}
