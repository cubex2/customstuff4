package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.CS4Plugin;
import cubex2.cs4.api.ContentRegistry;
import cubex2.cs4.api.CustomStuffPlugin;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.util.ResourceLocation;

@CS4Plugin
public class VanillaPlugin implements CustomStuffPlugin
{
    @Override
    public void registerContent(ContentRegistry registry)
    {
        registry.registerDeserializer(ResourceLocation.class, new ResourceLocationDeserializer());
        registry.registerDeserializer(WrappedItemStack.class, new WrappedItemStackDeserializer());
    }
}
