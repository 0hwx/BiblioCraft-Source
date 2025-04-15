package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.blocks.BiblioWoodBlock.EnumWoodType;
import net.minecraft.block.Block;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class BiblioWoodBlockItem extends ItemBlock
{
	private String[] names;

	public BiblioWoodBlockItem(Block block, String blockName)
	{
		super(block);
		setNames(blockName);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damageValue)
	{
		return damageValue;
	}

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return names[itemstack.getItemDamage()];
    }

    private void setNames(String blockName)
    {
    	names = new String[EnumWoodType.values().length];
    	for (int i = 0; i < names.length; i++)
    	{
    		names[i] = EnumWoodType.getEnum(i).getName() + blockName;
    	}
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
    	if (stack.getItemDamage() == 6)
    	{
    		NBTTagCompound nbt = stack.getTagCompound();
    		if (nbt != null)
    		{
    			tooltip.add(I18n.format("item.paneler.panels")+" \u00a7o"+nbt.getString("renderTexture"));
    		}
    	}
    	tooltip = addAdditionalInformation(stack, playerIn, tooltip);
    	super.addInformation(stack, playerIn, tooltip, advanced);
    }

    public List<String> addAdditionalInformation(ItemStack stack, EntityPlayer world, List<String> tooltip)
    {
    	return tooltip;
    }
}
