package jds.bibliocraft.models;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelToolRack
{
    public static IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/toolrack.obj"));

	public ModelToolRack() {}

    public void renderbase() {
        this.model.renderPart("base");
    }
    public void rendernubs() {
        this.model.renderPart("nubs");
    }
}
