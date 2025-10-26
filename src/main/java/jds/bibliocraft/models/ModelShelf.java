package jds.bibliocraft.models;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class ModelShelf
{
    public WavefrontObject model = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/shelf.obj"));

    public ModelShelf() {}

    public void renderShelf() {
        this.model.renderPart("shelf");
    }
    public void renderShelfTop() {
        this.model.renderPart("shelftop");
    }
}
