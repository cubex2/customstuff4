package cubex2.cs4.util;

import com.google.common.collect.Lists;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A deserializer for lists where a single element list can be specified without using brackets in the json file.
 */
public class ListDeserializer<T> implements JsonDeserializer<List<T>>
{
    private final Class<T> elementClass;

    public ListDeserializer(Class<T> elementClass)
    {
        this.elementClass = elementClass;
    }

    @Override
    public List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonArray())
        {
            JsonArray array = json.getAsJsonArray();
            List<T> result = Lists.newArrayListWithExpectedSize(array.size());
            for (int i = 0; i < array.size(); i++)
            {
                result.add(context.deserialize(array.get(i), elementClass));
            }
            return result;
        }

        List<T> result = Lists.newArrayListWithExpectedSize(1);
        result.add(context.deserialize(json, elementClass));
        return result;
    }
}
