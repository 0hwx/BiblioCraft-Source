package jds.bibliocraft.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import net.minecraft.world.World;


public abstract class BiblioSimpleBlock extends BiblioBlock
{
	public BiblioSimpleBlock(Material material, SoundType sound, String name)
	{
		super(material, sound, BlockLoader.biblioTab, name);
	}

	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player) { }

	@Override
	public ItemStack getPickBlockExtras(ItemStack stack, World world, int x, int y, int z)
	{
		return stack;
	}

}
