package jds.bibliocraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.gui.GuiRedstoneBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemRedstoneBook extends Item
{
	public static final String name = "BiblioRedBook";
	public static final ItemRedstoneBook instance = new ItemRedstoneBook();
	public String playername;

	public ItemRedstoneBook()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(64);
		setUnlocalizedName(name);
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		/*// TODO is so broken, cant access playername
	    List<EntityPlayer> p = player.playerEntities;
		if (p != null && p.size() > 0)
		{
		 playername = I18n.format("redbook.by") + " " + p.get(0).getDisplayName().getFormattedText();
		}
		else
		{
			playername = "by James Maxwell";
		}
		*/
		playername = "by James Maxwell";
		NBTTagCompound bookTag = stack.getTagCompound();

		if (bookTag != null)
		{
			playername = bookTag.getString("redstonebook");
		}
		else
		{
			NBTTagCompound newbooktag = new NBTTagCompound();
			newbooktag.setString("redstonebook", playername);
			stack.setTagCompound(newbooktag);
		}

		tooltip.add(playername);
    	super.addInformation(stack, player, tooltip, advanced);
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote)
		{
			openGui(player.getHeldItem());
		}
		return stack;
	}

	@SideOnly(Side.CLIENT)
	public void openGui(ItemStack book)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiRedstoneBook(book));
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:redstonebook");
    }
}
