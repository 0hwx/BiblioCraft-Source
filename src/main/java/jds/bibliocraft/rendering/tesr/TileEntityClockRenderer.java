package jds.bibliocraft.rendering.tesr;


import jds.bibliocraft.tileentities.base.BiblioTileEntity;
import jds.bibliocraft.tileentities.TileEntityClock;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityClockRenderer extends TileEntityBiblioRenderer {
    private ResourceLocation modelLocation = new ResourceLocation("bibliocraft:textures/models/clock.png");

    @Override
    public void renderTileEntityAt(BiblioTileEntity tile, double x, double y, double z, float tick) {
        TileEntityClock clock = (TileEntityClock) tile;

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glRotatef(getRotation(clock), 0.0F, 1.0F, 0.0F);

        bindTexture(modelLocation);

        Tessellator tessellator = Tessellator.instance;

        // Render clock face
        GL11.glPushMatrix();
        renderClockFace(tessellator, clock);
        GL11.glPopMatrix();

        // Render clock hands
        GL11.glPushMatrix();
        renderClockHands(tessellator, clock);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    private float getRotation(TileEntityClock clock) {
        switch (clock.getBlockMetadata()) {
            case 2:
                return 180.0F;
            case 3:
                return 0.0F;
            case 4:
                return 90.0F;
            case 5:
                return -90.0F;
            default:
                return 0.0F;
        }
    }

    private void renderClockFace(Tessellator tessellator, TileEntityClock clock) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, -0.5F);
        tessellator.startDrawingQuads();
        // Add vertices for the clock face
        tessellator.addVertexWithUV(-0.5, -0.5, 0, 0, 1);
        tessellator.addVertexWithUV(0.5, -0.5, 0, 1, 1);
        tessellator.addVertexWithUV(0.5, 0.5, 0, 1, 0);
        tessellator.addVertexWithUV(-0.5, 0.5, 0, 0, 0);
        tessellator.draw();
        GL11.glPopMatrix();
    }

    private void renderClockHands(Tessellator tessellator, TileEntityClock clock) {
        GL11.glPushMatrix();
        GL11.glRotatef(clock.hourCount, 0.0F, 0.0F, 1.0F);
        tessellator.startDrawingQuads();
        // Add vertices for the hour hand
        tessellator.addVertexWithUV(-0.05, -0.05, 0, 0, 1);
        tessellator.addVertexWithUV(0.05, -0.05, 0, 1, 1);
        tessellator.addVertexWithUV(0.05, 0.3, 0, 1, 0);
        tessellator.addVertexWithUV(-0.05, 0.3, 0, 0, 0);
        tessellator.draw();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glRotatef(clock.secondCount, 0.0F, 0.0F, 1.0F);
        tessellator.startDrawingQuads();
        // Add vertices for the minute hand
        tessellator.addVertexWithUV(-0.03, -0.03, 0, 0, 1);
        tessellator.addVertexWithUV(0.03, -0.03, 0, 1, 1);
        tessellator.addVertexWithUV(0.03, 0.4, 0, 1, 0);
        tessellator.addVertexWithUV(-0.03, 0.4, 0, 0, 0);
        tessellator.draw();
        GL11.glPopMatrix();
    }
}
