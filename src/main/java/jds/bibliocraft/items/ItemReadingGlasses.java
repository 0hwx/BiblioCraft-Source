package jds.bibliocraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BiblioTab;
import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.EnumHelper;

import java.util.List;


public class ItemReadingGlasses extends ItemArmor //implements IArmorTextureProvider
{
	private final static String[] subName = {"readingglasses", "tintedglasses", "monocle"};
	public static final String name = "BiblioGlasses";
	public static ArmorMaterial BIBLIO_ARMOR_MATERIAL = EnumHelper.addArmorMaterial("monocle", 5, new int[]{1, 1, 1, 1}, 1);
	public static final ItemReadingGlasses instance = new ItemReadingGlasses();
    public IIcon[] iconArray = new IIcon[subName.length];
	public ItemReadingGlasses()
	{
		super(BIBLIO_ARMOR_MATERIAL, 0, 0);
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		return armorType == 0;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		switch (stack.getItemDamage())
		{
		case 1:{return "bibliocraft:textures/armor/glasses_darktint.png";}
		case 2:{return "bibliocraft:textures/armor/glasses_mono.png";}
		default:{return "bibliocraft:textures/armor/glasses.png";}
		}
	}

    @Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
        String base = super.getUnlocalizedName();
        switch (itemStack.getItemDamage()) {
            case 1:
                return base + "." + subName[1];
            case 2:
                return base + "." + subName[2];
            default:
                return base + "." + subName[0];
        }
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list)
    {
    	if (tab instanceof BiblioTab)
    	{
			for(int i = 0; i < subName.length; ++i)
	        {
	            list.add(new ItemStack(this, 1, i));
	        }
    	}
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.iconArray[0] = register.registerIcon("bibliocraft:readingglasses");
        this.iconArray[1] = register.registerIcon("bibliocraft:tintedglasses");
        this.iconArray[2] = register.registerIcon("bibliocraft:monocle");
    }


    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        if (meta >= iconArray.length) {
            meta = 0;
        }
        return iconArray[meta];
    }

}
