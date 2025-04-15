package jds.bibliocraft.models;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelPotionShelf
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/potionshelf.obj"));

    public ModelPotionShelf() {}

    public void renderPotionShelf() {
        this.model.renderAll();
    }
}
