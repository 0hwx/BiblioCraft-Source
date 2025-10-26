package jds.bibliocraft.models;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class BiblioModelSimple {
    public IModelCustom model;


    public BiblioModelSimple(String modelFile) {
        this.model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/" + modelFile + ".obj"));
    }
}
