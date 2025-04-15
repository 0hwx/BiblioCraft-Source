package jds.bibliocraft.events;

import jds.bibliocraft.Config;
import jds.bibliocraft.items.ItemClipboard;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderClipboardText implements IItemRenderer {
    // thanks to Vazkii and https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/client/core/handler/RenderLexicon.java for figuring this out


    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = Minecraft.getMinecraft();
        NBTTagCompound cliptags = item.getTagCompound();
        if (cliptags != null) {
            int currentPage = cliptags.getInteger("currentPage");
            String pagenum = "page" + currentPage;
            NBTTagCompound pagetag = cliptags.getCompoundTag(pagenum);
            if (pagetag != null) {
                NBTTagCompound tasks = pagetag.getCompoundTag("tasks");
                if (tasks != null) { // && mc.thePlayer.getEquipProgress() == 0.0 && event.getSwingProgress() == 0.0) {
                    renderText(mc, pagetag.getString("title"), 0);
                    renderText(mc, tasks.getString("task1"), 1);
                    renderText(mc, tasks.getString("task2"), 2);
                    renderText(mc, tasks.getString("task3"), 3);
                    renderText(mc, tasks.getString("task4"), 4);
                    renderText(mc, tasks.getString("task5"), 5);
                    renderText(mc, tasks.getString("task6"), 6);
                    renderText(mc, tasks.getString("task7"), 7);
                    renderText(mc, tasks.getString("task8"), 8);
                    renderText(mc, tasks.getString("task9"), 9);
                }
            }
        }
    }

    private void renderText(Minecraft mc, String text, int verticlepos) {
        GL11.glPushMatrix();
        double handOffsett = 0;
        double titleOffset = 0;
        if (verticlepos == 0) {
            titleOffset = 0.03;
        }
        GL11.glTranslated(0.397 - titleOffset + handOffsett, -0.105 - (0.0326 * verticlepos), -0.64);
        GL11.glRotatef(180f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180f, 0.0f, 0.0f, 1.0f);
        GL11.glScaled(0.00225, 0.00225, 0.00225);
        mc.fontRenderer.drawString(text, 0, 0, 0x000000, false);
        GL11.glPopMatrix();
    }
}
