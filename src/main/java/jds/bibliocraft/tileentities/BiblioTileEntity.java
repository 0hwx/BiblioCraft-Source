package jds.bibliocraft.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.blocks.BiblioBlock;
import jds.bibliocraft.helpers.EnumShiftPosition;
import jds.bibliocraft.helpers.EnumVertPosition;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;


public abstract class BiblioTileEntity extends TileEntity implements IInventory, ISidedInventory
{
	private ForgeDirection angle = ForgeDirection.NORTH;
	private EnumShiftPosition shift = EnumShiftPosition.NO_SHIFT;
	private EnumVertPosition vertPosition = EnumVertPosition.WALL;
	public ItemStack[] inventory;
	private String customTexture = "none";
	private boolean isRetexturable; // this also means that the block comes in all the wood flavors
	private boolean isLocked = false;
	private String lockee = "";
	private int renderBoxAdditionalSize = 1;
    private int extendedMeta;

	public BiblioTileEntity(int inventorySize, boolean canRetexture)
	{
		this.inventory = new ItemStack[inventorySize];
		this.isRetexturable = canRetexture;
	}


	public boolean addStackToInventoryFromWorld(ItemStack stack, int slot, EntityPlayer player)
	{
		if (slot == -1)
			return false;
		boolean returnValue = false;
		ItemStack currentStack = getStackInSlot(slot);
		if (stack != null && currentStack == null)
		{
			setInventorySlotContents(slot, stack);
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			returnValue = true;
		}
		else if (stack != null && currentStack != null)
		{
			ItemStack leftStack = stack.copy();
			ItemStack rightStack = currentStack.copy();
			leftStack.stackSize = (1);
			rightStack.stackSize =(1);
			if (getIsItemStacksEqual(leftStack, rightStack))
			{
				int total = stack.stackSize + currentStack.stackSize;
				if (total > stack.getMaxStackSize() && currentStack.stackSize != currentStack.getMaxStackSize())
				{
					currentStack.stackSize =(stack.getMaxStackSize());
					stack.stackSize = (total - stack.getMaxStackSize());
					setInventorySlotContents(slot, currentStack);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
					returnValue = true;
				}
				else if (total <= stack.getMaxStackSize())
				{
					currentStack.stackSize = (total);
					setInventorySlotContents(slot, currentStack);
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					returnValue = true;
				}
			}

			if (returnValue)
			{
				getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		return returnValue;
	}

	public boolean getIsItemStacksEqual(ItemStack stack1, ItemStack stack2)
	{
		boolean output = false;
	    if (stack1 != null && stack2 != null)
	    {
	    	if (stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage())
	    	{
	    		NBTTagCompound tag1 = stack1.getTagCompound();
	    		NBTTagCompound tag2 = stack2.getTagCompound();
	    		if (tag1 == null && tag2 == null)
	    		{
	    			output = true;
	    		}
	    		else
	    		{
	    			output = tag1.equals(tag2);
	    		}
	    	}
	    }
		return output;
	}

	public boolean addStackToInventoryFromWorldSingleStackSize(ItemStack stack, int slot, EntityPlayer player)
	{
		boolean returnValue = false;
		ItemStack currentStack = getStackInSlot(slot);
		if (stack != null && currentStack == null)
		{
			if (stack.stackSize > 1)
			{
				ItemStack newStack = stack.copy();
				newStack.stackSize =(1);
				stack.stackSize =(stack.stackSize - 1);
				setInventorySlotContents(slot, newStack);
				player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
			}
			else
			{
				setInventorySlotContents(slot, stack);
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
			returnValue = true;
		}

		if (returnValue)
		{
			getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return returnValue;
	}

	public boolean removeStackFromInventoryFromWorld(int slot, EntityPlayer player, BiblioBlock block)
	{
		boolean returnValue = false;
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			Vec3 newPos = Vec3.createVectorHelper(xCoord,yCoord,zCoord);

           if (player != null)
			{
				newPos = block.getDropPositionOffset((int) newPos.xCoord, (int) newPos.yCoord, (int) newPos.zCoord, player);
			}
			block.dropStackInSlot(this.worldObj, xCoord, yCoord, zCoord, slot, newPos);
			setInventorySlotContents(slot, null);
			getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
			returnValue = true;
		}

		return returnValue;
	}

	/** Called when something is added or change in the inventory */
	public abstract void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack);

    /** Use this to load custom tags from the NBT data  */
    public abstract void loadCustomNBTData(NBTTagCompound nbt);

    /** Use this to save custom NBT data tags */
    public abstract NBTTagCompound writeCustomNBTData(NBTTagCompound nbt);

	public void setExtendedMeta(int extendedMeta)
	{
		this.extendedMeta = extendedMeta;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

    public void setAngle(ForgeDirection facing)
	{
		this.angle = facing;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setShiftPosition(EnumShiftPosition position)
	{
		this.shift = position;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setVertPosition(EnumVertPosition position)
	{
		this.vertPosition = position;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

    public int getExtendedMeta(){
        return this.extendedMeta;
    }

	public ForgeDirection getAngle()
	{
		return this.angle;
	}

	public EnumShiftPosition getShiftPosition()
	{
		return this.shift;
	}

	public EnumVertPosition getVertPosition()
	{
		return this.vertPosition;
	}

	public boolean canRetextureBlock()
	{
		return this.isRetexturable;
	}

	public String getCustomTextureString()
	{
		return this.customTexture;
	}

	public void setCustomTexureString(String tex)
	{
		this.customTexture = tex;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
	}

	public boolean isLocked()
	{
		return isLocked;
	}

	// as of now it only locks single blocks, so double clocks will have to be locked on the top and bottom blocks, as well as other multi blocks. I guess that is ok.
	public void setLocked(boolean locked)
	{
		isLocked = locked;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public String getLockee()
	{
		return lockee;
	}

	public void setLockee(String lockeeperson)
	{
		lockee = lockeeperson;
	}


	@Override
	public ItemStack getStackInSlot(int slot)
	{
		ItemStack output = null;
		if (slot >= 0 && slot < this.inventory.length)
		{
			output = inventory[slot];
		}
		return output;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if (slot >= 0 && slot < this.inventory.length)
		{
			inventory[slot] = stack;
			if (stack != null && stack.stackSize > getInventoryStackLimit()) // this may be a place that needs to be edited for limiting stuff, maybe look at brewing stand tile entity for reference on these limits I would like to impose
			{
				stack.stackSize =(getInventoryStackLimit());
			}
			setInventorySlotContentsAdditionalCommands(slot, stack);
			getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}



	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = getStackInSlot(slot);
		Item stackSizeTest = stack.getItem();
		if (stack != null)
		{
			if (stack.stackSize <= amount)
			{
				setInventorySlotContents(slot, null);
			}
			else
			{
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
	}

	@Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        AxisAlignedBB bb = INFINITE_EXTENT_AABB;
        bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + renderBoxAdditionalSize, yCoord + renderBoxAdditionalSize, zCoord + renderBoxAdditionalSize);
        return bb;
    }

	public void setRenderBoxAdditionalSize(int size)
	{
		this.renderBoxAdditionalSize = size;
	}



    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.extendedMeta = nbt.getInteger("ExtendedMeta");
        loadNBTData(nbt);
    }

    private void loadNBTData(NBTTagCompound nbt)
    {
    	NBTTagList tagList = nbt.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
		this.inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < this.inventory.length)
			{
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(tag); // new ItemStack(tag)); //todo use copy()
			}
		}

		this.isLocked = nbt.getBoolean("locked");
		this.lockee = nbt.getString("lockee");
		// angle, shift, vert position
		this.angle = getFacingFromAngleID(nbt.getInteger("angle"));
		this.shift = EnumShiftPosition.getEnumFromID(nbt.getInteger("shift"));
		this.vertPosition = EnumVertPosition.getEnumFromID(nbt.getInteger("position"));
		loadCustomNBTData(nbt);
		if (isRetexturable)
			this.customTexture = nbt.getString("customTexture");

    }

	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {   super.writeToNBT(nbt);
        nbt.setInteger("ExtendedMeta", this.extendedMeta);
        writeNBTData(nbt);
    }

    private NBTTagCompound writeNBTData(NBTTagCompound nbt)
    {
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++)
        {
            ItemStack stack = inventory[i];
            if (stack != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        nbt.setTag("Inventory", itemList);
        nbt.setBoolean("locked", isLocked);
        nbt.setString("lockee", lockee);
        nbt.setInteger("angle", getAngleIDFromFacing(angle));
        nbt.setInteger("shift", shift.getID());
        nbt.setInteger("position", vertPosition.getID());
        nbt = writeCustomNBTData(nbt);
        if (isRetexturable)
            nbt.setString("customTexture", customTexture);
        return nbt;
    }

	@Override
    public Packet getDescriptionPacket() {
        {
            NBTTagCompound dataTag = new NBTTagCompound();
            writeToNBT(dataTag);
            return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, dataTag);
        }
    }
    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet)
    {
        NBTTagCompound nbtData = packet.func_148857_g();
        readFromNBT(nbtData);
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }
//
//	@Override
//	public NBTTagCompound getUpdateTag()
//	{
//		NBTTagCompound tags = super.getUpdateTag();
//		return writeNBTData(tags);
//	}

//    private NBTTagCompound writeNBTData(NBTTagCompound nbt)
//    {
//    	NBTTagList itemList = new NBTTagList();
//    	for (int i = 0; i < inventory.size(); i++)
//    	{
//    		ItemStack stack = inventory.get(i);
//    		if (stack != null)
//    		{
//    			NBTTagCompound tag = new NBTTagCompound();
//    			tag.setByte("Slot", (byte) i);
//    			stack.writeToNBT(tag);
//    			itemList.appendTag(tag);
//    		}
//    	}
//    	nbt.setTag("Inventory", itemList);
//    	nbt.setBoolean("locked", isLocked);
//    	nbt.setString("lockee", lockee);
//    	nbt.setInteger("angle", getAngleIDFromFacing(angle));
//    	nbt.setInteger("shift", shift.getID());
//    	nbt.setInteger("position", vertPosition.getID());
//    	nbt = writeCustomNBTData(nbt);
//    	if (isRetexturable)
//    		nbt.setString("customTexture", customTexture);
//    	return nbt;
//    }

    private int getAngleIDFromFacing(ForgeDirection facing)
    {
    	int angleID = 0;
    	switch (facing)
    	{
	    	case WEST: { angleID = 1; break; }
	    	case NORTH: { angleID = 2; break; }
	    	case EAST: { angleID = 3; break; }
	    	case DOWN: { angleID = 4; break; }
	    	case UP: { angleID = 5; break; }
	    	default: { angleID = 0; break; }
    	}
    	return angleID;
    }

    public int getAngleID()
    {
    	return getAngleIDFromFacing(getAngle());
    }

    private ForgeDirection getFacingFromAngleID(int angle)
    {
    	ForgeDirection face = ForgeDirection.SOUTH;
    	switch (angle)
    	{
	    	case 1:{ face = ForgeDirection.WEST; break; }
	    	case 2:{ face = ForgeDirection.NORTH; break; }
	    	case 3:{ face = ForgeDirection.EAST; break; }
	    	case 4:{ face = ForgeDirection.DOWN; break; }
	    	case 5:{ face = ForgeDirection.UP; break; }
    	}
    	return face;
    }

    public void updateSurroundingBlocks(Block blocktype)
    {
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,  blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord + 1, yCoord, zCoord, blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord - 1, 	yCoord, 	zCoord + 1, blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord, 		yCoord, 	zCoord - 1, blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord, 		yCoord + 1, zCoord, 	 blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord, 		yCoord - 1, zCoord, 	 blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord, 		yCoord, 	zCoord, 	 blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord + 2, 	yCoord, 	zCoord, 	 blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord - 2,	yCoord, 	zCoord, 	 blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord, 		yCoord, 	zCoord + 2, blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord, 		yCoord, 	zCoord - 2, blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord, 		yCoord + 2, zCoord, 	 blocktype);
		worldObj.notifyBlocksOfNeighborChange(xCoord, 		yCoord - 2, zCoord, 	 blocktype);
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z)
    {
        return true;
    }

//	@Override
//	public boolean isEmpty()
//	{
//		boolean output = true;
//		for (int i = 0; i < inventory.size(); i++)
//		{
//			if (inventory.get(i) != null)
//			{
//				output = false;
//				break;
//			}
//		}
//		return output;
//	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public int getSizeInventory()
    {
		// TODO This might have to be tweaked for things like the chair and table that have extra special slots for carpets
		return this.inventory.length;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int simulate)
	{
		ItemStack returnStack = stack;
		if (slot < this.inventory.length) {
            ItemStack currentSlot = this.getStackInSlot(slot);
            if (currentSlot != null) {
                if (stack.getItem() == currentSlot.getItem() && currentSlot.stackSize < currentSlot.getMaxStackSize()) {
                    int count = currentSlot.stackSize + stack.stackSize;
                    if (count > stack.getMaxStackSize()) {
                        currentSlot.stackSize = (currentSlot.getMaxStackSize());
                        setInventorySlotContents(slot, currentSlot);
                        returnStack = stack.copy();
                        returnStack.stackSize =(count - currentSlot.getMaxStackSize());
                    } else {
                        stack.stackSize =(count);
                        setInventorySlotContents(slot, stack);
                        returnStack = null;
                    }
                }
            } else {
                this.setInventorySlotContents(slot, stack);
                returnStack = null;

            }
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int simulate)
	{
		ItemStack result = null;
		if (slot < this.inventory.length)
		{
			ItemStack slottedStack = this.getStackInSlot(slot);
			if (slottedStack != null)
			{
				result = slottedStack.copy();
				if (stack.stackSize >= slottedStack.stackSize)
				{
					// send it all
					this.setInventorySlotContents(slot, null);
				}
				else
				{
					result.stackSize =(stack.stackSize);
					slottedStack.stackSize =(slottedStack.stackSize - stack.stackSize);
					this.setInventorySlotContents(slot, slottedStack);
				}
			}
//
//			if (simulate)
//			{
//				// TODO return the simulated extracted ItemStack
//			}
		}
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO I may have to tweak this for certain use cases? Like map frames, armor stands, clipboard block,
		return 64;
	}

}
