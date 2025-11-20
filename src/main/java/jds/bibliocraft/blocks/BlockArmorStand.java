package jds.bibliocraft.blocks;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.blocks.base.BiblioWoodBlock;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityArmorStand;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


public class BlockArmorStand extends BiblioWoodBlock {
	public static final BlockArmorStand instance = new BlockArmorStand();
	public static final String name = "ArmorStand";

	public BlockArmorStand() {
		super(name, false);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (!world.isRemote && te != null && te instanceof TileEntityArmorStand) {
			TileEntityArmorStand tile = (TileEntityArmorStand) te;
			ItemStack playerhand = player.getHeldItem();
			boolean isPoweredBottom;
			boolean isPowerTop;
			int yCheck = (int) (hitY * 2);
			if (!tile.getIsBottomStand()) {
				tile = (TileEntityArmorStand) world.getTileEntity(x, y, z);
				if (tile == null)
					return false;
				isPowerTop = world.isBlockIndirectlyGettingPowered(x, y, z);
				isPoweredBottom = world
						.isBlockIndirectlyGettingPowered(x, y, z);
				yCheck += 2;
			} else {
				isPoweredBottom = world.isBlockIndirectlyGettingPowered(x, y, z);
				isPowerTop = world
						.isBlockIndirectlyGettingPowered(x, y, z);
			}

			if (player.isSneaking()) {
				if (isPoweredBottom || isPowerTop) {
					handleArmorTransation(player, tile, 0);
					handleArmorTransation(player, tile, 1);
					handleArmorTransation(player, tile, 2);
					handleArmorTransation(player, tile, 3);
					return true;
				}

				if (yCheck >= 0 && yCheck <= 3) {
					handleArmorTransation(player, tile, yCheck);
				}
				return true;
			}

			if (playerhand != null) {
				Item stackItem = playerhand.getItem();
				if (stackItem instanceof ItemArmor) {
					ItemArmor armorItem = (ItemArmor) stackItem;
					int armorType = armorItem.armorType;
					if ((yCheck == 0 && armorType == 3) ||
							(yCheck == 1 && armorType == 2) ||
							(yCheck == 2 && armorType == 1) ||
							(yCheck == 3 && armorType == 0)) {
						if (tile.addArmor(playerhand, armorType)) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							return true;
						}
					}
				}
			}

			player.openGui(BiblioCraft.instance, 1, world, tile.xCoord, tile.yCoord,
					tile.zCoord);

		}
		return true;
	}

	/**
	 * For armor type, 0 = feet, 1 = legs, 2 = chest, 3 = head
	 *
	 * @param player
	 * @param armorTile
	 * @param armortype
	 */
	private void handleArmorTransation(EntityPlayer player, TileEntityArmorStand armorTile, int armortype) {
		ItemStack playerArmor = player.inventory.armorInventory[armortype];
		int atilearmor = -1;
		switch (armortype) {
			case 0: {
				atilearmor = 3;
				break;
			}
			case 1: {
				atilearmor = 2;
				break;
			}
			case 2: {
				atilearmor = 1;
				break;
			}
			case 3: {
				atilearmor = 0;
				break;
			}
			default:
				break;
		}
		if (atilearmor != -1 && armortype >= 0 && armortype < 4) {
			ItemStack standArmor = armorTile.getStackInSlot(atilearmor);// getArmor(atilearmor);
			// ItemStack plegcopy = null;
			// ItemStack alegcopy = null;
			/*
			 * if (playerArmor != null)
			 * {
			 * plegcopy = playerArmor.copy();
			 * }
			 * if (standArmor != null)
			 * {
			 * alegcopy = standArmor.copy();
			 * }
			 */
			if (standArmor != null) {
				player.inventory.armorInventory[armortype] = standArmor;
				// sendPlayerArmorPacket(player, alegcopy, armortype);
			} else {
				player.inventory.armorInventory[armortype] = null;
			}

			if (playerArmor != null) {
				armorTile.setInventorySlotContents(atilearmor, playerArmor);
			} else {
				armorTile.setInventorySlotContents(atilearmor, null);
			}
			/*
			 * if (alegcopy == null)
			 * {
			 * player.inventory.armorInventory[armortype] = null;
			 * //sendPlayerArmorPacket(player, alegcopy, armortype); // I'm not sure I
			 * actually need these packets to the client anymore
			 * }
			 * if (playerArmor == null)
			 * {
			 * armorTile.setInventorySlotContents(atilearmor, null);
			 * }
			 */
		}
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityArmorStand();
	}

//	@Override
//	public List<String> getModelParts(BiblioTileEntity tile) {
//		List<String> modelParts = new ArrayList<String>();
//		if (tile != null && tile instanceof TileEntityArmorStand) {
//			TileEntityArmorStand te = (TileEntityArmorStand) tile;
//			if (te.getIsBottomStand()) {
//				modelParts.add("bottomStand");
//				modelParts.add("topStand");
//			}
//		}
//		return modelParts;
//	}

	@Override
	public void additionalPlacementCommands(BiblioTileEntity tile, EntityLivingBase player) {
		Block state = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord, tile.zCoord); // TODO changed this and it worked on the meta
																			// data
		ChunkCoordinates pos = new ChunkCoordinates(tile.xCoord, tile.yCoord + 1, tile.zCoord);
		tile.getWorldObj().setBlock(pos.posX, pos.posY, pos.posZ, state);
		TileEntity te = tile.getWorldObj().getTileEntity(pos.posX, pos.posY, pos.posZ);
		if (te != null && te instanceof TileEntityArmorStand) {
			TileEntityArmorStand stand = (TileEntityArmorStand) te;
			stand.setCustomTexureString(tile.getCustomTextureString());
			stand.setIsBottomStand(false);
			stand.setAngle(tile.getAngle());
		}

	}

//	@Override
//	public TRSRTransformation getAdditionalTransforms(TRSRTransformation transform, BiblioTileEntity tile) {
//		transform = transform.compose(new TRSRTransformation(new Vector3f(0.0f, 0.0f, 0.0f),
//				new Quat4f(0.0f, 0.0f, 0.0f, 1.0f),
//				new Vector3f(1.0f, 1.0f, 1.0f),
//				new Quat4f(0.0f, 0.0f, 0.0f, 1.0f)));
//		return transform;
//	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		if (world.isAirBlock(x, y, z)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta) {
		TileEntity t = world.getTileEntity(x, y, z);
		if (t != null && t instanceof TileEntityArmorStand) {
			TileEntityArmorStand tile = (TileEntityArmorStand) t;
            ChunkCoordinates newPos;
			if (tile.getIsBottomStand()) {
				newPos = new ChunkCoordinates(x, y + 1, z);
			} else {
				newPos = new ChunkCoordinates(x, y - 1, z);
				BiblioTileEntity top = (BiblioTileEntity) t;
				BiblioTileEntity bottom = (BiblioTileEntity) world.getTileEntity(newPos.posX, newPos.posY, newPos.posZ);
				top.setCustomTexureString(bottom.getCustomTextureString());
			}
			TileEntity sTile = world.getTileEntity(newPos.posX, newPos.posY, newPos.posZ);
			if (sTile != null && sTile instanceof TileEntityArmorStand) {

				dropItems(world, newPos.posX, newPos.posY, newPos.posZ);
				world.setBlockToAir(newPos.posX, newPos.posY, newPos.posZ);
			}
		}

		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, blockBroken, meta);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		AxisAlignedBB output = this.getBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof BiblioTileEntity) {
			BiblioTileEntity caseTile = (BiblioTileEntity) tile;
			if (caseTile.getAngle() == ForgeDirection.SOUTH || caseTile.getAngle() == ForgeDirection.NORTH) {
				output = this.getBlockBounds(0.3F, 0.0F, 0.0F, 0.7F, 1.0F, 1.0F);
			} else {
				output = this.getBlockBounds(0.0F, 0.0F, 0.3F, 1.0F, 1.0F, 0.7F);
			}
		}
		return output;
	}
}
