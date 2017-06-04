package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ShapelessRecipeWraper extends BlankRecipeWrapper
{
    private final IJeiHelpers jeiHelpers;
    private final DamageableShapelessOreRecipe recipe;

    public ShapelessRecipeWraper(DamageableShapelessOreRecipe recipe, IJeiHelpers jeiHelpers)
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
            List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(recipe.getInput());
            ingredients.setInputLists(ItemStack.class, inputs);
            ingredients.setOutput(ItemStack.class, recipeOutput);
        } catch (RuntimeException e)
        {
            e.printStackTrace();
        }
    }
}