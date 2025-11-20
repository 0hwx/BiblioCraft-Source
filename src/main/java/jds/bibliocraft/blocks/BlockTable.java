package jds.bibliocraft.blocks;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.blocks.base.BiblioWoodBlock;
import jds.bibliocraft.helpers.EnumColor;
import jds.bibliocraft.items.ItemDrill;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.states.TextureState;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityTable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class BlockTable extends BiblioWoodBlock
{
	public static final String name = "Table";
	public static final BlockTable instance = new BlockTable();

	public BlockTable()
	{
		super(name, false);
	}


	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			ItemStack playerhand = player.getHeldItem();
			TileEntityTable tabletile = (TileEntityTable)world.getTileEntity(x, y, z);
            ForgeDirection sides = ForgeDirection.getOrientation(side);
			if (sides == ForgeDirection.UP)
			{
				if (player.isSneaking())
				{
					player.openGui(BiblioCraft.instance, 9, world, x, y, z);
					return true;
				}
				else
				{
					 if (tabletile != null)
					 {
						 if (playerhand != null)
						 {
							 if(playerhand.getItem() == Item.getItemFromBlock(Blocks.carpet))
							 {
								 int additem = tabletile.setTableCloth(playerhand);
								 if (additem != -1)
								 {
									 if (additem == 0)
									 {
										player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
									 }
									 else
									 {
										playerhand.stackSize = (additem);
									 	player.inventory.setInventorySlotContents(player.inventory.currentItem, playerhand);
									 }
								 }
								 return true;
							 }

							 Item drilltest = playerhand.getItem();
							 if(drilltest != null)
							 {
								 if (drilltest instanceof ItemDrill)
								 {
									 dropStackInSlot(world, x, y, z, 1, Vec3.createVectorHelper(x, y + 1, z));
									 tabletile.setTableCloth(null);
									 return false;
								 }
							 }

						 }
						 if (playerhand != null && !tabletile.isSlotFull())
						 {
							 //place the item in the inventory
							 tabletile.addStackToInventoryFromWorld(playerhand, 0, player);
							 return true;
						 }
						 if (tabletile.isSlotFull())
						 {
							 // drop the item
                             dropStackInSlot(world, x, y, z, 0, Vec3.createVectorHelper(x, y + 1, z));
							 tabletile.setTableSlot(null);
							 return true;
						 }
					 }
				 }
				 return true;
			 }
			 else
			 {
				 //not top of table
				 if (playerhand != null)
				 {
					 if(playerhand.getItem() == Item.getItemFromBlock(Blocks.carpet))
					 {
						 int additem = tabletile.setCarpet(playerhand);
						 if (additem == -1)
						 {

						 }
						 else
						 {
							 if (additem == 0)
							 {
								player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							 }
							 else
							 {
								playerhand.stackSize = (additem);
								player.inventory.setInventorySlotContents(player.inventory.currentItem, playerhand);
							 }
						 }
						 return true;
					 }
				 }

			 }
			 if (playerhand != null && tabletile != null)
			 {
				 String itemname = playerhand.toString().toLowerCase();
				 if (itemname.contains("measure") || itemname.contains("wrench") || itemname.contains("screwdriver") || itemname.contains("crowbar") || itemname.contains("bibliodrill") || itemname.contains("handdrill"))
				 {
					 if (sides == ForgeDirection.NORTH || sides == ForgeDirection.SOUTH)
					 {
						 int xangle = tabletile.getSlotX();
						 switch (xangle)
						 {
							 case 0:{tabletile.setSlotX(1); break;}
							 case 1:{tabletile.setSlotX(2); break;}
							 case 2:{tabletile.setSlotX(3); break;}
							 default:{tabletile.setSlotX(0); break;}
						 }
					 }
					 if (sides == ForgeDirection.EAST || sides == ForgeDirection.WEST)
					 {
						 int yangle = tabletile.getSlotY();
						 switch (yangle)
						 {
							 case 0:{tabletile.setSlotY(1); break;}
							 case 1:{tabletile.setSlotY(2); break;}
							 case 2:{tabletile.setSlotY(3); break;}
							 default:{tabletile.setSlotY(0); break;}
						 }
					 }
				 }
			 }
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTable();
	}


	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{
		biblioTile.setAngle(ForgeDirection.SOUTH);
		if (biblioTile instanceof TileEntityTable)
		{
			checkNeighborTables(biblioTile.getWorldObj(), biblioTile.xCoord, biblioTile.yCoord, biblioTile.zCoord, (TileEntityTable)biblioTile);
		}
	}


	@Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
		 if (side == ForgeDirection.UP)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
    }

//    @Override
//    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift)
//    {
//        AxisAlignedBB output = this.getBlockBounds(0.00F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//        if (biblioTile != null && biblioTile instanceof TileEntityTable table)
//        {
//            boolean leg1 = table.getLeg1();
//            boolean leg2 = table.getLeg2();
//            boolean leg3 = table.getLeg3();
//            boolean leg4 = table.getLeg4();
//            boolean legMono = table.getMonoleg();
//            if (leg1 == false && leg2 == false && leg3 == false && leg4 == false && legMono == false)
//            {
//                output = this.getBlockBounds(0.00F, 0.88F, 0.0F, 1.0F, 1.0F, 1.0F);
//            }
//        }
//        return output;
//    }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
        TileEntity tilee = world.getTileEntity(x, y, z);
        if (tilee instanceof TileEntityTable)
        {
            TileEntityTable tile = (TileEntityTable)tilee;
            // If it's a 'four' connection (state 5), it has no legs
            if (tile.getConnectionState() == 5)
            {
                // Collision box is just the top
                return this.getBlockBounds(0.00F, 0.88F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        // All other states have legs, so return full block bounds
        return this.getBlockBounds(0.00F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityTable)
		{
			TileEntityTable tabletile = (TileEntityTable)world.getTileEntity(x, y, z);
			checkNeighborTables(tile.getWorldObj(), x, y, z, tabletile);
			//world.markBlockForUpdate(pos);
			tabletile.getWorldObj().markBlockForUpdate(x, y, z);
		}
	}

	 public void checkNeighborTables(World world, int x, int y, int z, TileEntityTable table)
	 {
         // Check all 4 horizontal neighbors
         boolean north = world.getBlock(x, y, z - 1) instanceof BlockTable;
         boolean south = world.getBlock(x, y, z + 1) instanceof BlockTable;
         boolean east = world.getBlock(x + 1, y, z) instanceof BlockTable;
         boolean west = world.getBlock(x - 1, y, z) instanceof BlockTable;

         int connections = (north ? 1 : 0) + (south ? 1 : 0) + (east ? 1 : 0) + (west ? 1 : 0);

         // 0=none, 1=one, 2=straight, 3=curve, 4=three, 5=four
         int newState = 0;
         // This is the rotation of the model
         ForgeDirection newAngle = ForgeDirection.SOUTH;

         switch (connections) {
             case 0:
                 newState = 0; // "none" (monoleg)
                 break;
             case 1:
                 newState = 1; // "one"
                 // Set angle based on which side is connected
                 if (south) newAngle = ForgeDirection.EAST;
                 else if (west) newAngle = ForgeDirection.SOUTH;
                 else if (east) newAngle = ForgeDirection.NORTH;
                 else if (north) newAngle = ForgeDirection.WEST;
                 break;
             case 2:
                 if (north && south) {
                     newState = 2; // "straight"
                     newAngle = ForgeDirection.NORTH; // N-S alignment
                 } else if (east && west) {
                     newState = 2; // "straight"
                     newAngle = ForgeDirection.EAST; // E-W alignment
                 } else {
                     newState = 3; // "curve"
                     // Set angle for the "inner corner"
                     if (south && east) newAngle = ForgeDirection.EAST;
                     else if (south && west) newAngle = ForgeDirection.SOUTH;
                     else if (north && west) newAngle = ForgeDirection.WEST;
                     else if (north && east) newAngle = ForgeDirection.NORTH;
                 }
                 break;
             case 3:
                 newState = 4; // "three"
                 // Set angle based on the "open" side
                 if (!north) newAngle = ForgeDirection.NORTH;
                 else if (!east) newAngle = ForgeDirection.EAST;
                 else if (!south) newAngle = ForgeDirection.SOUTH;
                 else if (!west) newAngle = ForgeDirection.WEST;
                 break;
             case 4:
                 newState = 5; // "four"
                 break;
         }

         // Set the new state and angle on the TileEntity
         table.setConnectionState(newState);
         table.setAngle(newAngle);
	 }

		@Override
	    public TextureState addAdditionTextureStateInformation(BiblioTileEntity tile, TextureState state)
	    {
			ItemStack cloth = tile.getStackInSlot(1);
			ItemStack carpet = tile.getStackInSlot(2);
			if (cloth != null)
			{
				state.setColorOne(EnumColor.getColorFromCarpetOrWool(cloth));
			}
			if (carpet != null)
			{
				state.setColorTwo(EnumColor.getColorFromCarpetOrWool(carpet));
			}
			return state;
	    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemTable(0, -0.5D, 0,180.0D, item);
                return;
            case EQUIPPED_FIRST_PERSON, EQUIPPED:
                renderItemTable(-0.5D,0D,0.5D, 90.0D, item);
                return;
            default:
                renderItemTable(0,-0.5D,0, 0, item);
        }
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, Tessellator tes) {
        tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1, 1, 1);
        tes.addTranslation(x + 0.5F, y, z + 0.5F);
        TileEntityTable tile = (TileEntityTable) world.getTileEntity(x, y, z);
        if (tile == null) return false;

        // 1. Get the base texture string
        String baseTextureName = tile.getCustomTextureString();

        // 2. Create the TextureState
        TextureState state = new TextureState(baseTextureName);

        // 3. Populate it with Color data from the TileEntity
        //    (This calls the helper method you provided)
        state = this.addAdditionTextureStateInformation(tile, state);
        ObjContext ctx = new ObjContext(world, x, y, z, tile.getAngle(), tile.getVertPosition(), tile.getShiftPosition());
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);

        renderTable(obj, world.getBlockMetadata(x,y,z),state,tile);

        return true;
    }


    public void renderItemTable(double x, double y, double z, double rotate, ItemStack item) {
        final Tessellator tes = Tessellator.instance;
        RenderHelper.disableStandardItemLighting();
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(x, y, z);
        ObjContext ctx = new ObjContext(null, x, y, z);
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);
        String customTextureName = "none";
        if (item.getTagCompound() != null) customTextureName = item.getTagCompound().getString("renderTexture");
        TextureState state = new TextureState(customTextureName);
        obj.start();
        renderTable(obj , item.getItemDamage(),state,null);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }

    public void renderTable(ObjBuilder obj , int meta, TextureState state,TileEntityTable table) {
        String[] none = {"none_top", "none_leg"};
        String none_cloth = "none_cloth";
        String[] one = {"one_top", "one_leg"};
        String one_cloth = "one_cloth";
        String straight = "straight_top";
        String straight_cloth = "straight_cloth";
        String[] curve = {"curve_top", "curve_leg"};
        String curve_cloth = "curve_cloth";
        String three = "three_top";
        String three_cloth = "three_cloth";
        String four = "four_top";
        String four_cloth = "four_cloth";

        IIcon woodIcon = this.getIcon(1,meta);
        IIcon woolIcon = getCustomTexture(state.getColorOne().getWoolTextureString());
        String customTextureName = state.getTextureString();

        if (customTextureName != null && !customTextureName.equals("none")) {
            woodIcon = this.getCustomTexture(customTextureName);
        }
        obj.setModel(EnumObjModels.TABLE);

        int connectionState = 0; // Default to "none" for item
        boolean hasCloth = false;

        if (table != null) {
            connectionState = table.getConnectionState();
            hasCloth = table.isClothSlotFull();
        }

        // --- 4. Render the correct model based on state ---
        switch (connectionState) {
            case 1: // "one"
                if (hasCloth) obj.renderPart(one_cloth, woolIcon);
                obj.setLockTopUV(true).renderPart(one, woodIcon);
                break;
            case 2: // "straight"
                if (hasCloth) obj.renderPart(straight_cloth, woolIcon);
                obj.setLockTopUV(true).renderPart(straight, woodIcon);
                break;
            case 3: // "curve"
                if (hasCloth) obj.renderPart(curve_cloth, woolIcon);
                obj.setLockTopUV(true).renderPart(curve, woodIcon);
                break;
            case 4: // "three"
                if (hasCloth) obj.renderPart(three_cloth, woolIcon);
                obj.setLockTopUV(true).renderPart(three, woodIcon);
                break;
            case 5: // "four"
                if (hasCloth) obj.renderPart(four_cloth, woolIcon);
                obj.setLockTopUV(true).renderPart(four, woodIcon);
                break;
            case 0: // "none" (default)
            default:
                if (hasCloth) obj.renderPart(none_cloth, woolIcon);
                obj.setLockTopUV(true).renderPart(none, woodIcon);
                break;
        }

    }

}
