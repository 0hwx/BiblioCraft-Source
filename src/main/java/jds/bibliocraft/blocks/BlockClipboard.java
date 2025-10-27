package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jds.bibliocraft.network.BiblioNetworking;
import jds.bibliocraft.network.packet.server.BiblioClipboard;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityClipboard;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockClipboard extends BiblioBlock
{
	public static final String name = "Clipboard";
	public static final BlockClipboard instance = new BlockClipboard();

	public BlockClipboard()
	{
		super(Material.wood, soundTypeWood, null, name);
		//setCreativeTab(CreativeTabs.)
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        return new ArrayList<>();
    }

	@Override
	public boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
        Vec3 pos = Vec3.createVectorHelper(x, y, z);
		if (player.isSneaking() && !world.isRemote)
		{
			dropStackInSlot(world, x, y, z, 0, pos);
			world.setBlockToAir(x, y, z);
			return true;
		}
		else if (!player.isSneaking() && world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && tile instanceof TileEntityClipboard)
			{
				int updatePos = getSelectionPointFromFace(EnumFacing.getFront(side), hitX, hitY, hitZ);
				BiblioNetworking.INSTANCE.sendToServer(new BiblioClipboard(x, y, z, updatePos));
				// ByteBuf buffer = Unpooled.buffer();
				// buffer.writeInt(pos.getX());
				// buffer.writeInt(pos.getY());
				// buffer.writeInt(pos.getZ());
				// buffer.writeInt(updatePos);
				// BiblioCraft.ch_BiblioClipboard.sendToServer(new FMLProxyPacket(new PacketBuffer(buffer), "BiblioClipboard"));
				return true;
			}
		}

		return false;
	}

	private int getSelectionPointFromFace(EnumFacing face, float hitx, float hity, float hitz)
	{
		switch (face)
		{
			case NORTH:{return getSelectionPoint(1.0f-hitx, 1.0f-hity);}
			case SOUTH:{return getSelectionPoint(hitx, 1.0f-hity);}
			case WEST:{return getSelectionPoint(hitz, 1.0f-hity);}
			case EAST:{return getSelectionPoint(1.0f-hitz, 1.0f-hity);}
			default: break;
		}
		return -1;
	}

	private int getSelectionPoint(float x, float y)
	{
		if (x > 0.21f && x < 0.272f)
		{
			float spacing = 0.0655f;
			for (int i = 0; i < 9; i++)
			{
				if (y > 0.23+(i*spacing) && y < 0.285f+(i*spacing))
				{

					return i;
				}
			}
		}

		if (y > 0.83 && y < 0.868f)
		{
			if (x > 0.296f && x < 0.387f)
			{
				return 10;
			}
			if (x > 0.599f && x < 0.843f)
			{
				return 11;
			}
		}

		return -1;
	}
    @Override
    public Item getItemDropped(int meta, Random rando, int par3) {
        return Item.getItemFromBlock(Blocks.air);
    }
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityClipboard();
	}


	@Override
	public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player)
	{

	}

	@Override
	public ItemStack getPickBlockExtras(ItemStack stack, World world, int x, int y, int z)
	{
		return stack;
	}

    @Override
    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift) {
        switch (biblioTile.getAngle())
        {
            case SOUTH:{this.setBlockBounds(0.97F, 0.08F, 0.15F, 1.0F, 0.92F, 0.85F); break;}
            case WEST:{this.setBlockBounds(0.15F, 0.08F, 0.97F, 0.85F, 0.92F, 1.0F); break;}
            case NORTH:{this.setBlockBounds(0.0F, 0.08F, 0.15F, 0.03F, 0.92F, 0.85F); break;}
            case EAST:{this.setBlockBounds(0.15F, 0.08F, 0.0F, 0.85F, 0.92F, 0.03F); break;}
            default: break;
        }
    }

}
