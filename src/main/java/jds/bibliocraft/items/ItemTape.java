package jds.bibliocraft.items;

import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemTape extends Item
{
	public static final String name = "tape";
	public static final ItemTape instance = new ItemTape();

	public ItemTape()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(64);
		setUnlocalizedName(name);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:tape");
    }
}
