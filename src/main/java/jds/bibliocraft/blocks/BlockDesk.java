package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.Config;
import jds.bibliocraft.helpers.EnumColor;
import jds.bibliocraft.helpers.EnumRelativeLocation;
import jds.bibliocraft.items.ItemRecipeBook;
import jds.bibliocraft.network.BiblioNetworking;
import jds.bibliocraft.network.packet.client.BiblioDeskOpenGui;
import jds.bibliocraft.states.TextureState;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityDesk;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


public class BlockDesk extends BiblioWoodBlock
{
	public static final String name = "Desk";
	public static final BlockDesk instance = new BlockDesk();

	public BlockDesk()
	{
		super(name, false);
	}

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
    	boolean output = false;
    	if (side == ForgeDirection.UP)
    	{
    		output = true;
    	}
        return output;
    }

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{

		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && tile instanceof TileEntityDesk)
			{
				TileEntityDesk desk = (TileEntityDesk)tile;
                ForgeDirection angle = desk.getAngle();
				boolean dontOpenGui = false;
				if (player.isSneaking())
				{
					// drop stuff
					if (isLeftBookStack(hitX, hitZ, ForgeDirection.getOrientation(side), angle))
					{
						dontOpenGui = desk.removeStackFromInventoryFromWorld(desk.getLeftBookFullSlot(), player, this);
					}
					else if (isRightBookStack(hitX, hitZ, ForgeDirection.getOrientation(side), angle))
					{
						dontOpenGui = desk.removeStackFromInventoryFromWorld(desk.getRightBookFullSlot(), player, this);
					}
					else if (isWritingBook(hitX, hitZ, ForgeDirection.getOrientation(side), angle))
					{
						dontOpenGui = desk.removeStackFromInventoryFromWorld(0, player, this);
					}

					if (ForgeDirection.getOrientation(side) != ForgeDirection.UP)
					{
						dontOpenGui = desk.removeStackFromInventoryFromWorld(9, player, this);
					}
				}
				else
				{
					// not sneaking
					ItemStack playerhand = player.getHeldItem();
					if (playerhand != null)
					{
						if (!Config.isBlock(playerhand) && Config.testBookValidity(playerhand))
						{
							if (isLeftBookStack(hitX, hitZ, ForgeDirection.getOrientation(side), angle))
							{
								if (desk.getLeftBookEmptySlot() != -1)
									dontOpenGui = desk.addStackToInventoryFromWorld(playerhand, desk.getLeftBookEmptySlot(), player);
							}
							else if (isRightBookStack(hitX, hitZ, ForgeDirection.getOrientation(side), angle))
							{
								if (desk.getLeftBookEmptySlot() != -1)
								dontOpenGui = desk.addStackToInventoryFromWorld(playerhand, desk.getRightBookEmptySlot(), player);
							}
							else if (isWritingBook(hitX, hitZ, ForgeDirection.getOrientation(side), angle))
							{
								dontOpenGui = desk.addStackToInventoryFromWorld(playerhand, 0, player);
							}
						}

						if (ForgeDirection.getOrientation(side) != ForgeDirection.UP && Block.getBlockFromItem(playerhand.getItem()) instanceof BlockCarpet)
						{
							dontOpenGui = desk.addStackToInventoryFromWorldSingleStackSize(playerhand, 9, player);
						}
					}
				}

				if (!dontOpenGui)
				{
					if (isWritingBook(hitX, hitZ, ForgeDirection.getOrientation(side), angle) && desk.getStackInSlot(0) != null)
					{
						// ByteBuf buffer = Unpooled.buffer();
						// buffer.writeInt(pos.getX());
						// buffer.writeInt(pos.getY());
						// buffer.writeInt(pos.getZ());
						// ByteBufUtils.writeItemStack(buffer, desk.getStackInSlot(0));
						// if (desk.getStackInSlot(0).getItem() == ItemRecipeBook.instance) // TODO added this if statment
						// {
						// 	buffer.writeBoolean(Config.enableRecipeBookCrafting);
						// }
						// else
						// {
						// 	buffer.writeBoolean(false);
						// }
						BiblioNetworking.INSTANCE.sendTo(new BiblioDeskOpenGui(x, y, z, desk.getStackInSlot(0), desk.getStackInSlot(0).getItem() == ItemRecipeBook.instance ? Config.enableRecipeBookCrafting : false), (EntityPlayerMP) player);
						// BiblioCraft.ch_BiblioDeskGUIS.sendTo(new FMLProxyPacket(new PacketBuffer(buffer), "BiblioDeskOpenGUI"), (EntityPlayerMP) player);
					}
					else
					{
						player.openGui(BiblioCraft.instance, 7, world, x, y, z);
					}
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDesk();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//		modelParts.add("candle");
//		modelParts.add("pen");
//		modelParts.add("deskTopEndRight");
//		modelParts.add("deskTopEndLeft");
//		modelParts.add("legLeft");
//		modelParts.add("legRight");
//		modelParts.add("shelfSingle");
//		if (tile instanceof TileEntityDesk)
//		{
//			TileEntityDesk desk = (TileEntityDesk)tile;
//			switch (desk.getSingleLeftRightCenter())
//			{
//				case LEFT:
//				{
//					modelParts = new ArrayList<String>();
//					modelParts.add("deskTopRight");
//					modelParts.add("deskTopEndLeft");
//					modelParts.add("legLeft");
//					modelParts.add("shelfLeft");
//					modelParts.add("candle");
//					modelParts.add("backRight");
//					break;
//				}
//				case RIGHT:
//				{
//					modelParts = new ArrayList<String>();
//					modelParts.add("deskTopEndRight");
//					modelParts.add("deskTopLeft");
//					modelParts.add("legRight");
//					modelParts.add("shelfRight");
//					modelParts.add("pen");
//					modelParts.add("backLeft");
//					break;
//				}
//				case CENTER:
//				{
//					modelParts = new ArrayList<String>();
//					modelParts.add("deskTopRight");
//					modelParts.add("deskTopLeft");
//					modelParts.add("backBoth");
//					break;
//				}
//				default: break;
//			}
//			if (desk.getOpenBook() != null && desk.getOpenBook().getItem() != null && !desk.getHasMap())
//			{
//				modelParts.add("bookOpen");
//			}
//			if (desk.getLeftBookStack() > 0) { modelParts.add("lbook1"); }
//			if (desk.getLeftBookStack() > 1) { modelParts.add("lbook2"); }
//			if (desk.getLeftBookStack() > 2) { modelParts.add("lbook3"); }
//			if (desk.getLeftBookStack() > 3) { modelParts.add("lbook4"); }
//
//			if (desk.getRightBookStack() > 0) { modelParts.add("rbook1"); }
//			if (desk.getRightBookStack() > 1) { modelParts.add("rbook2"); }
//			if (desk.getRightBookStack() > 2) { modelParts.add("rbook3"); }
//			if (desk.getRightBookStack() > 3) { modelParts.add("rbook4"); }
//
//			if (desk.getStackInSlot(9) != null)
//			{
//				modelParts.add("carpet");
//			}
//		}
//
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

    public boolean isWritingBook(float hitx, float hitz, ForgeDirection face, ForgeDirection angle)
    {
    	boolean returnValue = false;
    	if (face == ForgeDirection.UP)
    	{
	    	switch (angle)
	    	{
	    		case SOUTH: { if (hitz > 0.2 && hitz < 0.8 && hitx < 0.5) { returnValue = true; } break; }
	    		case WEST: { if (hitx > 0.2 && hitx < 0.8 && hitz < 0.5) { returnValue = true; } break; }
	    		case NORTH: { if (hitz > 0.2 && hitz < 0.8 && hitx > 0.5) { returnValue = true; } break; }
	    		case EAST: { if (hitx > 0.2 && hitx < 0.8 && hitz > 0.5) { returnValue = true; } break; }
	    		default: break;

	    	}
    	}
    	return returnValue;
    }
    public boolean isLeftBookStack(float hitx, float hitz, ForgeDirection face, ForgeDirection angle)
    {
    	boolean returnValue = false;
    	if (face == ForgeDirection.UP)
    	{
	    	switch (angle)
	    	{
	    		case SOUTH: { if (hitx > 0.5 && hitz < 0.5) { returnValue = true; } break; }
	    		case WEST: { if (hitx > 0.5 && hitz > 0.5) { returnValue = true; } break; }
	    		case NORTH: { if (hitx < 0.5 && hitz > 0.5) { returnValue = true; } break; }
	    		case EAST: { if (hitx < 0.5 && hitz < 0.5) { returnValue = true; } break; }
	    		default: break;

	    	}
    	}
    	return returnValue;
    }
    public boolean isRightBookStack(float hitx, float hitz, ForgeDirection face, ForgeDirection angle)
    {
    	boolean returnValue = false;
    	if (face == ForgeDirection.UP)
    	{
	    	switch (angle)
	    	{
	    		case SOUTH: { if (hitx > 0.5 && hitz > 0.5) { returnValue = true; } break; }
	    		case WEST: { if (hitx < 0.5 && hitz > 0.5) { returnValue = true; } break; }
	    		case NORTH: { if (hitx < 0.5 && hitz < 0.5) { returnValue = true; } break; }
	    		case EAST: { if (hitx > 0.5 && hitz < 0.5) { returnValue = true; } break; }
	    		default: break;
	    	}
    	}
    	return returnValue;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
    	if (world.isAirBlock(x, y + 1, z))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

	@Override
    public TextureState addAdditionTextureStateInformation(BiblioTileEntity tile, TextureState state)
    {
		ItemStack carpet = tile.getStackInSlot(9);
		if (carpet != null)
		{
			state.setColorOne(EnumColor.getColorFromCarpetOrWool(carpet));
		}
		return state;
    }

	@Override
	public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta)
	{
		dropItems(world, x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityDesk)
		{
			TileEntityDesk currDesk = (TileEntityDesk)tile;
			if (currDesk != null)
			{
				ForgeDirection angle = currDesk.getAngle();
				switch (currDesk.getSingleLeftRightCenter())
				{
					case LEFT:
					{
						//adjust desl on right
						adjustRightDesk(world, currDesk.xCoord, currDesk.yCoord, currDesk.zCoord, angle);
						break;
					}
					case RIGHT:
					{
						//adjust desk on left
						adjustLeftDesk(world, currDesk.xCoord, currDesk.yCoord, currDesk.zCoord, angle);
						break;
					}
					case CENTER:
					{
						//adjust desk on left and right
						adjustLeftDesk(world, currDesk.xCoord, currDesk.yCoord, currDesk.zCoord, angle);
						adjustRightDesk(world, currDesk.xCoord, currDesk.yCoord, currDesk.zCoord, angle);
						break;
					}
					default: break;
				}
			}
		}
		super.breakBlock(world, x, y, z, blockBroken, meta);
	}

	private void adjustLeftDesk(World world, int x, int y, int z, ForgeDirection angle)
	{
        switch (angle)
		{
			case SOUTH:
			{
				z--;
				break;
			}
			case WEST:
			{
				x++;
				break;
			}
			case NORTH:
			{
				z++;
				break;
			}
			case EAST:
			{
				x--;
				break;
			}
			default: break;
		}

		TileEntityDesk desk = (TileEntityDesk)world.getTileEntity(x, y, z);
		if (desk != null)
		{
			switch (desk.getSingleLeftRightCenter())
			{
				case LEFT:
				{
					desk.setSingleLeftRightCenter(EnumRelativeLocation.SINGLE);
					break;
				}
				case CENTER:
				{
					desk.setSingleLeftRightCenter(EnumRelativeLocation.RIGHT);
					break;
				}
				default: break;
			}
		}
	}

	private void adjustRightDesk(World world, int x, int y, int z, ForgeDirection angle)
	{
		switch (angle)
		{
			case SOUTH:
			{
				z++;
				break;
			}
			case WEST:
			{
				x--;
				break;
			}
			case NORTH:
			{
				z--;
				break;
			}
			case EAST:
			{
				x++;
				break;
			}
			default: break;
		}
		TileEntityDesk desk = (TileEntityDesk)world.getTileEntity(x, y, z);
		if (desk != null)
		{
			switch (desk.getSingleLeftRightCenter())
			{
				case RIGHT:
				{
					desk.setSingleLeftRightCenter(EnumRelativeLocation.SINGLE);
					break;
				}
				case CENTER:
				{
					desk.setSingleLeftRightCenter(EnumRelativeLocation.LEFT);
					break;
				}
				default: break;
			}
		}
	}
}
