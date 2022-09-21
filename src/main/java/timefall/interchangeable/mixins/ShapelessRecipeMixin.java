package timefall.interchangeable.mixins;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import timefall.interchangeable.config.ConfigManager;

import java.util.*;

@Mixin(ShapelessRecipe.class)
public class ShapelessRecipeMixin {

    private static HashMap<Item, Integer> SHAPELESS_INGREDIENTS = new HashMap<>();

    @ModifyArg(method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/recipe/RecipeMatcher;addInput(Lnet/minecraft/item/ItemStack;I)V"))
    private ItemStack breakMoreShit(ItemStack stack) {
        if ((Object) this instanceof ShapelessRecipe shapelessRecipe) {
            Collection<Item> itemCollection = new ArrayList<>(List.of());
            shapelessRecipe.getIngredients().forEach(ingredient ->
                    Arrays.stream(ingredient.getMatchingStacks()).toList().forEach(itemStack ->
                            itemCollection.add(itemStack.getItem())
                    )
            );

            HashMap<Item, Integer> INGREDIENT_ITEMS = new HashMap<>();
            shapelessRecipe.getIngredients().forEach(ingredient ->
                    Arrays.stream(ingredient.getMatchingStacks()).toList().forEach(itemStack ->
                            INGREDIENT_ITEMS.put(itemStack.getItem(),
                                    INGREDIENT_ITEMS.getOrDefault(itemStack.getItem(), 0) + 1)
                    )
            );

            // Check that the normal ingredient is interchangeable
            for (String[] array : ConfigManager.CONFIG_FILE.getEquivalenceClasses()) {
                // Check that the normal ingredient is interchangeable
                for (Item item : itemCollection) {
                    if (Arrays.stream(array).toList().contains(Registry.ITEM.getId(item).toString())) {
                        // Check that the given ingredient is interchangeable with normal ingredient
                        if (Arrays.stream(array).toList().contains(Registry.ITEM.getId(stack.getItem()).toString())) {
                            // Check that the given ingredient is interchangeable with normal ingredient
                            // return normal ingredient
                            if (SHAPELESS_INGREDIENTS.getOrDefault(item, 0) < INGREDIENT_ITEMS.getOrDefault(item, 0)) {
                                SHAPELESS_INGREDIENTS.put(item, SHAPELESS_INGREDIENTS.getOrDefault(item, 0) + 1);
                                return new ItemStack(item, stack.getCount());
                            }
                        }
                    }
                }
            }
        }
        return stack;
    }

    @Inject(method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z", at = @At("RETURN"))
    private void fuckYouYes(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir) {
        SHAPELESS_INGREDIENTS = new HashMap<>();
    }
}
