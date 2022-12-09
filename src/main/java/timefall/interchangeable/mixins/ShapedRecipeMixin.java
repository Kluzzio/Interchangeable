package timefall.interchangeable.mixins;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import timefall.interchangeable.config.ConfigManager;

import java.util.*;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    private static Ingredient recipeIngredient;

    @Inject(method = "matchesPattern", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;test(Lnet/minecraft/item/ItemStack;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getRecipeIngredient(CraftingInventory inv, int offsetX, int offsetY, boolean flipped, CallbackInfoReturnable<Boolean> cir,
                           int i, int j, int k, int l, Ingredient ingredient) {
        recipeIngredient = ingredient;
    }

    @ModifyArg(method = "matchesPattern", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;test(Lnet/minecraft/item/ItemStack;)Z"))
    private ItemStack interchangeShapedIngredients(ItemStack inputItemStack) {
        if (!ConfigManager.SUCCESSFULLY_LOADED_CONFIG)
            return inputItemStack;
        if ((Object) this instanceof ShapedRecipe shapedRecipe) {
            Collection<Item> itemCollection = new ArrayList<>(List.of());
            Arrays.stream(recipeIngredient.getMatchingStacks()).toList().forEach(itemStack -> itemCollection.add(itemStack.getItem()));

            for (Item validIngredientItem : itemCollection) {
                for (String[] array : ConfigManager.CONFIG_FILE.getEquivalenceClasses()) {
                    if (doesArrayContainItem(array, validIngredientItem))
                        if (doesArrayContainItem(array, inputItemStack.getItem()))
                            return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
                Map<String, String[]> substitutions = ConfigManager.CONFIG_FILE.getSubstitutions();
                String itemKey = Registries.ITEM.getId(validIngredientItem).toString();
                if (substitutions.containsKey(itemKey)) {
                    if (doesArrayContainItem(substitutions.get(itemKey), inputItemStack.getItem()))
                        return new ItemStack(validIngredientItem, inputItemStack.getCount());
                }
                Map<String, String[]> recipeSpecific = ConfigManager.CONFIG_FILE.getRecipeSpecificEquivalenceClasses();
                String recipeKey = shapedRecipe.getId().toString();
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
        return Arrays.stream(array).toList().contains(Registries.ITEM.getId(item).toString());
    }
}
