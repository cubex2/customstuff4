package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class MachineRecipeHandler implements IRecipeHandler<MachineRecipeImpl>
{
    private final Class<MachineRecipeImpl> clazz;
    private final String uid;
    private final IJeiHelpers jeiHelpers;

    public MachineRecipeHandler(Class<MachineRecipeImpl> clazz, String uid, IJeiHelpers jeiHelpers)
    {
        this.clazz = clazz;
        this.uid = uid;
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public Class<MachineRecipeImpl> getRecipeClass()
    {
        return clazz;
    }

    @Override
    public String getRecipeCategoryUid()
    {
        return uid;
    }

    @Override
    public String getRecipeCategoryUid(MachineRecipeImpl recipe)
    {
        return uid;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(MachineRecipeImpl recipe)
    {
        return new MachineRecipeWrapper(recipe, jeiHelpers);
    }

    @Override
    public boolean isRecipeValid(MachineRecipeImpl recipe)
    {
        return true;
    }
}
