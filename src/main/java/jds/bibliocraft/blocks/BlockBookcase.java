package jds.bibliocraft.blocks;

//import jds.bibliocraft.items.ItemDrill;
//import jds.bibliocraft.items.ItemLock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.Config;
import jds.bibliocraft.blocks.blockitems.BlockItemBookcase;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityBookcase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import static net.minecraftforge.common.util.ForgeDirection.*;


public class BlockBookcase extends BiblioWoodBlock
{
	public static final String name = "Bookcase";
	public static final BlockBookcase instance = new BlockBookcase(name);

	public BlockBookcase(String blockName)
	{
		super(blockName, true);
		setTickRandomly(true);
		//setUnlocalizedName(blockName);
	}
//    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
//        TileEntity tile = worldIn.getTileEntity(x, y, z);
//        if (tile != null && tile instanceof TileEntityBookcase) {
//            TileEntityBookcase bookcaseTile = (TileEntityBookcase) tile;
//            switch (bookcaseTile.getAngle()) {
//                case SOUTH:
//                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
//                    break;
//                case WEST:
//                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
//                    break;
//                case NORTH:
//                    this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//                    break;
//                case EAST:
//                    this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
//            }
//        }
//    }
//
//
//    @Override
//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
//    {
//        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
//    }
	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			BiblioTileEntity biblioTile = (BiblioTileEntity)world.getTileEntity(x, y, z);
			ItemStack playerhand = player.getHeldItem();
			if (playerhand != null && playerhand.getItem() instanceof BlockItemBookcase)
			{
				return false;
			}

			if (biblioTile instanceof TileEntityBookcase)
			{
				TileEntityBookcase tileBookCase = (TileEntityBookcase)biblioTile;
				int yCheck = (int) (hitY * 2);
				ForgeDirection angle = tileBookCase.getAngle();
				if  (yCheck == 1)  // so this is the top shelf
				{
					int booktest = isWhatBook(angle, hitX, hitZ);
					if (player.isSneaking())
					{
						if (booktest >= 0 && booktest < 16)
						{
							dropStackInSlot(world, x, y,z, booktest, getDropPositionOffset(x, y, z, player));
							tileBookCase.setBook(booktest, null);
						}
						return true;
					}
					else
					{
						if (playerhand != null)
						{
							if (!Config.isBlock(playerhand) && Config.testBookValidity(playerhand))
							{
								// here is where I try to add the book.
								if (booktest >= 0 && booktest < 16)
								{
									boolean addedBook = tileBookCase.setBook(booktest, playerhand);
									if (addedBook)
									{
										player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
										return true;
									}
								}
							}
							else
							{
								player.openGui(BiblioCraft.instance, 0, world, x, y, z);
								return true;
							}

						}
					}

				}
				else if (yCheck == 0)  // and this is the bottom shelf
				{
					int booktest = isWhatBook(angle, hitX, hitZ) + 8;
					if (player.isSneaking())
					{
						if (booktest >= 0 && booktest < 16)
						{
							dropStackInSlot(world, x, y, z, booktest, getDropPositionOffset(x, y, z, player));
							tileBookCase.setBook(booktest, null);
						}
						return true;
					}
					else
					{
						if (playerhand != null)
						{
							if (!Config.isBlock(playerhand) && Config.testBookValidity(playerhand))
							{
								if (booktest >= 0 && booktest < 16)
								{
									boolean addedBook = tileBookCase.setBook(booktest, playerhand);
									if (addedBook)
									{
										player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
										return true;
									}
								}
							}
							else
							{
								player.openGui(BiblioCraft.instance, 0, world, x, y, z);
								return true;
							}

						}
					}

				}
				// the gui should open if the player does not have a book in hand. Will use shift-click to remove books.
				player.openGui(BiblioCraft.instance, 0, world, x, y, z);
			}
		}
		return true;
	}

	@Override
    public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
    {
    	//System.out.println("Bookcase placment?");
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityBookcase();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//		if (tile instanceof TileEntityBookcase)
//		{
//			TileEntityBookcase bookcase = (TileEntityBookcase)tile;
//	    	int[] books = bookcase.getCheckedBooks();
//
//	    	modelParts.add("bookcase");
//	    	for (int i = 0; i < books.length; i++)
//	    	{
//	    		if (books[i] == 1)
//	    		{
//	    			modelParts.add("book" + (i+1));
//	    		}
//	    	}
//		}
//		else
//		{
////			modelParts = Lists.newArrayList(OBJModel.Group.ALL);
//		}
//
//		return modelParts;
//	}

	@Override
    public float getEnchantPowerBonus(World world, int x, int y, int z)
    {

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityBookcase)
		{
			TileEntityBookcase bookcase = (TileEntityBookcase) tile;
			return (1.0F / 8.0F)*bookcase.getFilledSlots();
		}
		else
		{
			return 0.0F;
		}
    }


	@Override
    public boolean canProvidePower()
    {
        return true;
    }

	@Override
    public int isProvidingWeakPower(IBlockAccess worldIn, int x, int y, int z, int side)
    {
		// IMPORTANT Number To Angle Conversion System
		// 0 = West
		// 1 = North
		// 2 = East
		// 3 = South

		// Faces
		// 0 = bottom
		// 1 = top
		// 2 = front of angle 1 = North
		// 3 = front of angle 3 = South
		// 4 = front of angle 0 = West
		// 5 = front of angle 2 = East

		TileEntity tile = worldIn.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityBookcase)
		{
			TileEntityBookcase bookcase = (TileEntityBookcase)worldIn.getTileEntity(x, y, z);
			ForgeDirection angle = bookcase.getAngle();
			if (bookcase.getredstone())
			{
				switch (angle)
				{
				case SOUTH:
				{
					if (side == EAST.ordinal())
					{
						return 0;
					}
					else
					{
						return bookcase.getRedstoneBookSlot();
					}
				}
				case WEST:
				{
					if (side == SOUTH.ordinal())
					{
						return 0;
					}
					else
					{
						return bookcase.getRedstoneBookSlot();
					}
				}
				case NORTH:
				{
					if (side == WEST.ordinal())
					{
						return 0;
					}
					else
					{
						return bookcase.getRedstoneBookSlot();
					}
				}
				case EAST:
				{
					if (side == NORTH.ordinal())
					{
						return 0;
					}
					else
					{
						return bookcase.getRedstoneBookSlot();
					}
				}
				default: return 0;
				}
			}
		}
		return 0;
    }

	@Override
	public int isProvidingStrongPower(IBlockAccess worldIn, int x, int y, int z, int side)
    {

		return isProvidingWeakPower(worldIn, x, y, z, side);
    }

	public static boolean isTopShelf(float hitY)
	{
		if (hitY > 0.5)
		{
			//Top shelf
			return true;
		}
		else
		{
			//Bottom shelf
			return false;
		}
	}

	public static int isWhatBook(ForgeDirection angle, float hitX, float hitZ)
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
			default:break;
		}
		return -1;
	}


    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x1, int y1, int z1, Random rando)
    {
        super.randomDisplayTick(world, x1, y1, z1, rando);

    	TileEntity tile = world.getTileEntity(x1, y1, z1);
        if (tile != null && tile instanceof TileEntityBookcase)
        {
	        TileEntityBookcase bookcase = (TileEntityBookcase)tile;
	        if (bookcase != null && bookcase.getFilledSlots() > 0)
	        {

		        for (int x = x1 - 2; x <= x1 + 2; ++x)
		        {
		            for (int z = y1 - 2; z <= y1 + 2; ++z)
		            {
		                if (x > x1 - 2 && x < x1 + 2 && z == y1 - 1)
		                {
		                    z = y1 + 2;
		                }
		                if (rando.nextInt(8) == 0)
		                {

		                    for (int y = y1 - 1; y <= y1 + 1; ++y)
		                    {

		                        if (world.getBlock(x, y, z) == Blocks.enchanting_table)
		                        {
		                            if (!world.isAirBlock((x - x1) / 2 + x1, y, (z - y1) / 2 + y1))
		                            {
		                                break;
		                            }
		                            double px = (double)x + 0.5D;
		                            double py = (double)y + 2.0D;
                            		double pz = (double)z + 0.5D;
		                            double velx = (double)((float)(x1 - x) + rando.nextFloat()) - 0.5D;
		                            double vely = (double)((float)(y1 - y) - rando.nextFloat() - 1.0F);
		                            double velz = (double)((float)(y1 - z) +  rando.nextFloat()) - 0.5D;
		                            world.spawnParticle("enchantmenttable", px, py, pz, velx, vely, velz);
		                        }
		                    }
		                }
		            }
		        }
   			}

        }
    }

//	@Override
//	public TRSRTransformation getAdditionalTransforms(TRSRTransformation transform, BiblioTileEntity tile)
//	{
//		return transform;
//	}

}
