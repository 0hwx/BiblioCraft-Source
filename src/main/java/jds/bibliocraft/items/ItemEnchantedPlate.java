package jds.bibliocraft.items;

import java.util.List;

import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.Config;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

public class ItemEnchantedPlate extends Item
{
	public static final String name = "EnchantedPlate";
	public static final ItemEnchantedPlate instance = new ItemEnchantedPlate();

	public ItemEnchantedPlate()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setMaxDamage(Config.enchPlateMaxUses);
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
		String nbt = I18n.format("plate.notValid");
		NBTTagList taglist = this.getEnchantmentTagList(stack);
		int tagscount = 0;
		if (taglist != null)
		{
			tagscount = taglist.tagCount();
		}
		if (tagscount > 0)
		{
			for (int x=0; x < taglist.tagCount(); x++)
			{
				short id = ((NBTTagCompound)taglist.getCompoundTagAt(x)).getShort("id");
				short lvl = ((NBTTagCompound)taglist.getCompoundTagAt(x)).getShort("lvl");

                if (id >= 0 && id < Enchantment.enchantmentsList.length) {
                    Enchantment enchantment = Enchantment.enchantmentsList[id];
                    if (enchantment != null) {
                        String ench = enchantment.getTranslatedName(lvl); // Get the translated name
                        tooltip.add(ench);
                    }
                }
			}
		}
		else
		{
			tooltip.add(nbt);
		}
    	super.addInformation(stack, playerIn, tooltip, advanced);
	}

    public NBTTagList getEnchantmentTagList(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("StoredEnchantments") ? (NBTTagList)stack.getTagCompound().getTag("StoredEnchantments") : new NBTTagList();
    }
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:enchplate");
    }
}
