package jds.bibliocraft.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import jds.bibliocraft.helpers.WoodRegistryEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

/**
 * Small helper that stores shaped recipe definitions (pattern + mapping + output + woodEntry)
 * and registers the dynamic RecipeDynamicFramedWood with GameRegistry.
 *
 * It also exposes entries for NEI to read (pattern, items, baseOutput, entry).
 */
public class FramedRecipeRegistry {
    public static class RecipeDef {
        public final int width;
        public final int height;
        public final Object[] input;
        public final ItemStack baseOutput;
        public final WoodRegistryEntry entry;

        public RecipeDef(int w, int h, Object[] input, ItemStack out, WoodRegistryEntry entry) {
            this.width = w;
            this.height = h;
            this.input = input;
            this.baseOutput = out;
            this.entry = entry;
        }
    }

    // All registered defs (used by NEI handler)
    public static final List<RecipeDef> defs = new ArrayList<RecipeDef>();

    /**
     * Build an inputs[] from shape strings and a char->object map (like your previous varargs).
     * pattern: e.g. new String[]{"ITF","SBS","SSS"}
     * charMap: mapping from Character to ingredient (ItemStack/Item/Block/String)
     */
    public static RecipeDef defineAndRegisterRecipe(ItemStack baseOutput, WoodRegistryEntry entry, String[] pattern, Map<Character, Object> charMap) {
        int height = pattern.length;
        int width = pattern[0].length();
        Object[] inputs = new Object[width * height];

        for (int y = 0; y < height; y++) {
            String row = pattern[y];
            if (row.length() != width) throw new IllegalArgumentException("Inconsistent pattern width");
            for (int x = 0; x < width; x++) {
                char c = row.charAt(x);
                if (charMap.containsKey(c)) inputs[y * width + x] = charMap.get(c);
                else inputs[y * width + x] = null;
            }
        }

        // create dynamic recipe and register it
        RecipeBiblioFramedShaped recipe = (RecipeBiblioFramedShaped) RecipeBiblioFramedShaped.addShapedWoodRecipe(baseOutput, entry, toVarargs(pattern, charMap));
        GameRegistry.addRecipe(recipe);

        RecipeDef def = new RecipeDef(width, height, inputs, baseOutput, entry);
        defs.add(def);

        return def;
    }

    /**
     * Build and register a dynamic SHAPELESS recipe.
     * ingredients: e.g. new Object[]{item1, item2, "oreDictName"}
     */
    public static RecipeDef defineAndRegisterRecipe(ItemStack baseOutput, WoodRegistryEntry entry, Object[] ingredients) {

        // Create the dynamic shapeless recipe
        // We MUST assume you created the 'RecipeBiblioFramedShapeless' class
        // and that it has this factory method.
        IRecipe recipe = RecipeBiblioFramedShapeless.addShapelessWoodRecipe(baseOutput, entry, ingredients);
        GameRegistry.addRecipe(recipe);

        // Create a def for NEI
        RecipeDef def = new RecipeDef(0, 0, ingredients, baseOutput, entry); // Use 0/0 width/height for shapeless
        defs.add(def);

        return def;
    }

    // Helper: convert pattern+map back to varargs used by addShapedWoodRecipe
    private static Object[] toVarargs(String[] pattern, Map<Character, Object> charMap) {
        // varargs: pattern rows..., then pairs: 'C', obj, 'D', obj...
        int pairs = charMap.size() * 2;
        Object[] out = new Object[pattern.length + pairs];
        int idx = 0;
        for (String s : pattern) out[idx++] = s;
        for (Map.Entry<Character, Object> e : charMap.entrySet()) {
            out[idx++] = e.getKey();
            out[idx++] = e.getValue();
        }
        return out;
    }
}

