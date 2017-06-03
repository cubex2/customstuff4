package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.vanilla.DamageableShapedOreRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class ShapedCraftingRecipeWrapperFactory implements IRecipeWrapperFactory<DamageableShapedOreRecipe>
{
    private final IJeiHelpers jeiHelpers;

    public ShapedCraftingRecipeWrapperFactory(IJeiHelpers jeiHelpers) {this.jeiHelpers = jeiHelpers;}

    @Override
    public IRecipeWrapper getRecipeWrapper(DamageableShapedOreRecipe recipe)
    {
        return new ShapedRecipeWrapper(recipe, jeiHelpers);
    }
}
