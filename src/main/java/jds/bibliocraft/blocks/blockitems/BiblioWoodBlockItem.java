package jds.bibliocraft.blocks.blockitems;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.blocks.BiblioWoodBlock.EnumWoodType;
import jds.bibliocraft.utils.BiblioWoodRegistry;
import net.minecraft.block.Block;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class BiblioWoodBlockItem extends ItemBlock
{
    private final Block block;

    public BiblioWoodBlockItem(Block block, String blockName)
    {
        super(block);
        this.block = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damageValue)
    {
        return damageValue;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int meta = stack.getItemDamage();
        BiblioWoodRegistry.WoodEntry entry = BiblioWoodRegistry.getWood(meta);
        if (entry != null) {
            String woodName = entry.name.toLowerCase().replace(" ", "_"); // WoodEntry should have getName()
            return "tile." + woodName + "." + block.getUnlocalizedName().substring(5);
        }
        return super.getUnlocalizedName(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if (stack.getItemDamage() == 6) // example for custom frame texture
        {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null)
            {
                tooltip.add(I18n.format("item.paneler.panels") + " \u00a7o" + nbt.getString("renderTexture"));
            }
        }
        tooltip = addAdditionalInformation(stack, playerIn, tooltip);
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    public List<String> addAdditionalInformation(ItemStack stack, EntityPlayer world, List<String> tooltip)
    {
        return tooltip;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tabs, List<ItemStack> list)
    {
        for (BiblioWoodRegistry.WoodEntry entry : BiblioWoodRegistry.getRegisteredWoods().values()) {
            list.add(new ItemStack(itemIn, 1, entry.meta)); // WoodEntry should track its meta
        }
    }
}
