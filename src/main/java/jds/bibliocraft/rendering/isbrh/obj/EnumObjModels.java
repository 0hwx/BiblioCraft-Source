package jds.bibliocraft.rendering.isbrh.obj;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

public enum EnumObjModels {
    TOOL_RACK("tool_rack", "bibliocraft:models/block/tool_rack.obj"),


    SHELF("shelf", "bibliocraft:models/block/shelf.obj"),
    POTION_SHELF("potion_shelf","bibliocraft:models/blocks/potion_shelf.obj"),

    LABEL("label","bibliocraft:models/blocks/label.obj"),


    FANCY_WORKBENCH("fancy_workbench", "bibliocraft:models/block/fancyworkbench.obj"),
    BOOKCASE("bookcase", "bibliocraft:models/block/bookcase.obj"),
    TABLE("table", "bibliocraft:models/block/table.obj"),


    TYPEWRITER("typewriter", "bibliocraft:models/blocks/typewriter.obj"),
    ;

    private final String name;
    private final ResourceLocation path;
    private WavefrontObject cachedModel;

    EnumObjModels(String name, String path) {
        this.name = name;
        this.path = new ResourceLocation(path);
    }

    public WavefrontObject getModel() {
        if (cachedModel == null)
            cachedModel = (WavefrontObject) AdvancedModelLoader.loadModel(path);
        return cachedModel;
    }

    public String getName() { return name; }

    public static EnumObjModels fromName(String name) {
        for (EnumObjModels model : values())
            if (model.name.equalsIgnoreCase(name)) return model;
        return null;
    }
}

