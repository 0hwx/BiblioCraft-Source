package jds.bibliocraft.rendering.tesr;

//import jds.bibliocraft.models.ModelArmorStand;
import jds.bibliocraft.models.ModelArmorStand;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;

import jds.bibliocraft.CommonProxy;
import jds.bibliocraft.entity.AbtractSteve;
import jds.bibliocraft.entity.ModelDummy;
import jds.bibliocraft.tileentities.TileEntityArmorStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityArmorStandRenderer extends TileEntitySpecialRenderer
{
	private AbstractClientPlayer steve;
	private RenderManager renderManager;
	private ModelDummy modelDummy= new ModelDummy();
    private ModelArmorStand armorStandModel = new ModelArmorStand();
    private int StandAngle;
    private int degreeAngle;



	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks)
	{   this.StandAngle = tile.getBlockMetadata();
        float scale = 0.6F;
        switch (this.StandAngle) {
            case 0:
                degreeAngle = 270;
                break;
            case 1:
                degreeAngle = 180;
                break;
            case 2:
                degreeAngle = 90;
                break;
            case 3:
                degreeAngle = 0;
                break;
        }
		if (steve == null)
		{
			steve = new AbtractSteve(tile.getWorldObj());
		}
        renderManager = RenderManager.instance;
//        renderManager.renderPosX = 0;
//        renderManager.renderPosY = 0;
//        renderManager.renderPosZ = 0;
//        steve.posX = 0;
//        steve.posY = 0;
//        steve.posZ = 0;
        TileEntityArmorStand stand = (TileEntityArmorStand)tile;




        if (tile != null && tile instanceof TileEntityArmorStand)
		{

//            if (stand.getIsBottomStand())
//			{
//                GL11.glPushMatrix();
//                GL11.glTranslated(x + (double)0.5F, y, z + (double)0.5F);
//                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
//                GL11.glTranslated((double)-0.5F, (double)0.0F, (double)-0.5F);
//                this.bindTexture(CommonProxy.ARMORSTAND_BLOCK);
//                this.armorStandModel.renderArmorStand();
//                GL11.glPopMatrix();
//            }
//            GL11.glPushMatrix();
//            GL11.glTranslated(x + (double)0.5F, y, z + (double)0.5F);
//            switch (stand.getAngleID()) {
//                case 0:
//                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
//                    break;
//                case 1:
//                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
//                case 2:
//                default:
//                    break;
//                case 3:
//                    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
//            }
//
//            GL11.glTranslated((double)-0.5F, (double)0.0F, (double)-0.5F);
//            this.bindTexture(CommonProxy.ARMORSTAND_BLOCK);
//            this.armorStandModel.renderArmorStand();
//            if (stand.getIsBottomStand())
//			{
//				float degreeAngle = 0.0f;
//				switch (stand.getAngle())
//				{
//					case SOUTH:
//					{
//						degreeAngle = 90.0f;
//						break;
//					}
//					case WEST:
//					{
//						degreeAngle = 180.0f;
//						break;
//					}
//					case NORTH:
//					{
//						degreeAngle = 270.0f;
//						break;
//					}
//					default:break;
//				}
                steve.inventory.armorInventory[3] = stand.getStackInSlot(0); // Set Helmet
                steve.inventory.armorInventory[2] = stand.getStackInSlot(1); // Set Chestplate
                steve.inventory.armorInventory[1] = stand.getStackInSlot(2); // Set Leggings
                steve.inventory.armorInventory[0] = stand.getStackInSlot(3); // Set Boots
//				steve.renderYawOffset = degreeAngle;
//				steve.rotationYawHead = degreeAngle;
                RenderHelper.enableStandardItemLighting();
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glTranslated(x + (double)0.5F, y, z + (double)0.5F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslated((double)-0.5F, (double)0.0F, (double)-0.5F);
                this.bindTexture(CommonProxy.TEST_BLOCK);
                this.armorStandModel.renderArmorStand();
		        double xPos = tile.xCoord + 0.5 - this.renderManager.viewerPosX;
		        double yPos = tile.yCoord + 0.07 - this.renderManager.viewerPosY;
		        double zPos = tile.zCoord + 0.5 - this.renderManager.viewerPosZ;
		        float yaw = degreeAngle;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				renderManager.func_147939_a(steve, xPos, yPos, zPos, yaw, 1.0f,false);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();

				// TODO the glint effect on the armor stand doesn't work yet.
//				GL11.glPushMatrix();
//				GL11.glTranslated(tile.xCoord + 0.5, tile.yCoord + 0.06, tile.zCoord + 0.5);
//				RenderHelper.enableStandardItemLighting();
//				bindTexture(CommonProxy.BLUEWOOL);
//				GL11.glScaled(20.0, 20.0, 20.0);
//				modelDummy.renderHead();
//				modelDummy.renderChest();
//				enchant(0);
//				enchant(1);
//				enchant(2);
//				GL11.glPopMatrix();
			}
	}

	public void enchant(int armorType)
	{
		float tickModifier = Minecraft.getSystemTime() % 3000L / 3000.0F * 48.0F;
		bindTexture(CommonProxy.GLINT_PNG);
        // Enable blending and set color for the glint effect
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);

        // Disable depth writing and set depth function for the glint effect
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDepthMask(false);
        for (int i = 0; i < 2; ++i)
        {
            // Disable lighting and set color for the shimmer
            RenderHelper.disableStandardItemLighting();
            GL11.glColor4f(0.5F * 0.76F, 0.25F * 0.76F, 0.8F * 0.76F, 1.0F);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);

            // Switch to texture matrix mode to manipulate the texture
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glLoadIdentity();

            // Calculate how much the effect moves based on the tick modifier
            float var23 = tickModifier * (0.001F + i * 0.003F) * 20.0F;
            float var24 = 0.33333334F;

            GL11.glScalef(var24, var24, var24);
            GL11.glRotatef(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.0F, var23, 0.0F);

            // Switch back to model view matrix mode
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            switch (armorType)
            {
            case 0:{modelDummy.renderHead(); break;}
            case 1:{modelDummy.renderChest(); break;}
            case 2:{modelDummy.renderLegs(); break;}
            case 3:{modelDummy.renderFeet(); break;}
            }
        }

        // Reset the color and matrix modes to default
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glDepthMask(true);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        // Re-enable lighting and blending after rendering the glint
        RenderHelper.enableStandardItemLighting();
        GL11.glDisable(GL11.GL_BLEND);

        // Reset the depth function back to normal
        GL11.glDepthFunc(GL11.GL_LEQUAL);
	}
}
