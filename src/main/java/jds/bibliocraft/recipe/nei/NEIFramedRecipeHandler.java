package jds.bibliocraft.recipe.nei; // Or your integration package

import codechicken.nei.NEIClientConfig;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler; // 1. Extend TemplateRecipeHandler
import jds.bibliocraft.recipe.RecipeBiblioFramedShapeless;
import jds.bibliocraft.recipe.RecipeBiblioFramedShaped;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting; // Import GuiCrafting
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;

public class NEIFramedRecipeHandler extends TemplateRecipeHandler {

    public class CachedShapedRecipe extends CachedRecipe {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;
        public int width;
        public int height;

        public CachedShapedRecipe(int width, int height, Object[] items, ItemStack out) {
            this.result = new PositionedStack(out, 119, 24);
            this.ingredients = new ArrayList<>();
            this.width = width;
            this.height = height;
            setIngredients(width, height, items);
        }

        public CachedShapedRecipe(RecipeBiblioFramedShaped recipe) {
            this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
        }

        public void setIngredients(int width, int height, Object[] items) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (items[y * width + x] == null) {
                        continue;
                    }
                    PositionedStack stack = new PositionedStack(items[y * width + x], 25 + x * 18, 6 + y * 18);
                    stack.setMaxSize(1);
                    this.ingredients.add(stack);
                }
            }
        }
        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIFramedRecipeHandler.this.cycleticks / 20, this.ingredients);
        }
        @Override
        public PositionedStack getResult() {
            return result;
        }
    }

    public int[][] stackorder = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 2 }, { 1, 2 }, { 2, 0 },
        { 2, 1 }, { 2, 2 } };

    public class CachedShapelessRecipe extends CachedRecipe {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedShapelessRecipe() {
            ingredients = new ArrayList<>();
        }
        public CachedShapelessRecipe(ItemStack output) {
            this();
            setResult(output);
        }
        public CachedShapelessRecipe(List<?> input, ItemStack output) {
            this(output);
            setIngredients(input);
        }

        public CachedShapelessRecipe(ShapelessRecipes recipe) {
            this(recipe.recipeItems, recipe.getRecipeOutput());
        }

        public void setIngredients(List<?> items) {
            ingredients.clear();
            int itemsSize = items.size();
            if (itemsSize > stackorder.length) {
                itemsSize = stackorder.length;
            }
            for (int ingred = 0; ingred < itemsSize; ingred++) {
                PositionedStack stack = new PositionedStack(
                    items.get(ingred),
                    25 + stackorder[ingred][0] * 18,
                    6 + stackorder[ingred][1] * 18);
                stack.setMaxSize(1);
                ingredients.add(stack);
            }
        }
        public void setResult(ItemStack output) {
            result = new PositionedStack(output, 119, 24);
        }
        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIFramedRecipeHandler.this.cycleticks / 20, this.ingredients);
        }
        @Override
        public PositionedStack getResult() {
            return result;
        }
    }

    @Override
    public String getRecipeTabName() {
        return getRecipeName();
    }

    @Override
    public String getRecipeName() {
        return "BiblioCraft Framed Recipes";
    }

    @Override
    public String getGuiTexture() {
        return "minecraft:textures/gui/container/crafting_table.png";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiCrafting.class;
    }

    @Override
    public String getOverlayIdentifier() {
        return "crafting";
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "crafting"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("item") && results.length > 0 && results[0] instanceof ItemStack) {
            loadCraftingRecipes((ItemStack) results[0]);
        }
        else if (outputId.equals("crafting")) {
            loadAllRecipes(outputId);
        }
    }

    /**
     * This is the "filter by NBT" method.
     * It's called by the method above when a user clicks an item.
     */
    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result != null && result.getItemDamage() == 6 && result.hasTagCompound()) {
            String targetTexture = result.getTagCompound().getString("renderTexture");
//            if (targetTexture == null) {
//                return; // Not a framed item
//            }

            List<IRecipe> allRecipes = CraftingManager.getInstance().getRecipeList();
            for (IRecipe recipe : allRecipes) {
                ItemStack output = recipe.getRecipeOutput();
                if (output != null &&
                    output.isItemEqual(result) && // Checks Item & Meta
                    output.hasTagCompound())
                {
                    String recipeTexture = output.getTagCompound().getString("renderTexture");
                    if (targetTexture.equals(recipeTexture)) {
                        if (recipe instanceof RecipeBiblioFramedShaped) {
                            this.arecipes.add(new CachedShapedRecipe((RecipeBiblioFramedShaped) recipe));
                        } else if (recipe instanceof RecipeBiblioFramedShapeless) {
                            this.arecipes.add(new CachedShapelessRecipe((RecipeBiblioFramedShapeless) recipe));
                        }
                    }
                }
            }
        }
    }

    /**
     * This is a new helper method to load ALL our recipes.
     * It's called when the user clicks the category button.
     */
    public void loadAllRecipes(String outputId) {
        if (outputId.equals("crafting")) {
            List<IRecipe> allRecipes = CraftingManager.getInstance().getRecipeList();
            for (IRecipe recipe : allRecipes) {
                // Add *only* your custom framed recipes
                if (recipe instanceof RecipeBiblioFramedShaped recipeShaped) {
                    this.arecipes.add(new CachedShapedRecipe(recipeShaped));
                } else if (recipe instanceof RecipeBiblioFramedShapeless recipeShapeless) {
                    this.arecipes.add(new CachedShapelessRecipe(recipeShapeless));
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<IRecipe> allRecipes = CraftingManager.getInstance().getRecipeList();

        for (IRecipe recipe : allRecipes) {
            CachedRecipe cr = null;
            if (recipe instanceof RecipeBiblioFramedShaped recipeShaped) {
                cr = new CachedShapedRecipe(recipeShaped);
            } else if (recipe instanceof RecipeBiblioFramedShapeless recipeShapeless) {
                cr = new CachedShapelessRecipe(recipeShapeless);
            }

            if (cr != null) {
                List<PositionedStack> recipeIngredients = cr.getIngredients();
                if (cr.containsWithNBT(recipeIngredients, ingredient)) {
                    cr.setIngredientPermutation(recipeIngredients, ingredient);
                    this.arecipes.add(cr);
                }
            }
        }
    }
}
