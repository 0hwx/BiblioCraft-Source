package jds.bibliocraft.tileentities;

import java.util.List;

import jds.bibliocraft.blocks.BiblioWoodBlock.EnumWoodType;
import jds.bibliocraft.blocks.BlockSeat;
import jds.bibliocraft.entity.EntitySeat;
import jds.bibliocraft.helpers.EnumColor;
import jds.bibliocraft.items.ItemSeatBack;
import jds.bibliocraft.items.ItemSeatBack2;
import jds.bibliocraft.items.ItemSeatBack3;
import jds.bibliocraft.items.ItemSeatBack4;
import jds.bibliocraft.items.ItemSeatBack5;
/*
import jds.bibliowood.bopwood.items.ItemSeatBackB1;
import jds.bibliowood.bopwood.items.ItemSeatBackB2;
import jds.bibliowood.bopwood.items.ItemSeatBackB3;
import jds.bibliowood.bopwood.items.ItemSeatBackB4;
import jds.bibliowood.bopwood.items.ItemSeatBackB5;
import jds.bibliowood.ebxlwood.items.ItemSeatBackE1;
import jds.bibliowood.ebxlwood.items.ItemSeatBackE2;
import jds.bibliowood.ebxlwood.items.ItemSeatBackE3;
import jds.bibliowood.ebxlwood.items.ItemSeatBackE4;
import jds.bibliowood.ebxlwood.items.ItemSeatBackE5;
import jds.bibliowood.forestrywood.items.ItemSeatBackF1;
import jds.bibliowood.forestrywood.items.ItemSeatBackF2;
import jds.bibliowood.forestrywood.items.ItemSeatBackF3;
import jds.bibliowood.forestrywood.items.ItemSeatBackF4;
import jds.bibliowood.forestrywood.items.ItemSeatBackF5;
import jds.bibliowood.highlandwood.items.ItemSeatBackH1;
import jds.bibliowood.highlandwood.items.ItemSeatBackH2;
import jds.bibliowood.highlandwood.items.ItemSeatBackH3;
import jds.bibliowood.highlandwood.items.ItemSeatBackH4;
import jds.bibliowood.highlandwood.items.ItemSeatBackH5;
import jds.bibliowood.naturawood.items.ItemSeatBackN1;
import jds.bibliowood.naturawood.items.ItemSeatBackN2;
import jds.bibliowood.naturawood.items.ItemSeatBackN3;
import jds.bibliowood.naturawood.items.ItemSeatBackN4;
import jds.bibliowood.naturawood.items.ItemSeatBackN5;
import jds.bibliowood.tfcwood.items.ItemSeatBackT1;
import jds.bibliowood.tfcwood.items.ItemSeatBackT2;
import jds.bibliowood.tfcwood.items.ItemSeatBackT3;
import jds.bibliowood.tfcwood.items.ItemSeatBackT4;
import jds.bibliowood.tfcwood.items.ItemSeatBackT5;
*/
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;


public class TileEntitySeat extends BiblioTileEntity
{
	public int hasBack;
	public boolean northConnect;
	public boolean southConnect;
	public boolean eastConnect;
	public boolean westConnect;
	public EnumColor seatColor = EnumColor.WHITE;
	public EnumWoodType backWoodType = EnumWoodType.OAK;
	public String customBackTex = "none";
	public boolean hasSitter = false;
	private int counter = 0;

	public TileEntitySeat()
	{
		super(3, true);
		// 1 slot for back item
		// 1 slot for seat cover item (vanilla carpet)
		// 1 slot for carpet
	}

	 @Override
    public void updateEntity()
    {
		if (!hasSitter)
		{
	    	if (counter >= 100 && !this.worldObj.isRemote)
	    	{
	    		counter = 0;
	    		this.scaneForEntityes();
	    	}
	    	else
	    	{
	    		counter++;
	    	}
		}
    }


    public void scaneForEntityes()
    {
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(this.xCoord -2.0, this.yCoord-1.0, this.zCoord-2.0, this.xCoord +3.0, this.yCoord+2.0, this.zCoord+3.0);
		List checkEntities = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, bb);

		for (int x = 0; x<checkEntities.size(); x++)
		{
			EntityCreature guy = (EntityCreature)checkEntities.get(x);
			if (guy.getCustomNameTag().length() > 0)
			{
				if (!hasSitter && guy.ridingEntity == null)
				{
					this.sitDown(guy);
				}
			}
		}
    }

    public void dismountEntity()
    {
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord+1.0, this.yCoord+1.5, this.zCoord+1.0);
		List checkEntities = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, bb);
		for (int x = 0; x<checkEntities.size(); x++)
		{
			EntityCreature guy = (EntityCreature)checkEntities.get(x);

			if (guy.getCustomNameTag().length() > 0)
			{
				if (guy.ridingEntity != null)
				{
					guy.ridingEntity.setDead();
					this.counter = 0;
					this.setSitter(false);
					break;
				}
			}
		}
    }

	public void sitDown(EntityLiving entity)
	{
		EntitySeat seatEntity = new EntitySeat(this.worldObj, xCoord, yCoord + 1.0d, zCoord, this);
		this.worldObj.spawnEntityInWorld(seatEntity);
		entity.setLeashedToEntity(seatEntity, true);
		this.setSitter(true);
	}


	public void setSitter(boolean sit)
	{
		hasSitter = sit;
		updateSurroundingBlocks(BlockSeat.instance);
	}
	public boolean getHasSitter()
	{
		return hasSitter;
	}
	public void removeBack()
	{
		hasBack = 0;
		customBackTex = "none";
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void removeCover()
	{
		seatColor = EnumColor.WHITE;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public EnumWoodType getBackWoodType()
	{
		return backWoodType;
	}

	public int addSeatCover(ItemStack cloth)
	{
		if (getStackInSlot(0) == null)
		{
			int clothStackSize = cloth.stackSize;
			if (clothStackSize == 1)
			{
				setInventorySlotContents(0, cloth);
				clothStackSize = 0;
			}
			else
			{
				ItemStack clothCopy = cloth.copy();
				clothCopy.stackSize = (1);
				setInventorySlotContents(0, clothCopy);
				clothStackSize--;
			}
			seatColor = EnumColor.getColorFromCarpetOrWool(cloth);
            getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
			return clothStackSize;
		}
		else
		{
			return -1;
		}
	}

	public int addSeatBack(ItemStack back)
	{
		if (getStackInSlot(1) == null)
		{
			int backStackSize = back.stackSize;
			if (backStackSize == 1)
			{
				setInventorySlotContents(1, back);
				backStackSize = 0;
			}
			else
			{
				ItemStack backCopy = back.copy();
				backCopy.stackSize = (1);
				setInventorySlotContents(1, backCopy);
				backStackSize--;
			}
			Item backType = back.getItem();
			if (backType instanceof ItemSeatBack)
			{
				hasBack = 1;
			}
			if (backType instanceof ItemSeatBack2)
			{
				hasBack = 2;
			}
			if (backType instanceof ItemSeatBack3)
			{
				hasBack = 3;
			}
			if (backType instanceof ItemSeatBack4)
			{
				hasBack = 4;
			}
			if (backType instanceof ItemSeatBack5)
			{
				hasBack = 5;
			}
			backWoodType = EnumWoodType.getEnum(back.getItemDamage());
			NBTTagCompound tags = back.getTagCompound();
			if (tags != null)
			{
				if (tags.hasKey("renderTexture"))
				{
					this.customBackTex = tags.getString("renderTexture");
				}
			}
            getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
			return backStackSize;
		}
		else
		{
			return -1;
		}
	}

	public void setCustomBackTex(String tex)
	{
		this.customBackTex = tex;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public String getCustomBackTexture()
	{
		return this.customBackTex;
	}

	public void removeSeatAddon(int slot)
	{
		setInventorySlotContents(slot, null);
		if (slot == 0)
		{
			seatColor = EnumColor.WHITE;
		}
		if (slot == 1)
		{
			hasBack = 0;
			backWoodType = EnumWoodType.OAK;
		}
		setCustomBackTex("none");
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void rotateConnections(boolean down, ForgeDirection oldAngle)
	{
		if (this.getAngle() != oldAngle)
		{
			boolean oldN = this.northConnect;
			boolean oldE = this.eastConnect;
			boolean oldS = this.southConnect;
			boolean oldW = this.westConnect;
			this.northConnect = false;
			this.eastConnect = false;
			this.southConnect = false;
			this.westConnect = false;

			if (down) // to the left
			{
				if (oldN)
				{
					this.westConnect = true;
				}
				if (oldE)
				{
					this.northConnect = true;
				}
				if (oldS)
				{
					this.eastConnect = true;
				}
				if (oldW)
				{
					this.southConnect = true;
				}
			}
			else
			{
				if (oldN)
				{
					this.eastConnect = true;
				}
				if (oldE)
				{
					this.southConnect = true;
				}
				if (oldS)
				{
					this.westConnect = true;
				}
				if (oldW)
				{
					this.northConnect = true;
				}
			}
            getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		}

	}


	public void setBack(int back)
	{
		hasBack = back;
	}

	public int getHasBack()
	{
		return hasBack;
	}

	public void setSeatColor(EnumColor color)
	{
		seatColor = color;
	}

	public EnumColor getSeatColor()
	{
		return seatColor;
	}
	public void setNorthConnect(boolean nCon)
	{
		northConnect = nCon;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public void setSouthConnect(boolean sCon)
	{
		southConnect = sCon;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public void setEastConnect(boolean eCon)
	{
		eastConnect = eCon;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public void setWestConnect(boolean wCon)
	{
		westConnect = wCon;
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	public boolean getNorthConnect()
	{
		return northConnect;
	}
	public boolean getSouthConnect()
	{
		return southConnect;
	}
	public boolean getEastConnect()
	{
		return eastConnect;
	}
	public boolean getWestConnect()
	{
		return westConnect;
	}

	public int setCarpet(ItemStack stack)
	{
		int stacksize = 0;
		if (stack == null)
		{
			if(isCarpetFull())
			{
				setInventorySlotContents(2, null);
			}
			stacksize = -1;
			return stacksize;
		}

		if (!isCarpetFull())
		{
			if (stack.stackSize > 1)
			{
				stacksize = stack.stackSize - 1;
			}
			else
			{
				stacksize = 0;
			}
			ItemStack carpetpiece = stack.copy();
			carpetpiece.stackSize = (1);
			setInventorySlotContents(2, carpetpiece);
		}
		else
		{
			stacksize = stack.stackSize;
		}
		return stacksize;
	}

	public EnumColor getCarpetColor()
	{
		if(isCarpetFull())
		{
			return EnumColor.getColorFromCarpetOrWool(getStackInSlot(2));
		}
		else
		{
			return EnumColor.WHITE;
		}
	}

	public boolean isCarpetFull()
	{
		if (inventory[2] != null)
		{
			return true;
		}
		else
		{
			return false;
		}
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

//	@Override
//	public String getName()
//	{
//		return BlockSeat.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{

	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		hasBack = nbt.getInteger("hasBack");
		northConnect = nbt.getBoolean("northConnect");
		southConnect = nbt.getBoolean("southConnect");
		eastConnect = nbt.getBoolean("eastConnect");
		westConnect = nbt.getBoolean("westConnect");
		seatColor = EnumColor.getColorEnumFromID(nbt.getInteger("seatColor"));
		backWoodType = EnumWoodType.getEnum(nbt.getInteger("backWoodType"));
		hasSitter = nbt.getBoolean("hasSitter");
		customBackTex = nbt.getString("customBackTexture");
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
		nbt.setInteger("hasBack", hasBack);
		nbt.setBoolean("northConnect", northConnect);
		nbt.setBoolean("southConnect", southConnect);
		nbt.setBoolean("eastConnect", eastConnect);
		nbt.setBoolean("westConnect", westConnect);
		nbt.setInteger("seatColor", seatColor.getID());
		nbt.setInteger("backWoodType", backWoodType.getID());
		nbt.setBoolean("hasSitter", hasSitter);
		nbt.setString("customBackTexture", this.customBackTex);
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
