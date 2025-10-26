package jds.bibliocraft.blocks;

import java.util.List;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.google.common.collect.Lists;

import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntitySwordPedestal;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSwordPedestal extends BiblioColorBlock
{
	public static final String name = "SwordPedestal";
	public static final BlockSwordPedestal instance = new BlockSwordPedestal();

	public BlockSwordPedestal()
	{
		super(Material.rock, soundTypeStone, name);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (!world.isRemote && te instanceof BiblioTileEntity)
		{
			BiblioTileEntity tile = (BiblioTileEntity)te;
			ItemStack swordtest = player.getHeldItem();
			if (tile.getStackInSlot(0) == null)
			{
				if (swordtest != null)
				{

					if (swordtest.getItem() instanceof ItemSword || swordtest.getUnlocalizedName().toLowerCase().contains("sword") || swordtest.getUnlocalizedName().toLowerCase().contains("gt.metatool.01.0"))
					{
						if (swordtest.getItem() == Item.getItemFromBlock(this.instance))
						{
							return true;
						}
						//System.out.println("I can haz sword?");
						tile.setInventorySlotContents(0, swordtest);
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						return true;
					}
				}
			}
			else
			{
				tile.removeStackFromInventoryFromWorld(0, player, this);
				return true;
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySwordPedestal();
	}


    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		AxisAlignedBB output = this.getBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	TileEntity pretile = world.getTileEntity(x, y, z);
    	if (pretile != null && pretile instanceof TileEntitySwordPedestal)
    	{
    		TileEntitySwordPedestal tile = (TileEntitySwordPedestal)pretile;
    		ForgeDirection angle = tile.getAngle();
    		float withsword = 0.0f;
    		if (tile.getStackInSlot(0) != null)
    		{
    			withsword = 0.76f;
    		}
    		switch (angle)
    		{
	    		case SOUTH:{output = this.getBlockBounds(0.3F, 0.0F, 0.1F, 0.7F, 0.24F+withsword, 0.9F);break;}
	    		case WEST:{output = this.getBlockBounds(0.1F, 0.0F, 0.3F, 0.9F, 0.24F+withsword, 0.7F);break;}
	    		case NORTH:{output = this.getBlockBounds(0.3F, 0.0F, 0.1F, 0.7F, 0.24F+withsword, 0.9F);break;}
	    		case EAST:{output = this.getBlockBounds(0.1F, 0.0F, 0.3F, 0.9F, 0.24F+withsword, 0.7F);break;}
	    		default: break;
    		}

    	}
    	return output;
	}

	@Override
    public boolean canProvidePower()
    {
        return true;
    }

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
    {
		TileEntitySwordPedestal tile = (TileEntitySwordPedestal)world.getTileEntity(x, y, z);
		if (tile != null)
		{
			if (tile.getStackInSlot(0) != null)
			{
				return 15;
			}
		}
		return 0;
    }

	@Override
	public int isProvidingStrongPower(IBlockAccess worldIn, int x, int y, int z, int side)
    {
		return isProvidingWeakPower(worldIn, x, y, z, side);
    }
}
