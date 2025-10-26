package jds.bibliocraft.models;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelBell extends BiblioModelSimple
{

    public ModelBell() {
        super("bell");
    }

    public void renderBell() {
        this.model.renderPart("bell");
    }
}
