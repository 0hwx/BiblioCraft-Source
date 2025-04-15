package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.items.ItemPaintingCanvas;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityPaintPress;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPaintingPress extends BiblioSimpleBlock
{
	public static final BlockPaintingPress instance = new BlockPaintingPress();
	public static final String name = "PaintingPress";

	public BlockPaintingPress()
	{
		super(Material.iron, soundTypeAnvil, name);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!world.isRemote && tile != null && tile instanceof TileEntityPaintPress)
		{
			TileEntityPaintPress paintPress = (TileEntityPaintPress)tile;
			ItemStack playerhand = player.getHeldItem();
			if (playerhand != null && playerhand.getItem() instanceof ItemPaintingCanvas)
			{
				int canvasAddReturn = paintPress.addCanvas(playerhand);
				if (canvasAddReturn == 0)
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					return true;
				}
				else if (canvasAddReturn > 0)
				{
					playerhand.stackSize = (canvasAddReturn);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, playerhand);
					return true;
				}
			}

			if (player.isSneaking())
			{
				if (paintPress.getStackInSlot(0) != null)
				{
					paintPress.removeStackFromInventoryFromWorld(0, player, this);
					//dropPainting(world, i, j, k);
					//paintPress.addCanvas(null);
					return true;
				}
			}

			if (isFrontHandleSpot(paintPress.getAngle(), ForgeDirection.getOrientation(side), hitX, hitY, hitZ))
			{
				if (paintPress.getStackInSlot(0) != null)
				{
					paintPress.setCycle(true);
					return true;

				}
			}
			player.openGui(BiblioCraft.instance, 0, world, x, y, z);
			return true;

		}
		return true;
	}

	public boolean isFrontHandleSpot(ForgeDirection angle, ForgeDirection face, float hitX, float hitY, float hitZ)
	{
		//System.out.println("angle  "+angle+"    face"+face+"    hitX  "+hitX+"   hitY  "+hitY+"    HitZ  "+hitZ);

		switch (angle)
		{
			case SOUTH:
			{
				if ((face == ForgeDirection.WEST && hitY > 0.75)||(face == ForgeDirection.UP && hitX < 0.16f))
				{
					return true;
				}
				break;
			}
			case WEST:
			{
				if ((face == ForgeDirection.NORTH && hitY > 0.75)||(face == ForgeDirection.UP && hitZ < 0.16f))
				{
					return true;
				}
				break;
			}
			case NORTH:
			{
				if ((face == ForgeDirection.EAST && hitY > 0.75)||(face == ForgeDirection.UP && hitX > 0.84f))
				{
					return true;
				}
				break;
			}
			case EAST:
			{
				if ((face == ForgeDirection.SOUTH && hitY > 0.75)||(face == ForgeDirection.UP && hitZ > 0.84f))
				{
					return true;
				}
				break;
			}
			default: break;
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPaintPress();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//
//		//modelParts.add("painting");
//		modelParts.add("base");
//		if (tile.getStackInSlot(0) != null)
//				modelParts.add("canvas");
//		return modelParts;
//	}
}
