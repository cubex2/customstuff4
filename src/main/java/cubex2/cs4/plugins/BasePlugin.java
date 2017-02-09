package cubex2.cs4.plugins;

import cubex2.cs4.api.CS4Plugin;
import cubex2.cs4.api.ContentRegistry;
import cubex2.cs4.api.CustomStuffPlugin;
import cubex2.cs4.data.ContentLoader;

@CS4Plugin
public class BasePlugin implements CustomStuffPlugin
{
    @Override
    public void registerContentType(ContentRegistry registry)
    {
        registry.registerContentType("contentLoader", ContentLoader.class);
    }
}
