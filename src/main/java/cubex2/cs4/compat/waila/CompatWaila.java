package cubex2.cs4.compat.waila;

import mcp.mobius.waila.api.IWailaRegistrar;

@SuppressWarnings("unused")
public class CompatWaila
{
    public static void callbackRegister(IWailaRegistrar registrar)
    {
        WailaCS4DataProvider provider = new WailaCS4DataProvider();

        for (Class block : WailaData.stackProviderBlocks)
        {
            registrar.registerStackProvider(provider, block);
        }

        WailaData.stackProviderBlocks.clear();
    }
}
