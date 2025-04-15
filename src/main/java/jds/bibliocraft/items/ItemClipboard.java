package jds.bibliocraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.blocks.BlockClipboard;
import jds.bibliocraft.gui.GuiClipboard;
import jds.bibliocraft.tileentities.TileEntityClipboard;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


public class ItemClipboard extends Item
{
	public static final String name = "BiblioClipboard";
	public static final ItemClipboard instance = new ItemClipboard();
	public ItemStack clipboardstack;

	public ItemClipboard()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
		{
			ItemStack stack = player.getHeldItem();
            ForgeDirection face = ForgeDirection.getOrientation(side);
			if (stack != null)
			{
				switch (face)
				{
					case NORTH:{setClipboardBlock(x, y, z-1, ForgeDirection.WEST, world, player, stack); break;}
					case SOUTH:{setClipboardBlock(x, y, z+1, ForgeDirection.EAST, world, player, stack); break;}
					case WEST:{setClipboardBlock(x-1, y, z, ForgeDirection.SOUTH, world, player, stack); break;}
					case EAST:{setClipboardBlock(x+1, y, z, ForgeDirection.NORTH, world, player, stack); break;}
					default: break;
				}
				return true;
			}
		}
		return false;
	}

	public void setClipboardBlock(int x, int y, int z, ForgeDirection angle, World world, EntityPlayer player, ItemStack stack)
	{
		Block testBlock = world.getBlock(x, y, z);
		if (testBlock.isAir(world, x, y, z))
		{
            Block state = BlockClipboard.instance;
            world.setBlock(x, y, z, state);
			TileEntityClipboard clipboard = (TileEntityClipboard)world.getTileEntity(x, y, z);
			if (clipboard != null)
			{
				clipboard.setAngle(angle);
				clipboard.setInventorySlotContents(0, stack);
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				clipboard.getNBTData();
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		NBTTagCompound test = player.getHeldItem().getTagCompound();
		if (test == null)
		{
			NBTTagCompound clipboard = new NBTTagCompound();
			NBTTagCompound page = new NBTTagCompound();
			NBTTagCompound tasks = new NBTTagCompound();
			int[] taskstates = {0,0,0,0,0,0,0,0,0};
			page.setIntArray("taskStates", taskstates);
			tasks.setString("task1", "");
			tasks.setString("task2", "");
			tasks.setString("task3", "");
			tasks.setString("task4", "");
			tasks.setString("task5", "");
			tasks.setString("task6", "");
			tasks.setString("task7", "");
			tasks.setString("task8", "");
			tasks.setString("task9", "");
			page.setTag("tasks", tasks);
			page.setString("title", "");
			clipboard.setTag("page1",  page);
			clipboard.setInteger("currentPage", 1);
			clipboard.setInteger("totalPages", 1);
			player.getHeldItem().setTagCompound(clipboard);
		}
		clipboardstack = player.getHeldItem();
		if (!player.isSneaking() && world.isRemote)
		{
			openWritingGUI(player.getHeldItem(),  true);
		}
		return stack;
	}

	@SideOnly(Side.CLIENT)
    public void openWritingGUI(ItemStack stack, boolean inInv)
    {
		Minecraft.getMinecraft().displayGuiScreen(new GuiClipboard(stack, inInv, 0, 0, 0));
    }

	@SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		String title = "";
		NBTTagCompound clipboard = stack.getTagCompound();
		if (clipboard != null)
		{
			int currpage = clipboard.getInteger("currentPage");
			if (currpage > 0)
			{
				String pagenum = "page"+currpage;
				NBTTagCompound page = clipboard.getCompoundTag(pagenum);
				if (page != null)
				{
					title = page.getString("title");
					title = title.trim();
					tooltip.add(title);
				}
			}

		}
    	super.addInformation(stack, playerIn, tooltip, advanced);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:clipboardsimple");
    }
}
