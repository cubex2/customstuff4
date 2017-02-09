package cubex2.cs4.data;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public final class ContentLoader implements Content
{
    public String type = "";
    public String file = "";
    public InitPhase initPhase = null;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (shouldInit(phase))
        {
            String json = helper.readJson(file);
            Class<? extends Content> contentClass = helper.getContentClass(type);
            if (json != null && contentClass != null)
            {
                List<? extends Content> contents = loadContent(json, contentClass, CustomStuff4.contentRegistry);
                contents.forEach(content -> content.init(phase, helper));
            }
        }
    }

    boolean shouldInit(InitPhase phase)
    {
        if (type == null || file == null)
            return false;

        return initPhase == null || initPhase == phase;
    }

    public static <T extends Content> List<T> loadContent(String json, Class<T> contentClass, DeserializationRegistry registry)
    {
        Gson gson = registerAdapters(new GsonBuilder(), registry)
                .registerTypeAdapter(new TypeToken<List<T>>() {}.getType(), deserializer(contentClass))
                .create();

        List<T> result = Lists.newArrayList();

        Map<String, List<T>> map = gson.fromJson(json, new TypeToken<Map<String, List<T>>>() {}.getType());

        map.values().forEach(result::addAll);

        return result;
    }

    private static GsonBuilder registerAdapters(GsonBuilder builder, DeserializationRegistry registry)
    {
        for (Pair<Type, JsonDeserializer<?>> pair : registry.getDeserializers())
        {
            builder = builder.registerTypeAdapter(pair.getLeft(), pair.getRight());
        }
        return builder;
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
