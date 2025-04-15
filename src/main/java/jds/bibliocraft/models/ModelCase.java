package jds.bibliocraft.models;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;


public class ModelCase
{
	private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/case.obj"));

    public ModelCase() {}

    public void renderCase() {
        this.model.renderAll();
    }

    public void rendercase_inside() {
        this.model.renderPart("case_inside");
    }

    public void renderlid_wood_open() {
        this.model.renderPart("case_lid_wood_open");
    }

    public void renderlid_latch_open() {
        this.model.renderPart("case_lid_latch_open");
    }

    public void renderlid_glass_open() {
        this.model.renderPart("case_lid_glass_open");
    }

    public void rendercase_lid_glass() {
        this.model.renderPart("case_lid_glass");
    }

    public void rendercase_lid_latch() {
        this.model.renderPart("case_lid_latch");
    }
    public void rendercase_lid_wood() {
        this.model.renderPart("case_lid_wood");
    }

    public void rendercase_lid_glass_item() {
        this.model.renderPart("case_lid_glass_item");
    }

    public void rendercase_lid_latch_item() {
        this.model.renderPart("case_lid_latch_item");
    }

    public void rendercase_lid_wood_item() {
        this.model.renderPart("case_lid_wood_item");
    }

    public void rendercase_bottom() {
        this.model.renderPart("case_bottom");
    }




}
