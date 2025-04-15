package jds.bibliocraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.BiblioTab;
import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;


public class ItemSeatBack2 extends Item
{
	private static final String[] subNames = {"OakSeatBack", "SpruceSeatBack", "BirchSeatBack", "JungleSeatBack", "AcaciaSeatBack", "OldOakSeatBack", "FramedSeatBack"};
	public static final String name = "seatback2";
	public static final ItemSeatBack2 instance = new ItemSeatBack2();

	public ItemSeatBack2()
	{
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setHasSubtypes(true);
        setMaxStackSize(64);
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list)
    {
    	if (tab instanceof BiblioTab)
    	{
	    	list.add(new ItemStack(this, 1, 0)); //oak
	    	list.add(new ItemStack(this, 1, 1)); //spruce
	    	list.add(new ItemStack(this, 1, 2)); //birch
	    	list.add(new ItemStack(this, 1, 3)); //jungle
	    	list.add(new ItemStack(this, 1, 4)); //acacia
	    	list.add(new ItemStack(this, 1, 5)); //bigoak
	    	list.add(new ItemStack(this, 1, 6));
    	}
     }

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
        String base = super.getUnlocalizedName();
        switch (itemStack.getItemDamage()) {
            case 1:
                return base + "." + subNames[1];
            case 2:
                return base + "." + subNames[2];
            case 3:
                return base + "." + subNames[3];
            case 4:
                return base + "." + subNames[4];
            case 5:
                return base + "." + subNames[5];
            case 6:
                return base + "." + subNames[6];
            default:
                return base + "." + subNames[0];
        }
	}

    @SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
    	if (stack.getItemDamage() == 6)
    	{
    		NBTTagCompound nbt = stack.getTagCompound();
    		if (nbt != null)
    		{
    			tooltip.add(I18n.format("item.paneler.panels")+" \u00a7o"+nbt.getString("renderTexture"));
    		}
    	}
    	super.addInformation(stack, player, tooltip, advanced);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:seatback2");
    }
}
