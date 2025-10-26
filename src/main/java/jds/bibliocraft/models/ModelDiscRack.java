package jds.bibliocraft.models;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import jds.bibliocraft.blocks.BlockDiscRack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelDiscRack
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/discrack.obj"));


    public ModelDiscRack() {}

    public void renderDiscRack() {
        this.model.renderPart("discRack");
    }
}
