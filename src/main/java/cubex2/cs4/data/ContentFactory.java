package cubex2.cs4.data;

import cubex2.cs4.api.Content;

import javax.annotation.Nullable;

public interface ContentFactory
{
    @Nullable
    Content createInstance(String typeName);

    @Nullable
    Class<? extends Content> getContentClass(String typeName);
}
