package jds.bibliocraft.items;

import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.helpers.BiblioSortingHelper;
import jds.bibliocraft.network.BiblioNetworking;
import jds.bibliocraft.network.packet.client.BiblioStockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemStockroomCatalog extends Item
{
	public static final String name = "StockroomCatalog";
	public static final ItemStockroomCatalog instance = new ItemStockroomCatalog();

	public ItemStockroomCatalog()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setUnlocalizedName(name);
	}

	public NBTTagList getCompassList(EntityPlayer player)
	{
		NBTTagList tags = new NBTTagList();
		int[] compasses = {-1,-1,-1,-1,-1,-1,-1,-1};
		ItemStack[] compassStacks = {null, null, null, null, null, null, null, null};
		for (int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			ItemStack invTest = player.inventory.getStackInSlot(i);
			if (invTest != null)
			{
				if (invTest.getItem() instanceof ItemWaypointCompass)
				{
					for (int n = 0; n < compasses.length; n++)
					{
						if (compasses[n] == -1)
						{
							compasses[n] = i;
							compassStacks[n] = invTest.copy();
							break;
						}
					}
				}
			}
		}

		for (int i = 0; i < compasses.length; i++)
		{
			String invName = "compass"+i;
			String invSlotName = "slot"+i;
			NBTTagCompound tagComp = new NBTTagCompound();
			tagComp.setInteger(invSlotName, compasses[i]);
			if (compasses[i] != -1)
			{
				ItemStack stack = compassStacks[i];
				if (stack != null)
				{
					stack.writeToNBT(tagComp);
				}
			}
			tags.appendTag(tagComp);
		}
		return tags;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote && !player.isSneaking())
		{
			String title = I18n.format("item.StockroomCatalog.name");
			NBTTagCompound tags = player.getHeldItem().getTagCompound();
			if (tags != null && tags.hasKey("display"))
			{
				NBTTagCompound display = tags.getCompoundTag("display");
				title = display.getString("Name");
			}
			// ByteBuf buffer = Unpooled.buffer();
			NBTTagCompound loadedTags = BiblioSortingHelper.getFullyLoadedSortedListsInNBTTags(player.getHeldItem(), world);
			loadedTags.setTag("compasses", getCompassList(player));
			loadedTags.setString("title", title);
			BiblioNetworking.INSTANCE.sendTo(new BiblioStockLog(loadedTags), (EntityPlayerMP) player);
			// ByteBufUtils.writeTag(buffer, loadedTags);
			// BiblioCraft.ch_BiblioStockCatalog.sendTo(new FMLProxyPacket(new PacketBuffer(buffer), "BiblioStockLog"), (EntityPlayerMP) player);
		}
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote && player.isSneaking())
		{
			ItemStack stack = player.getHeldItem();
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inventory = (IInventory)tile;
				int invSize = inventory.getSizeInventory();
				String invName = "stockcatalog.inventory.notfound";

//				if (inventory.getDisplayName() != null)
//				{
//					invName = inventory.getDisplayName().getUnformattedText();
//				} else if (inventory.getName() != null)
//				{
//					invName = inventory.getName();
//				}

				NBTTagCompound tags = stack.getTagCompound();
				if (tags == null)
				{
					tags = new NBTTagCompound();
				}

				NBTTagList inventoryList = tags.getTagList("inventoryList", Constants.NBT.TAG_COMPOUND);
				if (inventoryList == null)
				{
					inventoryList = new NBTTagList();
				}

				if (inventoryList.tagCount() > 0)
				{
					for (int i = 0; i < inventoryList.tagCount(); i++)
					{
						NBTTagCompound invTag = inventoryList.getCompoundTagAt(i);
						if (invTag != null)
						{
							if (invTag.getString("name").equals(invName) && invTag.getInteger("x") == x && invTag.getInteger("y") == y && invTag.getInteger("z") == z)
							{
								inventoryList.removeTag(i);
								tags.setTag("inventoryList", inventoryList);
								stack.setTagCompound(tags);
								return true;
							}
						}
					}
				}
				NBTTagCompound newInvTag = new NBTTagCompound();
				newInvTag.setString("name", invName);
				newInvTag.setInteger("x", x);
				newInvTag.setInteger("y", y);
				newInvTag.setInteger("z", z);
				inventoryList.appendTag(newInvTag);
				tags.setTag("inventoryList", inventoryList);
				stack.setTagCompound(tags);
				player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
				return true;
			}
		}
        return false;
    }
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:stockcatalog");
    }
}
