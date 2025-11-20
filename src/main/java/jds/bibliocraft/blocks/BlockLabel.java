package jds.bibliocraft.blocks;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.blocks.base.BiblioWoodBlock;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityLabel;

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


public class BlockLabel extends BiblioWoodBlock
{
	public static final String name = "Label";
	public static final BlockLabel instance = new BlockLabel();

	public BlockLabel()
	{
		super(name, false);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && player.isSneaking())
		{
			player.openGui(BiblioCraft.instance, 6, world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityLabel();
	}


	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{


	}


//    @Override
//	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) // todo fix the bounding box
//	{
//
//		TileEntity tile = world.getTileEntity(x, y, z);
//		if (tile != null && tile instanceof TileEntityLabel)
//		{
//			TileEntityLabel labelTile = (TileEntityLabel) tile;
//			switch (labelTile.getAngle())
//			{
//				case SOUTH:{this.setBlockBounds(0.94F, 0.125F, 0.22F, 1.0F, 0.436F, 0.78F);break;}
//				case WEST:{this.setBlockBounds(0.22F, 0.125F, 0.94F, 0.78F, 0.436F, 1.0F);break;}
//				case NORTH:{this.setBlockBounds(0.0F, 0.125F, 0.22F, 0.06F, 0.436F, 0.78F);break;}
//				case EAST:{this.setBlockBounds(0.22F, 0.125F, 0.0F, 0.78F, 0.436F, 0.06F);break;}
////				default: break;
//			}
//		}
//		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
//	}

    @Override
    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift)
    {
        switch (biblioTile.getAngle())
        {
            case SOUTH: { this.setBlockBounds(0.94F - shift, 0.065F, 0.19F, 1F -shift, 0.436F, 0.81F); break; }
            case WEST:  { this.setBlockBounds(0.19F, 0.065F, 0.94F -shift, 0.81F, 0.436F, 1F -shift); break; }
            case NORTH: { this.setBlockBounds(0F +shift, 0.065F, 0.19F, 0.06F +shift, 0.436F, 0.81F); break; }
            case EAST:  { this.setBlockBounds(0.19F, 0.065F, 0F +shift, 0.81F, 0.436F, 0.06F +shift); break; }
            default:    { this.setBlockBounds(0F +shift, 0.065F, 0.19F, 0.06F +shift, 0.436F, 0.81F); break; } // Default case
        }
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemLabel(0, -0.5D, -0.25D,180.0D, item);
                return;
            case EQUIPPED_FIRST_PERSON:
                renderItemLabel(-0.25D, 0.25D, 0.25D, 45.0D, item);
                return;
            case EQUIPPED:
                renderItemLabel(-0.5D, 0, 0.25D, 90.0D, item);
                return;
            default:
                renderItemLabel(0, -0.5D, -0.25D,0, item);
        }
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, Tessellator tes) {
        tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1, 1, 1);
        tes.addTranslation(x + 0.5F, y, z + 0.5F);
        BiblioTileEntity tile = (BiblioTileEntity)world.getTileEntity(x, y, z);
        ObjContext ctx = new ObjContext(world, x, y, z, tile.getAngle(), tile.getVertPosition(), tile.getShiftPosition());
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);

        renderLabel(obj, world.getBlockMetadata(x, y, z), tile.getCustomTextureString());

        return true;
    }


    public void renderItemLabel(double x, double y, double z, double rotate, ItemStack item) {
        final Tessellator tes = Tessellator.instance;
        RenderHelper.disableStandardItemLighting();
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(x, y, z);
        ObjContext ctx = new ObjContext(null, x, y, z);
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);
        String customTextureName = "none";
        if (item.getTagCompound() != null) customTextureName = item.getTagCompound().getString("renderTexture");
        obj.start();
        renderLabel(obj , item.getItemDamage(), customTextureName);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }

    public void renderLabel(ObjBuilder obj , int meta, String customTextureName) {
        IIcon woodIcon = this.getIcon(0,meta);

        if (customTextureName != null && !customTextureName.equals("none")) {
            woodIcon = this.getCustomTexture(customTextureName);
        }
        String[] shelf = {"left","right","bottom","back","top",};
        obj.setModel(EnumObjModels.LABEL);
        obj.renderPart(shelf, woodIcon);
    }

}
