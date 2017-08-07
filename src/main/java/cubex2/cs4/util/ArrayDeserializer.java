package cubex2.cs4.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.function.IntFunction;

/**
 * A deserializer for arrays where a single element array be specified without using brackets in the json file.
 *
 * @param <T>
 */
public class ArrayDeserializer<T> implements JsonDeserializer<T[]>
{
    private final IntFunction<T[]> supplier;
    private final Class<T> elementClass;

    public ArrayDeserializer(IntFunction<T[]> supplier, Class<T> elementClass)
    {
        this.supplier = supplier;
        this.elementClass = elementClass;
    }

    @Override
    public T[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonArray())
        {
            JsonArray array = json.getAsJsonArray();
            T[] result = supplier.apply(array.size());
            for (int i = 0; i < array.size(); i++)
            {
                result[i] = context.deserialize(array.get(i), elementClass);
            }
            return result;
        }
        T[] result = supplier.apply(1);
        result[0] = context.deserialize(json, elementClass);
        return result;
    }
}
