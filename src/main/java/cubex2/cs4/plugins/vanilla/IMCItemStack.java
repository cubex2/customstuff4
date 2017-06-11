package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;

class IMCItemStack extends IMCBase<WrappedItemStack>
{
    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        FMLInterModComms.sendMessage(modId, key, value.getItemStack().copy());
    }

    @Override
    protected boolean isReady()
    {
        return value.isItemLoaded();
    }
}
