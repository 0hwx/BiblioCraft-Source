package jds.bibliocraft.entity;

import jds.bibliocraft.CommonProxy;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntitySeatRenderer extends Render
{

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return CommonProxy.PLANKSOAK;
    }

    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks)
    {

    }

}
