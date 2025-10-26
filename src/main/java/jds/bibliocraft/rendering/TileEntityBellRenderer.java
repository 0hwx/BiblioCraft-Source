package jds.bibliocraft.rendering;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelBell;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityBell;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityBellRenderer extends TileEntityBiblioRenderer {
    private ModelBell bell = new ModelBell();

    @Override
    public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick) {
        TileEntityBell bell = (TileEntityBell) tile;
        if (bell != null) {
            GL11.glPushMatrix();
            renderbell(x, y, z , 1);
            GL11.glPopMatrix();
        }
    }

    public void renderbell(double x, double y, double z, double Scale) {
        GL11.glTranslated(x, y, z);
        GL11.glScaled(Scale, Scale, Scale);
        mc.renderEngine.bindTexture(CommonProxy.Bell);
        bell.renderBell();
    }


    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY:
                renderbell(1F, 0, 1F, 5F);
                return;
            case EQUIPPED:
                renderbell(-2.0F, 0.5F, -1.5F, 5F);
                return;
            case EQUIPPED_FIRST_PERSON:
                renderbell(-1.5F, 0.5F, -1.5F, 5F);
                return;
            case INVENTORY:
                renderbell(0.0F, 1.75F, 0.0F, 5F);
        }
    }
}
