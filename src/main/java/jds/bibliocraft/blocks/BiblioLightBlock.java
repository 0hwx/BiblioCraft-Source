package jds.bibliocraft.blocks;

import java.util.List;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.helpers.EnumColor;
import jds.bibliocraft.helpers.EnumMetalType;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.states.MetalTypeState;
import jds.bibliocraft.tileentities.BiblioLightTileEntity;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public abstract class BiblioLightBlock extends BiblioBlock
{
	public static String name = "bibliolight";
//	public static final PropertyEnum COLOR = PropertyEnum.create("color", EnumColor.class);

	public BiblioLightBlock(String name)
	{
		super(Material.iron, soundTypeMetal, BlockLoader.biblioLightsTab, name);
		setLightLevel(1.0f);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world,  int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new BiblioLightTileEntity();
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<net.minecraft.item.ItemStack> list)
	{
		for (int x = 0; x < 16; x++) /// 16 for 16 colors
		{
            list.add(new ItemStack(this, 1, x));
		}
	}

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int meta = stack.getItemDamage();
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{
	     int pitch = MathHelper.floor_double(player.rotationPitch * 3.0F / 180.0F + 0.5D) & 3;
	     ++pitch;
	     pitch %= 4;
	     if (pitch == 0)
	     {
	    	 biblioTile.setVertPosition(EnumVertPosition.CEILING);
	     }
	     else if (pitch == 1)
	     {
	    	 biblioTile.setVertPosition(EnumVertPosition.WALL);
	     }
	     else
	     {
	    	 biblioTile.setVertPosition(EnumVertPosition.FLOOR);
	     }
	     additionalLightPlacmentCommands(biblioTile);
	}

	public abstract void additionalLightPlacmentCommands(BiblioTileEntity biblioTile);

	@Override
	public ItemStack getPickBlockExtras(ItemStack stack, World world, int x, int y, int z)
	{
		return stack;
	}

}
