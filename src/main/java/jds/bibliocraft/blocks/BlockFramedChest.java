package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityFramedChest;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockFramedChest extends BiblioWoodBlock
{
	public static final String name = "FramedChest";
	public static final BlockFramedChest instance = new BlockFramedChest();

	public BlockFramedChest()
	{
		super(name, false);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && tile instanceof TileEntityFramedChest)
			{
				TileEntityFramedChest chest = (TileEntityFramedChest)tile;
				if (chest.getIsDouble())
				{
					TileEntityFramedChest chest2 = getAdjacentChest(chest, world);
					if (chest2 != null)
					{
						chest.setAdjacentChest(chest2);
						chest2.setAdjacentChest(chest);
						chest2.addUsingPlayer(true);
					}
				}

				chest.addUsingPlayer(true);
				player.openGui(BiblioCraft.instance, 0, world, x, y, z);
			}
		}
		return true;
	}

	public TileEntityFramedChest getAdjacentChest(TileEntityFramedChest chest, World world)
	{
		TileEntity tile = null;
		int x = chest.xCoord;
		int y = chest.yCoord;
		int z = chest.zCoord;
		switch (chest.getAngle())
		{
			case SOUTH:
			{
				if (chest.getIsLeft())
				{
					z++;
				}
				else
				{
					z--;
				}
				break;
			}
			case WEST:
			{
				if (chest.getIsLeft())
				{
					x--;
				}
				else
				{
					x++;
				}
				break;
			}
			case NORTH:
			{
				if (chest.getIsLeft())
				{
					z--;
				}
				else
				{
					z++;
				}
				break;
			}
			case EAST:
			{
				if (chest.getIsLeft())
				{
					x++;
				}
				else
				{
					x--;
				}
				break;
			}
			default: break;
		}
		tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityFramedChest)
		{
			TileEntityFramedChest chest2 = (TileEntityFramedChest)tile;
			if (chest2.getIsDouble())
			{
				return chest2;
			}
		}

		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFramedChest();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//		modelParts.add("small_chest");
//		if (tile instanceof TileEntityFramedChest)
//		{
//			TileEntityFramedChest chest = (TileEntityFramedChest)tile;
//			if (chest.getIsDouble())
//			{
//				modelParts = new ArrayList<String>();
//				if (chest.getIsLeft())
//				{
//					modelParts.add("large_chest_left");
//				}
//				else
//				{
//					modelParts.add("large_chest_right");
//				}
//			}
//		}
//		return modelParts;
//	}

	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{


	}

//	@Override
//	public TRSRTransformation getAdditionalTransforms(TRSRTransformation transform, BiblioTileEntity tile)
//	{
//		return transform;
//	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		AxisAlignedBB output = this.getBlockBounds(0.054F, 0.0F, 0.054F, 0.946F, 0.866F, 0.946F);
    	TileEntity tile = world.getTileEntity(x, y, z);
    	if (tile != null && tile instanceof TileEntityFramedChest)
    	{
    		TileEntityFramedChest chest = (TileEntityFramedChest)tile;
    		if (chest.getIsDouble())
    		{
    			switch (chest.getAngle())
    			{
	    			case SOUTH:
	    			{
	    				if (chest.getIsLeft())
	    				{
	    					output = this.getBlockBounds(0.054F, 0.0F, 0.054F, 0.946F, 0.866F, 1.0F);
	    				}
	    				else
	    				{
	    					output = this.getBlockBounds(0.054F, 0.0F, 0.0F, 0.946F, 0.866F, 0.946F);
	    				}
	    				break;
	    			}
	    			case WEST:
	    			{
	    				if (chest.getIsLeft())
	    				{
	    					output = this.getBlockBounds(0.0F, 0.0F, 0.054F, 0.946F, 0.866F, 0.946F);
	    				}
	    				else
	    				{
	    					output = this.getBlockBounds(0.054F, 0.0F, 0.054F, 1.0F, 0.866F, 0.946F);
	    				}
	    				break;
	    			}
	    			case NORTH:
	    			{
	    				if (chest.getIsLeft())
	    				{
	    					output = this.getBlockBounds(0.054F, 0.0F, 0.0F, 0.946F, 0.866F, 0.946F);
	    				}
	    				else
	    				{
	    					output = this.getBlockBounds(0.054F, 0.0F, 0.054F, 0.946F, 0.866F, 1.0F);
	    				}
	    				break;
	    			}
	    			case EAST:
	    			{
	    				if (chest.getIsLeft())
	    				{
	    					output = this.getBlockBounds(0.054F, 0.0F, 0.054F, 1.0F, 0.866F, 0.946F);
	    				}
	    				else
	    				{
	    					output = this.getBlockBounds(0.0F, 0.0F, 0.054F, 0.946F, 0.866F, 0.946F);
	    				}
	    				break;
	    			}
	    			default: break;
    			}
    		}
    	}
    	return output;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta)
	{
		dropItems(world, x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityFramedChest)
		{
			TileEntityFramedChest chest = (TileEntityFramedChest)tile;
			if (chest.getIsDouble())
			{
				TileEntityFramedChest chest2 = getAdjacentChest(chest, world);
				if (chest2 != null)
				{
					chest2.setIsDouble(false, true, null);
				}
			}
		}
		super.breakBlock(world, x, y, z, blockBroken, meta);
	}
}
