package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.items.ItemRecipeBook;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityFancyWorkbench;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFancyWorkbench extends BiblioWoodBlock
{
	public static final String name = "FancyWorkbench";
	public static final BlockFancyWorkbench instance = new BlockFancyWorkbench();

	public BlockFancyWorkbench()
	{
		super(name, false);
	}


	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		// TODO this crashes when the menu is opened for some reason
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!world.isRemote && tile != null && tile instanceof TileEntityFancyWorkbench)
		{
			TileEntityFancyWorkbench bench = (TileEntityFancyWorkbench)tile;
			ItemStack playerhand = player.getHeldItem();
			if (isBackOfBlock(bench.getAngle(), ForgeDirection.getOrientation(side)) && hitY > 0.22F && hitY < 0.74F)
			{
				int booknum = isWhatBook(bench.getAngle(), hitX, hitZ) + 1;
				//System.out.println(booknum);					ItemStack playerhand = player.getHeldItem(EnumHand.MAIN_HAND);
				if (playerhand != null)
				{
					if (playerhand.getItem() instanceof ItemBook || playerhand.getItem() instanceof ItemRecipeBook && booknum != -1)
					{
						if (bench.addStackToInventoryFromWorld(playerhand, booknum, player))
						{
							return true;
						}
					}
				}
				else if (player.isSneaking())
				{
					if (bench.getStackInSlot(booknum) != null && booknum != -1)
					{
						bench.removeStackFromInventoryFromWorld(booknum, player, this);
					}
					return true;
				}
			}
			if (!bench.isTooManyPlayers())
			{
				player.openGui(BiblioCraft.instance, 0, world, x, y, z);
			}
		}
		return true;
	}

	public int isWhatBook(ForgeDirection angle, float hitX, float hitZ)
	{
		int xt = (int) (hitX * 8);
		int zt = (int)( hitZ * 8);
		switch (angle)
		{
			case SOUTH:
			{
				return zt;
			}
			case WEST:
			{
				switch (xt)
				{
				case 0: return 7;
				case 1: return 6;
				case 2: return 5;
				case 3: return 4;
				case 4: return 3;
				case 5: return 2;
				case 6: return 1;
				case 7: return 0;
				default: break;
				}
			}
			case NORTH:
			{
				switch (zt)
				{
				case 0: return 7;
				case 1: return 6;
				case 2: return 5;
				case 3: return 4;
				case 4: return 3;
				case 5: return 2;
				case 6: return 1;
				case 7: return 0;
				default: break;
				}
			}
			case EAST:
			{
				return xt;
			}
			default: break;
		}
		return -1;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFancyWorkbench();
	}



	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{


	}
    @Override
    public IIcon getIcon(int side, int meta) {
        switch (meta)
        {
            case 0:
                return Blocks.planks.getIcon(0, 0);
            case 1:
                return Blocks.planks.getIcon(0, 1);
            case 2:
                return Blocks.planks.getIcon(0, 2);
            case 3:
                return Blocks.planks.getIcon(0, 3);
            case 4:
                return Blocks.planks.getIcon(0, 4);
            case 5:
                return Blocks.planks.getIcon(0, 5);
            default:
                break;
        }
        return Blocks.planks.getBlockTextureFromSide(side);
    }

}
