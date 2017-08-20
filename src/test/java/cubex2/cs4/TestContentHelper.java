package cubex2.cs4;

import com.google.common.collect.Maps;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;

import javax.annotation.Nullable;
import java.util.Map;

public class TestContentHelper implements ContentHelper
{
    private final Map<String, String> jsonByPath = Maps.newHashMap();
    private final Map<String, Class<? extends Content>> classByType = Maps.newHashMap();

    private final String modId;
    private final String defaultJson;
    private final Class<? extends Content> defaultClazz;

    public TestContentHelper(String defaultJson, Class<? extends Content> defaultClazz)
    {
        this("testmod", defaultJson, defaultClazz);
    }

    public TestContentHelper(String modId, String defaultJson, Class<? extends Content> defaultClazz)
    {
        this.modId = modId;
        this.defaultJson = defaultJson;
        this.defaultClazz = defaultClazz;
    }

    public TestContentHelper withJson(String path, String json)
    {
        jsonByPath.put(path, json);
        return this;
    }

    public TestContentHelper withClass(String typeName, Class<? extends Content> clazz)
    {
        classByType.put(typeName, clazz);
        return this;
    }

    @Nullable
    @Override
    public String readJson(String path)
    {
        return jsonByPath.getOrDefault(path, defaultJson);
    }

    @Nullable
    @Override
    public Class<? extends Content> getContentClass(String typeName)
    {
        return classByType.getOrDefault(typeName, defaultClazz);
    }

    @Override
    public String getModId()
    {
        return modId;
    }
}
