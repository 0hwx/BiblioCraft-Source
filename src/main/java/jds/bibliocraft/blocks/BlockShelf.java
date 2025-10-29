package jds.bibliocraft.blocks;


import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityShelf;
import jds.bibliocraft.utils.BiblioWoodRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;


public class BlockShelf extends BiblioWoodBlock
{
	public static final String name = "Shelf";
	public static final BlockShelf instance = new BlockShelf();

	public BlockShelf()
	{
		super(name, true);
	}


	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			ItemStack playerhand = player.getHeldItem();
			BiblioTileEntity biblioTile = (BiblioTileEntity)world.getTileEntity(x, y, z);
			if (biblioTile instanceof TileEntityShelf)
			{
				TileEntityShelf tile = (TileEntityShelf)biblioTile;

				 if (player.isSneaking())
				 {
					 player.openGui(BiblioCraft.instance, 3, world, x, y, z);
					 return true;
				 }

				 int slot = getSlotNumberFromClickon2x2block(tile.getAngle(), hitX, hitY, hitZ);
				 boolean testValue = false; // turns true if I add or remove something from the shelf
				 if (slot >= 0 && !player.isSneaking())
				 {
					 if (playerhand != null)
					 {
						 testValue = tile.addStackToInventoryFromWorld(playerhand, slot, player);
					 }

					 if (!testValue)
					 {
						 testValue = tile.removeStackFromInventoryFromWorld(slot, player, this);
					 }
				 }

				 if (!testValue)
				 {
					 player.openGui(BiblioCraft.instance, 3, world, x, y, z);
				 }
				 return true;

			}
		}
		return true;
	}


	@Override
    public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
    {

    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityShelf();
	}


	private boolean checkIfIsBackOfBlock(int angle, int face)
	{
		boolean angle1 = angle == 0 && face == 5;
		boolean angle2 = angle == 3 && face == 2;
		boolean angle3 = angle == 2 && face == 4;
		boolean angle4 = angle == 1 && face == 3;
		if (angle1 || angle2 || angle3 || angle4)
		{
			return true;
		}
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityShelf)
		{
			TileEntityShelf shelf = (TileEntityShelf)tile;
		    Block testBlock = world.getBlock(x, y + 1, z);
		    boolean isBlock;
		    if (shelf != null)
		    {
		        if (!world.isAirBlock(x, y + 1, z))
		        {
		        	isBlock = true;
		        }
		        else
		        {
		        	isBlock = false;
		        }
		    	shelf.setTop(isBlock);
		    }
		}
	}
    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemShelf(0, -0.5D, -0.25D,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON:
                renderItemShelf(-0.25D, 0.25D, 0.25D, 45.0D, item.getItemDamage());
                return;
            case EQUIPPED:
                renderItemShelf(-0.5D, 0, 0.25D, 90.0D, item.getItemDamage());
                return;
            default:
                renderItemShelf(0, -0.5D, -0.25D,0, item.getItemDamage());
        }
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block,Tessellator tes) {
        tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1, 1, 1);
        tes.addTranslation(x + 0.5F, y, z + 0.5F);
        TileEntityShelf tile = (TileEntityShelf)world.getTileEntity(x, y, z);
        ObjContext ctx = new ObjContext(world, x, y, z, tile.getAngle(), tile.getVertPosition(), tile.getShiftPosition());
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);

        renderShelf(obj, tile.getExtendedMeta(), tile.getTop());

//        tes.addTranslation(-x - .5F, -y - .5F, -z - .5F);
        return true;
    }


    public void renderItemShelf(double x, double y, double z, double rotate, int meta) {
        final Tessellator tes = Tessellator.instance;
        RenderHelper.disableStandardItemLighting();
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(x, y, z);
        ObjContext ctx = new ObjContext(null, x, y, z);
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);
        obj.start();
        renderShelf(obj , meta,true);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }

    public void renderShelf(ObjBuilder obj , int meta,boolean top) {
        IIcon woodIcon = BiblioWoodRegistry.getIcon(meta);
        obj.setModel(EnumObjModels.SHELF);

        if (top){
            obj.renderPart("shelftop", woodIcon);
        }
        obj.renderPart("shelf", woodIcon);
    }
}
