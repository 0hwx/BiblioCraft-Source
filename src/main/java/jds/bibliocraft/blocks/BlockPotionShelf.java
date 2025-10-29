package jds.bibliocraft.blocks;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.Config;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityPotionShelf;
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
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class BlockPotionShelf  extends BiblioWoodBlock
{
	public static final String name = "PotionShelf";
	public static final BlockPotionShelf instance = new BlockPotionShelf();

	public BlockPotionShelf()
	{
		super(name, true);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			ItemStack playerhand = player.getHeldItem();
			BiblioTileEntity tile = (BiblioTileEntity)world.getTileEntity(x, y, z);
			if (tile != null)
			{
				int potionSlot = getPotionShelfSlot(tile.getAngle(), ForgeDirection.getOrientation(side), hitX, hitY, hitZ);

				if (potionSlot >= 0 && player.isSneaking() && tile.getStackInSlot(potionSlot) != null)
				{
					if (tile.removeStackFromInventoryFromWorld(potionSlot, player, this))
					return true;
				}

				if (potionSlot >= 0 && playerhand != null && Config.testPotionValidity(playerhand.getUnlocalizedName(), playerhand.getDisplayName(), playerhand.getItem()))
				{
					// add item
					if (tile.addStackToInventoryFromWorld(playerhand, potionSlot, player))
					return true;
				}
			}
			player.openGui(BiblioCraft.instance, 2, world, x, y, z);
		}
		return true;
	}

	public static int getPotionShelfSlot(ForgeDirection angle, ForgeDirection hitSide, float hitX, float hitY, float hitZ)
	{
		int output = -1;
		if (isFrontOfBlock(hitSide, angle))
		{
			output = isWhatShelf(hitY)+isWhatPot(hitSide, hitX, hitZ);
		}
		else if (isBackOfBlock(hitSide, angle))
		{
			ForgeDirection invertedSide = hitSide;
			switch (hitSide)
			{
				case SOUTH: { invertedSide = ForgeDirection.NORTH;  break; }
				case WEST: { invertedSide = ForgeDirection.EAST;  break; }
				case NORTH: { invertedSide = ForgeDirection.SOUTH;  break; }
				case EAST: { invertedSide = ForgeDirection.WEST;  break; }
				default: break;
			}
			output = isWhatShelf(hitY) + isWhatPot(invertedSide, hitX, hitZ);
		}
		return output;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPotionShelf();
	}


	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{

	}


	private static int isWhatShelf(float hitY)
	{
		int yt = (int) (hitY * 3);
		switch (yt)
		{
		case 0: return 8;
		case 1: return 4;
		case 2: return 0;
		default: return 0;
		}


	}

	private static int isWhatPot(ForgeDirection angle, float hitX, float hitZ)
	{
		int xt = (int) (hitX * 4);
		int zt = (int)( hitZ * 4);
		switch (angle)
		{
			case WEST:
			{
				return zt;
			}
			case NORTH:
			{
				switch (xt)
				{
				case 0: return 3;
				case 1: return 2;
				case 2: return 1;
				case 3: return 0;
				default: break;
				}
			}
			case EAST:
			{
				switch (zt)
				{
				case 0: return 3;
				case 1: return 2;
				case 2: return 1;
				case 3: return 0;
				default: break;
				}
			}
			case SOUTH:
			{
				return xt;
			}
			default:break;
		}
		return 0;
	}

    @Override
    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift)
    {
        switch (biblioTile.getAngle())
        {
            case SOUTH: { this.setBlockBounds(0.75F-shift, 0.0F, 0.0F, 1.0F-shift, 1.0F, 1.0F); break; }
            case WEST:  { this.setBlockBounds(0.0F, 0.0F, 0.75F-shift, 1.0F, 1.0F, 1.0F-shift); break; }
            case EAST:  { this.setBlockBounds(0.0F, 0.0F, 0.0F+shift, 1.0F, 1.0F, 0.25F+shift); break; }
            case NORTH:
            default:    { this.setBlockBounds(0.0F+shift, 0.0F, 0.0F, 0.25F+shift, 1.0F, 1.0F); break; }
        }
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemPotionShelf(0, -0.5D, -0.25D,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON:
                renderItemPotionShelf(-0.25D, 0.25D, 0.25D, 45.0D, item.getItemDamage());
                return;
            case EQUIPPED:
                renderItemPotionShelf(-0.5D, 0, 0.25D, 90.0D, item.getItemDamage());
                return;
            default:
                renderItemPotionShelf(0, -0.5D, -0.25D,0, item.getItemDamage());
        }
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, Tessellator tes) {
        tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1, 1, 1);
        tes.addTranslation(x + 0.5F, y, z + 0.5F);
        TileEntityPotionShelf tile = (TileEntityPotionShelf)world.getTileEntity(x, y, z);
        ObjContext ctx = new ObjContext(world, x, y, z, tile.getAngle(), tile.getVertPosition(), tile.getShiftPosition());
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);

        renderPotionShelf(obj, tile.getExtendedMeta());

        return true;
    }


    public void renderItemPotionShelf(double x, double y, double z, double rotate, int meta) {
        final Tessellator tes = Tessellator.instance;
        RenderHelper.disableStandardItemLighting();
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(x, y, z);
        ObjContext ctx = new ObjContext(null, x, y, z);
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);
        obj.start();
        renderPotionShelf(obj , meta);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }

    public void renderPotionShelf(ObjBuilder obj , int meta) {
        IIcon woodIcon = BiblioWoodRegistry.getIcon(meta);
        String[] shelf = {"left","right","bottom","middle","top",};
        obj.setModel(EnumObjModels.POTION_SHELF);
        obj.renderPart(shelf, woodIcon);
    }
}
