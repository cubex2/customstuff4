package cubex2.cs4.data;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentRegistry;

public class ContentRegistryImpl implements ContentRegistry
{
    @Override
    public <T extends Content> void registerContentType(String typeName, Class<T> clazz)
    {

    }
}
