package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.plugins.jei.JEICompatRegistry;
import cubex2.cs4.plugins.vanilla.crafting.CraftingManagerCS4;
import cubex2.cs4.util.ItemHelper;
import cubex2.cs4.util.ReflectionHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class ShapelessRecipe extends SimpleContent
{
    List<RecipeInput> items = Lists.newArrayList();
    WrappedItemStack result;
    boolean remove = false;
    ResourceLocation recipeList = new ResourceLocation("minecraft", "vanilla");
    int[] damage = new int[0];

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        if (remove)
        {
            removeRecipe(CraftingManagerCS4.getRecipes(recipeList));
        } else
        {
            addRecipe();
        }
    }

    @Override
    protected boolean isReady()
    {
        if (result != null && !result.isItemLoaded())
            return false;

        return items.stream().allMatch(input -> input.isOreClass() || (input.isItemStack() && input.getStack().isItemLoaded()));
    }

    boolean removeRecipe(Collection<IRecipe> from)
    {
        if (items.size() == 0)
        {
            return removeWithResult(from);
        } else if (result == null)
        {
            return removeWithInput(from);
        } else
        {
            return removeWithBoth(from);
        }
    }

    private boolean removeWithResult(Collection<IRecipe> from)
    {
        return from.removeIf(this::matchesOutput);
    }

    private boolean removeWithInput(Collection<IRecipe> from)
    {
        List<IRecipe> recipes = from.stream()
                                    .filter(this::matchesInput)
                                    .collect(Collectors.toList());

        return from.removeAll(recipes);
    }

    private boolean removeWithBoth(Collection<IRecipe> from)
    {
        List<IRecipe> recipes = from.stream()
                                    .filter(this::matchesOutput)
                                    .filter(this::matchesInput)
                                    .collect(Collectors.toList());

        return from.removeAll(recipes);
    }

    private boolean matchesOutput(IRecipe recipe)
    {
        return OreDictionary.itemMatches(recipe.getRecipeOutput(), result.getItemStack(), false);
    }

    private boolean matchesInput(IRecipe recipe)
    {
        if (recipe instanceof ShapelessRecipes)
        {
            return matchesInput((ShapelessRecipes) recipe);
        } else if (recipe instanceof ShapelessOreRecipe)
        {
            return matchesInput((ShapelessOreRecipe) recipe);
        }

        return false;
    }

    private boolean matchesInput(ShapelessRecipes recipe)
    {
        if (isOreRecipe())
            return false;
        if (recipe.recipeItems.size() != getRecipeSize())
            return false;

        Object[] input = getRecipeInput();

        for (int i = 0; i < recipe.recipeItems.size(); i++)
        {
            Ingredient target = recipe.recipeItems.get(i);
            ItemStack source = (ItemStack) input[i];

            if (!target.apply(source))
                return false;
        }

        return true;
    }

    private boolean isOreRecipe()
    {
        return items.stream().anyMatch(RecipeInput::isOreClass);
    }

    private int getRecipeSize()
    {
        return items.size();
    }

    private boolean matchesInput(ShapelessOreRecipe recipe)
    {
        if (recipe.getIngredients().size() != getRecipeSize())
            return false;

        Object[] input = getRecipeInput();

        for (int i = 0; i < recipe.getIngredients().size(); i++)
        {
            Ingredient target = recipe.getIngredients().get(i);
            Object source = input[i];

            if (!ItemHelper.isSameRecipeInput(target, source))
                return false;
        }
        return true;
    }


    void addRecipe()
    {
        if (damage.length == 0)
            damage = new int[items.size()];

        Class<DamageableShapelessOreRecipe> recipeClass = JEICompatRegistry.getShapelessCraftingRecipeClass(recipeList);
        Constructor<DamageableShapelessOreRecipe> constructor = ReflectionHelper.getConstructor(recipeClass, ResourceLocation.class, int[].class, ItemStack.class, Object[].class);
        DamageableShapelessOreRecipe recipe = ReflectionHelper.newInstance(constructor, null, damage, result.getItemStack(), getInputForRecipe());

        if (recipe != null)
        {
            CraftingManagerCS4.addRecipe(recipeList, recipe);
        }
    }

    Object[] getInputForRecipe()
    {
        return items.stream()
                    .map(input -> input.isOreClass() ? input.getOreClass().getOreName() : input.getStack().getItemStack())
                    .toArray();
    }

    /**
     * Gets recipe input without the chars for the shape.
     */
    Object[] getRecipeInput()
    {
        return items.stream()
                    .map(input -> input.isOreClass() ? OreDictionary.getOres(input.getOreClass().getOreName()) : input.getStack().getItemStack())
                    .toArray();
    }
}
