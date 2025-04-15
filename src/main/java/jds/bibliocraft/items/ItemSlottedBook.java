package jds.bibliocraft.items;

import java.util.List;

import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.BlockLoader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemSlottedBook extends Item {
	public String playername;
	public static final String name = "SlottedBook";
	public static final ItemSlottedBook instance = new ItemSlottedBook();

	public ItemSlottedBook() {
		super();
		setCreativeTab(BlockLoader.biblioTab);
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setUnlocalizedName(name);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(BiblioCraft.instance, 101, player.worldObj, (int) player.posX, (int) player.posY,
					(int) player.posZ);
		}
		return stack;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		/*
		 * // TODO so broken, cant access playername
		 * List<EntityPlayer> p = player.playerEntities;
		 * if (p != null && p.size() > 0)
		 * {
		 * playername = I18n.format("redbook.by") +
		 * " "+p.get(0).getDisplayName().getFormattedText();
		 * }
		 * else
		 * {
		 * playername = "by Sir Hidington";
		 * }
		 */
		playername = "by Sir Hidington";
		NBTTagCompound bookTag = stack.getTagCompound();
		if (bookTag != null) {
			playername = bookTag.getString("authorName");
		} else {
			NBTTagCompound newbooktag = new NBTTagCompound();
			newbooktag.setString("authorName", playername);
			stack.setTagCompound(newbooktag);
		}
		tooltip.add(playername);
		super.addInformation(stack, player, tooltip, advanced);
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:slottedbook");
    }
}
