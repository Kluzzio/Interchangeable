package timefall.interchangeable.mixins;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timefall.interchangeable.config.ConfigManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Mixin(ShapelessRecipe.class)
public class ShapelessRecipeMixin {

    private static HashMap<Item, Integer> SHAPELESS_INGREDIENTS = new HashMap<>();

    @ModifyArg(method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/recipe/RecipeMatcher;addInput(Lnet/minecraft/item/ItemStack;I)V"))
    private ItemStack interchangeShapelessIngredients(ItemStack inputItemStack) {
        if (!ConfigManager.SUCCESSFULLY_LOADED_CONFIG)
            return inputItemStack;
        if ((Object) this instanceof ShapelessRecipe shapelessRecipe) {
            HashMap<Item, Integer> INGREDIENT_ITEMS = new HashMap<>();
            shapelessRecipe.getIngredients().forEach(ingredient ->
                    Arrays.stream(ingredient.getMatchingStacks()).toList().forEach(itemStack ->
                            INGREDIENT_ITEMS.put(itemStack.getItem(),
                                    INGREDIENT_ITEMS.getOrDefault(itemStack.getItem(), 0) + 1)
                    )
            );

            for (Item validIngredientItem : INGREDIENT_ITEMS.keySet()) {
                for (String[] array : ConfigManager.CONFIG_FILE.getEquivalenceClasses()) {
                    if (doesArrayContainItem(array, validIngredientItem))
                        if (doesArrayContainItem(array, inputItemStack.getItem()))
                            if (checkIngredientNotOverused(validIngredientItem, INGREDIENT_ITEMS))
                                return makeUseOfIngredientAndDocument(validIngredientItem, inputItemStack);
                }
                Map<String, String[]> substitutions = ConfigManager.CONFIG_FILE.getSubstitutions();
                String itemKey = Registry.ITEM.getId(validIngredientItem).toString();
                if (substitutions.containsKey(itemKey)) {
                    if (doesArrayContainItem(substitutions.get(itemKey), inputItemStack.getItem()))
                        if (checkIngredientNotOverused(validIngredientItem, INGREDIENT_ITEMS))
                            return makeUseOfIngredientAndDocument(validIngredientItem, inputItemStack);
                }
                Map<String, String[]> recipeSpecific = ConfigManager.CONFIG_FILE.getRecipeSpecificEquivalenceClasses();
                String recipeKey = shapelessRecipe.getId().toString();
                if (recipeSpecific.containsKey(recipeKey)) {
                    if (doesArrayContainItem(recipeSpecific.get(recipeKey), validIngredientItem))
                        if (doesArrayContainItem(recipeSpecific.get(recipeKey), inputItemStack.getItem()))
                            if (checkIngredientNotOverused(validIngredientItem, INGREDIENT_ITEMS))
                                return makeUseOfIngredientAndDocument(validIngredientItem, inputItemStack);
                }
            }
        }
        return inputItemStack;
    }

    @Inject(method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z", at = @At("RETURN"))
    private void clearShapelessIngredientsHash(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir) {
        SHAPELESS_INGREDIENTS = new HashMap<>();
    }

    private static boolean doesArrayContainItem(String[] array, Item item) {
        return Arrays.stream(array).toList().contains(Registry.ITEM.getId(item).toString());
    }

    private static boolean checkIngredientNotOverused(Item validIngredientItem, HashMap<Item, Integer> INGREDIENT_ITEMS) {
        return SHAPELESS_INGREDIENTS.getOrDefault(validIngredientItem, 0) < INGREDIENT_ITEMS.getOrDefault(validIngredientItem, 0);
    }

    private static ItemStack makeUseOfIngredientAndDocument(Item validIngredientItem, ItemStack inputItemStack) {
        SHAPELESS_INGREDIENTS.put(validIngredientItem, SHAPELESS_INGREDIENTS.getOrDefault(validIngredientItem, 0) + 1);
        return new ItemStack(validIngredientItem, inputItemStack.getCount());
    }
}
