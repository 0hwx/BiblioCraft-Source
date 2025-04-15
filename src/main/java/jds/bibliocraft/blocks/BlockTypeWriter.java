package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityTypewriter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTypeWriter extends BiblioColorBlock
{
	public static final String name = "Typewriter";
	public static final BlockTypeWriter instance = new BlockTypeWriter();

	public BlockTypeWriter()
	{
		super(Material.iron, soundTypeMetal, name);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!world.isRemote && tile != null && tile instanceof TileEntityTypewriter)
		{
			TileEntityTypewriter typewriter = (TileEntityTypewriter)tile;
			ItemStack playerhand = player.getHeldItem();
			ForgeDirection typeAngle = typewriter.getAngle();
            ForgeDirection sides = ForgeDirection.getOrientation(side);
			if (player.isSneaking())
			{
				if (typewriter.getStackInSlot(0) != null)
				{
					typewriter.removeStackFromInventoryFromWorld(0, player, this);
				}
			}
			else
			{
				if (playerhand != null)
				{
					if (playerhand.getItem() == Items.paper)
					{
						int returnsize = typewriter.addPaper(playerhand);
						if (returnsize > 0)
						{
							playerhand.stackSize = (returnsize);
							player.inventory.setInventorySlotContents(player.inventory.currentItem, playerhand);
						}
						else if (returnsize == 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						}
					}
					return true;
				}

				if (isFrontFace(typeAngle, sides) && typewriter.getStackInSlot(1) == null)
				{
					if (typewriter.getHasEnoughPaper())
					{

						if (typewriter.getBookWriteCount() < 15)
						{
							typewriter.entityName = player.getCommandSenderName();
							typewriter.entityType = 0;
							typewriter.setBookWriteCount(typewriter.getBookWriteCount()+1, true);
						}
						else
						{
							if (typewriter.removePaperForBook())
							{
								typewriter.foundValidEntity = true;
								typewriter.entityName = player.getCommandSenderName();
								typewriter.entityType = 0;
								typewriter.writeCustomBook();
							}
						}
					}
					return true;
				}

				if (typewriter.getStackInSlot(1) != null)
				{
					typewriter.foundValidEntity = false;
					typewriter.entityName = "";
					typewriter.entityType = 0;
					typewriter.setBookWriteCount(0, true);
					typewriter.removeStackFromInventoryFromWorld(1, player, this);
				}
			}
		}
		return true;
	}

	public boolean isFrontFace(ForgeDirection typeAngle, ForgeDirection face)
	{
		switch (typeAngle)
		{
			case SOUTH:{if (face == ForgeDirection.WEST){return true;}break;}
			case WEST:{if (face == ForgeDirection.NORTH){return true;}break;}
			case NORTH:{if (face == ForgeDirection.EAST){return true;}break;}
			case EAST:{if (face == ForgeDirection.SOUTH){return true;}break;}
			default: break;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTypewriter();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//		modelParts.add("base");
//		return modelParts;
//	}

//	@Override
//	public TRSRTransformation getAdditionalTransforms(TRSRTransformation transform, BiblioTileEntity tile)
//	{
//		transform = transform.compose(new TRSRTransformation(new Vector3f(0.09f, 0.0f, 0.0f),
//				   new Quat4f(0.0f, 1.0f, 0.0f, 1.0f),
//				   new Vector3f(1.0f, 1.0f, 1.0f),
//				   new Quat4f(0.0f, 1.0f, 0.0f, 1.0f)));
//		return transform;
//	}

    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		AxisAlignedBB output = this.getBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof BiblioTileEntity)
		{
			BiblioTileEntity biblioTile = (BiblioTileEntity)tile;
			switch (biblioTile.getAngle())
			{
				case SOUTH:{output = this.getBlockBounds(0.05F, 0.0F, 0.25F, 0.55F, 0.3F, 0.75F); break;}
				case WEST:{output = this.getBlockBounds(0.25F, 0.0F, 0.05F, 0.75F, 0.3F, 0.55F); break;}
				case NORTH:{output = this.getBlockBounds(0.45F, 0.0F, 0.25F, 0.95F, 0.3F, 0.75F); break;}
				case EAST:{output = this.getBlockBounds(0.25F, 0.0F, 0.45F, 0.75F, 0.3F, 0.95F); break;}
				default:break;
			}
		}
		return output;
	}
}
