package jds.bibliocraft.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jds.bibliocraft.BiblioCraft;
import jds.bibliocraft.Config;
import jds.bibliocraft.items.ItemDeathCompass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;


public class EventSpawn
{

	private EventDeathDrop death = BiblioCraft.eDeathDrop;
	public EventSpawn()
	{

	}

	@SubscribeEvent
	public void onSpawn(EntityJoinWorldEvent event)  //LivingSpawnEvent
	{
		if (Config.enableDeathCompass)
		{
			if (event.entity instanceof EntityPlayer && death.lplayerNames.size() > 0 && event.entity.isEntityAlive())
			{
				String eventPlayerName = event.entity.getCommandSenderName();
				EntityPlayer player = (EntityPlayer)event.entity;
				for (int i = 0; i < death.lplayerNames.size(); i++)
				{
					String deathlyName = (String)death.lplayerNames.get(i);
					if (deathlyName.contentEquals(eventPlayerName))
					{
						ItemStack deathCompass = new ItemStack(ItemDeathCompass.instance, 1, 0);
						NBTTagCompound compTags = new NBTTagCompound();
						int x = (Integer)death.ldeathX.get(i);
						int y = (Integer)death.ldeathZ.get(i);
						compTags.setInteger("XCoord", x);
						compTags.setInteger("ZCoord", y);
						compTags.setString("WaypointName", "Location of Death");
						deathCompass.setTagCompound(compTags);

						addToPlayerInventory(deathCompass, player);
						death.ldeathX.remove(i);
						death.ldeathZ.remove(i);
						death.lplayerNames.remove(i);
						break;
					}
				}
			}
		}
	}

	private void addToPlayerInventory(ItemStack stack, EntityPlayer player)
	{
		InventoryPlayer inv = player.inventory;
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			if (inv.getStackInSlot(i) == null)
			{
				player.inventory.setInventorySlotContents(i, stack);
				// since sometimes this doesnt operate correctly, I think I should try to send a packet with the i slot and apply clientside too (maybe?) Seems to be working now, revisit if issues arise
				break;
			}
		}

	}
}
