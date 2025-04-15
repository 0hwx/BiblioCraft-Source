package jds.bibliocraft.items;

import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemFramingBoard extends Item
{
	public static final String name = "FramingBoard";
	public static final ItemFramingBoard instance = new ItemFramingBoard();

	public ItemFramingBoard()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(64);
		setUnlocalizedName(name);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:framingboard");
    }
}
