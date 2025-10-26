package jds.bibliocraft.blocks;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.containers.ContainerWeaponRack;
import jds.bibliocraft.rendering.isbrh.obj.EnumObjModels;
import jds.bibliocraft.rendering.isbrh.obj.ObjBuilder;
import jds.bibliocraft.rendering.isbrh.obj.ObjContext;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityToolRack;
import jds.bibliocraft.utils.BiblioWoodRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;


public class BlockToolRack extends BiblioWoodBlock
{
	public static final String name = "ToolRack";
	public static final BlockToolRack instance = new BlockToolRack();

    public BlockToolRack()
	{
		super(name, true);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
            ForgeDirection sides = ForgeDirection.getOrientation(side);
			BiblioTileEntity tile = (BiblioTileEntity)world.getTileEntity(x, y, z);
			if (isFrontOfBlock(sides, tile.getAngle()) && !player.isSneaking())
			{
				 int slot = getSlotNumberFromClickon2x2block(tile.getAngle(), hitX, hitY, hitZ);
				 boolean testValue = false; // turns true if I add or remove something from the shelf
				 if (slot >= 0)
				 {
					 if (player.getHeldItem() != null && ContainerWeaponRack.isItemTool(player.getHeldItem().getItem(), player.getHeldItem()))
					 {
						 testValue = tile.addStackToInventoryFromWorld(player.getHeldItem(), slot, player);
					 }

					 if (!testValue)
					 {
						 testValue = tile.removeStackFromInventoryFromWorld(slot, player, this);
					 }
				 }
			}
			else
			{
				player.openGui(BiblioCraft.instance, 4, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityToolRack();
	}


	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{


	}

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case INVENTORY:
                renderItemToolRack(0, -0.5D, -0.25D,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON:
                renderItemToolRack(-0.25D, 0.25D, 0.25D, 45.0D, item.getItemDamage());
                return;
            case EQUIPPED:
                renderItemToolRack(-0.5D, 0, 0.25D, 90.0D, item.getItemDamage());
                return;
            default:
                renderItemToolRack(0, -0.5D, -0.25D, 0, item.getItemDamage());
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

        renderToolRack(obj, world.getBlockMetadata(x, y, z));

//        tes.addTranslation(-x - .5F, -y - .5F, -z - .5F);
        return true;
    }


    public void renderItemToolRack(double x, double y, double z, double rotate, int meta) {
        final Tessellator tes = Tessellator.instance;
        RenderHelper.disableStandardItemLighting();
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(x, y, z);
        ObjContext ctx = new ObjContext(null, x, y, z);
        ObjBuilder obj = new ObjBuilder(tes).setContext(ctx);
        obj.start();
        renderToolRack(obj , meta);
        obj.end();
        RenderHelper.enableStandardItemLighting();
    }

    public void renderToolRack(ObjBuilder obj , int meta) {
        String[] Wood = {"bottom","top","left","right","center"};
        String[] nub = {"nub0","nub1","nub2","nub3","nub4","nub5","nub6","nub7"};

        IIcon woodIcon = BiblioWoodRegistry.getIcon(meta);
        IIcon metalIcon = Blocks.iron_block.getIcon(0,0);
        obj.setModel(EnumObjModels.TOOL_RACK);
        obj.renderPart(Wood, woodIcon);
        obj.renderPart(nub, metalIcon);
    }
}
