package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import net.minecraftforge.fml.common.event.FMLInterModComms;

class IMCString extends IMCBase<String>
{
    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        FMLInterModComms.sendMessage(modId, key, value);
    }

    @Override
    protected boolean isReady()
    {
        return true;
    }
}
