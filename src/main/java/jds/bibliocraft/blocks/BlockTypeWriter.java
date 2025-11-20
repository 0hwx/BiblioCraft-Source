package jds.bibliocraft.blocks;

import jds.bibliocraft.blocks.base.BiblioColorBlock;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityTypewriter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class BlockTypeWriter extends BiblioColorBlock
{
	public static final String name = "Typewriter";
	public static final BlockTypeWriter instance = new BlockTypeWriter();

	public BlockTypeWriter()
	{
		super(Material.iron, soundTypeMetal, name);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!world.isRemote && tile != null && tile instanceof TileEntityTypewriter)
		{
			TileEntityTypewriter typewriter = (TileEntityTypewriter)tile;
			ItemStack playerhand = player.getHeldItem();
			ForgeDirection typeAngle = typewriter.getAngle();
            ForgeDirection sides = ForgeDirection.getOrientation(side);
			if (player.isSneaking())
			{
				if (typewriter.getStackInSlot(0) != null)
				{
					typewriter.removeStackFromInventoryFromWorld(0, player, this);
				}
			}
			else
			{
				if (playerhand != null)
				{
					if (playerhand.getItem() == Items.paper)
					{
						int returnsize = typewriter.addPaper(playerhand);
						if (returnsize > 0)
						{
							playerhand.stackSize = (returnsize);
							player.inventory.setInventorySlotContents(player.inventory.currentItem, playerhand);
						}
						else if (returnsize == 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						}
					}
					return true;
				}

				if (isFrontFace(typeAngle, sides) && typewriter.getStackInSlot(1) == null)
				{
					if (typewriter.getHasEnoughPaper())
					{

						if (typewriter.getBookWriteCount() < 15)
						{
							typewriter.entityName = player.getCommandSenderName();
							typewriter.entityType = 0;
							typewriter.setBookWriteCount(typewriter.getBookWriteCount()+1, true);
						}
						else
						{
							if (typewriter.removePaperForBook())
							{
								typewriter.foundValidEntity = true;
								typewriter.entityName = player.getCommandSenderName();
								typewriter.entityType = 0;
								typewriter.writeCustomBook();
							}
						}
					}
					return true;
				}

				if (typewriter.getStackInSlot(1) != null)
				{
					typewriter.foundValidEntity = false;
					typewriter.entityName = "";
					typewriter.entityType = 0;
					typewriter.setBookWriteCount(0, true);
					typewriter.removeStackFromInventoryFromWorld(1, player, this);
				}
			}
		}
		return true;
	}

	public boolean isFrontFace(ForgeDirection typeAngle, ForgeDirection face)
	{
		switch (typeAngle)
		{
			case SOUTH:{if (face == ForgeDirection.WEST){return true;}break;}
			case WEST:{if (face == ForgeDirection.NORTH){return true;}break;}
			case NORTH:{if (face == ForgeDirection.EAST){return true;}break;}
			case EAST:{if (face == ForgeDirection.SOUTH){return true;}break;}
			default: break;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTypewriter();
	}

    @Override
    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift)
    {
        switch (biblioTile.getAngle())
        {
            case SOUTH: { this.setBlockBounds(0.5F - shift, 0, 0.25F, 1F -shift, 0.3F, 0.75F); break; }
            case WEST:  { this.setBlockBounds(0.25F, 0, 0.5F -shift, 0.75F, 0.3F, 1F -shift); break; }
            case NORTH: { this.setBlockBounds(0F +shift, 0.0F, 0.25F, 0.5F +shift, 0.3F, 0.75F); break; }
            case EAST:  { this.setBlockBounds(0.25F, 0.0F, 0F +shift, 0.75F, 0.3F, 0.5F +shift); break; }
            default:    { this.setBlockBounds(0F +shift, 0.0F, 0.25F, 0.5F +shift, 0.3F, 0.75F); break; } // Default case
        }
    }

    private IIcon[] baseIcon = new IIcon[16];
    private IIcon[] paperIcon = new IIcon[16];
    private IIcon paperBlankIcon;

    @Override
    public IIcon getIcon(int side, int meta) {
        IIcon icon = baseIcon[meta];
        return icon != null ? icon : super.getIcon(side, meta);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        for (int i = 0; i < 16; i++){
            baseIcon[i] = iconRegister.registerIcon("bibliocraft:typewriter/typewriter" + i);
            paperIcon[i] = iconRegister.registerIcon("bibliocraft:typewriter/typewriter_paper_" + i);
        }
        paperBlankIcon = iconRegister.registerIcon("bibliocraft:typewriter/typewriter_paper_blank");
    }


    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemTypeWriter(0, -0.5D, -0.5D,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON:
                renderItemTypeWriter(-0.25D, 0.5D, 0.25D, 45.0D, item.getItemDamage());
                return;
            case EQUIPPED:
                renderItemTypeWriter(-0.5D, 0, 0.25D, 90.0D, item.getItemDamage());
                return;
            default:
                renderItemTypeWriter(0, -0.5D, -0.25D, 0, item.getItemDamage());
        }
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, Tessellator tes) {
        tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tes.setColorOpaque_F(1, 1, 1);
        tes.addTranslation(x + 0.5F, y, z + 0.5F);
        TileEntityTypewriter tile = (TileEntityTypewriter)world.getTileEntity(x, y, z);
        ObjContext ctx = new ObjContext(world, x, y, z, tile.getAngle(), tile.getVertPosition(), tile.getShiftPosition());
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);

        renderTypeWriter(obj,tes, world.getBlockMetadata(x, y, z),false, tile);

        return true;
    }


    public void renderItemTypeWriter(double x, double y, double z, double rotate, int meta) {
        final Tessellator tes = Tessellator.instance;
        double scale = 2D;
        RenderHelper.disableStandardItemLighting();
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(x, y, z);
        GL11.glScaled(scale, scale, scale);
        ObjContext ctx = new ObjContext(null, x, y, z);
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);
        obj.start();
        renderTypeWriter(obj ,tes, meta,true, null);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }

    public void renderTypeWriter(ObjBuilder obj ,Tessellator tes, int meta , boolean isItem,TileEntityTypewriter tile) {
        String[] paper = {"paperLine1","paperLine2","paperLine3","paperLine4","paperLine5","paperLine6","paperLine7"};
        String base = "base";
        String slide = "slide";
        obj.setModel(EnumObjModels.TYPEWRITER);
        obj.renderPart(base, baseIcon[meta]);

        if (isItem) {
            obj.renderPart(paper[4], paperBlankIcon);
            obj.renderPart(slide, baseIcon[meta]);

        }

        if (tile != null) {
            int paperWriteCount = tile.getBookWriteCount();
            float slideOffset = paperWriteCount / 100F;

            if (paperWriteCount > 0) slideOffset -= 0.07F;

            switch (tile.getAngle()){
                case SOUTH:{ tes.addTranslation(0, 0, -slideOffset); break; }
                case WEST:{ tes.addTranslation(slideOffset, 0, 0); break; }
                case NORTH:{ tes.addTranslation(0, 0, slideOffset); break; }
                case EAST:{ tes.addTranslation(-slideOffset, 0, 0); break; }
                default: break;
            }


            if (tile.getHasPaper()) {
                int paperIndex = getPaperIndexFromCount(paperWriteCount);
                IIcon paperIconToUse = paperWriteCount > 0 ? paperIcon[paperWriteCount] : paperBlankIcon;
                obj.renderPart(paper[paperIndex], paperIconToUse);
            }
        }
        obj.renderPart(slide, baseIcon[meta]);
    }

    /**
     * Calculates the paperLine index (0-6) from the write count (0-15).
     * This is non-linear to handle 3-count groups:
     * - paperLine3 (index 2) handles counts 5, 6, 7
     */
    private int getPaperIndexFromCount(int paperWriteCount) {
        if (paperWriteCount >= 14) return 6; // paperLine7 (14, 15)
        if (paperWriteCount >= 12) return 5; // paperLine6 (12, 13)
        if (paperWriteCount >= 10) return 4; // paperLine5 (10, 11)
        if (paperWriteCount >= 8)  return 3; // paperLine4 (8, 9)
        if (paperWriteCount >= 5)  return 2; // paperLine3 (5, 6, 7)
        if (paperWriteCount >= 3)  return 1; // paperLine2 (3, 4)
        return 0; // paperLine1 (1, 2)
    }
}
