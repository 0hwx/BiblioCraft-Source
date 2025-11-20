package jds.bibliocraft.recipe;

import java.util.ArrayList;
import java.util.List;

import jds.bibliocraft.helpers.WoodRegistryEntry;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World; // Imported World
import net.minecraftforge.oredict.OreDictionary;

public class RecipeBiblioFramedShapeless extends ShapelessRecipes
{
    /**
     * The wood type this recipe is for.
     * This is used to check the NBT tags of ingredients.
     */
    private final WoodRegistryEntry woodEntry;

    /**
     * Private constructor. Use the factory method to create.
     */
    private RecipeBiblioFramedShapeless(ItemStack output, List<ItemStack> inputList, WoodRegistryEntry entry)
    {
        super(output, inputList);
        this.woodEntry = entry;
    }

    /**
     * Factory method to create and configure a new framed shapeless recipe.
     * NOTE: Renamed from addShapedWoodRecipe to addShapelessWoodRecipe.
     */
    public static IRecipe addShapelessWoodRecipe(ItemStack outputTemplate, WoodRegistryEntry entry, Object ... ingredients)
    {
        // Create a copy of the output to modify
        ItemStack result = outputTemplate.copy();

        // --- Ingredient parsing ---
        List<ItemStack> inputStacks = new ArrayList<>();
        for (Object obj : ingredients)
        {
            ItemStack stack = null;
            if (obj instanceof ItemStack) {
                // This is the key: The ingredient (e.g., a framed block)
                // already has the correct NBT, so we just copy it.
                stack = ((ItemStack) obj).copy();
            } else if (obj instanceof Item) {
                stack = new ItemStack((Item) obj);
            } else if (obj instanceof Block) {
                stack = new ItemStack((Block) obj, 1, OreDictionary.WILDCARD_VALUE);
            } else if (obj instanceof String) {
                // Basic OreDict support
                List<ItemStack> ores = OreDictionary.getOres((String) obj);
                if (!ores.isEmpty()) {
                    stack = ores.get(0).copy();
                }
            }

            if (stack == null) {
                throw new IllegalArgumentException("Invalid shapeless ingredient for " + outputTemplate.getDisplayName());
            }
            inputStacks.add(stack);
        }

        // --- NBT logic ---
        // Pre-bake the correct texture NBT onto the result stack
        NBTTagCompound tags = new NBTTagCompound();
        tags.setString("renderTexture", entry.getTextureString());
        result.setTagCompound(tags);

        // Create and return the new recipe instance
        return new RecipeBiblioFramedShapeless(result, inputStacks, entry);
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

        // 2. If they do, check NBT of framed items (meta 6)
        String expectedTexture = this.woodEntry.getTextureString();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stackInSlot = inv.getStackInSlot(i);

            // We only care about framed items (meta 6)
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
     * This is now 100% correct, as the NBT was pre-baked.
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        return this.getRecipeOutput().copy();
    }
}
