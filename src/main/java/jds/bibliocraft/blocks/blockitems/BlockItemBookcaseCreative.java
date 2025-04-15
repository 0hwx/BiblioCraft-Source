package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.blocks.BlockBookcaseCreative;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlockItemBookcaseCreative extends BiblioWoodBlockItem
{
	public static BlockItemBookcaseCreative instance = new BlockItemBookcaseCreative(BlockBookcaseCreative.instance);;

	public BlockItemBookcaseCreative(Block block)
	{
		super(block, BlockBookcaseCreative.name);
		setHasSubtypes(true);
        setUnlocalizedName(BlockBookcaseCreative.name);
		//instance = new BlockItemBookcaseCreative(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(I18n.format("item.creativebookcase.randombooks"));
		tooltip.add("\u00a7d" + I18n.format("item.creativebookcase.mode"));
    	if (stack.getItemDamage() == 6)
    	{
    		NBTTagCompound nbt = stack.getTagCompound();
    		if (nbt != null)
    		{
    			tooltip.add(I18n.format("item.paneler.panels")+" \u00a7o"+nbt.getString("renderTexture"));
    		}
    	}
    	super.addInformation(stack, playerIn, tooltip, advanced);
	}
}
