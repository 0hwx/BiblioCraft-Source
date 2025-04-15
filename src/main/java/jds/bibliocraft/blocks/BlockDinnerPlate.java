package jds.bibliocraft.blocks;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityDinnerPlate;
import net.minecraft.block.material.Material;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;


public class BlockDinnerPlate extends BiblioSimpleBlock
{
	public static final BlockDinnerPlate instance = new BlockDinnerPlate();
	public static final String name = "DinnerPlate";
	private int clicks = 0;

	public BlockDinnerPlate()
	{
		super(Material.glass, soundTypeGlass, name);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return this.getBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 0.1F, 0.85F);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			ItemStack playerStack = player.getHeldItem();
			TileEntityDinnerPlate plateTile = (TileEntityDinnerPlate)world.getTileEntity(x, y, z);
			if (plateTile != null)
			{
				if (playerStack != null)
				{
					if (playerStack.getItem() instanceof ItemFood)
					{
						int addedFood = plateTile.addFood(playerStack);
						if (addedFood != -1)
						{
							if (addedFood == 0)
							{
								player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							}
							else
							{
								ItemStack newStack = playerStack.copy();
								newStack.stackSize = (addedFood);
								//System.out.println(addedFood);
								player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
								player.inventory.setInventorySlotContents(player.inventory.currentItem, newStack);
							}
							return true;
						}
					}
				}
				if (plateTile.isPlateEmpty() || player.isSneaking())
				{
					player.openGui(BiblioCraft.instance, 0, world, x, y, z);
					return true;
				}

				FoodStats playerFoodStats = player.getFoodStats();
				if (playerFoodStats.needFood())
				{
					clicks++;
					if (clicks >= 1 && clicks <= 3)
					{
						world.playSoundAtEntity(player , "random.eat", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
					}
					else
					{
						ItemStack slot0 = plateTile.getStackInSlot(0);
						ItemStack slot1 = plateTile.getStackInSlot(1);
						ItemStack slot2 = plateTile.getStackInSlot(2);
						if (slot0 != null && playerFoodStats.needFood() && slot0.getItem() instanceof ItemFood)
						{
							ItemStack food0 = plateTile.getFood(0);
							if (plateTile.isFoodHaveBowl(food0, world, player))
							{
								dropEmptyBowl(world, x, y, z);
							}
						}
						if (slot1 != null && playerFoodStats.needFood() && slot1.getItem() instanceof ItemFood)
						{
							ItemStack food1 = plateTile.getFood(1);
							if (plateTile.isFoodHaveBowl(food1, world, player))
							{
								dropEmptyBowl(world,  x, y, z);
							}
						}
						if (slot2 != null && playerFoodStats.needFood() && slot2.getItem() instanceof ItemFood)
						{
							ItemStack food2 = plateTile.getFood(2);
							if (plateTile.isFoodHaveBowl(food2, world, player))
							{
								dropEmptyBowl(world,  x, y, z);
							}
						}
						world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
						clicks = 0;
					}
				}
			}
		}
		return true;
	}

	 private void dropEmptyBowl(World world, int x, int y, int z)
	{
		ItemStack bowl = new ItemStack(Items.bowl, 0);
		bowl.stackSize = 1;
		if (bowl != null && bowl.stackSize > 0)
		{
			float iAdjust = 0;
			float kAdjust;
			//System.out.println(caseTile.getAngle());

			EntityItem entityItem = new EntityItem(world, x +0.5F, y +0.5F, z +0.5F, new ItemStack(bowl.getItem(), bowl.stackSize, bowl.getItemDamage()));
			entityItem.motionX = 0;
			entityItem.motionY = 0;
			entityItem.motionZ = 0;
			world.spawnEntityInWorld(entityItem);
			bowl.stackSize = 0;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDinnerPlate();
	}

//	@Override
//	public TRSRTransformation getAdditionalTransforms(TRSRTransformation transform, BiblioTileEntity tile)
//	{
//		transform = transform.compose(new TRSRTransformation(new Vector3f(0.0f, 0.0f, 0.0f),
//				   new Quat4f(0.0f, 1.0f, 0.0f, 1.0f),
//				   new Vector3f(1.0f, 1.0f, 1.0f),
//				   new Quat4f(0.0f, 1.0f, 0.0f, 1.0f)));
//		return transform;
//	}
}
