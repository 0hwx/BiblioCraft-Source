package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.gui.GuiTypesetting;
import jds.bibliocraft.items.ItemChase;
import jds.bibliocraft.items.ItemPlate;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityTypeMachine;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTypesettingTable extends BiblioSimpleBlock
{
	public static final BlockTypesettingTable instance = new BlockTypesettingTable();
	public static final String name = "TypesettingTable";

	public BlockTypesettingTable()
	{
		super(Material.wood, soundTypeWood, name);
	}
	/** Returns the slot number or -2 if no action should be taken, and -1 for gui action. */
	public static int getSlot(TileEntityTypeMachine tile, ForgeDirection face, float hitX, float hitZ)
	{
		int output = -2;
		if (face != ForgeDirection.UP)
			return output;

		float adjustedX = hitX;
		float adjustedZ = hitZ;

		switch (tile.getAngle())
		{
			case WEST:
			{
				adjustedX = hitZ;
				adjustedZ = 1.0f - hitX;
				break;
			}
			case NORTH:
			{
				adjustedX = 1.0f - hitX;
				adjustedZ = 1.0f - hitZ;
				break;
			}
			case EAST:
			{
				adjustedX = 1.0f - hitZ;
				adjustedZ = hitX;
				break;
			}
			default: break;
		}
		// so adjustments have been made. 0,0 is the bottom left of the top of the block.
		// use adjutedX and adjustedZ values.
		output = -1; // set the default output, if the method made it this far, to -1 to represent opening the GUI
		// chase slot = 1
		 // book/left slot = 0.
		 // new plate/right slot = 2

		 if (adjustedX <= 0.41f)
		 {
			 if (adjustedZ <= 0.5f)
			 {
				 output = 0; // book slot
			 }
			 else
			 {
				 output = 2; // plate output slot
			 }
		 }
		 else if (adjustedX >= 0.57 && adjustedZ > 0.3f && adjustedZ < 0.7f)
		 {
			 output = 1;
		 }

		return output;
	}


	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		 int iCheck = (int) (hitX * 2);
		 int iCheck2 = (int) (hitX * 3);
		 int kCheck = (int) (hitZ * 3);
		 int kCheck2 = (int) (hitZ * 2);
		 TileEntity t = world.getTileEntity(x, y, z);
		 if (t != null && t instanceof TileEntityTypeMachine)
		 {
			 TileEntityTypeMachine tile = (TileEntityTypeMachine)t;
             ForgeDirection sides = ForgeDirection.getOrientation(side);
			 int slot = getSlot(tile, sides, hitX, hitZ);
			 if (!world.isRemote)
			 {
				 ItemStack playerhand = player.getHeldItem();
				 switch (slot)
				 {
					 case 0:
					 {
						 // book / left slot
						 if (player.isSneaking())
				 			{
				 				 if (plateResetorSaveBook(tile, world, player))
				 				 {
				 					tile.booklistset();
				 				 }
				 			}
				 			else
				 			{
				 				if (!addBookorPlate(tile, player, world))
				 				{
				 					tile.removeStackFromInventoryFromWorld(slot, player, this);
				 				}
				 			}
						 break;
					 }
					 case 1:
					 {
						 // chase slot
						 // I should probly use the new method I created for this that is default to my tiles.
						 boolean addedStack = false;
						 if (playerhand != null && playerhand.getItem() instanceof ItemChase)
						 {
							 addedStack = tile.addStackToInventoryFromWorld(playerhand, slot, player);
						 }
						 if (!addedStack)
						 {
							 tile.removeStackFromInventoryFromWorld(slot, player, this);
						 }
						 break;
					 }
					 case 2:
					 {
						 //plate / right slot
						 tile.removeStackFromInventoryFromWorld(slot, player, this);
						 break;
					 }
				 }
			 }
			 else
			 {
				 if (slot == -1)
				 {
					 //gui client side
					 openGUI(player, tile);
				 }
			 }
		 }
		 return true;
	}

	@SideOnly(Side.CLIENT)
	private void openGUI(EntityPlayer player, TileEntityTypeMachine tile)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiTypesetting(player, tile));
	}

	 public boolean addBookorPlate(TileEntityTypeMachine tile, EntityPlayer player, World world)
	 {
		 ItemStack playerhand = player.getHeldItem();
		 if (playerhand != null)
		 {
			 boolean hasAdded = tile.addBookorPlate(playerhand, world);
			 if (hasAdded)
			 {
				 player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				 return true;
			 }
		 }
		 return false;
	 }

	 public boolean plateResetorSaveBook(TileEntityTypeMachine tile, World world, EntityPlayer player)
	 {
		 boolean hasReset = tile.resetPlate();
		 boolean hasSavedBook = tile.saveBook(world);
		 boolean hasEnchantedBook = tile.enchantPlate(player);
		 boolean hasAtlas = tile.createAtlasPlate(player);
		 if (hasReset || hasSavedBook || hasEnchantedBook || hasAtlas)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	 }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTypeMachine();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return this.getBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.92F, 1.0F);
	}

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
    	boolean output = true;
    	if (side == ForgeDirection.UP)
    	{
    		output = false;
    	}
        return output;
    }

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile)
//	{
//		List<String> modelParts = new ArrayList<String>();
//		modelParts.add("base");
//
//		ItemStack book = tile.getStackInSlot(0);
//		if (book != null)
//		{
//			if (book.getItem() instanceof ItemPlate)
//			{
//				modelParts.add("plateLeft");
//			}
//			else
//			{
//				if (tile instanceof TileEntityTypeMachine)
//				{
//					TileEntityTypeMachine type = (TileEntityTypeMachine)tile;
//					if (type.bookIsSaved)
//					{
//						modelParts.add("bookBlue");
//					}
//					else
//					{
//						if (type.enchantedBookCheck())
//						{
//							modelParts.add("bookEnchant");
//						}
//						else
//						{
//							modelParts.add("bookRed");
//						}
//					}
//				}
//			}
//		}
//
//		ItemStack chase = tile.getStackInSlot(1);
//		if (chase != null)
//		{
//			if (chase.stackSize > 0)
//			{
//				modelParts.add("chase1");
//			}
//			if (chase.stackSize > 16)
//			{
//				modelParts.add("chase2");
//			}
//			if (chase.stackSize > 32)
//			{
//				modelParts.add("chase3");
//			}
//			if (chase.stackSize > 48)
//			{
//				modelParts.add("chase4");
//			}
//		}
//
//		ItemStack plate = tile.getStackInSlot(2);
//		if (plate != null)
//		{
//			modelParts.add("plateRight");
//		}
//
//
//		return modelParts;
//	}
}
