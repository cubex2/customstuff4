package cubex2.cs4.plugins;

import cubex2.cs4.api.CS4Plugin;
import cubex2.cs4.api.ContentRegistry;
import cubex2.cs4.api.CustomStuffPlugin;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.data.ContentLoader;
import cubex2.cs4.data.InitPhaseDeserializer;

@CS4Plugin
public class BasePlugin implements CustomStuffPlugin
{
    @Override
    public void registerContent(ContentRegistry registry)
    {
        registry.registerDeserializer(InitPhase.class, new InitPhaseDeserializer());
        registry.registerDeserializer(ContentLoader.class, ContentLoader.DESERIALIZER);

        registry.registerContentType("contentLoader", ContentLoader.class);

        registry.registerLoaderPredicate("requireModsLoaded", new ModLoadedPredicate());
        registry.registerLoaderPredicate("requireModsNotLoaded", new ModNotLoadedPredicate());
    }
}
