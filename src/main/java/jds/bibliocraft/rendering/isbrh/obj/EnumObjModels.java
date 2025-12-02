package jds.bibliocraft.rendering.isbrh.obj;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

public enum EnumObjModels {
    TOOL_RACK("tool_rack", "bibliocraft:models/block/tool_rack.obj"),


    SHELF("shelf", "bibliocraft:models/block/shelf.obj"),
    POTION_SHELF("potion_shelf","bibliocraft:models/blocks/potion_shelf.obj"),

    LABEL("label","bibliocraft:models/blocks/label.obj"),

    TABLE("table","bibliocraft:models/blocks/table.obj"),
    SEAT("seat","bibliocraft:models/blocks/seat.obj"),


    FANCY_WORKBENCH("fancy_workbench", "bibliocraft:models/block/fancyworkbench.obj"),
    FRAMED_CHEST("framed_chest", "bibliocraft:models/block/framedchest.obj"),
    BOOKCASE("bookcase", "bibliocraft:models/block/bookcase.obj"),


    TYPEWRITER("typewriter", "bibliocraft:models/blocks/typewriter.obj"),
    TYPESETTING("typesetting", "bibliocraft:models/blocks/typesettingtable.obj"),
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

