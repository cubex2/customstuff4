package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.data.SimpleContent;
import net.minecraft.util.ResourceLocation;

import java.util.List;

class FluidModifier extends SimpleContent
{
    ResourceLocation block;
    Boolean canCreateSource = null;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        modifiers.add(this);
    }

    @Override
    protected boolean isReady()
    {
        return true;
    }

    private static final List<FluidModifier> modifiers = Lists.newLinkedList();

    static List<FluidModifier> getModifiers()
    {
        return modifiers;
    }
}
