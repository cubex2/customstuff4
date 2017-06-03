package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class ShapelessCraftingRecipeWrapperFactory implements IRecipeWrapperFactory<DamageableShapelessOreRecipe>
{
    private final IJeiHelpers jeiHelpers;

    public ShapelessCraftingRecipeWrapperFactory(IJeiHelpers jeiHelpers) {this.jeiHelpers = jeiHelpers;}

    @Override
    public IRecipeWrapper getRecipeWrapper(DamageableShapelessOreRecipe recipe)
    {
        return new ShapelessRecipeWraper(recipe, jeiHelpers);
    }
}
