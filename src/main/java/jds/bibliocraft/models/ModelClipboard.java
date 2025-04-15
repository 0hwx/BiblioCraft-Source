package jds.bibliocraft.models;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraft.util.ResourceLocation;

public class ModelClipboard  // ISmartBlockModel, ISmartItemModel, IPerspectiveAwareModel
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/clipboard.obj"));


    public ModelClipboard() {}

    public void renderClipboard() {
        this.model.renderPart("clipboard");
    }
    public void renderbox() {
        this.model.renderPart("box");
    }

    public void renderbox1c() {
        this.model.renderPart("box1c");
    }
    public void renderbox2c() {
        this.model.renderPart("box2c");
    }
    public void renderbox3c() {
        this.model.renderPart("box3c");
    }
    public void renderbox4c() {
        this.model.renderPart("box4c");
    }
    public void renderbox5c() {
        this.model.renderPart("box5c");
    }
    public void renderbox6c() {
        this.model.renderPart("box6c");
    }
    public void renderbox7c() {
        this.model.renderPart("box7c");
    }
    public void renderbox8c() {
        this.model.renderPart("box8c");
    }
    public void renderbox9c() {
        this.model.renderPart("box9c");
    }

    public void renderbox1x() {
        this.model.renderPart("box1x");
    }
    public void renderbox2x() {
        this.model.renderPart("box2x");
    }
    public void renderbox3x() {
        this.model.renderPart("box3x");
    }
    public void renderbox4x() {
        this.model.renderPart("box4x");
    }
    public void renderbox5x() {
        this.model.renderPart("box5x");
    }
    public void renderbox6x() {
        this.model.renderPart("box6x");
    }
    public void renderbox7x() {
        this.model.renderPart("box7x");
    }
    public void renderbox8x() {
        this.model.renderPart("box8x");
    }
    public void renderbox9x() {
        this.model.renderPart("box9x");
    }
}
