package jds.bibliocraft.rendering;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.Config;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.models.ModelDiscRack;
import jds.bibliocraft.tileentities.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityDiscRack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class TileEntityDiscRackRenderer extends TileEntityBiblioRenderer
{
	//EnumVertPosition vert = EnumVertPosition.WALL;
    private ModelDiscRack DiscRack = new ModelDiscRack();
	boolean rotated = false;
	boolean isBlock = false;

	@Override
	public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick)
	{
		TileEntityDiscRack rack = (TileEntityDiscRack)tile;
        if (rack != null) {
            GL11.glPushMatrix();
            renderDisckRack(this.globalX, this.globalY, this.globalZ, 1);
            GL11.glPopMatrix();
            rotated = rack.getWallRotation();
            for (int i = 0; i < tile.getSizeInventory(); i++) {
                ItemStack stack = tile.getStackInSlot(i);
                isBlock = Config.isBlock(stack);
                double adjustx = 0;
                double adjusty = 0;
                switch (getVertPosition()) {
                    case FLOOR: {
                        if (isBlock) {
                            adjustx = -0.2;
                            adjusty = 0.4;
                        }
                        if (getAngle() == ForgeDirection.NORTH || getAngle() == ForgeDirection.SOUTH) {
                            renderSlotItem(stack, 0.5, 0.0 + adjusty, 0.62 - (0.105 * i) + adjustx, 1.0f);
                        } else {
                            renderSlotItem(stack, 0.5, 0.0 + adjusty, -0.23 + (0.105 * i) + adjustx, 1.0f);
                        }
                        break;
                    }
                    case WALL: {
                        if (rotated) {
                            renderSlotItem(stack, 0.09 + +(0.105 * i), 0.42, 0.35, 1.0f);
                        } else {
                            renderSlotItem(stack, 0.5, 0.03 + (0.105 * i), 0.35, 1.0f);
                        }
                        break;
                    }
                    case CEILING: {
                        if (isBlock) {
                            adjustx = -0.23;
                            adjusty = 0.02;
                        }
                        if (getAngle() == ForgeDirection.NORTH || getAngle() == ForgeDirection.SOUTH) {
                            renderSlotItem(stack, 0.50, 0.57 + adjusty, 0.09 + (0.105 * i) + adjustx, 1.0f);
                        } else {
                            renderSlotItem(stack, 0.50, 0.57 + adjusty, 0.92 - (0.105 * i) + adjustx, 1.0f);
                        }
                        break;

                    }
                }
            }
        }
	}

    private void renderDisckRack(double x, double y, double z, double scale)
    {
        GL11.glTranslated(x, y, z);
        GL11.glScaled(scale, scale, scale);
        mc.renderEngine.bindTexture(CommonProxy.DiscRack);
        DiscRack.renderDiscRack();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        renderDisckRack(0.0F, 0.0F, 0.0F, 1F);
//        switch (type) {
//            case ENTITY:
//                renderDisckRack(1F, 0, 1F, 1F);
//                return;
//            case EQUIPPED:
//                renderDisckRack(-2.0F, 0.5F, -1.5F, 1F);
//                return;
//            case EQUIPPED_FIRST_PERSON:
//                renderDisckRack(-1.5F, 0.5F, -1.5F, 1F);
//                return;
//            case INVENTORY:
//                renderDisckRack(0.0F, 1.75F, 0.0F, 1F);
//        }
    }

	@Override
	public void additionalGLStuffForItemStack()
	{
		// need to get vert position
		// need to get rotation
		// test if block
		if (getVertPosition() == EnumVertPosition.CEILING)
		{
			if (isBlock)
			{
				GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
			}
		}
		else
		{
			if (!isBlock)
			{
				GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
			}
			else
			{
				GL11.glTranslated(0.0, 0.21, 0.0);
			}
			if (rotated && getVertPosition() == EnumVertPosition.WALL)
			{
				GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
				if (isBlock)
				{
					GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
					GL11.glTranslated(0.0, 0.21, 0.17);
				}
			}
		}
		if (getVertPosition() == EnumVertPosition.FLOOR)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslated(0.0, -0.3, 0.3);
		}

	}
}
