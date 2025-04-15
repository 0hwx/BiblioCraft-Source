package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityCookieJar;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockCookieJar extends BiblioSimpleBlock
{
	public static final BlockCookieJar instance = new BlockCookieJar();
	public static final String name = "CookieJar";

	public BlockCookieJar()
	{
		super(Material.glass, soundTypeMetal, name);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x+ 0.18F, y, z + 0.18F, x + 0.82F, y + 0.75F,z + 0.82F);
	}

    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return this.getSelectedBoundingBoxFromPool(world,x,y,z);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!world.isRemote && tile != null && tile instanceof TileEntityCookieJar)
		{
			TileEntityCookieJar cookiejar = (TileEntityCookieJar)world.getTileEntity(x, y, z);
			if (cookiejar != null)
			{
				cookiejar.setIsOpen(true);
				//world.scheduleBlockUpdate(pos, this, 0, 0);

				//world.markBlockRangeForRenderUpdate(i, j, k, i, j, k);
			}
			player.openGui(BiblioCraft.instance, 0, world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCookieJar();
	}


	@Override
    public boolean canProvidePower()
    {
        return true;
    }

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
    {
		TileEntityCookieJar cookiejar = (TileEntityCookieJar)world.getTileEntity(x, y, z);
		if (cookiejar != null)
		{
			boolean ison = cookiejar.getIsOpen();
			if (ison)
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

    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.glass.getBlockTextureFromSide(side);
    }
}
