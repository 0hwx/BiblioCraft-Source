package jds.bibliocraft.rendering.tesr;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import jds.bibliocraft.helpers.EnumShiftPosition;
import jds.bibliocraft.helpers.EnumVertPosition;
import jds.bibliocraft.items.ItemClipboard;
import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;


public abstract class TileEntityBiblioRenderer extends TileEntitySpecialRenderer implements IItemRenderer
{
	private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
	Minecraft mc = Minecraft.getMinecraft();
	private ForgeDirection angle = ForgeDirection.NORTH;
	private EnumVertPosition vert = EnumVertPosition.FLOOR;
	private EnumShiftPosition shift = EnumShiftPosition.NO_SHIFT;
	public float xshift;
	public float zshift;
	public int degreeAngle;
	public double globalX;
	public double globalY;
	public double globalZ;

	private RenderItem itemRenderer;
	private RenderManager renderManager = RenderManager.instance;

	@Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
	{
		this.globalX = x;
		this.globalY = y;
		this.globalZ = z;
		BiblioTileEntity tile = (BiblioTileEntity)tileEntity;
		if (tile != null)
		{
			this.angle = tile.getAngle();
			this.vert = tile.getVertPosition();
			this.shift = tile.getShiftPosition();
		}
		xshift = 0.0f;
		zshift = 0.0f;
	    if (this.itemRenderer == null)
	    {
            itemRenderer = new RenderItem() {

                @Override
                public byte getMiniBlockCount(ItemStack stack, byte original) {
                    return 1;
                }

                @Override
                public boolean shouldBob() {
                    return false;
                }

                @Override
                public boolean shouldSpreadItems() {
                    return false;
                }
            };
            this.itemRenderer.setRenderManager(this.renderManager);
	    }
	    float halfShift = 0.25f;
	    float fullShift = 0.5f;
		switch (angle)
		{
			case SOUTH:
			{
				degreeAngle = 270;
				xshift = 1.0f;
				zshift = 0.0f;
				if (shift == EnumShiftPosition.FULL_SHIFT)
				{
					xshift += -fullShift;
					zshift += 0;
				}
				if (shift == EnumShiftPosition.HALF_SHIFT)
				{
					xshift += -halfShift;
					zshift += 0;
				}
				break;
			}
			case WEST:
			{
				degreeAngle = 180;
				xshift = 1.0f;
				zshift = 1.0f;
				if (shift == EnumShiftPosition.FULL_SHIFT)
				{
					xshift += 0.0f;
					zshift += -fullShift;
				}
				if (shift == EnumShiftPosition.HALF_SHIFT)
				{
					xshift += 0.0f;
					zshift += -halfShift;
				}
				break;
			}
			case NORTH:
			{
				degreeAngle = 90;
				xshift = 0.0f;
				zshift = 1.0f;
				if (shift == EnumShiftPosition.FULL_SHIFT)
				{
					xshift += fullShift;
					zshift += 0;
				}
				if (shift == EnumShiftPosition.HALF_SHIFT)
				{
					xshift += halfShift;
					zshift += 0;
				}
				break;
			}
			case EAST:
			{
				degreeAngle = 0;
				xshift = 0.0f;
				zshift = 0.0f;
				if (shift == EnumShiftPosition.FULL_SHIFT)
				{
					xshift += 0.0f;
					zshift += fullShift;
				}
				if (shift == EnumShiftPosition.HALF_SHIFT)
				{
					xshift += 0.0f;
					zshift += halfShift;
				}
				break;
			}
			default:break;
		}
        renderTileEntityAt(tile, x, y, z, partialTicks);
	}

	public abstract void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick);

	public ForgeDirection getAngle()
	{
		return this.angle;
	}

	public EnumVertPosition getVertPosition()
	{
		return this.vert;
	}

	public EnumShiftPosition getShiftPosition()
	{
		return this.shift;
	}

    public void renderSlotItem(ItemStack stack, double x, double y, double z, float scale)
    {
        renderSlotItem(stack, x, y, z, scale, 0);
    }

    public void renderSlotItem(ItemStack stack , double x, double y, double z, float scale, float rotate)
    {
        if (stack != null && stack.stackSize != 0)
        {
            EntityItem entityItem = new EntityItem(null, 0.0D, 0.0D, 0.0D, stack);
            entityItem.hoverStart = 0.0F;
            switch (this.angle)
            {
                case SOUTH:
                {
                    double tx = x;
                    x = -z;
                    z = tx;
                    break;
                }
                case WEST:
                {
                    x *= -1;
                    z *= -1;
                    break;
                }
                case NORTH:
                {
                    double tx = x;
                    x = z;
                    z = -tx;
                    break;
                }
                case EAST:
                {
                    break;
                }
                default: break;
            }
            GL11.glPushMatrix();
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            GL11.glTranslated(this.globalX + x + xshift, this.globalY + y, this.globalZ + z + zshift);
            GL11.glRotatef(degreeAngle + 180.0F, 0.0F, 1.0F, 0.0F);
            additionalGLStuffForItemStack();
            Block testBlock = Block.getBlockFromItem(stack.getItem());
            if (isRotatedBlock(stack.getUnlocalizedName()))
            {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (stack.getItem() instanceof ItemClipboard)
            {
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslated(0.4, 0.05, 0.0);
            }

            if (testBlock == null)
            {
                // is item
                scale *= 0.7f;
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(rotate, 1.0F, 0.0F, 0.0F);
            this.itemRenderer.doRender(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            GL11.glPopMatrix();
        }
    }

	private final String itemsThatNeedRotating[] = {
			"swordpedestal", "Typewriter", "BiblioCraft:PaintingPress",
			"BiblioCraft:PrintingPress", "BiblioCraft:TypesettingTable", "Lantern", "Lamp", "item.tconstruct"};

	private boolean isRotatedBlock(String name)
	{
		boolean value = false;
		for (int i = 0; i < itemsThatNeedRotating.length; i++)
		{
			if (name.contains(itemsThatNeedRotating[i]))
			{
				value = true;
			}
		}
		return value;
	}

	public void additionalGLStuffForItemStack()
	{

	}

    public void renderItemMap(ItemStack stack, float x, float y, float z, float scale)
    {
    	GL11.glPushMatrix();
    	GL11.glTranslated(this.globalX + x, this.globalY + y + 1.02f, this.globalZ + z);
    	GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
    	scale *= 0.0063;
    	GL11.glScalef(scale, scale, scale);
        this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        Tessellator tessellator = Tessellator.instance;
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-7.0D, 135.0D, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(135.0D, 135.0D, 0.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(135.0D, -7.0D, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(-7.0D, -7.0D, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        MapData mapdata =Items.filled_map.getMapData(stack, this.renderManager.worldObj);
        if (mapdata != null)
        {
            this.mc.entityRenderer.getMapItemRenderer().func_148250_a(mapdata, false);
        }
        GL11.glPopMatrix();
    }


	public String getTextureString(ResourceLocation location)
	{
		return location.toString();
	}

	public void renderText(String text, double xAdjust, double yAdjust, double zAdjust)
	{
		FontRenderer fontRender = this.func_147498_b();
		float offsetx = 0.0f;
		float offsetz = 0.0f;
		switch (this.getAngle())
		{
			case NORTH:{offsetx = 0.0116f; break;}
			case SOUTH:{offsetx = -0.0116f;  break; }
			case WEST:{offsetz = -0.0116f; break;}
			case EAST:{offsetz = 0.0116f; break;}
			default: break;
		}
		GL11.glPushMatrix();
		GL11.glTranslated(this.globalX + 0.5 + offsetx, this.globalY, this.globalZ + 0.5 + offsetz);

		switch (this.getAngle())
		{
			case SOUTH:{GL11.glRotatef(180, 0.0f, 1.0f, 0.0f); break; }
			case WEST:{GL11.glRotatef(90, 0.0f, 1.0f, 0.0f); break; }
			case EAST:{GL11.glRotatef(-90, 0.0f, 1.0f, 0.0f); break;}
			default: break;
		}

		GL11.glTranslated(-0.5 + xAdjust, yAdjust, zAdjust);
		GL11.glDepthMask(false);
		GL11.glScalef(0.0045F, 0.0045F, 0.0045F);
		GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		switch (this.shift)
		{
			case HALF_SHIFT:
			{
				GL11.glTranslated(0.0, 0.0, -95.0);
				break;
			}
			case FULL_SHIFT:
			{
				GL11.glTranslated(0.0, 0.0, -205.0);
				break;
			}
			default: break;
		}
		additionalGLStuffForText();
        GL11.glNormal3f(0.0F, 0.0F, -0.010416667F);
		fontRender.drawString(text, 0, 0, 0);
		GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	public void additionalGLStuffForText()
	{

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
    }
}
