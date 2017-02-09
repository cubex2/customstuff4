package cubex2.cs4.data;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import cubex2.cs4.api.Content;
import cubex2.cs4.util.IOHelper;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ContentLoader implements Content
{
    public String type;
    public String file;

    public List<? extends Content> loadContent(File modDirectory, ContentFactory factory)
    {
        String json = IOHelper.readStringFromModFile(modDirectory, file);
        Class<? extends Content> contentClass = factory.getContentClass(type);
        if (json != null && contentClass != null)
        {
            return loadContent(json, contentClass);
        } else
        {
            return Collections.emptyList();
        }
    }

    static <T extends Content> List<T> loadContent(String json, Class<T> contentClass)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<List<T>>() {}.getType(), deserializer(contentClass))
                                     .create();

        List<T> result = Lists.newArrayList();

        Map<String, List<T>> map = gson.fromJson(json, new TypeToken<Map<String, List<T>>>() {}.getType());

        map.values().forEach(result::addAll);

        return result;
    }

    private static <T extends Content> JsonDeserializer<List<T>> deserializer(Class<T> contentClass)
    {
        return (json, typeOfT, context) ->
        {
            List<T> result = Lists.newArrayList();

            if (json.isJsonArray())
            {
                JsonArray array = json.getAsJsonArray();
                for (int i = 0; i < array.size(); i++)
                {
                    result.add(context.deserialize(array.get(i), contentClass));
                }
            } else if (json.isJsonObject())
            {
                result.add(context.deserialize(json, contentClass));
            }

            return result;
        };
    }
}
