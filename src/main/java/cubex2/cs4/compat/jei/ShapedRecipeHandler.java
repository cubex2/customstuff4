package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.vanilla.DamageableShapedOreRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ShapedRecipeHandler implements IRecipeHandler<DamageableShapedOreRecipe>
{
    private final Class<DamageableShapedOreRecipe> clazz;
    private final String uid;
    private final IJeiHelpers jeiHelpers;

    public ShapedRecipeHandler(Class<DamageableShapedOreRecipe> clazz, String uid, IJeiHelpers jeiHelpers)
    {
        this.clazz = clazz;
        this.uid = uid;
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public Class<DamageableShapedOreRecipe> getRecipeClass()
    {
        return clazz;
    }

    @Override
    public String getRecipeCategoryUid()
    {
        return uid;
    }

    @Override
    public String getRecipeCategoryUid(DamageableShapedOreRecipe recipe)
    {
        return uid;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(DamageableShapedOreRecipe recipe)
    {
        return new ShapedRecipeWrapper(recipe, jeiHelpers);
    }

    @Override
    public boolean isRecipeValid(DamageableShapedOreRecipe recipe)
    {
        return true;
    }
}
