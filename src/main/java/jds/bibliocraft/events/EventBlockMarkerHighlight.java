package jds.bibliocraft.events;

import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.blocks.base.BiblioBlock;
import jds.bibliocraft.blocks.BlockBookcase;
import jds.bibliocraft.blocks.BlockDiscRack;
import jds.bibliocraft.blocks.BlockFurniturePaneler;
import jds.bibliocraft.blocks.BlockPotionShelf;
import jds.bibliocraft.blocks.BlockPrintingPress;
import jds.bibliocraft.blocks.BlockTypesettingTable;
import jds.bibliocraft.entity.EntityCatalogFX;
import jds.bibliocraft.helpers.BiblioSortingHelper;
import jds.bibliocraft.items.ItemReadingGlasses;
import jds.bibliocraft.items.ItemStockroomCatalog;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityArmorStand;
import jds.bibliocraft.tileentities.TileEntityBookcase;
import jds.bibliocraft.tileentities.TileEntityCase;
import jds.bibliocraft.tileentities.TileEntityDesk;
import jds.bibliocraft.tileentities.TileEntityDinnerPlate;
import jds.bibliocraft.tileentities.TileEntityDiscRack;
import jds.bibliocraft.tileentities.TileEntityFancyWorkbench;
import jds.bibliocraft.tileentities.TileEntityFramedChest;
import jds.bibliocraft.tileentities.TileEntityFurniturePaneler;
import jds.bibliocraft.tileentities.TileEntityLabel;
import jds.bibliocraft.tileentities.TileEntityPotionShelf;
import jds.bibliocraft.tileentities.TileEntityPrintPress;
import jds.bibliocraft.tileentities.TileEntityShelf;
import jds.bibliocraft.tileentities.TileEntitySwordPedestal;
import jds.bibliocraft.tileentities.TileEntityTable;
import jds.bibliocraft.tileentities.TileEntityToolRack;
import jds.bibliocraft.tileentities.TileEntityTypeMachine;
import jds.bibliocraft.tileentities.TileEntityTypewriter;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;


public class EventBlockMarkerHighlight
{
	private Minecraft mc = Minecraft.getMinecraft();
	private int counter = 0;
	private int particle = 0;

	@SubscribeEvent
	public void DrawBlockHighlightEvent(DrawBlockHighlightEvent event) {
        if (counter >= 80) {
            ItemStack playerhand = event.player.getHeldItem();
            if (playerhand != null) {
                if (playerhand.getItem() instanceof ItemStockroomCatalog) {

                    NBTTagCompound tags = playerhand.getTagCompound();
                    if (tags != null) {
                        NBTTagList invList = tags.getTagList("inventoryList", Constants.NBT.TAG_COMPOUND);
                        if (invList != null && invList.tagCount() > 0) {
                            for (int i = 0; i < invList.tagCount(); i++) {
                                NBTTagCompound invTag = invList.getCompoundTagAt(i);
                                if (invTag != null && invTag.hasKey("x") && invTag.hasKey("y") && invTag.hasKey("z")) {
                                    TileEntity tile = event.player.worldObj.getTileEntity(invTag.getInteger("x"), invTag.getInteger("y"), invTag.getInteger("z"));
                                    if (tile != null) {
                                        //System.out.println("particle");
                                        spawnParticle(event.player.worldObj, invTag.getInteger("x"), invTag.getInteger("y"), invTag.getInteger("z"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            counter = 0;
        } else {
            counter++;
        }

        ItemStack headArmor = event.player.inventory.armorItemInSlot(3);
        boolean hasText = false;
        if (canHeadArmorRead(headArmor)) {
            TileEntity te = event.player.worldObj.getTileEntity(event.target.blockX, event.target.blockY, event.target.blockZ);
                if (te != null && te instanceof BiblioTileEntity) {
                    ArrayList<String> display = new ArrayList<String>();
                    BiblioTileEntity tile = (BiblioTileEntity) te;
                    ForgeDirection sideHit = ForgeDirection.getOrientation(event.target.sideHit);
                    float hitX = (float) (event.target.hitVec.xCoord - event.target.blockX);
                    float hitY = (float) (event.target.hitVec.yCoord - event.target.blockY);
                    float hitZ = (float) (event.target.hitVec.zCoord - event.target.blockZ);
                    boolean isFront = BiblioBlock.isBackOfBlock(tile.getAngle(), sideHit);
                    int slot = -1;
                    if (tile instanceof TileEntityBookcase) {
                        if (isFront) {
                            int yCheck = (int) (hitY * 2);
                            slot = BlockBookcase.isWhatBook(tile.getAngle(), hitX, hitZ);
                            if (yCheck == 0)
                                slot += 8;
                        }
                    }
                    if (tile instanceof TileEntityShelf || tile instanceof TileEntityToolRack) {
                        slot = BiblioBlock.getSlotNumberFromClickon2x2block(tile.getAngle(), hitX, hitY, hitZ);
                    }
                    if (tile instanceof TileEntityFancyWorkbench) {
                        if (isFront && hitY > 0.22F && hitY < 0.74F) {
                            slot = BlockBookcase.isWhatBook(tile.getAngle(), hitX, hitZ);
                            slot++;
                        }
                    }
                    if (tile instanceof TileEntityDesk || tile instanceof TileEntityTable) {
                        if (sideHit == ForgeDirection.UP) {
                            slot = 0;
                        }
                    }
                    if (tile instanceof TileEntityCase || tile instanceof TileEntitySwordPedestal || tile instanceof TileEntityTypewriter) {
                        slot = 0;
                    }
                    if (tile instanceof TileEntityPotionShelf) {
                        slot = BlockPotionShelf.getPotionShelfSlot(tile.getAngle(), sideHit, hitX, hitY, hitZ);
                    }
                    if (tile instanceof TileEntityLabel) {
                        float aimpos = 0.0f;
                        if (tile.getStackInSlot(1) == null && tile.getStackInSlot(2) == null) {
                            slot = 0;
                        } else {
                            switch (tile.getAngle()) {
                                case SOUTH: {
                                    aimpos = hitZ;
                                    break;
                                }
                                case WEST: {
                                    aimpos = 1 - hitX;
                                    break;
                                }
                                case NORTH: {
                                    aimpos = 1 - hitZ;
                                    break;
                                }
                                case EAST: {
                                    aimpos = hitX;
                                    break;
                                }
                                default:
                                    break;
                            }
                            if (aimpos < 0.42f) {
                                slot = 1;
                            } else if (aimpos < 0.56f) {
                                slot = 0;
                            } else {
                                slot = 2;
                            }
                        }
                    }
                    if (tile instanceof TileEntityFramedChest) {
                        TileEntityFramedChest chest = (TileEntityFramedChest) tile;
                        if (chest != null)
                            slot = BiblioSortingHelper.getLargestStackSlotInList(BiblioSortingHelper.getStackForBuiltinLabel(chest));
                    }
                    if (tile instanceof TileEntityArmorStand) {
                        TileEntityArmorStand stand = (TileEntityArmorStand) tile;

                        if (stand.getIsBottomStand()) {
                            if (hitY > 0.5f) {
                                //pants
                                slot = 2;
                            } else {
                                //shoes
                                slot = 3;
                            }
                        } else {
                            tile = (BiblioTileEntity) event.player.worldObj.getTileEntity(event.target.blockX, event.target.blockY - 1, event.target.blockZ);
                            if (tile == null)
                                return;
                            if (hitY > 0.5f) {
                                //helmet
                                slot = 0;
                            } else {
                                //chest
                                slot = 1;
                            }
                        }
                    }
                    if (tile instanceof TileEntityTypeMachine) {
                        TileEntityTypeMachine type = (TileEntityTypeMachine) tile;
                        slot = BlockTypesettingTable.getSlot((TileEntityTypeMachine) tile, sideHit, hitX, hitZ);
                        if (type.getLevels() > 0 && (type.enchantedBookCheck() || type.atlasBookCheck()) && slot == 0) {
                            display.add(ChatFormatting.LIGHT_PURPLE + I18n.format("typesetting.requires") + " " + type.getLevels() + " " + I18n.format("typesetting.levels"));
                        } else {
                            display.add(I18n.format("typesetting.selectbook") + ": " + type.getBookname());
                        }

                    }
                    if (tile instanceof TileEntityPrintPress) {
                        slot = BlockPrintingPress.getSlot((TileEntityPrintPress) tile, sideHit, hitX, hitZ);
                    }
                    if (tile instanceof TileEntityDinnerPlate) {
                        if (tile.getStackInSlot(0) != null) {
                            slot = 0;
                        } else if (tile.getStackInSlot(1) != null) {
                            slot = 1;
                        } else if (tile.getStackInSlot(2) != null) {
                            slot = 2;
                        }
                    }
                    if (tile instanceof TileEntityDiscRack) {
                        slot = BlockDiscRack.getDiscSlot(hitX, hitY, hitZ, tile.getAngle(), tile.getVertPosition(), ((TileEntityDiscRack) tile).getWallRotation());
                    }
                    if (tile instanceof TileEntityFurniturePaneler) {
                        slot = BlockFurniturePaneler.checkTopClickedPlace(tile.getAngle(), hitX, hitY, hitZ);
                    }
                    if (slot >= 0 && slot < tile.getSizeInventory()) {
                        ItemStack stack = tile.getStackInSlot(slot);
                        if (stack != null && stack.getItem() != null) {

                            int stacksize = stack.stackSize;
                            if (stacksize > 1) {
                                display.add(stacksize + " " + stack.getDisplayName());
                            } else {
                                display.add(stack.getDisplayName());
                            }
                            if (stack.getItem() instanceof ItemRecord) {
                                ItemRecord record = (ItemRecord) stack.getItem();
                                if (record != null) {
                                    display.add(record.getRecordNameLocal());
                                }
                            }
                            NBTTagCompound tags = stack.getTagCompound();
                            if (tags != null) {
                                if (tags.hasKey("bookName")) {
                                    display.add(tags.getString("bookName"));
                                }
                                if (tags.hasKey("author")) {
                                    display.add("by " + tags.getString("author"));
                                }
                                if (tags.hasKey("ench") || tags.hasKey("StoredEnchantments")) {
                                    NBTTagList enchantments;
                                    if (tags.hasKey("ench")) {
                                        enchantments = tags.getTagList("ench", Constants.NBT.TAG_COMPOUND);
                                    } else {
                                        enchantments = tags.getTagList("StoredEnchantments", Constants.NBT.TAG_COMPOUND);
                                    }
                                    for (int i = 0; i < enchantments.tagCount(); i++) {
                                        NBTTagCompound etag = enchantments.getCompoundTagAt(i);
                                        if (etag != null) {
                                            short id = etag.getShort("id");
                                            short lvl = etag.getShort("lvl");
                                            for (Enchantment enchantment : Enchantment.enchantmentsList) {
                                                if (enchantment != null && enchantment.effectId == id) {
                                                    String name = enchantment.getTranslatedName(lvl);
                                                    if (name != null && name.length() > 0) {
                                                        display.add(ChatFormatting.LIGHT_PURPLE + name);
                                                    }
                                                    break; // Exit the loop once the enchantment is found //todo check this
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (display != null && !display.isEmpty()) {
                        //System.out.println("testq");
                        NBTTagCompound armorTags = headArmor.getTagCompound();
                        if (armorTags == null)
                            armorTags = new NBTTagCompound();
                        NBTTagList names = new NBTTagList();
                        for (int i = 0; i < display.size(); i++) {
                            names.appendTag(new NBTTagString(display.get(i)));
                        }
                        armorTags.setTag("text", names);
                        headArmor.setTagCompound(armorTags);
                        event.player.setCurrentItemOrArmor(4, headArmor); //todo check
//                    event.getPlayer().inventory.armorInventory.set(3, headArmor); //setInventorySlotContents(3, headArmor);
                        hasText = true;
                        //System.out.println("test");
                    }
                }
            }
            if (!hasText && headArmor != null) {
                NBTTagCompound armorTags = headArmor.getTagCompound();
                if (armorTags != null) {
                    NBTTagList names = new NBTTagList();
                    armorTags.setTag("text", names);
                    headArmor.setTagCompound(armorTags);
                    event.player.setCurrentItemOrArmor(4, headArmor);
//                event.getPlayer().inventory.armorInventory.set(3, headArmor);
                }
            }
    }

	private void spawnParticle(World world, double x, double y, double z)
	{
		//EntityCatalogFX fx = new EntityCatalogFX(world, x+0.5, y+0.5, z+0.5);
		double motX = 0.0, motY = 0.0, motZ = 0.0;
		double motion = 0.07;
		double altMotion = 0.01;
		switch (this.particle)
		{
			case 0:
			{
				motX = motion;
				motY = altMotion;
				motZ = altMotion;
				break;
			}
			case 1:
			{
				motX = -motion;
				motY = altMotion;
				motZ = altMotion;
				break;
			}
			case 2:
			{
				motX = altMotion;
				motY = motion;
				motZ = altMotion;
				break;
			}
			case 3:
			{
				motX = altMotion;
				motY = altMotion;
				motZ = motion;
				break;
			}
			case 4:
			{
				motX = altMotion;
				motY = altMotion;
				motZ = -motion;
				break;
			}
		}

		if (this.particle >= 4)
		{
			this.particle = 0;
		}
		else
		{
			this.particle++;
		}
		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityCatalogFX(world, x + 0.5, y + 0.5, z + 0.5, motX, motY, motZ));
	}

	public static boolean canHeadArmorRead(ItemStack stack)
	{
		boolean canRead = false;
		if (stack != null)
		{
			if (stack.getItem() instanceof ItemReadingGlasses)
			{
				canRead = true;
			}
			else
			{
				NBTTagCompound tags = stack.getTagCompound();
				if (tags != null && tags.hasKey("ench"))
				{
					NBTTagList enchlist = tags.getTagList("ench", Constants.NBT.TAG_COMPOUND);
					for (int i = 0; i < enchlist.tagCount(); i++)
					{
						NBTTagCompound etag = enchlist.getCompoundTagAt(i);
						if (etag != null && etag.hasKey("id"))
						{   short id = etag.getShort("id");
                            for (Enchantment enchantment : Enchantment.enchantmentsList) {
                                if (enchantment != null && enchantment.effectId == id) {
                                    // Check if the enchantment is the one you want (e.g., readingEnch)
                                    if (enchantment == BiblioCraft.readingEnch) {
                                        canRead = true;
                                    }
                                    break; // No need to check further once we find the match
                                }
                            }
						}
					}
				}
			}
		}
		return canRead;
	}
}
