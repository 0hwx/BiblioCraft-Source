package jds.bibliocraft.tileentities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jds.bibliocraft.Config;
import jds.bibliocraft.blocks.BlockMapFrame;
import jds.bibliocraft.helpers.EnumVertPosition;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMapFrame extends BiblioTileEntity implements ITickable//TileEntity implements ISidedInventory
{
	public int mapRotation;
	public boolean topFrame = false;
	public boolean rightFrame = false;
	public boolean bottomFrame = false;
	public boolean leftFrame = false;
	public boolean hasMap;
	public boolean showText = false;
	public String showTextString = "";
	private int counter = 1;
	public ArrayList xPin;
	public ArrayList yPin;
	public ArrayList pinStrings;
	public ArrayList pinColors;
	private EntityItemFrame fauxFrame;// = new EntityItemFrame(this.worldObj);
	private float checkVariance = 0.01f;

	public int mapXCenter;
	public int mapZCenter;
	public int mapScale;

	public TileEntityMapFrame()
	{
		super(1, true);
		if (xPin == null)
		{
			xPin = new ArrayList();
		}
		if (yPin == null)
		{
			yPin = new ArrayList();
		}
		if (pinStrings == null)
		{
			pinStrings = new ArrayList();
		}
		if (pinColors == null)
		{
			pinColors = new ArrayList();
		}
	}

	public int[] getWorldCoordsFromPin(int index)
	{
		int[] coords = new int[2];
		if (hasMap)
		{
			int xCenter = mapXCenter;
			int zCenter = mapZCenter;
			//System.out.println("map center = "+xCenter+"   "+zCenter);
			//int mapScale = mapScale; // 0 = 128x128, 1 = 256x256 , 2 = 512x512 , 3 = 1024x1024 , 4 = 2048x2048
			int mapScaleFactor = 128;
			switch (mapScale)
			{
				case 1:{mapScaleFactor = 256; break;}
				case 2:{mapScaleFactor = 512; break;}
				case 3:{mapScaleFactor = 1024; break;}
				case 4:{mapScaleFactor = 2048; break;}
				default: break;
			}
			// angles and vert angles make a difference it seems.
			// vertAngle 0 and 2 have matching problems.
			// vertAngle 1 + angle 0, 1, match, also 2, 3 match, it seems
			switch(this.getVertPosition())
			{
				case FLOOR:
				{
					switch (mapRotation)
					{
						case 0:
						{
							float realX = (float)(((Float)xPin.get(index)-0.5)*mapScaleFactor + xCenter);
							float realY = (float)(((Float)yPin.get(index)-0.5)*mapScaleFactor + zCenter);
							int finalX = (int)realX;
							int finalY = (int)realY;
							coords[0] = finalX;
							coords[1] = finalY;
							return coords;
						}
						case 1:
						{
							float realX = (float)(((Float)yPin.get(index)-0.5)*mapScaleFactor + xCenter);
							float realY = (float)((0.5-(Float)xPin.get(index))*mapScaleFactor + zCenter);
							int finalX = (int)realX;
							int finalY = (int)realY;
							coords[0] = finalX;
							coords[1] = finalY;
							return coords;
						}
						case 2:
						{
							float realX = (float)((0.5-(Float)xPin.get(index))*mapScaleFactor + xCenter);
							float realY = (float)((0.5-(Float)yPin.get(index))*mapScaleFactor + zCenter);
							int finalX = (int)realX;
							int finalY = (int)realY;
							coords[0] = finalX;
							coords[1] = finalY;
							return coords;
						}
						case 3:
						{
							float realX = (float)((0.5-(Float)yPin.get(index))*mapScaleFactor + xCenter);
							float realY = (float)(((Float)xPin.get(index)-0.5)*mapScaleFactor + zCenter);
							int finalX = (int)realX;
							int finalY = (int)realY;
							coords[0] = finalX;
							coords[1] = finalY;
							return coords;
						}
						default: break;
					}
					break;
				}
				case WALL:
				{
					if (this.getAngle() == ForgeDirection.WEST || this.getAngle() == ForgeDirection.NORTH) // these are correct
					{
						switch (mapRotation)
						{
							case 0:
							{
								float realX = (float)((0.5-(Float)xPin.get(index))*mapScaleFactor + xCenter);
								float realY = (float)((0.5-(Float)yPin.get(index))*mapScaleFactor + zCenter);
								int finalX = (int)realX;
								int finalY = (int)realY;
								coords[0] = finalX;
								coords[1] = finalY;
								return coords;
							}
							case 1:
							{
								float realX = (float)((0.5-(Float)yPin.get(index))*mapScaleFactor + xCenter);
								float realY = (float)(((Float)xPin.get(index)-0.5)*mapScaleFactor + zCenter);
								int finalX = (int)realX;
								int finalY = (int)realY;
								coords[0] = finalX;
								coords[1] = finalY;
								return coords;
							}
							case 2:
							{
								float realX = (float)(((Float)xPin.get(index)-0.5)*mapScaleFactor + xCenter);
								float realY = (float)(((Float)yPin.get(index)-0.5)*mapScaleFactor + zCenter);
								int finalX = (int)realX;
								int finalY = (int)realY;
								coords[0] = finalX;
								coords[1] = finalY;
								return coords;
							}
							case 3:
							{
								float realX = (float)(((Float)yPin.get(index)-0.5)*mapScaleFactor + xCenter);
								float realY = (float)((0.5-(Float)xPin.get(index))*mapScaleFactor + zCenter);
								int finalX = (int)realX;
								int finalY = (int)realY;
								coords[0] = finalX;
								coords[1] = finalY;
								return coords;
							}
							default: break;
						}
					}
					else   // work on these next
					{
						switch (mapRotation)
						{
							case 0:
							{
								float realX = (float)(((Float)xPin.get(index)-0.5)*mapScaleFactor + xCenter);
								float realY = (float)((0.5-(Float)yPin.get(index))*mapScaleFactor + zCenter);
								int finalX = (int)realX;
								int finalY = (int)realY;
								coords[0] = finalX;
								coords[1] = finalY;
								return coords;
							}
							case 1:
							{
								float realX = (float)((0.5-(Float)yPin.get(index))*mapScaleFactor + xCenter);
								float realY = (float)((0.5-(Float)xPin.get(index))*mapScaleFactor + zCenter);
								int finalX = (int)realX;
								int finalY = (int)realY;
								coords[0] = finalX;
								coords[1] = finalY;
								return coords;
							}
							case 2:
							{
								float realX = (float)((0.5-(Float)xPin.get(index))*mapScaleFactor + xCenter);
								float realY = (float)(((Float)yPin.get(index)-0.5)*mapScaleFactor + zCenter);
								int finalX = (int)realX;
								int finalY = (int)realY;
								coords[0] = finalX;
								coords[1] = finalY;
								return coords;
							}
							case 3:
							{
								float realX = (float)(((Float)yPin.get(index)-0.5)*mapScaleFactor + xCenter);
								float realY = (float)(((Float)xPin.get(index)-0.5)*mapScaleFactor + zCenter);
								int finalX = (int)realX;
								int finalY = (int)realY;
								coords[0] = finalX;
								coords[1] = finalY;
								return coords;
							}
							default: break;
						}
					}
				}
				case CEILING:
				{
					switch (mapRotation)
					{
						case 0:
						{
							float realX = (float)(((Float)xPin.get(index)-0.5)*mapScaleFactor + xCenter);
							float realY = (float)((0.5-(Float)yPin.get(index))*mapScaleFactor + zCenter);
							int finalX = (int)realX;
							int finalY = (int)realY;
							coords[0] = finalX;
							coords[1] = finalY;
							return coords;
						}
						case 1:
						{
							float realX = (float)((0.5-(Float)yPin.get(index))*mapScaleFactor + xCenter);
							float realY = (float)((0.5-(Float)xPin.get(index))*mapScaleFactor + zCenter);
							int finalX = (int)realX;
							int finalY = (int)realY;
							coords[0] = finalX;
							coords[1] = finalY;
							return coords;
						}
						case 2:
						{
							float realX = (float)((0.5-(Float)xPin.get(index))*mapScaleFactor + xCenter);
							float realY = (float)(((Float)yPin.get(index)-0.5)*mapScaleFactor + zCenter);
							int finalX = (int)realX;
							int finalY = (int)realY;
							coords[0] = finalX;
							coords[1] = finalY;
							return coords;
						}
						case 3:
						{
							float realX = (float)(((Float)yPin.get(index)-0.5)*mapScaleFactor + xCenter);
							float realY = (float)(((Float)xPin.get(index)-0.5)*mapScaleFactor + zCenter);
							int finalX = (int)realX;
							int finalY = (int)realY;
							coords[0] = finalX;
							coords[1] = finalY;
							return coords;
						}
						default: break;
					}
					break;
				}
			}
		}
		return null;
	}

	public void addPinCoords(float x, float y, String name, float color)
	{
		if (xPin.size() <= 32)
		{
			xPin.add(x);
			yPin.add(y);
			pinStrings.add(name);
			pinColors.add(color);
            getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
			this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		}
		else
		{
			System.out.println("pin limit of 32 reached");
		}
	}

	public void editPinData(String name, float color, int index)
	{
		pinStrings.remove(index);
		pinStrings.add(index, name);
		pinColors.remove(index);
		pinColors.add(index, color);
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		//pinStrings.add(name);
		//pinColors.add(color);
	}

	public void removePin(int index)
	{
		xPin.remove(index);
		yPin.remove(index);
		pinStrings.remove(index);
		pinColors.remove(index);
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
	}

	public int findPinCoords(float x, float y)
	{
		float xCheck;
		float yCheck;
		for (int n = 0; n < xPin.size(); n++)
		{
			xCheck = Math.abs(((Float)xPin.get(n)) - x);
			if (xCheck < checkVariance)
			{
				yCheck =  Math.abs(((Float)yPin.get(n)) - y);
				if (yCheck < checkVariance)
				{
					return n;
				}
			}

		}
		return -1;
	}


	public ArrayList getPinXCoords()
	{
		return xPin;
	}
	public ArrayList getPinYCoords()
	{
		return yPin;
	}
	public ArrayList getPinNames()
	{
		return pinStrings;
	}
	public ArrayList getPinColors()
	{
		return pinColors;
	}

	public String getPinName(int index)
	{
		if (pinStrings.size() > 0)
		{
			return (String)pinStrings.get(index);
		}
		else
		{
			return "No Name Found";
		}
	}

	public int checkFace(ForgeDirection angle, ForgeDirection face, EnumVertPosition vertAngle)
	{
		//System.out.println("Angle = "+angle+"  vertAngle = "+vertAngle+"   Face = "+face);
		if (vertAngle == EnumVertPosition.CEILING && face == ForgeDirection.DOWN)
		{
			return 2;
		}
		if (vertAngle == EnumVertPosition.FLOOR && face == ForgeDirection.UP)
		{
			return 2;
		}
		if (vertAngle == EnumVertPosition.WALL)
		{
			if (angle == ForgeDirection.NORTH && face == ForgeDirection.EAST)
			{
				return 1;
			}
			if (angle == ForgeDirection.EAST && face == ForgeDirection.SOUTH)
			{
				return 0;
			}
			if (angle == ForgeDirection.SOUTH && face == ForgeDirection.WEST)
			{
				return 1;
			}
			if (angle == ForgeDirection.WEST && face == ForgeDirection.NORTH)
			{
				return 0;
			}
		}
		return -1;
	}


	public void rotateMap()
	{
		if (mapRotation >= 3)
		{
			mapRotation = 0;
		}
		else
		{
			mapRotation++;
		}
		// call in a waypoint rotater method
		rotateWaypoints();
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void rotateWaypoints()
	{
		//System.out.println("rotating waypoints maybe? "+rot);
		ArrayList xCurrent = xPin;
		ArrayList yCurrent = yPin;
		if (((this.getAngle() == ForgeDirection.SOUTH || this.getAngle() == ForgeDirection.EAST )&& this.getVertPosition() == getVertPosition().WALL) || this.getVertPosition() == EnumVertPosition.CEILING)
		{
			//System.out.println("reverse bias");
			xPin = yCurrent;
			yPin = xCurrent;
			yCurrent = yPin;
			for (int n = 0; n < xCurrent.size(); n++)
			{
				yPin.set(n, 1-(Float)yCurrent.get(n));
			}
		}
		else
		{
			xPin = yCurrent;
			yPin = xCurrent;
			xCurrent = xPin;
			for (int n = 0; n < xCurrent.size(); n++)
			{
				xPin.set(n, 1-(Float)xCurrent.get(n));
			}
		}
	}

	public void removeMap()
	{
		hasMap = false;
		mapXCenter = 0;
		mapZCenter = 0;
		mapScale = 0;
		setInventorySlotContents(0, null);
        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean addMap(ItemStack map)
	{
		if (getStackInSlot(0) == null)
		{
			setInventorySlotContents(0, map);
			hasMap = true;
			MapData mapdata =Items.filled_map.getMapData(map, this.worldObj);

			mapXCenter = mapdata.xCenter;
			mapZCenter = mapdata.zCenter;
			mapScale = mapdata.scale;
            getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}
		else
		{
			return false;
		}
	}

	/*
	public void setVertAngle(int setAng)
	{
		vertAngle = setAng;
	}
	public int getVertAngle()
	{
		return vertAngle;
	}
	*/
	public void setHasMap(boolean set)
	{
		hasMap = set;
	}
	public boolean getHasMap()
	{
		return hasMap;
	}
	/*
	public void setAngle(int setAng)
	{
		angle = setAng;
		getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
	}
	public int getAngle()
	{
		return angle;
	}
	*/
	public void setRotation(int setRot)
	{
		mapRotation = setRot;
	}
	public int getRotation()
	{
		return mapRotation;
	}

	public void setTopFrame(boolean set)
	{
		topFrame = set;
	}

	public boolean getTopFrame()
	{
		return topFrame;
	}

	public void setRightFrame(boolean set)
	{
		rightFrame = set;
	}

	public boolean getRightFrame()
	{
		return rightFrame;
	}

	public void setBottomFrame(boolean set)
	{
		bottomFrame = set;
	}

	public boolean getBottomFrame()
	{
		return bottomFrame;
	}

	public void setLeftFrame(boolean set)
	{
		leftFrame = set;
	}

	public boolean getLeftFrame()
	{
		return leftFrame;
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

    public void setFrame(World world)
	{
		fauxFrame = new EntityItemFrame(world);
	}

	public void setShowText(boolean show, float hitx, float hity)
	{
		if (hasMap)
		{
			int pin = -1;
			if (hitx != -1)
			{
				pin = this.findPinCoords(hitx, hity);
			}
			else
			{
				showText = show;
			}
			if (pin != -1)
			{
				showTextString = (String) pinStrings.get(pin);
				//System.out.println(showTextString);
				showText = show;
			}
		}
	}

	public void addMapPinDataFromAtlas(NBTTagCompound atlasMapData)
	{
		NBTTagList mapXPins = atlasMapData.getTagList("xMapWaypoints", Constants.NBT.TAG_FLOAT);
		this.xPin.clear();
		for (int i = 0; i < mapXPins.tagCount(); i++)
		{
			float xpindata = mapXPins.func_150308_e(i);
			if (this.getVertPosition() == EnumVertPosition.WALL && (this.getAngle() == ForgeDirection.WEST || this.getAngle() == ForgeDirection.NORTH))
			{
				xpindata = 1.0f - xpindata;
			}
			this.xPin.add(xpindata);
		}

		NBTTagList mapYPins = atlasMapData.getTagList("yMapWaypoints", Constants.NBT.TAG_FLOAT);
		this.yPin.clear();
		for (int i = 0; i < mapYPins.tagCount(); i++)
		{
			float ypindata = mapYPins.func_150308_e(i);//ydata.data;
			if (this.getVertPosition() == EnumVertPosition.CEILING || this.getVertPosition() == EnumVertPosition.WALL)
			{
				// ceiling
				ypindata = 1.0f - ypindata;
			}
			this.yPin.add(ypindata);
		}

		NBTTagList mapPinNames = atlasMapData.getTagList("MapWaypointNames", Constants.NBT.TAG_STRING);
		this.pinStrings.clear();
		for (int i = 0; i < mapPinNames.tagCount(); i++)
		{
			String name = mapPinNames.getStringTagAt(i);
			this.pinStrings.add(name);
		}

		NBTTagList mapPinColors = atlasMapData.getTagList("MapWaypointColors", Constants.NBT.TAG_FLOAT);
		this.pinColors.clear();
		for (int i = 0; i < mapPinColors.tagCount(); i++)
		{
			float color = mapPinColors.func_150308_e(i);
			this.pinColors.add(color);
		}

        getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void tick()
	{
		if (!this.worldObj.isRemote)
		{
			if (counter >= Config.mapUpdateRate)
			{
				counter = 1;

				if (hasMap)
				{
					if (this.fauxFrame == null)
					{
						if (this.worldObj != null)
						{
							this.setFrame(this.worldObj);
						}
						return;
					}

					List players = this.fauxFrame.worldObj.playerEntities;
					ItemStack mapstack = getStackInSlot(0);
					mapstack.setItemFrame(fauxFrame);

					MapData mapdata =Items.filled_map.getMapData(mapstack, this.worldObj);
		            Iterator iterator = players.iterator();
		            while (iterator.hasNext())
		            {
		            	 EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
		                Items.filled_map.updateMapData(getWorldObj(), entityplayermp, mapdata);
	                     byte[] packet = mapdata.getUpdatePacketData(mapstack, getWorldObj(), entityplayermp);
//	                     if (packet != null)
//	                     {
//	                         entityplayermp.connection.sendPacket(packet);
//	                     }
		             }

				}
			}
			else
			{
				counter++;
			}
		}
	}

//	@Override
//	public String getName()
//	{
//		return BlockMapFrame.name;
//	}

	@Override
	public void setInventorySlotContentsAdditionalCommands(int slot, ItemStack stack)
	{

	}

	@Override
	public void loadCustomNBTData(NBTTagCompound nbt)
	{
		this.mapRotation = nbt.getInteger("MapRotation");
		this.topFrame = nbt.getBoolean("topFrame");
		this.rightFrame = nbt.getBoolean("rightFrame");
		this.bottomFrame = nbt.getBoolean("bottomFrame");
		this.leftFrame = nbt.getBoolean("leftFrame");
		this.hasMap = nbt.getBoolean("hasMap");
		this.mapXCenter = nbt.getInteger("mapXCenter");
		this.mapZCenter = nbt.getInteger("mapZCenter");
		this.mapScale = nbt.getInteger("mapScale");


		NBTTagList mapXPins = nbt.getTagList("xMapWaypoints", Constants.NBT.TAG_FLOAT);
		this.xPin.clear();
		for (int i = 0; i < mapXPins.tagCount(); i++)
		{
			String xpinName = "x"+i;
			float xpindata = mapXPins.func_150308_e(i);
			this.xPin.add(xpindata);
		}

		NBTTagList mapYPins = nbt.getTagList("yMapWaypoints", Constants.NBT.TAG_FLOAT);
		this.yPin.clear();
		for (int i = 0; i < mapYPins.tagCount(); i++)
		{
			String xpinName = "x"+i;
			float ypindata = mapYPins.func_150308_e(i);
			this.yPin.add(ypindata);
		}

		NBTTagList mapPinNames = nbt.getTagList("MapWaypointNames", Constants.NBT.TAG_STRING);
		this.pinStrings.clear();
		for (int i = 0; i < mapPinNames.tagCount(); i++)
		{
			String name = mapPinNames.getStringTagAt(i);
			this.pinStrings.add(name);
		}

		NBTTagList mapPinColors = nbt.getTagList("MapWaypointColors", Constants.NBT.TAG_FLOAT);
		this.pinColors.clear();
		for (int i = 0; i < mapPinColors.tagCount(); i++)
		{
			float color = mapPinColors.func_150308_e(i);
			this.pinColors.add(color);
		}
	}

	@Override
	public NBTTagCompound writeCustomNBTData(NBTTagCompound nbt)
	{
		nbt.setInteger("MapRotation", mapRotation);
		nbt.setBoolean("topFrame", topFrame);
		nbt.setBoolean("rightFrame", rightFrame);
		nbt.setBoolean("bottomFrame", bottomFrame);
		nbt.setBoolean("leftFrame", leftFrame);
		nbt.setBoolean("hasMap", hasMap);
		nbt.setInteger("mapXCenter", mapXCenter);
		nbt.setInteger("mapZCenter", mapZCenter);
		nbt.setInteger("mapScale", mapScale);

		NBTTagList mapXPins = new NBTTagList();
		for (int i = 0; i < this.xPin.size(); i++)
		{
			mapXPins.appendTag(new NBTTagFloat((Float)this.xPin.get(i)));
		}
		nbt.setTag("xMapWaypoints", mapXPins);

		NBTTagList mapYPins = new NBTTagList();
		for (int i = 0; i < this.yPin.size(); i++)
		{
			mapYPins.appendTag(new NBTTagFloat((Float)this.yPin.get(i)));
		}
		nbt.setTag("yMapWaypoints", mapYPins);

		NBTTagList mapPinNames = new NBTTagList();
		for (int i = 0; i < this.pinStrings.size(); i++)
		{
			mapPinNames.appendTag(new NBTTagString((String)this.pinStrings.get(i)));
		}
		nbt.setTag("MapWaypointNames", mapPinNames);

		NBTTagList mapPinColors = new NBTTagList();
		for (int i = 0; i < this.pinColors.size(); i++)
		{
			mapPinColors.appendTag(new NBTTagFloat((Float)this.pinColors.get(i)));
		}
		nbt.setTag("MapWaypointColors", mapPinColors);

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

