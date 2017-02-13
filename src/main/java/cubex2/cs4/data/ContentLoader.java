package cubex2.cs4.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.LoaderPredicate;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public final class ContentLoader implements Content
{
    public String type = "";
    public String file = "";
    public Map<String, List<String>> predicateMap = Maps.newHashMap();

    private final List<Content> contents = Lists.newArrayList();

    List<Content> getContents()
    {
        return contents;
    }

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        contents.forEach(content -> content.init(phase, helper));
    }

    public void deserializeContent(ContentHelper helper)
    {
        if (shouldInit())
        {
            String json = helper.readJson(file);
            Class<? extends Content> contentClass = helper.getContentClass(type);
            if (json != null && contentClass != null)
            {
                List<? extends Content> contents = loadContent(json, contentClass, CustomStuff4.contentRegistry);
                this.contents.addAll(contents);
                for (Content content : this.contents)
                {
                    if (content instanceof ContentLoader)
                    {
                        ((ContentLoader) content).deserializeContent(helper);
                    }
                }
            }
        }
    }

    boolean shouldInit()
    {
        if (type == null || file == null)
            return false;

        return checkPredicates(CustomStuff4.contentRegistry);
    }

    boolean checkPredicates(LoaderPredicateRegistry registry)
    {
        return predicateMap.entrySet().stream()
                           .allMatch(e ->
                                     {
                                         LoaderPredicate predicate = registry.getPredicate(e.getKey());
                                         return predicate != null && predicate.getResult(e.getValue());
                                     });

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

    static GsonBuilder registerAdapters(GsonBuilder builder, DeserializationRegistry registry)
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

    public static final JsonDeserializer<ContentLoader> DESERIALIZER = (json, typeOfT, context) ->
    {
        ContentLoader loader = new ContentLoader();

        JsonObject jsonObject = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
        {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (key.equals("type"))
            {
                loader.type = value.getAsString();
            } else if (key.equals("file"))
            {
                loader.file = value.getAsString();
            } else
            {
                List<String> predicateValues = Lists.newArrayList();

                if (value.isJsonArray())
                {
                    JsonArray jsonArray = value.getAsJsonArray();
                    jsonArray.forEach(element -> predicateValues.add(element.getAsString()));
                } else
                {
                    predicateValues.add(value.getAsString());
                }

                loader.predicateMap.put(key, predicateValues);
            }
        }

        return loader;
    };
}
