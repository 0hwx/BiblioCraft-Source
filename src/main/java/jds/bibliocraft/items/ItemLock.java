package jds.bibliocraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;


public class ItemLock extends Item
{
	public static final String name = "BiblioCreativeLock";
	public static final ItemLock instance = new ItemLock();

	public ItemLock()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setUnlocalizedName(name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{

		}
		return false;
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		tooltip.add(I18n.format("lock.creativeonly"));
    	super.addInformation(stack, playerIn, tooltip, advanced);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:lock");
    }
}
