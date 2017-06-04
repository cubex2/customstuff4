package cubex2.cs4.plugins.jei;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;

import java.util.Collections;
import java.util.List;

public class JEIDescription extends SimpleContent
{
    public List<WrappedItemStack> items = Collections.emptyList();
    public String[] desc = new String[0];

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        JEICompatRegistry.addDescription(this);
    }

    @Override
    protected boolean isReady()
    {
        return items.stream().allMatch(WrappedItemStack::isItemLoaded);
    }
}
