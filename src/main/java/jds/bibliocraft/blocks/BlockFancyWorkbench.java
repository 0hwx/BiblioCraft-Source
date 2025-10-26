package jds.bibliocraft.blocks;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.items.ItemRecipeBook;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityFancyWorkbench;
import jds.bibliocraft.tileentities.TileEntityShelf;
import jds.bibliocraft.utils.BiblioWoodRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;


public class BlockFancyWorkbench extends BiblioWoodBlock
{
	public static final String name = "FancyWorkbench";
	public static final BlockFancyWorkbench instance = new BlockFancyWorkbench();

	public BlockFancyWorkbench()
	{
		super(name, false);
	}


	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		// TODO this crashes when the menu is opened for some reason
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!world.isRemote && tile != null && tile instanceof TileEntityFancyWorkbench)
		{
			TileEntityFancyWorkbench bench = (TileEntityFancyWorkbench)tile;
			ItemStack playerhand = player.getHeldItem();
			if (isBackOfBlock(bench.getAngle(), ForgeDirection.getOrientation(side)) && hitY > 0.22F && hitY < 0.74F)
			{
				int booknum = isWhatBook(bench.getAngle(), hitX, hitZ) + 1;
				//System.out.println(booknum);					ItemStack playerhand = player.getHeldItem(EnumHand.MAIN_HAND);
				if (playerhand != null)
				{
					if (playerhand.getItem() instanceof ItemBook || playerhand.getItem() instanceof ItemRecipeBook && booknum != -1)
					{
						if (bench.addStackToInventoryFromWorld(playerhand, booknum, player))
						{
							return true;
						}
					}
				}
				else if (player.isSneaking())
				{
					if (bench.getStackInSlot(booknum) != null && booknum != -1)
					{
						bench.removeStackFromInventoryFromWorld(booknum, player, this);
					}
					return true;
				}
			}
			if (!bench.isTooManyPlayers())
			{
				player.openGui(BiblioCraft.instance, 0, world, x, y, z);
			}
		}
		return true;
	}

	public int isWhatBook(ForgeDirection angle, float hitX, float hitZ)
	{
		int xt = (int) (hitX * 8);
		int zt = (int)( hitZ * 8);
		switch (angle)
		{
			case SOUTH:
			{
				return zt;
			}
			case WEST:
			{
				switch (xt)
				{
				case 0: return 7;
				case 1: return 6;
				case 2: return 5;
				case 3: return 4;
				case 4: return 3;
				case 5: return 2;
				case 6: return 1;
				case 7: return 0;
				default: break;
				}
			}
			case NORTH:
			{
				switch (zt)
				{
				case 0: return 7;
				case 1: return 6;
				case 2: return 5;
				case 3: return 4;
				case 4: return 3;
				case 5: return 2;
				case 6: return 1;
				case 7: return 0;
				default: break;
				}
			}
			case EAST:
			{
				return xt;
			}
			default: break;
		}
		return -1;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFancyWorkbench();
	}



	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{


	}

    private IIcon bookIcon;
    private IIcon benchsidesIcon;

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
       bookIcon = iconRegister.registerIcon("bibliocraft:bookcase_books");
       benchsidesIcon = iconRegister.registerIcon("bibliocraft:benchsides");
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemFancyWorkbench(0, -0.5D, 0,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON, EQUIPPED:
                renderItemFancyWorkbench(-0.5D,0D,0.5D, 90.0D, item.getItemDamage());
                return;
            default:
                renderItemFancyWorkbench(0,-0.5D,0, 0, item.getItemDamage());
        }
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block,Tessellator tes) {
        tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1, 1, 1);
        tes.addTranslation(x + 0.5F, y, z + 0.5F);
        TileEntityFancyWorkbench tile = (TileEntityFancyWorkbench) world.getTileEntity(x, y, z);
        ObjContext ctx = new ObjContext(world, x, y, z, tile.getAngle(), tile.getVertPosition(), tile.getShiftPosition());
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);

        renderFancyWorkbench(obj, world.getBlockMetadata(x, y, z), tile.getBookArray(), false);

        return true;
    }


    public void renderItemFancyWorkbench(double x, double y, double z, double rotate, int meta) {
        final Tessellator tes = Tessellator.instance;
        RenderHelper.disableStandardItemLighting();
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(x, y, z);
        ObjContext ctx = new ObjContext(null, x, y, z);
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);
        obj.start();
        renderFancyWorkbench(obj , meta,null,true);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }

    public void renderFancyWorkbench(ObjBuilder obj , int meta, int[] count, boolean renderAllBooks) {
        String[] book = {"book1", "book2", "book3", "book4", "book5", "book6", "book7","book8"};
        IIcon woodIcon = BiblioWoodRegistry.getIcon(meta);
        IIcon CraftingTopIcon = Blocks.crafting_table.getIcon(1,0);
        obj.setModel(EnumObjModels.FANCY_WORKBENCH);
        if (renderAllBooks) {
            obj.renderPart(book, bookIcon);
        } else if (count != null && count.length == 8) {
            for (int i = 0; i < 8; i++) {
                if (count[i] == 1) {
                    obj.renderPart(book[i], bookIcon);
                }
            }
        }

        obj.renderPart("top", CraftingTopIcon);
        obj.renderPart("bench", woodIcon);
        obj.renderPart("sides", benchsidesIcon);
    }

}
