package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ShapelessRecipeHandler implements IRecipeHandler<DamageableShapelessOreRecipe>
{
    private final Class<DamageableShapelessOreRecipe> clazz;
    private final String uid;
    private final IJeiHelpers jeiHelpers;

    public ShapelessRecipeHandler(Class<DamageableShapelessOreRecipe> clazz, String uid, IJeiHelpers jeiHelpers)
    {
        this.clazz = clazz;
        this.uid = uid;
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public Class<DamageableShapelessOreRecipe> getRecipeClass()
    {
        return clazz;
    }

    @Override
    public String getRecipeCategoryUid()
    {
        return uid;
    }

    @Override
    public String getRecipeCategoryUid(DamageableShapelessOreRecipe recipe)
    {
        return uid;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(DamageableShapelessOreRecipe recipe)
    {
        return new ShapelessRecipeWraper(recipe, jeiHelpers);
    }

    @Override
    public boolean isRecipeValid(DamageableShapelessOreRecipe recipe)
    {
        return true;
    }
}
