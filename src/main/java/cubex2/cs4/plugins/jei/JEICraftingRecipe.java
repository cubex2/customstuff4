package cubex2.cs4.plugins.jei;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;

public class JEICraftingRecipe extends JEIRecipe
{
    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        super.doInit(phase, helper);

        JEICompatRegistry.addCraftingRecipe(this);
    }

    @Override
    protected boolean isReady()
    {
        return true;
    }

    public String getUid()
    {
        return recipeList.toString() + "#" + "crafting_recipes";
    }
}
