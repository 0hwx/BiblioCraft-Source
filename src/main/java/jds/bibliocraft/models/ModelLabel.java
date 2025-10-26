package jds.bibliocraft.models;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelLabel
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/label.obj"));

    public ModelLabel() {}

    public void renderLabel() {
        this.model.renderPart("label");
    }
}
