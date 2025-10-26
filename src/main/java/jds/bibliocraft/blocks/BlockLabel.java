package jds.bibliocraft.blocks;

import java.util.List;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.google.common.collect.Lists;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityLabel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockLabel extends BiblioWoodBlock
{
	public static final String name = "Label";
	public static final BlockLabel instance = new BlockLabel();

	public BlockLabel()
	{
		super(name, false);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && player.isSneaking())
		{
			player.openGui(BiblioCraft.instance, 6, world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityLabel();
	}


	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{


	}


    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) // todo fix the bounding box
	{

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityLabel)
		{
			TileEntityLabel labelTile = (TileEntityLabel) tile;
			switch (labelTile.getAngle())
			{
				case SOUTH:{this.setBlockBounds(0.94F, 0.125F, 0.22F, 1.0F, 0.436F, 0.78F);break;}
				case WEST:{this.setBlockBounds(0.22F, 0.125F, 0.94F, 0.78F, 0.436F, 1.0F);break;}
				case NORTH:{this.setBlockBounds(0.0F, 0.125F, 0.22F, 0.06F, 0.436F, 0.78F);break;}
				case EAST:{this.setBlockBounds(0.22F, 0.125F, 0.0F, 0.78F, 0.436F, 0.06F);break;}
//				default: break;
			}
		}
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

}
