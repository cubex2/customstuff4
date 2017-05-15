package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import net.minecraftforge.fml.common.registry.GameRegistry;

class SmeltingRecipe extends SimpleContent
{
    WrappedItemStack input;
    WrappedItemStack result;
    float xp;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        GameRegistry.addSmelting(input.getItemStack(), result.getItemStack(), xp);
    }

    @Override
    protected boolean isReady()
    {
        return input.isItemLoaded() && result.isItemLoaded();
    }
}
