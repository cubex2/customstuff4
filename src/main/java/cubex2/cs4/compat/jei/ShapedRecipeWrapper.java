package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.vanilla.DamageableShapedOreRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ShapedRecipeWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper
{
    private final IJeiHelpers jeiHelpers;
    private final DamageableShapedOreRecipe recipe;

    public ShapedRecipeWrapper(DamageableShapedOreRecipe recipe, IJeiHelpers jeiHelpers)
    {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        ItemStack recipeOutput = recipe.getRecipeOutput();

        try
        {
            List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(Arrays.asList(recipe.getInput()));
            ingredients.setInputLists(ItemStack.class, inputs);
            ingredients.setOutput(ItemStack.class, recipeOutput);
        } catch (RuntimeException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth()
    {
        return recipe.getWidth();
    }

    @Override
    public int getHeight()
    {
        return recipe.getHeight();
    }

}
