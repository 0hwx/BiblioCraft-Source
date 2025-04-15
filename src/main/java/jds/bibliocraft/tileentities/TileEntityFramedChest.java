package jds.bibliocraft.tileentities;

import java.util.Iterator;
import java.util.List;

import jds.bibliocraft.blocks.BlockFramedChest;
import jds.bibliocraft.containers.ContainerFramedChest;
import jds.bibliocraft.helpers.BiblioSortingHelper;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityFramedChest extends BiblioTileEntity implements ITickable
{

    private boolean openChest = false;
	private ItemStack labelStack = null;
	private boolean isDouble = false;
	private boolean isLeft = true;

	private int ticksSinceSync = 0;
	private int numPlayersUsing = 0;
	private float prevLidAngle = 0.0f;
	private float lidAngle = 0.0f;

	public TileEntityFramedChest adjacentDoubleChest = null;
	//public ResourceLocation customChestFillerTexture = null;



	public TileEntityFramedChest()
	{
		super(27, true);
	}

	public float getPrevLidAngle()
	{
		return this.prevLidAngle;
	}

	public void addUsingPlayer(boolean add)
	{
		if (add)
		{
			this.numPlayersUsing++;
		}
		else
		{
			this.numPlayersUsing--;
			if (this.numPlayersUsing < 0)
			{
				this.numPlayersUsing = 0;
			}
		}
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public ItemStack getLabelStack()
	{
		return this.labelStack;
	}
	private void setLabelStack(ItemStack stack)
	{
		this.labelStack = stack;
	}

	public boolean getIsDouble()
	{
		return this.isDouble;
	}

	public boolean getIsLeft()
	{
		return this.isLeft;
	}

	public void setIsDouble(boolean dub, boolean left, TileEntityFramedChest secondChest)
	{
		this.isDouble = dub;
		this.isLeft = left;
		this.adjacentDoubleChest = secondChest;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setAdjacentChest(TileEntityFramedChest secondChest)
	{
		this.adjacentDoubleChest = secondChest;
	}

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    public void setOpenChest(boolean chest)
    {
    	this.openChest = chest;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    public boolean getOpenChest()
    {
    	return this.openChest;
    }

	@Override
    public void tick()
    {

        ++this.ticksSinceSync;
        float f;
        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
        {
            this.numPlayersUsing = 0;
            f = 5.0F;
            List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(((float)this.xCoord - f), ((float)this.yCoord - f), ((float)this.zCoord - f), ((float)(this.xCoord + 1) + f), ((float)(this.yCoord + 1) + f), ((float)(this.zCoord + 1) + f)));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
            	EntityPlayer player = (EntityPlayer)iterator.next();

                if (player.openContainer instanceof ContainerFramedChest)
                {
                    IInventory iinventory = ((ContainerFramedChest)player.openContainer).getMainTile();

                    if (iinventory == this || iinventory instanceof TileEntityFramedChest && checkIfIsAdjacentDoubleChest((TileEntityFramedChest)iinventory))
                    {
                    	this.numPlayersUsing++;
                    }
                }
            }
        }


        this.prevLidAngle = this.lidAngle;
        f = 0.1F;
        double d2;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && !this.worldObj.isRemote)
        {
            double d1 = (double)this.xCoord + 0.5D;
            d2 = (double)this.zCoord + 0.5D;
            if (this.getIsDouble() && this.adjacentDoubleChest != null)
            {
                if (this.adjacentDoubleChest.zCoord == (this.zCoord +1)) //zpos
                {
                    d2 += 0.5D;
                }
                if (this.adjacentDoubleChest.xCoord == (this.xCoord +1)) //xpos
                {
                    d1 += 0.5D;
                }
            }
            if ((this.getIsDouble() && this.getIsLeft()) || !this.getIsDouble())
            {
            	this.getWorldObj().playSoundEffect(d1, yCoord + 0.5D, d2, "random.chestopen",  0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
        }

        if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0F) || (numPlayersUsing > 0 && this.lidAngle < 1.0F))
        {
        	//first we deal with the lid angle
            float f1 = this.lidAngle;

            if (numPlayersUsing > 0)
            {
                this.lidAngle += f;
            }
            else
            {
                this.lidAngle -= f;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;
            // here is where we deal with the lid closing sound
            if (this.lidAngle < f2 && f1 >= f2 && !this.worldObj.isRemote)
            {
                d2 = (double)this.xCoord + 0.5D;
                double d0 = (double)this.zCoord + 0.5D;

                if (this.getIsDouble() && this.adjacentDoubleChest != null)
                {
	                if (this.adjacentDoubleChest.zCoord == (this.zCoord +1)) //zpos
	                {
	                    d0 += 0.5D;
	                }
	                if (this.adjacentDoubleChest.xCoord == (this.xCoord +1)) //xpos
	                {
	                    d2 += 0.5D;
	                }
                }

                if ((this.getIsDouble() && this.getIsLeft()) || !this.getIsDouble())
                {
                	this.getWorldObj().playSoundEffect(d2, (double)this.yCoord + 0.5D, d0, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }

	public void setLidAngle(float langle)
	{
		this.lidAngle = langle;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public float getLidAngle()
	{
		return this.lidAngle;
	}

	private boolean checkIfIsAdjacentDoubleChest(TileEntityFramedChest tile)
	{
		if (tile != null && this.adjacentDoubleChest != null)
		{
			if (tile.getIsDouble() && this.adjacentDoubleChest.getIsDouble() && this.getIsDouble() && this.getIsLeft())
			{
				if (tile.xCoord == this.adjacentDoubleChest.xCoord && tile.yCoord == this.adjacentDoubleChest.yCoord && tile.zCoord == this.adjacentDoubleChest.zCoord)
				{
					return true;
				}
			}
		}
		return false;
	}

//	@Override
//	public String getName()
//	{
//		return BlockFramedChest.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{
		ItemStack bestStack = BiblioSortingHelper.getLargestStackInList(BiblioSortingHelper.getStackForBuiltinLabel(this));
		if (bestStack != null)
		{
			bestStack.stackSize = (1);
		}
		this.labelStack = bestStack;
	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.openChest = nbt.getBoolean("openChest");
		this.isDouble = nbt.getBoolean("isDouble");
		this.isLeft = nbt.getBoolean("isLeft");
		this.numPlayersUsing = nbt.getInteger("numPlayersUsing");
		this.labelStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("labelStack"));
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
    	nbt.setBoolean("openChest", this.openChest);
    	nbt.setBoolean("isDouble", this.isDouble);
    	nbt.setBoolean("isLeft", this.isLeft);
    	nbt.setInteger("numPlayersUsing", this.numPlayersUsing);
    	if (this.labelStack != null)
    	{
    		nbt.setTag("labelStack", this.labelStack.writeToNBT(new NBTTagCompound()));
    	}
    	else
    	{
    		nbt.setTag("labelStack", new NBTTagCompound());
    	}
		return nbt;
	}

//	@Override
//	public ITextComponent getDisplayName()
//	{
//		ITextComponent chat = new ChatComponentText(getName());
//		return chat;
//	}

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }
}
