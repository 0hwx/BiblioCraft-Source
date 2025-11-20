package jds.bibliocraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.blocks.base.BiblioSimpleBlock;
import jds.bibliocraft.blocks.base.BiblioWoodBlock;
import jds.bibliocraft.gui.GuiTypesetting;
import jds.bibliocraft.items.ItemChase;
import jds.bibliocraft.items.ItemPlate;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.tileentities.TileEntityTypeMachine;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class BlockTypesettingTable extends BiblioSimpleBlock
{
	public static final BlockTypesettingTable instance = new BlockTypesettingTable();
	public static final String name = "TypesettingTable";

    public static final int SLOT_NONE = -2;
    public static final int SLOT_GUI = -1;
    public static final int SLOT_BOOK = 0;
    public static final int SLOT_CHASE = 1;
    public static final int SLOT_PLATE = 2;

    public static class SlotHitbox {
        public final float minX, minZ, maxX, maxZ;
        public final int slotId;

        /**
         * @param slotId The ID to return if this hitbox is clicked
         * @param minX The minimum X bound (inclusive)
         * @param minZ The minimum Z bound (inclusive)
         * @param maxX The maximum X bound (inclusive)
         * @param maxZ The maximum Z bound (inclusive)
         */
        public SlotHitbox(int slotId, float minX, float minZ, float maxX, float maxZ) {
            this.slotId = slotId;
            this.minX = minX;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxZ = maxZ;
        }

        /**
         * Checks if the point (x, z) is inside this hitbox's bounds.
         */
        public boolean contains(float x, float z) {
            return x >= this.minX && x <= this.maxX &&
                z >= this.minZ && z <= this.maxZ;
        }
    }

    // --- 3. Create a static list of all your hitboxes ---
    // These values now match the original logic perfectly.
    public static final List<SlotHitbox> HITBOXES = new ArrayList<>();

    static {
        // (slotId, minX, minZ, maxX, maxZ)

        // Book Slot: (x <= 0.41) && (z <= 0.5)
        HITBOXES.add(new SlotHitbox(SLOT_BOOK, 0.0f, 0.0f, 0.41f, 0.5f));

        // Plate Slot: (x <= 0.41) && (z > 0.5)
        // Note: We use 0.50001f to simulate (z > 0.5)
        HITBOXES.add(new SlotHitbox(SLOT_PLATE, 0.0f, 0.50001f, 0.41f, 1.0f));

        // Chase Slot: (x >= 0.57) && (z > 0.3) && (z < 0.7)
        // Note: We use 0.30001f and 0.69999f to simulate (z > 0.3) and (z < 0.7)
        HITBOXES.add(new SlotHitbox(SLOT_CHASE, 0.57f, 0.30001f, 1.0f, 0.69999f));
    }


    public BlockTypesettingTable() {
        super(Material.wood, soundTypeWood, name);
    }

    /**
     * Reworked to use a clean, data-driven list of hitboxes.
     * Calculates which slot on the TOP face was hit.
     */
    public static int getSlot(TileEntityTypeMachine tile, ForgeDirection face, float hitX, float hitZ) {
        // This method only works on the TOP face.
        if (face != ForgeDirection.UP) {
            return SLOT_NONE;
        }

        // 1. Rotate the hit coordinates to match the block's "default" (South) state.
        float adjustedX = hitX;
        float adjustedZ = hitZ;

        switch (tile.getAngle()) {
            case WEST:
                adjustedX = hitZ;
                adjustedZ = 1.0f - hitX;
                break;
            case NORTH:
                adjustedX = 1.0f - hitX;
                adjustedZ = 1.0f - hitZ;
                break;
            case EAST:
                adjustedX = 1.0f - hitZ;
                adjustedZ = hitX;
                break;
            default: // SOUTH
                break;
        }

        // 2. Check the (adjustedX, adjustedZ) point against our list of hitboxes.
        for (SlotHitbox hitbox : HITBOXES) {
            if (hitbox.contains(adjustedX, adjustedZ)) {
                return hitbox.slotId; // We found a match!
            }
        }

        // 3. If no specific hitbox was hit, they hit the main body.
        return SLOT_GUI;
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
    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.92F, 1.0F);
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

    private IIcon typesetting;

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        typesetting = iconRegister.registerIcon("bibliocraft:typesettingtable");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return typesetting;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemTypesettingTable(0, -0.5D, 0,180.0D);
                return;
            case EQUIPPED_FIRST_PERSON, EQUIPPED:
                renderItemTypesettingTable(-0.5D,0D,0.5D, 90.0D);
                return;
            default:
                renderItemTypesettingTable(0,-0.5D,0, 0);
        }
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, Tessellator tes) {
        tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1, 1, 1);
        tes.addTranslation(x + 0.5F, y, z + 0.5F);
        TileEntityTypeMachine tile = (TileEntityTypeMachine) world.getTileEntity(x, y, z);
        ObjContext ctx = new ObjContext(world, x, y, z, tile.getAngle(), tile.getVertPosition(), tile.getShiftPosition());
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);

        renderTypesettingTable(obj,tile);

        return true;
    }


    public void renderItemTypesettingTable(double x, double y, double z, double rotate) {
        final Tessellator tes = Tessellator.instance;
        RenderHelper.disableStandardItemLighting();
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(x, y, z);
        ObjContext ctx = new ObjContext(null, x, y, z);
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);
        obj.start();
        renderTypesettingTable(obj, null);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }

    public void renderTypesettingTable(ObjBuilder obj , TileEntityTypeMachine tile) {
        obj.setModel(EnumObjModels.TYPESETTING);
        List<String> modelParts = new ArrayList<>();

        modelParts.add("base");
        if (tile != null) {
            ItemStack book = tile.getStackInSlot(0);
            if (book != null) {
                if (book.getItem() instanceof ItemPlate) {
                    modelParts.add("plateLeft");
                } else {
                    if (tile.bookIsSaved) {
                        modelParts.add("bookBlue");
                    } else {
                        if (tile.enchantedBookCheck()) {
                            modelParts.add("bookEnchant");
                        } else {
                            modelParts.add("bookRed");
                        }
                    }
                }
            }

            ItemStack chase = tile.getStackInSlot(1);
            if (chase != null) {
                if (chase.stackSize > 0) {
                    modelParts.add("chase1");
                }
                if (chase.stackSize > 16) {
                    modelParts.add("chase2");
                }
                if (chase.stackSize > 32) {
                    modelParts.add("chase3");
                }
                if (chase.stackSize > 48) {
                    modelParts.add("chase4");
                }
            }

            ItemStack plate = tile.getStackInSlot(2);
            if (plate != null) {
                modelParts.add("plateRight");
            }
        }

        obj.renderPart(modelParts, typesetting);
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
