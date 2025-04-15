package jds.bibliocraft.models;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelCookieJar
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/cookiejar.obj"));

    public ModelCookieJar() {}

    public void renderCookieJar() {
        this.model.renderAll();
    }

    public void renderlid() {
        this.model.renderPart("lid");
    }

    public void renderJar() {
        this.model.renderPart("jar");
    }

    public void renderCookie(int Count) {
        switch (Count) {
            case 0:
                this.model.renderPart("cookie001");
                break;
            case 1:
                this.model.renderPart("cookie002");
                break;
            case 2:
                this.model.renderPart("cookie003");
                break;
            case 3:
                this.model.renderPart("cookie004");
                break;
            case 4:
                this.model.renderPart("cookie005");
                break;
            case 5:
                this.model.renderPart("cookie006");
                break;
            case 6:
                this.model.renderPart("cookie007");
                break;
            case 7:
                this.model.renderPart("cookie008");
        }
    }


}
