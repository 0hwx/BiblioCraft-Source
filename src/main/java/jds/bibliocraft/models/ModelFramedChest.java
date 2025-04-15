package jds.bibliocraft.models;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelFramedChest
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/framedchest.obj"));

    public ModelFramedChest() {}

    public void renderFramedChest() {
        this.model.renderAll();
    }
}
