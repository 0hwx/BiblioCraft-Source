package jds.bibliocraft.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.api.render.IISBRH;
import jds.bibliocraft.helpers.CustomBlockItemDataPack;
import jds.bibliocraft.items.ItemDrill;
import jds.bibliocraft.items.ItemLock;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;


public abstract class BiblioBlock extends BlockContainer implements IISBRH, IItemRenderer
{
	//private boolean hasCustomWoods = false;

	private String customTexture = "none";
	private CustomBlockItemDataPack customData = null;


	public BiblioBlock(Material material, SoundType sound, CreativeTabs tab, String name)
	{
		super(material);
		this.setStepSound(sound);
		if (tab != null)
		{
			setCreativeTab(tab);
		}

		setBlockName("BiblioCraft:" + name);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		boolean returnValue = true;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof BiblioTileEntity)
		{
			BiblioTileEntity biblioTile = (BiblioTileEntity)tile;
			String playername = player.getDisplayName();
			boolean islocked = biblioTile.isLocked();
			String lockeename = biblioTile.getLockee();
			if (!world.isRemote)
			{
				 ItemStack playerhand = player.getHeldItem();
				 if (playerhand != null)
				 {
					 if (playerhand.getItem() instanceof ItemLock)
					 {
							 if (islocked)
							 {
								 if (playername.contains(lockeename))
								 {
									 biblioTile.setLocked(false);
									 player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("lock.unlocked")));
								 }
								 else
								 {
									 player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("lock.notowner")));
								 }
							 }
							 else
							 {
								 biblioTile.setLocked(true);
								 biblioTile.setLockee(playername);
								 player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("lock.locked")));
							 }
							 return true;
					 }
				 }
				if (!islocked || playername.contains(lockeename))  // The fix to all these lock issues is to use the playername.contains(lockeename) instead of an ==
				{
					if (playerhand != null)
					{
						if (playerhand.getItem() instanceof ItemDrill && !(biblioTile instanceof TileEntityTable))
						{
							return false;
						}

					}
					returnValue = onBlockActivatedCustomCommands(world, x, y, z, player, side, hitX, hitY, hitZ);
				}
				else
				{
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("lock.notowner")));
				}
			}
			else
			{
				//client side
				returnValue = onBlockActivatedCustomCommands(world, x, y, z, player, side, hitX, hitY, hitZ);
				ItemStack playerhand = player.getHeldItem();
				if (playerhand != null)
				{
					if (playerhand.getItem() instanceof ItemDrill)
					{
						returnValue = false;
					}
				}
			}
		}

		return returnValue;
	}
	@Override
    public boolean canPlaceBlockOnSide(World worldIn, int x, int y, int z, int side)
    {
        return true;
    }

	/** Only runs server side on block right click if the block isnt locked or is the correct player who locked it */
	public abstract boolean onBlockActivatedCustomCommands(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ);

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

	@Override
	public boolean hasTileEntity()
	{
		return true;
	}

	@Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof BiblioTileEntity)
        {
        	BiblioTileEntity biblioTile = (BiblioTileEntity)tile;
        	this.customTexture = biblioTile.getCustomTextureString();
        	this.customData = getCustomDataOnHarvest(biblioTile);
        }
    }

	public CustomBlockItemDataPack getCustomDataOnHarvest(BiblioTileEntity tile)
	{
		return new CustomBlockItemDataPack();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> retern = new ArrayList<ItemStack>();
        Random rand = world instanceof World ? world.rand : new Random();
        int count = quantityDropped(metadata, fortune, rand);
        for(int i = 0; i < count; i++)
        {
            Item item = getItemDropped(metadata, rand, fortune);
            if (item != null)
            {
            	ItemStack newStack = new ItemStack(item, 1, damageDropped(metadata));
            	if (!(customTexture.contentEquals("none") || customTexture.contentEquals("")))
            	{
            		NBTTagCompound tags = new NBTTagCompound();
            		tags.setString("renderTexture", this.customTexture);
            		newStack.setTagCompound(tags);
            	}
            	if (this.customData != null && this.customData.hasData)
            	{
            		NBTTagCompound tags = new NBTTagCompound();
            		if (newStack.getTagCompound() != null)
            		{
            			tags = newStack.getTagCompound();
            		}
            		tags = this.customData.applyDataToItemStack(tags);
            		newStack.setTagCompound(tags);
            	}

            	retern.add(newStack);
            }
        }

        return retern;
    }

	public Vec3 getDropPositionOffset(int x1, int y1, int z1, EntityPlayer player) // move this to parent class I think.
	{
        Vec3 playerPos = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);

        double x = x1 - playerPos.xCoord;
        double z = z1 - playerPos.zCoord;
        if (x > 1)
            x = 1;
        if (x < -1)
            x = -1;
        if (z > 1)
            z = 1;
        if (z < -1)
            z = -1;
        x = x1 - x;
        z = z1 - z;
        Vec3 pos = Vec3.createVectorHelper(x, y1, z);
        return pos;
	}

	public void dropStackInSlot(World world, int x, int y, int z, int slot, Vec3 extractPos)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(!(tileEntity instanceof IInventory))
		{
			return;
		}

		IInventory inventory = (IInventory) tileEntity;
		BiblioTileEntity biblioTile = (BiblioTileEntity) tileEntity;
		ItemStack stack;
		stack = biblioTile.getStackInSlot(slot);
		if (stack != null && stack.stackSize > 0)
		{
			float adjusti = 0.0F;
			float adjustk = 0.0F;
			switch (biblioTile.getAngle())
			{
				case SOUTH: {adjusti = -0.2F; adjustk = 0.0F; break;}
				case WEST: {adjusti = 0.0F; adjustk = -0.2F; break;}
				case NORTH: {adjusti = 0.2F; adjustk = 0.0F; break;}
				case EAST: {adjusti = 0.0F; adjustk = 0.2F; break;}
				default: {adjusti = 0.2F; adjustk = 0.0F; break;}
			}

			EntityItem entityItem = new EntityItem(world, extractPos.xCoord+0.5F+adjusti, extractPos.yCoord + 0.5F, extractPos.zCoord +0.5F+adjustk, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));

			if (stack.hasTagCompound())
			{
				entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
			}

			entityItem.motionX = 0;
			entityItem.motionY = 0;
			entityItem.motionZ = 0;
			world.spawnEntityInWorld(entityItem);
			stack.stackSize = 0;
		}

	}

	@Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof BiblioTileEntity)
		{
			BiblioTileEntity tilee = (BiblioTileEntity)tile;
			if (tilee.isLocked())
			{
				return -1.0F;
			}
		}
		return 3.0F;
    }

	@Override
	public int damageDropped(int meta)
	{
        return meta;
	}



    public ForgeDirection getFacing(int angle)
    {
    	ForgeDirection face = ForgeDirection.WEST;
    	switch (angle)
    	{
	    	case 0:{ face = ForgeDirection.SOUTH; break; }
	    	case 2:{ face = ForgeDirection.NORTH; break; }
	    	case 3:{ face = ForgeDirection.EAST; break; }
    	}
    	return face;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
    {
    	TileEntity tile = world.getTileEntity(x, y, z);
    	if (tile != null && tile instanceof BiblioTileEntity tileEntity)
    	{
	        int angle = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	        ++angle;
	        angle %= 4;

	        BiblioTileEntity biblioTile = (BiblioTileEntity) world.getTileEntity(x, y, z);
	        biblioTile.setAngle(getFacing(angle));
	        NBTTagCompound tags = itemStack.getTagCompound();
	        if (tags != null)
	        {
	        	if (tags.hasKey("renderTexture"))
	        	{
	        		biblioTile.setCustomTexureString(tags.getString("renderTexture"));
	        	}

	        	if (tags.hasKey("textscale"))
	        	{
	        		customData.applyDataToBlock(tags, biblioTile);
	        	}

	        }
	        additionalPlacementCommands(biblioTile, player);
    	}
    }

    /** Called when the block is placed  */
    public abstract void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player);
	@Override
	public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta)
	{
		if (!(blockBroken instanceof BlockFancySign)) // This is so the fancy sign retains its inventory
			dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, blockBroken, meta);
	}

	public void dropItems(World world, int x, int y, int z)
	{
		Random rando = new Random();

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory))
		{
			return;
		}
		IInventory inventory = (IInventory) tileEntity;

		for (int x1 = 0; x1 < inventory.getSizeInventory(); x1++)
		{
			ItemStack item = inventory.getStackInSlot(x1);

			if (item != null && item.stackSize > 0)
			{
				float ri = rando.nextFloat() * 0.8F + 0.1F;
				float rj = rando.nextFloat() * 0.8F + 0.1F;
				float rk = rando.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + ri, y + rj, z + rk, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound())
				{
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rando.nextGaussian() * factor;
				entityItem.motionY = rando.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rando.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
	{
       Item item = getItem(world, x, y, z);
       if (item == null)
       {
           return null;
       }
       Block block = item instanceof ItemBlock ? Block.getBlockFromItem(item) : this;
       ItemStack stack = new ItemStack(item, 1, block.getDamageValue(world, x, y, z));
       stack = getPickBlockExtras(stack, world, x, y, z);
       return stack;
	}

	/** Additional stuff for the getPickBlock method if needed, just return the stack if not */
	public abstract ItemStack getPickBlockExtras(ItemStack stack, World world, int x, int y, int z);

    @Override
    public boolean isOpaqueCube() { return false; }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, Tessellator tessellator) {
        return false;
    }

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
    }

    @Override
    public int getRenderType()
    {
        return IISBRH.RenderId;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity collider) {
        this.setBlockBoundsBasedOnState(world,x,y,z);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collider);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof BiblioTileEntity biblioTile)
        {
            float shift = 0.0f;
            switch (biblioTile.getShiftPosition())
            {
                case NO_SHIFT:   { shift = 0.0f; break; }
                case HALF_SHIFT: { shift = 0.25f; break; }
                case FULL_SHIFT: { shift = 0.5f; break; }
            }

            // Call the abstract method for the subclass to handle the rest
            setCustomBlockBounds(biblioTile, shift);
        }
        else
        {
            // Always have a default case
            super.setBlockBoundsBasedOnState(world, x, y, z);
        }
    }

    public abstract void setCustomBlockBounds(BiblioTileEntity biblioTile, float shift);


    @Override
	public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z)
	{
        ForgeDirection[] axises = new ForgeDirection[]{ForgeDirection.UP, ForgeDirection.DOWN};
		return axises;
	}

	@Override
	public boolean rotateBlock(World world,  int x, int y, int z, ForgeDirection axis)
	{
		boolean returnValue = false;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof BiblioTileEntity)
		{
			BiblioTileEntity te = (BiblioTileEntity)tile;
            ForgeDirection angle = te.getAngle();
			returnValue = true;
			switch (axis)
			{
				case DOWN:
				{
					te.setAngle(rotateY(angle));
					break;
				}
				case UP:
				{
					te.setAngle(rotateYCCW(angle));
					break;
				}
				default: returnValue = false;
			}
		}
		return returnValue;
	}

    public static boolean isFrontOfBlock(ForgeDirection face, ForgeDirection angle)
    {
    	boolean returnValue = false;
    	if ((face == ForgeDirection.SOUTH && angle == ForgeDirection.EAST) ||
			(face == ForgeDirection.WEST && angle == ForgeDirection.SOUTH) ||
			(face == ForgeDirection.NORTH && angle == ForgeDirection.WEST) ||
			(face == ForgeDirection.EAST && angle == ForgeDirection.NORTH))
    	{
    		returnValue = true;
    	}
    	return returnValue;
    }

    public static boolean isBackOfBlock(ForgeDirection face, ForgeDirection angle)
    {
    	boolean returnValue = false;
    	if ((face == ForgeDirection.SOUTH && angle == ForgeDirection.WEST) ||
			(face == ForgeDirection.WEST && angle == ForgeDirection.NORTH) ||
			(face == ForgeDirection.NORTH && angle == ForgeDirection.EAST) ||
			(face == ForgeDirection.EAST && angle == ForgeDirection.SOUTH))
    	{
    		returnValue = true;
    	}
    	return returnValue;
    }

 	public static int getSlotNumberFromClickon2x2block(ForgeDirection angle, float hitX, float hitY, float hitZ)
 	{
 		 int xCheck = (int)(hitX * 2);
 		 int yCheck = (int)(hitY * 2);
 		 int zCheck = (int)(hitZ * 2);
 		 int slot = -1;

 		if ((yCheck == 1 && zCheck == 0 && angle == ForgeDirection.SOUTH) || (yCheck == 1 && xCheck == 1 && angle == ForgeDirection.WEST) || (yCheck == 1 && zCheck == 1 && angle == ForgeDirection.NORTH) || (yCheck == 1 && xCheck == 0 && angle == ForgeDirection.EAST))
 		 {
 			 slot = 0;
 		 }
 		 if ((yCheck == 1 && zCheck == 1 && angle == ForgeDirection.SOUTH) || (yCheck == 1 && xCheck == 0 && angle == ForgeDirection.WEST) || (yCheck == 1 && zCheck == 0 && angle == ForgeDirection.NORTH) || (yCheck == 1 && xCheck == 1 && angle == ForgeDirection.EAST))
 		 {
 			 slot = 1;
 		 }
 		 if ((yCheck == 0 && zCheck == 0 && angle == ForgeDirection.SOUTH) || (yCheck == 0 && xCheck == 1 && angle == ForgeDirection.WEST) || (yCheck == 0 && zCheck == 1 && angle == ForgeDirection.NORTH) || (yCheck == 0 && xCheck == 0 && angle == ForgeDirection.EAST))
 		 {
 			 slot = 2;
 		 }
 		 if ((yCheck == 0 && zCheck == 1 && angle == ForgeDirection.SOUTH) || (yCheck == 0 && xCheck == 0 && angle == ForgeDirection.WEST) || (yCheck == 0 && zCheck == 0 && angle == ForgeDirection.NORTH) || (yCheck == 0 && xCheck == 1 && angle == ForgeDirection.EAST))
 		 {
 			 slot = 3;
 		 }
 		 return slot;
 	}


    /**
     * Rotate this Facing around the Y axis clockwise (NORTH => EAST => SOUTH => WEST => NORTH)
     */
    public ForgeDirection rotateY(ForgeDirection facing)
    {
        switch (facing)
        {
            case NORTH:
                return facing.EAST;
            case EAST:
                return facing.SOUTH;
            case SOUTH:
                return facing.WEST;
            case WEST:
                return facing.NORTH;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    public ForgeDirection rotateYCCW(ForgeDirection facing) {
        switch (facing) {
            case NORTH:
                return facing.WEST;
            case EAST:
                return facing.NORTH;
            case SOUTH:
                return facing.EAST;
            case WEST:
                return facing.SOUTH;
            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public AxisAlignedBB getBlockBounds(float x1, float y1, float z1, float x2, float y2, float z2)
    {
        this.setBlockBounds(x1, y1, z1, x2, y2, z2);
        return AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2);
    }
}
