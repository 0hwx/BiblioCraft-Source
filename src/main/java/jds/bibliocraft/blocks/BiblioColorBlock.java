package jds.bibliocraft.blocks;

import jds.bibliocraft.BlockLoader;
import jds.bibliocraft.helpers.EnumColor;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;


public abstract class BiblioColorBlock extends BiblioBlock
{
    public BiblioColorBlock(Material material, SoundType sound, String name)
    {
        super(material, sound, BlockLoader.biblioTab, name);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
        for (int x = 0; x < 16; x++) { // 16 for 16 colors
            subItems.add(new ItemStack(this, 1, x));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public void additionalPlacementCommands(BiblioTileEntity biblioTile, EntityLivingBase player) {
        // No additional placement commands for 1.7.10
    }

    @Override
    public ItemStack getPickBlockExtras(ItemStack stack, World world, int x, int y, int z) {
        return stack;
    }

    @Override
    public int getRenderType() {
        return 3; // Standard block rendering
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int meta = stack.getItemDamage();
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }
}
