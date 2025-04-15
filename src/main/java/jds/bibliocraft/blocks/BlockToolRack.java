package jds.bibliocraft.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.containers.ContainerWeaponRack;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityToolRack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


public class BlockToolRack extends BiblioWoodBlock
{
	public static final String name = "ToolRack";
	public static final BlockToolRack instance = new BlockToolRack();

	public BlockToolRack()
	{
		super(name, true);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
            ForgeDirection sides = ForgeDirection.getOrientation(side);
			BiblioTileEntity tile = (BiblioTileEntity)world.getTileEntity(x, y, z);
			if (isFrontOfBlock(sides, tile.getAngle()) && !player.isSneaking())
			{
				 int slot = getSlotNumberFromClickon2x2block(tile.getAngle(), hitX, hitY, hitZ);
				 boolean testValue = false; // turns true if I add or remove something from the shelf
				 if (slot >= 0)
				 {
					 if (player.getHeldItem() != null && ContainerWeaponRack.isItemTool(player.getHeldItem().getItem(), player.getHeldItem()))
					 {
						 testValue = tile.addStackToInventoryFromWorld(player.getHeldItem(), slot, player);
					 }

					 if (!testValue)
					 {
						 testValue = tile.removeStackFromInventoryFromWorld(slot, player, this);
					 }
				 }
			}
			else
			{
				player.openGui(BiblioCraft.instance, 4, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityToolRack();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
////		List<String> modelParts = Lists.newArrayList(OBJModel.Group.ALL);
//		return List.of();
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
}
