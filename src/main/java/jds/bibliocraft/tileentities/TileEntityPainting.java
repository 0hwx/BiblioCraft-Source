package jds.bibliocraft.tileentities;

import jds.bibliocraft.Config;
import jds.bibliocraft.blocks.BlockPaintingFrameBorderless;
import jds.bibliocraft.helpers.EnumPaintingFrame;
import jds.bibliocraft.items.ItemPaintingCanvas;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPainting extends BiblioTileEntity
{
	// TODO something in this is crashing
	public int paintingType = 0;
	public String paintingTitle = "blank";
	public int paintingRotation = 0;
	public int paintingMasterCorner = 0;
	public int paintingScale = 1;
	public int paintingAspectRatio = 0;
	public int paintingPixelRes = 0;
	public int customPaintingAspectX = 1;
	public int customPaintingAspectY = 1;
	public boolean connectedTop = false;
	public boolean connectedLeft = false;
	public boolean connectedBottom = false;
	public boolean connectedRight = false;
	public boolean showTLCorner = true;
	public boolean showTRCorner = true;
	public boolean showBRCorner = true;
	public boolean showBLCorner = true;
	public boolean containerUpdate = false;
	public boolean hideFrame;
	public EnumPaintingFrame style = EnumPaintingFrame.BORDERLESS;

	public TileEntityPainting()
	{
		super(1, true); // single slot for the painting itself
	}

	public void setFrameStyle(EnumPaintingFrame frame)
	{
		this.style = frame;
	}

	public EnumPaintingFrame getFrameStyle()
	{
		return this.style;
	}

    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
    	double distance = Config.renderDistancePainting;
        return distance*distance;
    }

	public void setHideFrame(boolean hide)
	{
		this.hideFrame = hide;
	}

	public boolean getHideFrame()
	{
		return this.hideFrame;
	}

	public void setConnectTop(boolean connect)
	{
		this.connectedTop = connect;
		checkDiagnoalsForCorners(true);
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setConnectLeft(boolean connect)
	{
		this.connectedLeft = connect;

		//getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
		checkDiagnoalsForCorners(true);
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
//		sendUpdateNotify();
	}

	public void setConnectBottom(boolean connect)
	{
		this.connectedBottom = connect;
		checkDiagnoalsForCorners(true);
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setConnectRight(boolean connect)
	{
		this.connectedRight = connect;
		checkDiagnoalsForCorners(true);
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setPaintingRotation(int rot)
	{
		this.paintingRotation = rot;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setPacketUpdate(int corner, int scale, int pixels, int aspect, int rotation, int aspectX, int aspectY)
	{
		this.paintingMasterCorner = corner;
		this.paintingScale = scale;
		this.paintingPixelRes = pixels;
		this.paintingAspectRatio = aspect;
		this.paintingRotation = rotation;
		this.customPaintingAspectX = aspectX;
		this.customPaintingAspectY = aspectY;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setPacketAspectsUpdate(int aspectX, int aspectY)
	{
		this.customPaintingAspectX = aspectX;
		this.customPaintingAspectY = aspectY;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setContainterUpdate(boolean update)
	{
		this.containerUpdate = update;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean getContainerUpdate()
	{
		return this.containerUpdate;
	}

	public int getCustomPaintingAspectX()
	{
		return this.customPaintingAspectX;
	}

	public int getCustomPaintingAspectY()
	{
		return this.customPaintingAspectY;
	}

	public void resetPaintingData()
	{
		paintingRotation = 0;
		paintingMasterCorner = 0;
		paintingScale = 1;
		paintingAspectRatio = 0;
		paintingPixelRes = 0;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void checkDiagnoalsForCorners(boolean checkFurtherPaintings)
	{
		TileEntity topLeftdiagTile = null;//worldObj.getTileEntity(pos);
		TileEntity topRightdiagTile = null;//worldObj.getTileEntity(pos);
		TileEntity bottomRightdiagTile = null;//worldObj.getTileEntity(pos);
		TileEntity bottomLeftdiagTile = null;//worldObj.getTileEntity(pos);
		switch (this.getAngle())
		{
			case SOUTH:
			{
				topLeftdiagTile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord-1);
				topRightdiagTile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord+1);
				bottomRightdiagTile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord+1);
				bottomLeftdiagTile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord-1); // good
				break;
			}
			case WEST:
			{
				topLeftdiagTile = worldObj.getTileEntity(xCoord+1, yCoord+1, zCoord);
				topRightdiagTile = worldObj.getTileEntity(xCoord-1, yCoord+1, zCoord);
				bottomRightdiagTile = worldObj.getTileEntity(xCoord-1, yCoord-1, zCoord);
				bottomLeftdiagTile = worldObj.getTileEntity(xCoord+1, yCoord-1, zCoord);
				break;
			}
			case NORTH:
			{
				topLeftdiagTile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord+1);
				topRightdiagTile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord-1);
				bottomRightdiagTile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord-1);
				bottomLeftdiagTile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord+1);
				break;
			}
			case EAST:
			{
				topLeftdiagTile = worldObj.getTileEntity(xCoord-1, yCoord+1, zCoord);
				topRightdiagTile = worldObj.getTileEntity(xCoord+1, yCoord+1, zCoord);
				bottomRightdiagTile = worldObj.getTileEntity(xCoord+1, yCoord-1, zCoord);
				bottomLeftdiagTile = worldObj.getTileEntity(xCoord-1, yCoord-1, zCoord);
				break;
			}
			default: break;
		}

		this.showTLCorner = true;
		this.showTRCorner = true;
		this.showBRCorner = true;
		this.showBLCorner = true;

		if (topLeftdiagTile != null && topLeftdiagTile instanceof TileEntityPainting)
		{
			TileEntityPainting paintTile = (TileEntityPainting)topLeftdiagTile;
			if (paintTile.getConnectBottom() && paintTile.getConnectRight())
			{
				this.showTLCorner = false;
				if (checkFurtherPaintings)
				{
					paintTile.checkDiagnoalsForCorners(false);
				}
				else
				{
                    getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
				}
				paintTile.updateLocalPaintings();
			}
		}

		if (topRightdiagTile != null && topRightdiagTile instanceof TileEntityPainting)
		{
			TileEntityPainting paintTile = (TileEntityPainting)topRightdiagTile;
			if (paintTile.getConnectBottom() && paintTile.getConnectLeft())
			{
				this.showTRCorner = false;
				if (checkFurtherPaintings)
				{
					paintTile.checkDiagnoalsForCorners(false);
				}
				else
				{
                    getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
				}
				paintTile.updateLocalPaintings();
			}
		}

		if (bottomRightdiagTile != null && bottomRightdiagTile instanceof TileEntityPainting)
		{
			TileEntityPainting paintTile = (TileEntityPainting)bottomRightdiagTile;
			if (paintTile.getConnectTop() && paintTile.getConnectLeft())
			{
				this.showBRCorner = false;
				if (checkFurtherPaintings)
				{
					paintTile.checkDiagnoalsForCorners(false);
				}
				else
				{
                    getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
				}
				paintTile.updateLocalPaintings();
			}
		}

		if (bottomLeftdiagTile != null && bottomLeftdiagTile instanceof TileEntityPainting)
		{
			TileEntityPainting paintTile = (TileEntityPainting)bottomLeftdiagTile;
			if (paintTile.getConnectTop() && paintTile.getConnectRight())
			{
				this.showBLCorner = false;
				if (checkFurtherPaintings)
				{
					paintTile.checkDiagnoalsForCorners(false);
					//paintTile.updateLocalPaintings();
				}
				else
				{
                    getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
				}
				paintTile.updateLocalPaintings();
			}
		}
	}

	public void updateLocalPaintings()
	{
		TileEntity topTile = null;//worldObj.getTileEntity(pos);
		TileEntity bottomTile = null;//worldObj.getTileEntity(pos);
		TileEntity rightTile = null;//worldObj.getTileEntity(pos);
		TileEntity leftTile = null;//worldObj.getTileEntity(pos);
		switch (this.getAngle())
		{
			case SOUTH:
			{
				topTile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
				bottomTile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
				rightTile = worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
				leftTile = worldObj.getTileEntity(xCoord, yCoord, zCoord-1); // good
				break;
			}
			case WEST:
			{
				topTile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
				bottomTile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
				rightTile = worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
				leftTile = worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
				break;
			}
			case NORTH:
			{
				topTile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
				bottomTile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
				rightTile = worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
				leftTile = worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
				break;
			}
			case EAST:
			{
				topTile = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
				bottomTile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
				rightTile = worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
				leftTile = worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
				break;
			}
			default: break;
		}

		if (topTile != null && topTile instanceof TileEntityPainting)
		{
			TileEntityPainting tile = (TileEntityPainting)topTile;
			tile.updateMe();
		}
		if (bottomTile != null && bottomTile instanceof TileEntityPainting)
		{
			TileEntityPainting tile = (TileEntityPainting)bottomTile;
			tile.updateMe();
		}
		if (rightTile != null && rightTile instanceof TileEntityPainting)
		{
			TileEntityPainting tile = (TileEntityPainting)rightTile;
			tile.updateMe();
		}
		if (leftTile != null && leftTile instanceof TileEntityPainting)
		{
			TileEntityPainting tile = (TileEntityPainting)leftTile;
			tile.updateMe();
		}
	}

	public void updateMe()
	{
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	private void sendUpdateNotify()
	{
		if (this.getAngle() == ForgeDirection.SOUTH || this.getAngle() == ForgeDirection.NORTH)
		{
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord+1, zCoord-1, BlockPaintingFrameBorderless.instance);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord+1, zCoord+1, BlockPaintingFrameBorderless.instance);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord-1, zCoord-1, BlockPaintingFrameBorderless.instance);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord-1, zCoord+1, BlockPaintingFrameBorderless.instance);
			//world.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord-1, zCoord+1), BlockPaintingFrameBorderless.instance);
		}
		else
		{
			worldObj.notifyBlocksOfNeighborChange(xCoord+1, yCoord+1, zCoord, BlockPaintingFrameBorderless.instance);
			worldObj.notifyBlocksOfNeighborChange(xCoord-1, yCoord+1, zCoord, BlockPaintingFrameBorderless.instance);
			worldObj.notifyBlocksOfNeighborChange(xCoord+1, yCoord-1, zCoord, BlockPaintingFrameBorderless.instance);
			worldObj.notifyBlocksOfNeighborChange(xCoord-1, yCoord-1, zCoord, BlockPaintingFrameBorderless.instance);
		}
	}
	public boolean getConnectTop()
	{
		return this.connectedTop;
	}

	public boolean getConnectLeft()
	{
		return this.connectedLeft;
	}

	public boolean getConnectBottom()
	{
		return this.connectedBottom;
	}

	public boolean getConnectRight()
	{
		return this.connectedRight;
	}

	public boolean getShowTLCorner()
	{
		return this.showTLCorner;
	}
	public boolean getShowTRCorner()
	{
		return this.showTRCorner;
	}
	public boolean getShowBRCorner()
	{
		return this.showBRCorner;
	}
	public boolean getShowBLCorner()
	{
		return this.showBLCorner;
	}

	public int getPaintingRotation()
	{
		return this.paintingRotation;
	}

	public int getPaintingCorner()
	{
		return this.paintingMasterCorner;
	}

	public int getPaintingScale()
	{
		return this.paintingScale;
	}

	public int getPaintingRes()
	{
		return this.paintingPixelRes;
	}

	public int getPaintingAspectRatio()
	{
		return this.paintingAspectRatio;
	}

	public boolean hasPainting()
	{
		if (getStackInSlot(0) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getPaintingType()
	{
		return this.paintingType;
	}

	public String getPaintingTitle()
	{
		return this.paintingTitle;
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
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
		return 1;
	}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        AxisAlignedBB bb = INFINITE_EXTENT_AABB;
        bb = AxisAlignedBB.getBoundingBox(xCoord-3-this.paintingScale*(this.paintingAspectRatio+1),
        							  yCoord-3-this.paintingScale*(this.paintingAspectRatio+1),
        							  zCoord-3-this.paintingScale*(this.paintingAspectRatio+1),
        							  xCoord+4+this.paintingScale*(this.paintingAspectRatio+1),
        							  yCoord+4+this.paintingScale*(this.paintingAspectRatio+1),
        							  zCoord+4+this.paintingScale*(this.paintingAspectRatio+1));
        return bb;
    }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		Item stackitem = itemstack.getItem();
		if (stackitem != null)
		{
			if (stackitem == ItemPaintingCanvas.instance)
			{
				return true;
			}
		}
		return false;
	}

//	@Override
//	public String getName()
//	{
//		String output = BlockPaintingFrameBorderless.name;
//		switch (this.getFrameStyle())
//		{
//			case BORDERLESS: { output = BlockPaintingFrameBorderless.name; break; }
//			case FANCY: { output = BlockPaintingFrameFancy.name; break; }
//			case FLAT: { output = BlockPaintingFrameFlat.name; break; }
//			case MIDDLE: { output = BlockPaintingFrameMiddle.name; break; }
//			case SIMPLE: { output = BlockPaintingFrameSimple.name; break; }
//		}
//		return output;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{
		if (stack != null)
		{
			if (stack.getItem() == ItemPaintingCanvas.instance)
			{
				NBTTagCompound tags = stack.getTagCompound();
				if (tags != null)
				{
					this.paintingTitle = tags.getString("paintingTitle");
					this.paintingType = tags.getInteger("paintingType");
				}
			}
		}
	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.paintingType = nbt.getInteger("paintingType");
		this.paintingTitle = nbt.getString("paintingTitle");
		this.connectedTop = nbt.getBoolean("connectedTop");
		this.connectedLeft = nbt.getBoolean("connectedLeft");
		this.connectedBottom = nbt.getBoolean("connectedBottom");
		this.connectedRight = nbt.getBoolean("connectedRight");
		this.paintingRotation = nbt.getInteger("paintingRotation");
		this.showTLCorner = nbt.getBoolean("showTLCorner");
		this.showTRCorner = nbt.getBoolean("showTRCorner");
		this.showBRCorner = nbt.getBoolean("showBRCorner");
		this.showBLCorner = nbt.getBoolean("showBLCorner");
		this.paintingMasterCorner = nbt.getInteger("masterCorner");
		this.paintingScale = nbt.getInteger("paintingScale");
		this.paintingPixelRes = nbt.getInteger("paintingPixelRes");
		this.paintingAspectRatio = nbt.getInteger("paintingAspectRatio");
		this.customPaintingAspectX = nbt.getInteger("customPaintingAspectX");
		this.customPaintingAspectY = nbt.getInteger("customPaintingAspectY");
		this.hideFrame = nbt.getBoolean("hideFrame");
		this.style = EnumPaintingFrame.getEnumFromID(nbt.getInteger("frameStyle"));
		this.containerUpdate = nbt.getBoolean("containerUpdate");
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
    	nbt.setInteger("paintingType", this.paintingType);
    	nbt.setString("paintingTitle", this.paintingTitle);
    	nbt.setBoolean("connectedTop", this.connectedTop);
    	nbt.setBoolean("connectedLeft", this.connectedLeft);
    	nbt.setBoolean("connectedBottom", this.connectedBottom);
    	nbt.setBoolean("connectedRight", this.connectedRight);
    	nbt.setInteger("paintingRotation", this.paintingRotation);
    	nbt.setBoolean("showTLCorner", this.showTLCorner);
    	nbt.setBoolean("showTRCorner", this.showTRCorner);
    	nbt.setBoolean("showBRCorner", this.showBRCorner);
    	nbt.setBoolean("showBLCorner", this.showBLCorner);
    	nbt.setInteger("masterCorner", this.paintingMasterCorner);
    	nbt.setInteger("paintingScale", this.paintingScale);
    	nbt.setInteger("paintingPixelRes", this.paintingPixelRes);
    	nbt.setInteger("paintingAspectRatio", this.paintingAspectRatio);
    	nbt.setInteger("customPaintingAspectX", this.customPaintingAspectX);
    	nbt.setInteger("customPaintingAspectY", this.customPaintingAspectY);
    	nbt.setBoolean("hideFrame", this.hideFrame);
    	nbt.setInteger("frameStyle", this.style.getID());
    	nbt.setBoolean("containerUpdate", this.containerUpdate);
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
