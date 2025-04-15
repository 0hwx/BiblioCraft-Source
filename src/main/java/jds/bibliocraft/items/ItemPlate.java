package jds.bibliocraft.items;

import java.util.List;

import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

public class ItemPlate extends Item
{
	public static final String name = "PrintPlate";
	public static final ItemPlate instance = new ItemPlate();

	public ItemPlate()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setUnlocalizedName(name);
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		String book = I18n.format("plate.notValid"); //"author, title";

		NBTTagCompound bookTag = stack.getTagCompound();

		if (bookTag != null)
		{
			book = bookTag.getString("bookName");
		}
		else
		{
			NBTTagCompound newbooktag = new NBTTagCompound();
			newbooktag.setString("bookName", book);
			stack.setTagCompound(newbooktag);
		}
		tooltip.add(book);
    	super.addInformation(stack, playerIn, tooltip, advanced);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:plate");
    }
}
