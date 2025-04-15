package jds.bibliocraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class ItemAtlasPlate extends Item
{
	public static final String name = "AtlasPlate";
	public static final ItemAtlasPlate instance = new ItemAtlasPlate();

	public ItemAtlasPlate()
	{
		super();
		setMaxStackSize(1);
		setUnlocalizedName(name);
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		NBTTagCompound tags = stack.getTagCompound();
		if (tags == null)
		{
			tooltip.add("Not Valid");
		}
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:atlasplate");
    }
}
