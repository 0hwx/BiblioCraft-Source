package jds.bibliocraft.models;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelFramedChest
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/framedchest.obj"));

    public ModelFramedChest() {}

    public void renderFramedChest(String part) {
        this.model.renderPart(part);
    }
    public void renderFramedChest() {
        this.model.renderAll();
    }

    public void latch() {
        this.model.renderPart("latch");
    }

    // small chest
    public void SmallChest() {this.model.renderPart("small_chest");}
    public void latchItem() {
        this.model.renderPart("latch_item");
    }
    public void SmallLidItem() {
        this.model.renderPart("small_lid_item");
    }


    // large chest
    public void LargeChestLeft() {
        this.model.renderPart("large_chest_left");
    }
    public void LargeLidLeft() {
        this.model.renderPart("large_lid_left");
    }

    public void LargeChestRight() {
        this.model.renderPart("large_chest_right");
    }
    public void LargeLidRight() {
        this.model.renderPart("large_lid_right");
    }
}
