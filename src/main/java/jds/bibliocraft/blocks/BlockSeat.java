package jds.bibliocraft.blocks;

import java.util.List;

import jds.bibliocraft.Config;
import jds.bibliocraft.blocks.base.BiblioWoodBlock;
import jds.bibliocraft.entity.EntitySeat;
import jds.bibliocraft.helpers.SeatHelper;
import jds.bibliocraft.items.ItemSeatBack;
import jds.bibliocraft.items.ItemSeatBack2;
import jds.bibliocraft.items.ItemSeatBack3;
import jds.bibliocraft.items.ItemSeatBack4;
import jds.bibliocraft.items.ItemSeatBack5;
import jds.bibliocraft.states.TextureState;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntitySeat;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSeat extends BiblioWoodBlock
{
	public static final String name = "Seat";
	public static final BlockSeat instance = new BlockSeat();

	public BlockSeat()
	{
		super(name, false);

	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!world.isRemote && tile != null && tile instanceof TileEntitySeat)
		{
			TileEntitySeat seatTile = (TileEntitySeat)tile;
			ItemStack playerStack = player.getHeldItem();
			if (playerStack != null)
			{
				if (playerStack.getItem() == Item.getItemFromBlock(Blocks.carpet))
				{
					if (ForgeDirection.getOrientation(side) == ForgeDirection.UP || ForgeDirection.getOrientation(side) == ForgeDirection.DOWN)
					{
						int returnStackSize = seatTile.addSeatCover(playerStack);
						if (returnStackSize != -1)
						{
							if (returnStackSize == 0)
							{
								player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							}
							else
							{
								playerStack.stackSize = (returnStackSize);
								player.inventory.setInventorySlotContents(player.inventory.currentItem, playerStack);
							}
							return true;
						}
					}
					else
					{
						int returnStackSize = seatTile.setCarpet(playerStack);
						if (returnStackSize != -1)
						{
							 if (returnStackSize == 0)
							 {
								player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							 }
							 else
							 {
								 playerStack.stackSize = (returnStackSize);
								 player.inventory.setInventorySlotContents(player.inventory.currentItem, playerStack);
							 }
						}
						return true;
					}
				}
				Item playerItem = playerStack.getItem();
				if (playerItem instanceof ItemSeatBack || playerItem instanceof ItemSeatBack2 || playerItem instanceof ItemSeatBack3 || playerItem instanceof ItemSeatBack4 || playerItem instanceof ItemSeatBack5)
				{
					int returnStackSize = seatTile.addSeatBack(playerStack);
					if (returnStackSize != -1)
					{
						if (returnStackSize == 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						}
						else
						{
							playerStack.stackSize = (returnStackSize);
							player.inventory.setInventorySlotContents(player.inventory.currentItem, playerStack);
						}
						return true;
					}
				}
			}
			if (!seatTile.getHasSitter())
			{
				sitDown(player, world, x, y, z, seatTile);
				seatTile.setSitter(true);
			}
			else if (player.isSneaking())
			{
				seatTile.dismountEntity();
			}
		}
		return true;
	}

	public void sitDown(EntityPlayer player, World world, int x, int y, int z, TileEntitySeat tile)
	{
		EntitySeat seatEntity = new EntitySeat(world, x, y + 1.0d, z, tile);
		world.spawnEntityInWorld(seatEntity);
		player.mountEntity(seatEntity); //todo check this

	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySeat();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//		modelParts.add("stool");
//		modelParts.add("seat");
//
//		if (tile instanceof TileEntitySeat)
//		{
//			TileEntitySeat seat = (TileEntitySeat)tile;
//			if (!seat.getSouthConnect() && !seat.getNorthConnect() && !seat.getEastConnect() && !seat.getWestConnect())
//			{
//				modelParts.add("leg1");
//				modelParts.add("leg2");
//				modelParts.add("leg3");
//				modelParts.add("leg4");
//				modelParts.add("brace1");
//				modelParts.add("brace2");
//				modelParts.add("brace3");
//				modelParts.add("brace4");
//			}
//			else
//			{
//				modelParts = getSeatAndLegParts(seat, modelParts);
//			}
//
//			if (seat.getHasBack() > 0)
//			{
//				modelParts = getSeatBackParts(seat, modelParts);
//			}
//
//			if (seat.isCarpetFull())
//			{
//				modelParts.add("carpet");
//			}
//
//		}
//		return modelParts;
//	}

	public List<String> getSeatBackParts(TileEntitySeat tile, List<String> parts)
	{
		SeatHelper adjust = new SeatHelper(tile.getSouthConnect(), tile.getWestConnect(), tile.getNorthConnect(), tile.getEastConnect(), tile.getAngle());
		boolean southConnect = adjust.getSouthConnect();
		boolean northConnect = adjust.getNorthConnect();
		boolean eastConnect = adjust.getEastConnect();
		boolean westConnect = adjust.getWestConnect();

		switch (tile.getHasBack())
		{
			case 1:
			{
				parts.add("backSupport");
				parts.add("backCloth");
				break;
			}
			case 2:
			{
				parts.add("backWood2");
				parts.add("backCloth2");
				break;
			}
			case 3:
			{
				parts.add("backWood2");
				parts.add("backWood2Top");
				parts.add("backCloth2");
				break;
			}
			case 4:
			{
				parts.add("backSupport2");
				parts.add("backCloth003");
				break;
			}
			case 5:
			{
				parts.add("backWood2");
				parts.add("fancyBackWood");
				parts.add("backCloth2");
				break;
			}
			default: break;
		}

		if (southConnect)
		{
			switch (tile.getHasBack())
			{
				case 1:{parts.add("backCloth1connect1"); break;}
				case 4:{parts.add("backCloth2connect1"); break;}
				default: break;
			}
		}
		if (northConnect)
		{
			switch (tile.getHasBack())
			{
				case 1:{parts.add("backCloth1connect3"); break;}
				case 4:{parts.add("backCloth2connect3"); break;}
				default: break;
			}
		}
		return parts;
	}

	public List<String> getSeatAndLegParts(TileEntitySeat tile, List<String> parts)
	{
		SeatHelper adjust = new SeatHelper(tile.getSouthConnect(), tile.getWestConnect(), tile.getNorthConnect(), tile.getEastConnect(), tile.getAngle());
		boolean southConnect = adjust.getSouthConnect();
		boolean northConnect = adjust.getNorthConnect();
		boolean eastConnect = adjust.getEastConnect();
		boolean westConnect = adjust.getWestConnect();

		parts = getSeatParts(parts, southConnect, northConnect, eastConnect, westConnect);
		parts = getLegParts(parts, southConnect, northConnect, eastConnect, westConnect);
		return parts;
	}

	public List<String> getSeatParts(List<String> parts, boolean southConnect, boolean northConnect, boolean eastConnect, boolean westConnect)
	{
		if (southConnect)
		{
			parts.add("bench1");
		}
		if (eastConnect)
		{
			parts.add("bench2");
		}
		if (northConnect)
		{
			parts.add("bench3");
		}
		if (westConnect)
		{
			parts.add("bench4");
		}

		if (southConnect)
		{
			parts.add("benchSeat1");
		}
		if (eastConnect)
		{
			parts.add("benchSeat2");
		}
		if (northConnect)
		{
			parts.add("benchSeat3");
		}
		if (westConnect)
		{
			parts.add("benchSeat4");
		}
		return parts;
	}

	public List<String> getLegParts(List<String> parts, boolean southConnect, boolean northConnect, boolean eastConnect, boolean westConnect)
	{
		boolean leg1 = true;
		boolean leg2 = true;
		boolean leg3 = true;
		boolean leg4 = true;
		if ((southConnect && northConnect) || (eastConnect && westConnect))
		{
			leg1 = false;
			leg2 = false;
			leg3 = false;
			leg4 = false;
		}
		if (southConnect && !northConnect && eastConnect && !westConnect)
		{
			leg2 = false;
		}
		if (southConnect && !northConnect && !eastConnect && westConnect)
		{
			leg1 = false;
		}
		if (!southConnect && northConnect && eastConnect && !westConnect)
		{
			leg3 = false;
		}
		if (!southConnect && northConnect && !eastConnect && westConnect)
		{
			leg4 = false;
		}
		if (southConnect && !northConnect && !eastConnect && !westConnect)
		{
			leg1 = false;
			leg2 = false;
		}
		if (!southConnect && northConnect && !eastConnect && !westConnect)
		{
			leg3 = false;
			leg4 = false;
		}
		if (!southConnect && !northConnect && eastConnect && !westConnect)
		{
			leg2 = false;
			leg3 = false;
		}
		if (!southConnect && !northConnect && !eastConnect && westConnect)
		{
			leg1 = false;
			leg4 = false;
		}

		if (leg1)
		{
			parts.add("leg1");
		}
		if (leg2)
		{
			parts.add("leg2");
		}
		if (leg3)
		{
			parts.add("leg3");
		}
		if (leg4)
		{
			parts.add("leg4");
		}

		if (leg1 && leg2)
		{
			parts.add("brace1");
		}
		if (leg2 && leg3)
		{
			parts.add("brace2");
		}
		if (leg3 && leg4)
		{
			parts.add("brace3");
		}
		if (leg1 && leg4)
		{
			parts.add("brace4");
		}
		return parts;
	}

	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{


	}

//	@Override
//	public TRSRTransformation getAdditionalTransforms(TRSRTransformation transform, BiblioTileEntity tile)
//	{
//		transform = transform.compose(new TRSRTransformation(new Vector3f(-0.0f, 0.0f, -0.0f),
//			     new Quat4f(0.0f, 1.0f, 0.0f, 1.0f),
//			     new Vector3f(1.0f, 1.0f, 1.0f),
//			     new Quat4f(0.0f, 1.0f, 0.0f, 1.0f)));
//		return transform;
//	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return this.getBlockBounds(0.16F, 0.0F, 0.16F, 0.84F, 0.74F, 0.84F);
	}

	@Override
    public TextureState addAdditionTextureStateInformation(BiblioTileEntity tile, TextureState state)
    {
		if (tile instanceof TileEntitySeat)
		{
			TileEntitySeat seat = (TileEntitySeat)tile;
			state.setColorOne(seat.getSeatColor());
			state.setColorTwo(seat.getCarpetColor());
			state.setAdditionalWood(seat.getBackWoodType());
			state.setAdditionalTextureString(seat.getCustomBackTexture());
			state.setFlag(seat.getHasBack() > 0);
			state.setFlag2(tile.getBlockMetadata() == EnumWoodType.FRAME.getID());
		}
		return state;
    }

	@Override
    public boolean canProvidePower()
    {
		if (Config.chairRedstone)
		{
			return true;
		}
		else
		{
			return false;
		}
    }

	@Override
	public int isProvidingStrongPower(IBlockAccess worldIn, int x, int y, int z, int side)
    {

		return isProvidingWeakPower(worldIn, x, y, z, side);
    }

	@Override
	  public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
    {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (Config.chairRedstone && tile != null && tile instanceof TileEntitySeat)
		{
			TileEntitySeat seat = (TileEntitySeat)tile;
			if (seat.getHasSitter())
			{
				return 15;
			}
		}
		return 0;
    }

	// on block broken additional thing
	private void breakConnectsFix(World world, int x, int y, int z, TileEntitySeat tileSeat)
    {
    	boolean northConnect = tileSeat.getNorthConnect();
		boolean southConnect = tileSeat.getSouthConnect();
		boolean eastConnect = tileSeat.getEastConnect();
		boolean westConnect = tileSeat.getWestConnect();
		TileEntity tile;
		TileEntitySeat adjTile;
		// south == PosK, ,east == posI, ,north == negK, ,west == negI
		if (northConnect)
		{
			tile = world.getTileEntity(x, y, z - 1);
			if (tile != null && tile instanceof TileEntitySeat)
			{
				adjTile = (TileEntitySeat)tile;
				adjTile.setSouthConnect(false);
			}
		}
		if (southConnect)
		{
			tile = world.getTileEntity(x, y, z + 1);
			if (tile != null && tile instanceof TileEntitySeat)
			{
				adjTile = (TileEntitySeat)tile;
				adjTile.setNorthConnect(false);
			}
		}
		if (eastConnect)
		{
			tile = world.getTileEntity(x + 1, y, z);
			if (tile != null && tile instanceof TileEntitySeat)
			{
				adjTile = (TileEntitySeat)tile;
				adjTile.setWestConnect(false);
			}
		}
		if (westConnect)
		{
			tile = world.getTileEntity(x - 1, y, z);
			if (tile != null && tile instanceof TileEntitySeat)
			{
				adjTile = (TileEntitySeat)tile;
				adjTile.setEastConnect(false);
			}
		}
    }

	@Override
	public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntitySeat)
		{
			breakConnectsFix(world, x, y, z, (TileEntitySeat)tile);
		}
		super.breakBlock(world, x, y, z, blockBroken, meta);
	}
}
