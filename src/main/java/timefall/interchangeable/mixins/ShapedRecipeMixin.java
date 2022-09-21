package timefall.interchangeable.mixins;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import timefall.interchangeable.config.ConfigManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    private static Ingredient recipeIngredient;

    @Inject(method = "matchesPattern", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;test(Lnet/minecraft/item/ItemStack;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getMeShit(CraftingInventory inv, int offsetX, int offsetY, boolean flipped, CallbackInfoReturnable<Boolean> cir,
                           int i, int j, int k, int l, Ingredient ingredient) {
        recipeIngredient = ingredient;
    }

    @ModifyArg(method = "matchesPattern", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;test(Lnet/minecraft/item/ItemStack;)Z"))
    private ItemStack fuckShitUp(ItemStack inputItemStack) {
        if (ConfigManager.SUCCESSFULLY_LOADED_CONFIG) {
            if ((Object) this instanceof ShapedRecipe shapedRecipe) {
                // ID is the name of the output item for some reason?
                Identifier identifier = shapedRecipe.getId();

                // We need to look at the current slot
                // See what ingredient should go there
                Collection<Item> itemCollection = new ArrayList<>(List.of());
                Arrays.stream(recipeIngredient.getMatchingStacks()).toList().forEach(itemStack -> itemCollection.add(itemStack.getItem()));
                //if interchangeable
                //look in config for the item to be interchangeable
                for (String[] array : ConfigManager.CONFIG_FILE.getEquivalenceClasses()) {
                    // Check that the normal ingredient is interchangeable
                    for (Item validIngredientItem : itemCollection) {
                        if (Arrays.stream(array).toList().contains(Registry.ITEM.getId(validIngredientItem).toString())) {
                            // Check that the given ingredient is interchangeable with normal ingredient
                            if (Arrays.stream(array).toList().contains(Registry.ITEM.getId(inputItemStack.getItem()).toString())) {
                                // return normal ingredient
                                return new ItemStack(validIngredientItem, inputItemStack.getCount());
                            }
                        }
                    }
                }
            }
        }
        return inputItemStack;
    }
}
