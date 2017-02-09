package cubex2.cs4.data;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.ContentRegistry;
import cubex2.cs4.util.IOHelper;

import javax.annotation.Nullable;
import java.io.File;

public class ContentHelperImpl implements ContentHelper
{
    private final File modDirectory;
    private final ContentRegistry registry;

    public ContentHelperImpl(File modDirectory, ContentRegistry registry)
    {
        this.modDirectory = modDirectory;
        this.registry = registry;
    }

    @Nullable
    @Override
    public String readJson(String path)
    {
        String json = IOHelper.readStringFromModFile(modDirectory, path);
        return json == null ? "{}" : json;
    }

    @Nullable
    @Override
    public Class<? extends Content> getContentClass(String typeName)
    {
        return registry.getContentClass(typeName);
    }
}
