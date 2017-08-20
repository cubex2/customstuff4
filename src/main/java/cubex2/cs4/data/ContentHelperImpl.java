package cubex2.cs4.data;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.util.IOHelper;

import javax.annotation.Nullable;
import java.io.File;

public class ContentHelperImpl implements ContentHelper
{
    private final String modId;
    private final File modDirectory;

    public ContentHelperImpl(String modId, File modDirectory)
    {
        this.modId = modId;
        this.modDirectory = modDirectory;
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
        return CustomStuff4.contentRegistry.getContentClass(typeName);
    }

    @Override
    public String getModId()
    {
        return modId;
    }
}
