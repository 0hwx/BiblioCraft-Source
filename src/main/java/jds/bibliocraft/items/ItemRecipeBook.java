package jds.bibliocraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jds.bibliocraft.Config;
import jds.bibliocraft.network.BiblioNetworking;
import jds.bibliocraft.network.packet.client.BiblioOpenBook;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemRecipeBook extends Item
{
	public static final String name = "RecipeBook";
	public static final ItemRecipeBook instance = new ItemRecipeBook();

	private int[] ingredientCounts = new int[9];
	private String[] ingredientNames = new String[9];
	private int ingredientsTest;
	public boolean showText = false;
	public boolean showTextChanged = false;
	public String showTextString = "";

	public ItemRecipeBook()
	{
		super();
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setUnlocalizedName(name);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			BiblioNetworking.INSTANCE.sendTo(new BiblioOpenBook(Config.enableRecipeBookCrafting), (EntityPlayerMP) player);
			// ByteBuf buffer = Unpooled.buffer();
			// buffer.writeBoolean(Config.enableRecipeBookCrafting);
			// BiblioCraft.ch_BiblioOpenBook.sendTo(new FMLProxyPacket(new PacketBuffer(buffer), "BiblioOpenBook"), (EntityPlayerMP) player);
		}
		return stack;
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt != null)
		{
			return nbt.getBoolean("signed");
		}
		return false;
	}
    /*
	@SideOnly(Side.CLIENT)
    public void openRecipeBookGUI(ItemStack stack, int slot)
    {
		Minecraft.getMinecraft().displayGuiScreen(new GuiRecipeBook(stack, false, 0, 0, 0, slot));
    }
    */
	@SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		compareingredients(stack);
		tooltip.add(I18n.format("book.ingredients"));
		for (int i = 0; i<this.ingredientCounts.length; i++)
		{
			ingredientsTest = this.ingredientCounts[i];
			if (this.ingredientCounts[i] != 0 && !this.ingredientNames[i].contains("Air"))
			{
				tooltip.add(this.ingredientCounts[i]+"x "+this.ingredientNames[i]);
			}
		}
    	super.addInformation(stack, playerIn, tooltip, advanced);
	}

	public void updateFromPacket(String displayString)
	{
		this.showText = true;
		this.showTextChanged = true;
		this.showTextString = displayString;
	}

	public void compareingredients(ItemStack stack)
	{
		ingredientCounts = new int[9];
		ingredientNames = new String[9];
		if (stack.getItem() instanceof ItemRecipeBook)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt != null)
			{
				NBTTagList tagList = nbt.getTagList("grid", Constants.NBT.TAG_COMPOUND);
				for (int i = 0; i < 9; i++)
				{
					NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
					byte slot = tag.getByte("Slot");
					//System.out.println(slot);
					if (slot >= 0 && slot < 9)
					{
						ItemStack nbtStack = ItemStack.loadItemStackFromNBT(tag);
						if (nbtStack != null)
						{
							int n = 0;
							boolean complete = false;
							boolean havematch = false;
							for (int m = 0; m < this.ingredientNames.length; m++)
							{
								if (this.ingredientNames[m] != null)
								{
									if (this.ingredientNames[m].matches(nbtStack.getDisplayName()))
									{
										n = m;
										havematch = true;
									}
								}
							}

							if (havematch)
							{
								this.ingredientCounts[n] += 1;
							}
							else
							{
								while (!complete)
								{
									if (this.ingredientCounts[n] == 0)
									{
										this.ingredientCounts[n] += 1;
										this.ingredientNames[n] = nbtStack.getDisplayName();
										complete = true;
									}
									else
									{
										if (n < 8)
										{
											n++;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon("bibliocraft:recipebook");
    }
}
