package jds.bibliocraft.models;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import scala.reflect.internal.Trees;


public class ModelFancyWorkbench
{
    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("bibliocraft", "models/block/fancyworkbench.obj"));

    public ModelFancyWorkbench() {}

    public void renderbench() {
        this.model.renderPart("bench");
    }
    public void rendertop() {
        this.model.renderPart("top");
    }

    public void rendersides() {
        this.model.renderPart("sides");
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
        }
    }

}
