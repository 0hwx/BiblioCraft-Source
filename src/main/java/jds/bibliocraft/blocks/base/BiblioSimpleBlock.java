package jds.bibliocraft.blocks.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
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

    @Override
    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift)
    {

    }
}
