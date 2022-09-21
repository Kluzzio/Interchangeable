package timefall.interchangeable.mixins;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import timefall.interchangeable.config.ConfigManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Mixin(SmithingRecipe.class)
public class SmithingRecipeMixin {

   /* @ModifyArg(method = "matches", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;test(Lnet/minecraft/item/ItemStack;)Z", ordinal = 0))
    private ItemStack aaa(ItemStack inputItemStack) {
        if ((Object) this instanceof SmithingRecipe smithingRecipe) {
            HashMap<Item, Integer> INGREDIENT_ITEMS = new HashMap<>();
            smithingRecipe.getIngredients().forEach(ingredient ->
                    Arrays.stream(ingredient.getMatchingStacks()).toList().forEach(itemStack ->
                            INGREDIENT_ITEMS.put(itemStack.getItem(),
                                    INGREDIENT_ITEMS.getOrDefault(itemStack.getItem(), 0) + 1)
                    )
            );

            for (Item validIngredientItem : INGREDIENT_ITEMS.keySet()) {
                for (String[] array : ConfigManager.CONFIG_FILE.getEquivalenceClasses()) {
                    if (doesArrayContainItem(array, validIngredientItem))
                        if (doesArrayContainItem(array, inputItemStack.getItem()))
                            return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
                Map<String, String[]> substitutions = ConfigManager.CONFIG_FILE.getSubstitutions();
                String itemKey = Registry.ITEM.getId(validIngredientItem).toString();
                if (substitutions.containsKey(itemKey)) {
                    if (doesArrayContainItem(substitutions.get(itemKey), inputItemStack.getItem()))
                        return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
                Map<String, String[]> recipeSpecific = ConfigManager.CONFIG_FILE.getRecipeSpecificEquivalenceClasses();
                String recipeKey = smithingRecipe.getId().toString();
                if (recipeSpecific.containsKey(recipeKey)) {
                    if (doesArrayContainItem(recipeSpecific.get(recipeKey), validIngredientItem))
                        if (doesArrayContainItem(recipeSpecific.get(recipeKey), inputItemStack.getItem()))
                            return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
            }
        }
        return inputItemStack;
    }

    @ModifyArg(method = "matches", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;test(Lnet/minecraft/item/ItemStack;)Z", ordinal = 1))
    private ItemStack bbb(ItemStack inputItemStack) {
        if ((Object) this instanceof SmithingRecipe smithingRecipe) {
            HashMap<Item, Integer> INGREDIENT_ITEMS = new HashMap<>();
            smithingRecipe.getIngredients().forEach(ingredient ->
                    Arrays.stream(ingredient.getMatchingStacks()).toList().forEach(itemStack ->
                            INGREDIENT_ITEMS.put(itemStack.getItem(),
                                    INGREDIENT_ITEMS.getOrDefault(itemStack.getItem(), 0) + 1)
                    )
            );

            for (Item validIngredientItem : INGREDIENT_ITEMS.keySet()) {
                for (String[] array : ConfigManager.CONFIG_FILE.getEquivalenceClasses()) {
                    if (doesArrayContainItem(array, validIngredientItem))
                        if (doesArrayContainItem(array, inputItemStack.getItem()))
                            return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
                Map<String, String[]> substitutions = ConfigManager.CONFIG_FILE.getSubstitutions();
                String itemKey = Registry.ITEM.getId(validIngredientItem).toString();
                if (substitutions.containsKey(itemKey)) {
                    if (doesArrayContainItem(substitutions.get(itemKey), inputItemStack.getItem()))
                        return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
                Map<String, String[]> recipeSpecific = ConfigManager.CONFIG_FILE.getRecipeSpecificEquivalenceClasses();
                String recipeKey = smithingRecipe.getId().toString();
                if (recipeSpecific.containsKey(recipeKey)) {
                    if (doesArrayContainItem(recipeSpecific.get(recipeKey), validIngredientItem))
                        if (doesArrayContainItem(recipeSpecific.get(recipeKey), inputItemStack.getItem()))
                            return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
            }
        }
        return inputItemStack;
    }

    @ModifyArg(method = "testAddition", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;test(Lnet/minecraft/item/ItemStack;)Z"))
    private ItemStack ccc(ItemStack inputItemStack) {
        if ((Object) this instanceof SmithingRecipe smithingRecipe) {
            HashMap<Item, Integer> INGREDIENT_ITEMS = new HashMap<>();
            smithingRecipe.getIngredients().forEach(ingredient ->
                    Arrays.stream(ingredient.getMatchingStacks()).toList().forEach(itemStack ->
                            INGREDIENT_ITEMS.put(itemStack.getItem(),
                                    INGREDIENT_ITEMS.getOrDefault(itemStack.getItem(), 0) + 1)
                    )
            );

            for (Item validIngredientItem : INGREDIENT_ITEMS.keySet()) {
                for (String[] array : ConfigManager.CONFIG_FILE.getEquivalenceClasses()) {
                    if (doesArrayContainItem(array, validIngredientItem))
                        if (doesArrayContainItem(array, inputItemStack.getItem()))
                            return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
                Map<String, String[]> substitutions = ConfigManager.CONFIG_FILE.getSubstitutions();
                String itemKey = Registry.ITEM.getId(validIngredientItem).toString();
                if (substitutions.containsKey(itemKey)) {
                    if (doesArrayContainItem(substitutions.get(itemKey), inputItemStack.getItem()))
                        return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
                Map<String, String[]> recipeSpecific = ConfigManager.CONFIG_FILE.getRecipeSpecificEquivalenceClasses();
                String recipeKey = smithingRecipe.getId().toString();
                if (recipeSpecific.containsKey(recipeKey)) {
                    if (doesArrayContainItem(recipeSpecific.get(recipeKey), validIngredientItem))
                        if (doesArrayContainItem(recipeSpecific.get(recipeKey), inputItemStack.getItem()))
                            return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
            }
        }
        return inputItemStack;
    }

    private static boolean doesArrayContainItem(String[] array, Item item) {
        return Arrays.stream(array).toList().contains(Registry.ITEM.getId(item).toString());
    }*/
}
