package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import net.minecraftforge.oredict.OreDictionary;

class OreDictionaryEntry extends SimpleContent
{
    String oreClass;
    WrappedItemStack item;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        OreDictionary.registerOre(oreClass, item.getItemStack());
    }

    @Override
    protected boolean isReady()
    {
        return item.isItemLoaded();
    }
}
