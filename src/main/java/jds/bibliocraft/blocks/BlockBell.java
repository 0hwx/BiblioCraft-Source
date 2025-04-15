package jds.bibliocraft.blocks;

import cpw.mods.fml.common.network.NetworkRegistry;
import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.network.BiblioNetworking;
import jds.bibliocraft.network.packet.client.BiblioSoundPlayer;
import jds.bibliocraft.tileentities.TileEntityBell;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;


public class BlockBell extends BiblioSimpleBlock
{
	public static final BlockBell instance = new BlockBell();
	public static final String name = "Bell";
	public static final float range = 32.0F;

	public BlockBell()
	{
		super(Material.iron, soundTypeMetal, name);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return this.getBlockBounds(0.4F, 0.0F, 0.4F, 0.6F, 0.2F, 0.6F);
	}

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			//world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), CommonProxy.SOUND_DING, SoundCategory.BLOCKS, 1.0F, 1.0F);
			NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, range);
			BiblioNetworking.INSTANCE.sendToAllAround(new BiblioSoundPlayer(CommonProxy.SOUND_BELL_DING_TEXT, x, y, z, 1.0F, 1.0F), target);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityBell();
	}

	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
}
