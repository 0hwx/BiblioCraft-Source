package jds.bibliocraft.recipe.nei;

import codechicken.nei.api.IRecipeFilter;
import codechicken.nei.api.IRecipeFilter.IRecipeFilterProvider;
import codechicken.nei.recipe.ShapedRecipeHandler;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.minecraft.item.ItemStack;

public class BiblioRecipeFilterProvider implements IRecipeFilterProvider {

    @Override
    public IRecipeFilter getRecipeFilter() {
        return (handler, recipeIndex) -> {
            // Check if this is one of the vanilla handlers we want to filter
            boolean isVanillaHandler = (handler.getClass() == ShapedRecipeHandler.class ||
                handler.getClass() == ShapelessRecipeHandler.class);

            if (isVanillaHandler) {
                // It is. Get the recipe's output stack
                ItemStack output = handler.getResultStack(recipeIndex).item;

                if (output != null &&
                    output.getItemDamage() == 6 && // This is one of our framed items
                    output.hasTagCompound() &&
                    output.getTagCompound().hasKey("renderTexture"))
                {
                    // This is one of our NBT-tagged recipes.
                    // HIDE it (return false) from this vanilla handler.
                    return false;
                }
            }
            return true;
        };
    }
}
