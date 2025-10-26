package jds.bibliocraft.rendering;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.models.ModelLabel;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityLabel;
import jds.bibliocraft.utils.BiblioWoodRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityLabelRenderer extends TileEntityBiblioRenderer implements IItemRenderer
{
    private ModelLabel modelShelf = new ModelLabel();
    private int meta;
    private int rotation;
	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick) {
        TileEntityLabel label = (TileEntityLabel) tile;
        if (label != null) {
            this.meta = label.getBlockMetadata();
            GL11.glPushMatrix();
            switch (this.getAngle()) {
                case WEST: // south
                    rotation = 0;
                    break;
                case SOUTH: // east
                    rotation = 90;
                    break;
                case EAST: // north
                    rotation = 180;
                    break;
                case NORTH: // west
                    rotation = 270;
                    break;
            }
            renderLabel(x + 0.5D, y, z + 0.5D, rotation, this.meta);
            GL11.glPopMatrix();
            ItemStack stackLeft = tile.getStackInSlot(1);
            ItemStack stackMiddle = tile.getStackInSlot(0);
            ItemStack stackRight = tile.getStackInSlot(2);

            if (stackLeft == null && stackRight == null) {
                renderSlotItem(stackMiddle, 0.5, 0.27, 0.06, 0.5f);
            } else {
                renderSlotItem(stackLeft, 0.36, 0.31, 0.05, 0.32f);
                renderSlotItem(stackMiddle, 0.5, 0.22, 0.06, 0.32f);
                renderSlotItem(stackRight, 0.64, 0.31, 0.05, 0.32f);
            }
        }
    }
        public void renderLabel(double x, double y, double z, double rotate, int meta) {
        GL11.glTranslated(x, y, z);
        GL11.glRotated(rotate, 0.0D, 1.0D, 0.0D);
        mc.renderEngine.bindTexture(BiblioWoodRegistry.getResource(meta));
        modelShelf.renderLabel();
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
            case INVENTORY:
                renderLabel(0, -0.5D, 0.3D,180.0D, item.getItemDamage());
                return;
            case EQUIPPED_FIRST_PERSON:
                renderLabel(0.5D, 0, 0.5D, 90.0D, item.getItemDamage());
                return;
            case EQUIPPED:
                renderLabel(0.5D, 0, 0.5D, 0, item.getItemDamage());
                return;
            default:
                renderLabel(0, -0.5D, -0.25D, 0, item.getItemDamage());
        }
    }
}
