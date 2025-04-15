package jds.bibliocraft.models;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelBookcase
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/bookcase.obj"));


    public ModelBookcase() {}

    public void renderBookcase() {
        this.model.renderPart("bookcase");
    }

    public void renderbook(int Count) {
        switch (Count) {
            case 0:
                this.model.renderPart("book1");
                break;
            case 1:
                this.model.renderPart("book2");
                break;
            case 2:
                this.model.renderPart("book3");
                break;
            case 3:
                this.model.renderPart("book4");
                break;
            case 4:
                this.model.renderPart("book5");
                break;
            case 5:
                this.model.renderPart("book6");
                break;
            case 6:
                this.model.renderPart("book7");
                break;
            case 7:
                this.model.renderPart("book8");
                break;
            case 8:
                this.model.renderPart("book9");
                break;
            case 9:
                this.model.renderPart("book10");
                break;
            case 10:
                this.model.renderPart("book11");
                break;
            case 11:
                this.model.renderPart("book12");
                break;
            case 12:
                this.model.renderPart("book13");
                break;
            case 13:
                this.model.renderPart("book14");
                break;
            case 14:
                this.model.renderPart("book15");
                break;
            case 15:
                this.model.renderPart("book16");
                break;
        }
    }
}
