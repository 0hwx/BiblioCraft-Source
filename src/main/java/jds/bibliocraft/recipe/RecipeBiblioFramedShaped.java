package jds.bibliocraft.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jds.bibliocraft.helpers.WoodRegistryEntry;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World; // Import World
import net.minecraftforge.oredict.OreDictionary;

public class RecipeBiblioFramedShaped extends ShapedRecipes
{
    /**
     * The wood type this recipe is for.
     * This is used to check the NBT tags of ingredients.
     */
    private final WoodRegistryEntry woodEntry;

    /**
     * Private constructor. Use the addShapedWoodRecipe factory to create.
     */
    private RecipeBiblioFramedShaped(int width, int height, ItemStack[] ingredientsIn, ItemStack output, WoodRegistryEntry entry)
    {
        super(width, height, ingredientsIn, output);
        this.woodEntry = entry; // Store the entry for this recipe
    }

    /**
     * Factory method to create and configure a new framed wood recipe.
     * This is what FramedRecipeRegistry should call.
     */
    public static IRecipe addShapedWoodRecipe(ItemStack outputTemplate, WoodRegistryEntry entry, Object ... input)
    {
        // --- This block parses the recipe shape and ingredients ---
        ItemStack result = outputTemplate.copy();
        int index = 0;
        int width = 0, height = 0;
        StringBuilder shape = new StringBuilder();

        if (input[index] instanceof String[]) {
            String[] rows = (String[]) input[index++];
            height = rows.length;
            width = rows[0].length();
            for (String row : rows) shape.append(row);
        } else {
            while (index < input.length && input[index] instanceof String) {
                String row = (String) input[index++];
                height++;
                width = row.length();
                shape.append(row);
            }
        }

        Map<Character, ItemStack> ingredients = new HashMap<>();
        while (index < input.length) {
            Character key = (Character) input[index++];
            Object value = input[index++];

            ItemStack stack = null;
            if (value instanceof ItemStack) {
                stack = ((ItemStack) value).copy();
            } else if (value instanceof Item) {
                stack = new ItemStack((Item) value);
            } else if (value instanceof Block) {
                stack = new ItemStack((Block) value, 1, OreDictionary.WILDCARD_VALUE);
            } else if (value instanceof String) {
                // This is a simple OreDict lookup, it won't work for
                // ingredients that also need NBT checks.
                List<ItemStack> ores = OreDictionary.getOres((String) value);
                if (!ores.isEmpty()) {
                    stack = ores.get(0).copy();
                }
            }

            if (stack == null) {
                throw new IllegalArgumentException("Invalid ingredient for key '" + key + "'");
            }

            ingredients.put(key, stack);
        }

        ItemStack[] grid = new ItemStack[width * height];
        for (int i = 0; i < grid.length; i++) {
            char c = shape.charAt(i);
            grid[i] = ingredients.get(c);
        }

        // Store this recipe's texture string to the output NBT
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("renderTexture", entry.getTextureString());
        result.setTagCompound(tag);

        // Create the new recipe instance
        return new RecipeBiblioFramedShaped(width, height, grid, result, entry);
    }

    /**
     * Checks if the crafting grid matches the recipe.
     * This now ALSO checks the NBT of framed ingredients.
     */
    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        // 1. First, check if the items/metas match (vanilla check)
        if (!super.matches(inv, world)) {
            return false;
        }

        // 2. If they do, now check NBT of framed items (meta 6)
        String expectedTexture = this.woodEntry.getTextureString();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stackInSlot = inv.getStackInSlot(i);

            // We only care about framed items (which you've set to meta 6)
            if (stackInSlot != null && stackInSlot.getItemDamage() == 6) {

                // It is a framed item. Does it have NBT?
                if (!stackInSlot.hasTagCompound()) {
                    // It's a framed item with no texture.
                    return true;
                }

                // It has NBT. Does the texture string match?
                String actualTexture = stackInSlot.getTagCompound().getString("renderTexture");
                if (actualTexture == null || !actualTexture.equals(expectedTexture)) {
                    // Texture mismatch!
                    return false;
                }
            }
        }

        // All items/metas matched, and all framed items had the correct texture.
        return true;
    }

    /**
     * Returns the crafting result.
     * This is correct, as the NBT was pre-baked in addShapedWoodRecipe.
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        return this.getRecipeOutput().copy();
    }
}
