package jds.bibliocraft.blocks;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.Config;

import jds.bibliocraft.states.TextureState;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BiblioWoodBlock extends BiblioBlock
{
	private boolean isHalfBlock = false;
    private IIcon[] icons = new IIcon[EnumWoodType.values().length];
	public BiblioWoodBlock(String name, boolean isHalfBlock)
	{
		super(Material.wood, soundTypeWood, BlockLoader.biblioTab, name);
		this.isHalfBlock = isHalfBlock;
	}


    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        for (int x = 0; x <= BlockLoader.NUMBER_OF_WOODS; x++) {
            list.add(new ItemStack(this, 1, x));
        }
    }

	@Override
	public ItemStack getPickBlockExtras(ItemStack stack, World world, int x, int y, int z)
	{
		TileEntity wtile = world.getTileEntity(x, y, z);
		if (wtile != null && wtile instanceof BiblioTileEntity)
		{
			BiblioTileEntity tile = (BiblioTileEntity)wtile;
			if (tile.getBlockMetadata() == 6)
			{
				String customTexture = tile.getCustomTextureString();
				if (!customTexture.equals("none") || !customTexture.equals(""))
				{
					NBTTagCompound tags = new NBTTagCompound();
					tags.setString("renderTexture", customTexture);
					stack.setTagCompound(tags);
				}
			}
		}
		return stack;
	}

    @Override
    public IIcon getIcon(int side, int meta) {
        EnumWoodType wood = EnumWoodType.getEnum(meta);
        return icons[wood.getID()];
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        for (EnumWoodType wood : EnumWoodType.values()) {
            icons[wood.getID()] = iconRegister.registerIcon(wood.getTextureString());
        }
    }
    public TextureState addAdditionTextureStateInformation(BiblioTileEntity tile, TextureState state)
    {
    	return state;
    }


	/** Only runs server side on block right click if the block isnt locked or is the correct player who locked it */
	@Override
	public abstract boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ);

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

	@Override
	public abstract void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player);

    public static enum EnumWoodType {
        OAK(0, "oak", "minecraft:blocks/planks_oak"),
        SPRUCE(1, "spruce", "minecraft:blocks/planks_spruce"),
        BIRCH(2, "birch", "minecraft:blocks/planks_birch"),
        JUNGLE(3, "jungle", "minecraft:blocks/planks_jungle"),
        ACACIA(4, "acacia", "minecraft:blocks/planks_acacia"),
        DARKOAK(5, "darkoak", "minecraft:blocks/planks_big_oak"),
        FRAME(6, "framed", "bibliocraft:blocks/frame");

        private int ID;
        private String name;
        private String textureString;

        private EnumWoodType(int ID, String name, String texture) {
            this.ID = ID;
            this.name = name;
            this.textureString = texture;
        }

        public String getName() {
            return name;
        }

        public int getID() {
            return this.ID;
        }

        public String getTextureString() {
            return this.textureString;
        }

        @Override
        public String toString() {
            return getName();
        }

        public static EnumWoodType getEnum(int meta) {
            for (EnumWoodType type : values()) {
                if (type.getID() == meta) {
                    return type;
                }
            }
            return OAK;
        }
    }
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World worldIn, int x, int y, int z)
    {
        return this.getCollisionBoundingBoxFromPool(worldIn, x, y, z);
    }
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
    	TileEntity tile = world.getTileEntity(x, y, z);
		if (this.isHalfBlock && tile != null && tile instanceof BiblioTileEntity)
		{
			BiblioTileEntity biblioTile = (BiblioTileEntity)tile;
			float shift = 0.0f;
			switch (biblioTile.getShiftPosition())
			{
				case NO_SHIFT: { shift = 0.0f; break; }
				case HALF_SHIFT: { shift = 0.25f; break; }
				case FULL_SHIFT: { shift = 0.5f; break; }
			}
			switch (biblioTile.getAngle())
			{
				case SOUTH:{this.setBlockBounds(0.0F-shift, 0.0F, 0.0F, 0.5F-shift, 1.0F, 1.0F); break;}
				case WEST:{this.setBlockBounds(0.0F, 0.0F, 0.0F-shift, 1.0F, 1.0F, 0.5F-shift); break;}
				case NORTH:{this.setBlockBounds(0.5F+shift, 0.0F, 0.0F, 1.0F+shift, 1.0F, 1.0F); break;}
				case EAST:{this.setBlockBounds(0.0F, 0.0F, 0.5F+shift, 1.0F, 1.0F, 1.0F+shift); break;}
				default: {this.setBlockBounds(0.5F+shift, 0.0F, 0.0F, 1.0F+shift, 1.0F, 1.0F); break;}
			}
		}
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int output = 0;
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof BiblioTileEntity && Config.emitLight)
		{
			BiblioTileEntity tile = (BiblioTileEntity)te;
			for (int i = 0; i < tile.inventory.length; i++)
			{
				ItemStack stack = tile.getStackInSlot(i);
				if (stack != null)
				{
					Block block = Block.getBlockFromItem(stack.getItem());
					if (block != null && !(block instanceof BiblioWoodBlock))
					{
//						int light = block.getLightValue(world, x, y, z); //block.getLightValue(state, world, pos);
//						if (light > output)
//						{
//							output = light;
//							//TODO the light value is not updated right away for some reason.
//						}
					}
				}
			}
		}

		return output;
	}
}
