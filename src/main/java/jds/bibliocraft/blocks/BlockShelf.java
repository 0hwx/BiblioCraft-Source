package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityShelf;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;



public class BlockShelf extends BiblioWoodBlock
{
	public static final String name = "Shelf";
	public static final BlockShelf instance = new BlockShelf();

	public BlockShelf()
	{
		super(name, true);
	}


	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			ItemStack playerhand = player.getHeldItem();
			BiblioTileEntity biblioTile = (BiblioTileEntity)world.getTileEntity(x, y, z);
			if (biblioTile instanceof TileEntityShelf)
			{
				TileEntityShelf tile = (TileEntityShelf)biblioTile;

				 if (player.isSneaking())
				 {
					 player.openGui(BiblioCraft.instance, 3, world, x, y, z);
					 return true;
				 }

				 int slot = getSlotNumberFromClickon2x2block(tile.getAngle(), hitX, hitY, hitZ);
				 boolean testValue = false; // turns true if I add or remove something from the shelf
				 if (slot >= 0 && !player.isSneaking())
				 {
					 if (playerhand != null)
					 {
						 testValue = tile.addStackToInventoryFromWorld(playerhand, slot, player);
					 }

					 if (!testValue)
					 {
						 testValue = tile.removeStackFromInventoryFromWorld(slot, player, this);
					 }
				 }

				 if (!testValue)
				 {
					 player.openGui(BiblioCraft.instance, 3, world, x, y, z);
				 }
				 return true;

			}
		}
		return true;
	}


	@Override
    public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
    {

    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityShelf();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
////		List<String> modelParts = Lists.newArrayList(OBJModel.Group.ALL);
////		if (tile instanceof TileEntityShelf)
////		{
////			TileEntityShelf shelf = (TileEntityShelf)tile;
////	    	boolean hasTop = shelf.getTop();
////	    	if (!hasTop)
////	    	{
////	    		modelParts = new ArrayList<String>();
////	    		modelParts.add("shelf_bottom");
////	    	}
////		}
//		return List.of();
//	}

	private boolean checkIfIsBackOfBlock(int angle, int face)
	{
		boolean angle1 = angle == 0 && face == 5;
		boolean angle2 = angle == 3 && face == 2;
		boolean angle3 = angle == 2 && face == 4;
		boolean angle4 = angle == 1 && face == 3;
		if (angle1 || angle2 || angle3 || angle4)
		{
			return true;
		}
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityShelf)
		{
			TileEntityShelf shelf = (TileEntityShelf)tile;
		    Block testBlock = world.getBlock(x, y + 1, z);
		    boolean isBlock;
		    if (shelf != null)
		    {
		        if (!world.isAirBlock(x, y + 1, z))
		        {
		        	isBlock = true;
		        }
		        else
		        {
		        	isBlock = false;
		        }
		    	shelf.setTop(isBlock);
		    }
		}
	}
    public void registerBlockIcons(IIconRegister icon) {
        this.blockIcon = icon.registerIcon("planks_spruce");
    }

//	@Override
//	public TRSRTransformation getAdditionalTransforms(TRSRTransformation transform, BiblioTileEntity tile)
//	{
//		return transform;
//	}
}
