package jds.bibliocraft.rendering;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelCookieJar;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityCookieJar;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityCookieJarRenderer extends TileEntityBiblioRenderer implements IItemRenderer {
   private ModelCookieJar cookieJar = new ModelCookieJar();
   private int Count = 0;


    @Override
    public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick) {
        TileEntityCookieJar cookiejar = (TileEntityCookieJar)tile;
        if (cookiejar != null) {
            this.Count = cookiejar.getCookies();
            GL11.glPushMatrix();
            renderCookieJar(x + (double) 1.0F, y, z + (double) 1.0F,1, this.Count);
            GL11.glPopMatrix();
        }
    }

    public void renderCookieJar(double x, double y, double z , double Scale, int count) {
        GL11.glTranslated(x, y, z);
        GL11.glScaled(Scale, Scale, Scale);
        mc.renderEngine.bindTexture(CommonProxy.IRON);
        cookieJar.renderlid();
        mc.renderEngine.bindTexture(CommonProxy.GLASS);
        cookieJar.renderJar();
        mc.renderEngine.bindTexture(CommonProxy.COOKIE);
        for (int i = 0; i < count; i++) {
            cookieJar.renderCookie(i);
        }
    }
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY:
                renderCookieJar(0.6F, -0.5F, 0.6F,1.2F, 8);
                return;
            case EQUIPPED:
                renderCookieJar(1.0F, 0.1F, 1.0F,1.2F, 8);
                return;
            case EQUIPPED_FIRST_PERSON:
                renderCookieJar(0.5F, 0.35F, 1.2F,1.2F, 8);
                return;
            case INVENTORY:
                renderCookieJar(0.0F, -1.0F, 0.0F,1.2F, 8);
        }
    }
}
