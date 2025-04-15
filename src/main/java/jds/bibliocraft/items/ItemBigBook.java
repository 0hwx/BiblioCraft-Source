package jds.bibliocraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.gui.GuiBigBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;


public class ItemBigBook extends Item
{
	public String playername;
	public static final String name = "BigBook";
	public static final ItemBigBook instance = new ItemBigBook();

	public ItemBigBook()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setUnlocalizedName(name);
	}


	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote)
		{
			openGui(player.getHeldItem(), player.getDisplayName());
		}
		return stack;
	}

	@SideOnly(Side.CLIENT)
	public void openGui(ItemStack book, String author)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiBigBook(book, true, 0, 0, 0, author));
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		/*// TODO is broken, cant get access to the player and playername in this CLIENT SIDE thing, wtf?
	    List<EntityPlayer> p = world.playerEntities;
		if (p != null && p.size() > 0)
		{
			playername = p.get(0).getDisplayName().getFormattedText();
		}
		else
		{
			playername = "Douglas Adams";
		}
		*/
		playername = "Douglas Adams";
		NBTTagCompound bookTag = stack.getTagCompound();
		if (bookTag != null)
		{
			playername = bookTag.getString("author");
		}
		/*
		else
		{
			NBTTagCompound newbooktag = new NBTTagCompound();
			newbooktag.setString("author", playername);
			stack.setTagCompound(newbooktag);
		}
		*/
		tooltip.add(I18n.format("redbook.by")+" "+playername);
    	super.addInformation(stack, player, tooltip, advanced);
    	super.addInformation(stack, player, tooltip, advanced);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		NBTTagCompound tags = stack.getTagCompound();
		if (tags != null)
		{
			if (tags.getBoolean("signed"))
			{
				return true;
			}
		}
		return false;
	}

	@Override
    public int getItemEnchantability()
    {
        return 1;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:bigbook");
    }
}
