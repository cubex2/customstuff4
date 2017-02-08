package cubex2.cs4.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import cubex2.cs4.api.Content;

import java.util.List;

public final class ContentList implements Content
{
    public List<ContentLoader> loaders = Lists.newArrayList();

    public static final JsonDeserializer<ContentList> DESERIALIZER = (json, typeOfT, context) ->
    {
        ContentList list = new ContentList();
        JsonArray jsonArray = json.getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++)
        {
            list.loaders.add(context.deserialize(jsonArray.get(i), ContentLoader.class));
        }

        return list;
    };
}
