package jds.bibliocraft.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;

public class EnchantmentReading extends Enchantment
{
	public EnchantmentReading()
	{
		super(150,5, EnumEnchantmentType.armor_head);
		this.setName(StatCollector.translateToLocal("enchant.reading"));
//		this.setUnlocalizedName("bibliocraft.readingench");
	}

	@Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 15 * (enchantmentLevel - 1);
    }

	@Override
    public int getMaxEnchantability(int par1)
    {
		//System.out.println(par1);
        return super.getMinEnchantability(par1) + 50;
    }

	@Override
    public int getMaxLevel()
    {
        return 1;
    }

	@Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
		if (stack.getItem() instanceof ItemBook)
		{
			return true;
		}
		else
		{
			return false;
		}
    }

	@Override
	public String getName()
	{
		return StatCollector.translateToLocal("enchant.reading");
	}

}
