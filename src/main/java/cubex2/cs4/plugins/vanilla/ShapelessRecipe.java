package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class ShapelessRecipe extends SimpleContent
{
    List<RecipeInput> items = Lists.newArrayList();
    WrappedItemStack result;
    boolean remove = false;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        if (remove)
        {
            removeRecipe(CraftingManager.getInstance().getRecipeList());
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
        return OreDictionary.itemMatches(recipe.getRecipeOutput(), result.createItemStack(), false);
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
            ItemStack target = recipe.recipeItems.get(i);
            ItemStack source = (ItemStack) input[i];

            if (!OreDictionary.itemMatches(target, source, true))
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
        if (recipe.getInput().size() != getRecipeSize())
            return false;

        Object[] input = getRecipeInput();

        for (int i = 0; i < recipe.getInput().size(); i++)
        {
            Object target = recipe.getInput().get(i);
            Object source = input[i];

            if (!ItemHelper.isSameRecipeInput(target, source))
                return false;
        }
        return true;
    }


    private void addRecipe()
    {
        ShapelessOreRecipe recipe = new ShapelessOreRecipe(result.createItemStack(), getInputForRecipe());
        GameRegistry.addRecipe(recipe);
    }

    Object[] getInputForRecipe()
    {
        return items.stream()
                    .map(input -> input.isOreClass() ? input.getOreClass() : input.getStack().createItemStack())
                    .toArray();
    }

    /**
     * Gets recipe input without the chars for the shape.
     */
    Object[] getRecipeInput()
    {
        return items.stream()
                    .map(input -> input.isOreClass() ? OreDictionary.getOres(input.getOreClass()) : input.getStack().createItemStack())
                    .toArray();
    }

    public static final JsonDeserializer<ShapelessRecipe> DESERIALIZER = (json, typeOfT, context) ->
    {
        JsonObject jsonObject = json.getAsJsonObject();
        ShapelessRecipe recipe = new ShapelessRecipe();

        if (jsonObject.has("items"))
        {
            recipe.items = context.deserialize(jsonObject.get("items"), new TypeToken<List<RecipeInput>>() {}.getType());
        }

        if (jsonObject.has("result"))
        {
            recipe.result = context.deserialize(jsonObject.get("result"), WrappedItemStack.class);
        }

        if (jsonObject.has("remove"))
        {
            recipe.remove = jsonObject.get("remove").getAsBoolean();
        }

        return recipe;
    };
}
