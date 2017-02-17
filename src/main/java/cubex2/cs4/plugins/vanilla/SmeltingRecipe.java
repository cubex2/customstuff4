package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

class SmeltingRecipe implements Content
{
    WrappedItemStack input;
    WrappedItemStack result;
    float xp;

    private boolean initialized = false;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (!initialized && isReady())
        {
            GameRegistry.addSmelting(input.createItemStack(), result.createItemStack(), xp);

            initialized = true;
        }
    }

    private boolean isReady()
    {
        return input.isItemLoaded() && result.isItemLoaded();
    }
}
