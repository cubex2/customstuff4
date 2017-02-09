package cubex2.cs4.data;

import com.google.common.collect.Maps;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentRegistry;

import javax.annotation.Nullable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class ContentRegistryImpl implements ContentRegistry
{
    private final Map<String, Class<? extends Content>> types = Maps.newHashMap();

    @Override
    public <T extends Content> void registerContentType(String typeName, Class<T> clazz)
    {
        checkArgument(!types.containsKey(typeName), "Duplicate typeName: %s", typeName);

        types.put(typeName, clazz);
    }

    @Nullable
    @Override
    public Class<? extends Content> getContentClass(String typeName)
    {
        return types.get(typeName);
    }
}
