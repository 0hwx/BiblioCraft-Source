package jds.bibliocraft.tileentities.base;

import jds.bibliocraft.helpers.EnumMetalType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class BiblioLightTileEntity extends BiblioTileEntity
{
	private EnumMetalType type;

	public BiblioLightTileEntity()
	{
		super(0, false);
	}

	public EnumMetalType getLightType()
	{
		return this.type;
	}

	public void setLightType(EnumMetalType light)
	{
		this.type =  light;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		//worldObj.markBlockForUpdate(getPos());
	}

//	@Override
//	public String getName()
//	{
//		return BlockLampGold.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{

	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.type = EnumMetalType.getEnumFromID(nbt.getInteger("type"));
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
		nbt.setInteger("type", this.type.getID());
		return nbt;
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
		return 0;
	}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return false;
	}

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }
}

