package jds.bibliocraft.blocks.base;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.Config;

import jds.bibliocraft.states.TextureState;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.utils.ParticleHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BiblioWoodBlock extends BiblioBlock
{
	private boolean isHalfBlock = false;
    private IIcon[] icons = new IIcon[EnumWoodType.values().length];
    private final ParticleHelper.Callback particleCallback;

	public BiblioWoodBlock(String name, boolean isHalfBlock)
	{
		super(Material.wood, soundTypeWood, BlockLoader.biblioTab, name);
		this.isHalfBlock = isHalfBlock;

        particleCallback = new ParticleHelper.DefaultCallback(this);
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
        BiblioTileEntity tile = (BiblioTileEntity) world.getTileEntity(x, y, z);
        if (tile == null) {
            return null;
        }

        if (tile.getBlockMetadata() == 6)
			{
				String customTexture = tile.getCustomTextureString();
				if (!customTexture.equals("none"))
				{
					NBTTagCompound tags = new NBTTagCompound();
					tags.setString("renderTexture", customTexture);
					stack.setTagCompound(tags);
				}
			}
        return stack;

	}
//
//    /* DROP HANDLING */
//    // Hack: When harvesting we need to get the drops in onBlockHarvested,
//    // because Mojang destroys the block and tile before calling getDrops.
//    private final ThreadLocal<ArrayList<ItemStack>> drops = new ThreadLocal<>();
//
//    @Override
//    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer playerProfile) {
//        drops.set(getDrops(world, x, y, z));
//    }
//
//    @Override
//    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
//        ArrayList<ItemStack> ret = drops.get();
//        drops.remove();
//
//        // not harvested, get drops normally
//        if (ret == null) {
//            ret = getDrops(world, x, y, z);
//        }
//
//        return ret;
//    }
//
//    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z) {
//        ArrayList<ItemStack> drops = new ArrayList<>();
//
//        ItemStack stack = getPickBlockExtras(null, world, x, y, z);
//        if (stack != null) {
//            drops.add(stack);
//        }
//
//        return drops;
//    }


    @Override
    public IIcon getIcon(int side, int meta) {
        EnumWoodType wood = EnumWoodType.getEnum(meta);
        return icons[wood.getID()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        BiblioTileEntity biblioTile = (BiblioTileEntity) world.getTileEntity(x, y, z);
        if (!biblioTile.getCustomTextureString().equals("none")){
            return getCustomTexture(biblioTile.getCustomTextureString());
        }
        EnumWoodType wood = EnumWoodType.getEnum(world.getBlockMetadata(x, y, z));
        return icons[wood.getID()];
    }


    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        for (EnumWoodType wood : EnumWoodType.values()) {
            icons[wood.getID()] = iconRegister.registerIcon(wood.getTextureString());
        }
    }

    /* Particles */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        return ParticleHelper.addHitEffects(worldObj, this, target, effectRenderer, particleCallback);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(World worldObj, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        return ParticleHelper.addDestroyEffects(worldObj, this, x, y, z, meta, effectRenderer, particleCallback);
    }

    public IIcon getCustomTexture(String texture)
    {
    	return ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite(texture);
    }

    public TextureState addAdditionTextureStateInformation(BiblioTileEntity tile, TextureState state)
    {
    	return state;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof BiblioTileEntity biblioTile) {
            if (world.getBlockMetadata(x,y,z) == 6 && player.getHeldItem() != null && biblioTile.getCustomTextureString().equals("none")) {
                biblioTile.setCustomTexureString(player.getHeldItem().getIconIndex().getIconName());
                return true;
            }
//            else if (player.getHeldItem() == null && !biblioTile.getCustomTextureString().equals("none")) {
//                biblioTile.setCustomTexureString("none");
//            }
        }
       return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

	/** Only runs server side on block right click if the block isnt locked or is the correct player who locked it */
	@Override
	public abstract boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ);

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

	@Override
	public abstract void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player);

    public static enum EnumWoodType {
        OAK(0, "oak", "planks_oak"),
        SPRUCE(1, "spruce", "planks_spruce"),
        BIRCH(2, "birch", "planks_birch"),
        JUNGLE(3, "jungle", "planks_jungle"),
        ACACIA(4, "acacia", "planks_acacia"),
        DARKOAK(5, "darkoak", "planks_big_oak"),
        FRAME(6, "framed", "bibliocraft:frame");

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

    @Override
    public void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift)
    {
        if(isHalfBlock) {
            switch (biblioTile.getAngle()) {
                case SOUTH: {
                    this.setBlockBounds(0.5F - shift, 0.0F, 0.0F, 1.0F - shift, 1.0F, 1.0F);
                    break;
                }
                case WEST: {
                    this.setBlockBounds(0.0F, 0.0F, 0.5F - shift, 1.0F, 1.0F, 1.0F - shift);
                    break;
                }
                case EAST: {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F + shift, 1.0F, 1.0F, 0.5F + shift);
                    break;
                }
                case NORTH:
                default: {
                    this.setBlockBounds(0.0F + shift, 0.0F, 0.0F, 0.5F + shift, 1.0F, 1.0F);
                    break;
                }
            }
        }
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
