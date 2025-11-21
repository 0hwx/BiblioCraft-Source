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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockTable extends BiblioWoodBlock {
    public static final String name = "Table";
    public static final BlockTable instance = new BlockTable();

    public BlockTable() {
        super(name, false);
    }


    @Override
    public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            ItemStack playerhand = player.getHeldItem();
            TileEntityTable tabletile = (TileEntityTable) world.getTileEntity(x, y, z);
            ForgeDirection sides = ForgeDirection.getOrientation(side);
            if (sides == ForgeDirection.UP) {
                if (player.isSneaking()) {
                    player.openGui(BiblioCraft.instance, 9, world, x, y, z);
                    return true;
                } else {
                    if (tabletile != null) {
                        if (playerhand != null) {
                            if (playerhand.getItem() == Item.getItemFromBlock(Blocks.carpet)) {
                                int additem = tabletile.setTableCloth(playerhand);
                                if (additem != -1) {
                                    if (additem == 0) {
                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                    } else {
                                        playerhand.stackSize = (additem);
                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, playerhand);
                                    }
                                }
                                return true;
                            }

                            Item drilltest = playerhand.getItem();
                            if (drilltest != null) {
                                if (drilltest instanceof ItemDrill) {
                                    dropStackInSlot(world, x, y, z, 1, Vec3.createVectorHelper(x, y + 1, z));
                                    tabletile.setTableCloth(null);
                                    return false;
                                }
                            }

                        }
                        if (playerhand != null && !tabletile.isSlotFull()) {
                            //place the item in the inventory
                            tabletile.addStackToInventoryFromWorld(playerhand, 0, player);
                            return true;
                        }
                        if (tabletile.isSlotFull()) {
                            // drop the item
                            dropStackInSlot(world, x, y, z, 0, Vec3.createVectorHelper(x, y + 1, z));
                            tabletile.setTableSlot(null);
                            return true;
                        }
                    }
                }
                return true;
            } else {
                //not top of table
                if (playerhand != null) {
                    if (playerhand.getItem() == Item.getItemFromBlock(Blocks.carpet)) {
                        int additem = tabletile.setCarpet(playerhand);
                        if (additem == -1) {

                        } else {
                            if (additem == 0) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            } else {
                                playerhand.stackSize = (additem);
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, playerhand);
                            }
                        }
                        return true;
                    }
                }

            }
            if (playerhand != null && tabletile != null) {
                String itemname = playerhand.toString().toLowerCase();
                if (itemname.contains("measure") || itemname.contains("wrench") || itemname.contains("screwdriver") || itemname.contains("crowbar") || itemname.contains("bibliodrill") || itemname.contains("handdrill")) {
                    if (sides == ForgeDirection.NORTH || sides == ForgeDirection.SOUTH) {
                        int xangle = tabletile.getSlotX();
                        switch (xangle) {
                            case 0: {
                                tabletile.setSlotX(1);
                                break;
                            }
                            case 1: {
                                tabletile.setSlotX(2);
                                break;
                            }
                            case 2: {
                                tabletile.setSlotX(3);
                                break;
                            }
                            default: {
                                tabletile.setSlotX(0);
                                break;
                            }
                        }
                    }
                    if (sides == ForgeDirection.EAST || sides == ForgeDirection.WEST) {
                        int yangle = tabletile.getSlotY();
                        switch (yangle) {
                            case 0: {
                                tabletile.setSlotY(1);
                                break;
                            }
                            case 1: {
                                tabletile.setSlotY(2);
                                break;
                            }
                            case 2: {
                                tabletile.setSlotY(3);
                                break;
                            }
                            default: {
                                tabletile.setSlotY(0);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTable();
    }


    @Override
    public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player) {
        biblioTile.setAngle(ForgeDirection.SOUTH);
//        if (biblioTile instanceof TileEntityTable) {
//            checkNeighborTables(biblioTile.getWorldObj(), biblioTile.xCoord, biblioTile.yCoord, biblioTile.zCoord, (TileEntityTable) biblioTile);
//        }
    }


    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        if (side == ForgeDirection.UP) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift)
    {

        if (biblioTile != null && biblioTile instanceof TileEntityTable tile)
        {

            IBlockAccess world = tile.getWorldObj();
            int x = tile.xCoord; int y = tile.yCoord; int z = tile.zCoord;
            boolean north = isNeighborTable(world, x, y, z, ForgeDirection.NORTH);
            boolean east = isNeighborTable(world, x, y, z, ForgeDirection.EAST);
            boolean south = isNeighborTable(world, x, y, z, ForgeDirection.SOUTH);
            boolean west = isNeighborTable(world, x, y, z, ForgeDirection.WEST);

            boolean hasLegNW = !(north || west);
            boolean hasLegNE = !(north || east);
            boolean hasLegSE = !(south || east);
            boolean hasLegSW = !(south || west);
            boolean hasMonoLeg = !north && !east && !south && !west;

            if (!hasLegNW && !hasLegNE && !hasLegSE && !hasLegSW && !hasMonoLeg)
            {
                this.setBlockBounds(0.00F, 0.88F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

            }
        }

    }

//    @Override
//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
//        TileEntity tilee = world.getTileEntity(x, y, z);
//        if (tilee instanceof TileEntityTable) {
//            TileEntityTable tile = (TileEntityTable) tilee;
//            // If it's a 'four' connection (state 5), it has no legs
//            if (tile.getConnectionState() == 5) {
//                // Collision box is just the top
//                return this.getBlockBounds(0.00F, 0.88F, 0.0F, 1.0F, 1.0F, 1.0F);
//            }
//        }
//        // All other states have legs, so return full block bounds
//        return this.getBlockBounds(0.00F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//    }

//    @Override
//    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
//        TileEntity tile = world.getTileEntity(x, y, z);
//        if (tile != null && tile instanceof TileEntityTable) {
//            TileEntityTable tabletile = (TileEntityTable) world.getTileEntity(x, y, z);
//            checkNeighborTables(tile.getWorldObj(), x, y, z, tabletile);
//            //world.markBlockForUpdate(pos);
//            tabletile.getWorldObj().markBlockForUpdate(x, y, z);
//        }
//    }


    @Override
    public TextureState addAdditionTextureStateInformation(BiblioTileEntity tile, TextureState state) {
        ItemStack cloth = tile.getStackInSlot(1);
        ItemStack carpet = tile.getStackInSlot(2);
        if (cloth != null) {
            state.setColorOne(EnumColor.getColorFromCarpetOrWool(cloth));
        }
        if (carpet != null) {
            state.setColorTwo(EnumColor.getColorFromCarpetOrWool(carpet));
        }
        return state;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemTable(0, -0.5D, 0, 180.0D, item);
                return;
            case EQUIPPED_FIRST_PERSON, EQUIPPED:
                renderItemTable(-0.5D, 0D, 0.5D, 90.0D, item);
                return;
            default:
                renderItemTable(0, -0.5D, 0, 0, item);
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

        renderTable(obj, world.getBlockMetadata(x, y, z), state, tile);

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
        renderTable(obj, item.getItemDamage(), state, null);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }



    public void renderTable(ObjBuilder obj, int meta, TextureState state, TileEntityTable tile) {
        // 1. Setup Textures
        IIcon woodIcon = this.getIcon(1, meta);
        IIcon woolIcon = this.getCustomTexture(state.getColorOne().getWoolTextureString());
        IIcon carpetIcon = this.getCustomTexture(state.getColorTwo().getWoolTextureString());
        String customTextureName = state.getTextureString();

        if (customTextureName != null && !customTextureName.equals("none")) {
            woodIcon = this.getCustomTexture(customTextureName);
        }

        obj.setModel(EnumObjModels.TABLE);

        List<String> woodParts = new ArrayList<>();
        List<String> clothParts = new ArrayList<>();

        // 2. Determine State
        boolean isItem = (tile == null);
        boolean hasCloth = false;
        boolean hasCarpet = false;
        // 3. Determine Connections
        boolean north = false, east = false, south = false, west = false;
        int connectionCount = 0;

        if (!isItem) {
            hasCloth = tile.isClothSlotFull();
            hasCarpet = tile.isCarpetFull();
            IBlockAccess world = tile.getWorldObj();
            int x = tile.xCoord; int y = tile.yCoord; int z = tile.zCoord;
            north = isNeighborTable(world, x, y, z, ForgeDirection.NORTH);
            east = isNeighborTable(world, x, y, z, ForgeDirection.EAST);
            south = isNeighborTable(world, x, y, z, ForgeDirection.SOUTH);
            west = isNeighborTable(world, x, y, z, ForgeDirection.WEST);

            if(north) connectionCount++;
            if(east) connectionCount++;
            if(south) connectionCount++;
            if(west) connectionCount++;
        }

        // The Center is Always Rendered
        woodParts.add("top_center");
        woodParts.add("top_north");
        woodParts.add("top_east");
        woodParts.add("top_south");
        woodParts.add("top_west");

        // If cloth is present, the "Plus Shape" (Center + N/E/S/W extensions) is always rendered.
        // The corners of the cloth are the only dynamic parts.
        if (hasCloth) {
            clothParts.add("cloth_center");
            clothParts.add("cloth_north");
            clothParts.add("cloth_east");
            clothParts.add("cloth_south");
            clothParts.add("cloth_west");
        }
        // Carpet is simple: if present, render the carpet group.
        if (hasCarpet) obj.renderPart("carpet", carpetIcon);

        // Zero Connection / Item Case (Mono Table)
        // If the table has no neighbors, it uses a unique "Center Post" leg style.
        if (connectionCount == 0) {
            // Render standard corners
            woodParts.add("corner_nw");
            woodParts.add("corner_ne");
            woodParts.add("corner_se");
            woodParts.add("corner_sw");

            // Render single center leg
            woodParts.add("leg_none");

            if (hasCloth) {
                // Render side drapes for the "Plus" shape.
                // Note: We DO NOT render corner cloth parts here, maintaining the Plus shape.
                clothParts.add("cloth_side_north");
                clothParts.add("cloth_side_east");
                clothParts.add("cloth_side_south");
                clothParts.add("cloth_side_west");
            }
        }
        // Connected Case (Multiblock Table)
        // If the table connects to neighbors, it switches to using 4 Corner Legs.
        else {
            // 1. Handle Sides
            // render the cloth extensions (connected).
            handleSide(clothParts, north, "north", hasCloth, west, east, "west", "east");
            handleSide(clothParts, east, "east", hasCloth, north, south, "north", "south");
            handleSide(clothParts, south, "south", hasCloth, east, west, "east", "west");
            handleSide(clothParts, west, "west", hasCloth, south, north, "south", "north");

            // 2. Handle Corners & Legs
            // Decides whether a corner is "Connected" (no leg, maybe cloth) or "Normal" (has leg, wood top).
            handleCorner(woodParts, clothParts, north, west, "nw", "northwest", hasCloth);
            handleCorner(woodParts, clothParts, north, east, "ne", "northeast", hasCloth);
            handleCorner(woodParts, clothParts, south, east, "se", "southeast", hasCloth);
            handleCorner(woodParts, clothParts, south, west, "sw", "southwest", hasCloth);
        }

        // --- Final Render Calls ---
        if (!clothParts.isEmpty()) obj.renderPart(clothParts, woolIcon);
        if (!woodParts.isEmpty()) obj.renderPart(woodParts, woodIcon);
    }

    /**
     * Logic for rendering a specific SIDE (North, East, South, West).
     *
     * @param cloth          List of cloth parts to add to.
     * @param connected      Is this side connected to a neighbor?
     * @param side           The suffix for the side (e.g., "north").
     * @param hasCloth       Does the table have cloth?
     * @param leftConnected  Is the side to the left connected? (Used for extension drapes).
     * @param rightConnected Is the side to the right connected? (Used for extension drapes).
     * @param leftName       Name of the left side (e.g., "west").
     * @param rightName      Name of the right side (e.g., "east").
     */
    private static void handleSide(List<String> cloth,
                                   boolean connected, String side, boolean hasCloth,
                                   boolean leftConnected, boolean rightConnected,
                                   String leftName, String rightName) {
        if (!connected) {
            // Render the standard cloth drape ("cloth_side_north").
            if (hasCloth) cloth.add("cloth_side_" + side);
        } else {
            // Case: Connected Side
            // Wood top is skipped (covered by extension).
            // Check if we need drapes on the sides of the extension.
            // Example: If North is connected, but West is open, we need a drape on the West face of the North extension.
            if (hasCloth) {
                if (!leftConnected) cloth.add("cloth_side_" + leftName + side);
                if (!rightConnected) cloth.add("cloth_side_" + rightName + side);
            }
        }
    }

    /**
     * Logic for rendering a specific CORNER (NW, NE, SE, SW).
     *
     * @param wood           List of wood parts.
     * @param cloth          List of cloth parts.
     * @param side1Connected Is the first adjacent side connected?
     * @param side2Connected Is the second adjacent side connected?
     * @param shortName      Short suffix for wood parts (e.g., "nw").
     * @param longName       Long suffix for cloth parts (e.g., "northwest").
     * @param hasCloth       Does the table have cloth?
     */
    private static void handleCorner(List<String> wood, List<String> cloth,
                                     boolean side1Connected, boolean side2Connected,
                                     String shortName, String longName, boolean hasCloth) {
        // Logic:
        // 1. If EITHER adjacent side is connected, the corner becomes part of a continuous surface.
        //    We use "connected_nw" (which usually has no leg).
        // 2. If BOTH sides are unconnected, it's an exposed corner.
        //    We use "corner_nw" (wood top) and "leg_nw" (the leg).
        if (side1Connected || side2Connected) {
            // Connected Corner
            wood.add("connected_" + shortName);

            // Cloth logic: If connected, we fill the corner gap with cloth.
            // If unconnected, we leave it empty to maintain the "Plus" shape.
            if (hasCloth) {
                cloth.add("cloth_" + longName);
            }
        } else {
            // Normal/Exposed Corner
            wood.add("corner_" + shortName);
            wood.add("leg_" + shortName);

            // Note: No cloth is added here.
            // Exposed corners on a connected table remain wood-topped to look like the "Plus" shape is sitting on it.
        }
    }

    private boolean isNeighborTable(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
        Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
        return block instanceof BlockTable;
    }
}

