package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class MachineRecipeWrapperFactory implements IRecipeWrapperFactory<MachineRecipeImpl>
{
    private final IJeiHelpers jeiHelpers;

    public MachineRecipeWrapperFactory(IJeiHelpers jeiHelpers) {this.jeiHelpers = jeiHelpers;}

    @Override
    public IRecipeWrapper getRecipeWrapper(MachineRecipeImpl recipe)
    {
        return new MachineRecipeWrapper(recipe, jeiHelpers);
    }
}
