package jds.bibliocraft.models;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;

/**
 * Enum-based model registry for all OBJ models used by ObjBuilder.
 * Each constant defines a model name and its resource path.
 *
 * Example:
 *   WavefrontObject rack = EnumObjModels.TOOL_RACK.getModel();
 */
public enum BiblioModels {

    TOOL_RACK("tool_rack", "bibliocraft:models/block/tool_rack.obj"),

    TABLE("table", "bibliocraft:models/block/table.obj"),
    BOOKCASE("bookcase", "bibliocraft:models/block/bookcase.obj"),
    DESK("desk", "bibliocraft:models/block/desk.obj"),
    LABEL("label", "bibliocraft:models/block/label.obj");

    private final String name;
    private final ResourceLocation resource;
    private WavefrontObject cachedModel;

    BiblioModels(String name, String path) {
        this.name = name;
        this.resource = new ResourceLocation(path);
    }

    /**
     * Loads and caches the model if not already loaded.
     * @return WavefrontObject instance for this model.
     */
    public WavefrontObject getModel() {
        if (cachedModel == null) {
            IModelCustom model = AdvancedModelLoader.loadModel(resource);
            if (model instanceof WavefrontObject) {
                cachedModel = (WavefrontObject) model;
            } else {
                throw new IllegalStateException("Model at " + resource + " is not a WavefrontObject!");
            }
        }
        return cachedModel;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getResource() {
        return resource;
    }

    /**
     * Retrieves an EnumObjModels constant by name (case-insensitive).
     * @param name The model name string (e.g., "tool_rack").
     * @return EnumObjModels instance or null if not found.
     */
    public static BiblioModels fromName(String name) {
        for (BiblioModels model : values()) {
            if (model.name.equalsIgnoreCase(name)) {
                return model;
            }
        }
        return null;
    }
}
