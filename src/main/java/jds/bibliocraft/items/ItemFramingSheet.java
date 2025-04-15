package jds.bibliocraft.items;


import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemFramingSheet extends Item
{
	public static final String name = "FramingSheet";
	public static final ItemFramingSheet instance = new ItemFramingSheet();

	public ItemFramingSheet()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(64);
		setUnlocalizedName(name);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:framingsheet");
    }
}
