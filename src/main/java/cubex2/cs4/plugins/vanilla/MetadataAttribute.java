package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;

public interface MetadataAttribute<T>
{
    @Nullable
    T get(int meta);

    boolean hasEntry(int meta);

    class FromMap<T> implements MetadataAttribute<T>
    {
        private Map<Integer, T> map = Maps.newHashMap();

        @Nullable
        public T get(int meta)
        {
            return map.get(meta);
        }

        public boolean hasEntry(int meta)
        {
            return map.containsKey(meta);
        }

        private void addEntry(int meta, T value)
        {
            map.put(meta, value);
        }
    }

    class Constant<T> implements MetadataAttribute<T>
    {
        private final T value;

        Constant(T value) {this.value = value;}

        @Nullable
        @Override
        public T get(int meta)
        {
            return value;
        }

        @Override
        public boolean hasEntry(int meta)
        {
            return true;
        }
    }

    static <T> MetadataAttribute<T> map()
    {
        return new FromMap<>();
    }

    static <T> MetadataAttribute<T> constant(T value)
    {
        return new Constant<>(value);
    }

    static <T> JsonDeserializer<MetadataAttribute<T>> deserializer(Type elementType)
    {
        return (json, typeOfT, context) ->
        {


            if (isMetaMap(json))
            {
                FromMap<T> map = new FromMap<>();
                json.getAsJsonObject().entrySet()
                    .forEach(e -> map.addEntry(Integer.parseInt(e.getKey()), context.deserialize(e.getValue(), elementType)));
                return map;
            } else
            {
                return constant(context.deserialize(json, elementType));
            }
        };
    }

    static boolean isMetaMap(JsonElement json)
    {
        return json.isJsonObject() && json.getAsJsonObject()
                                          .entrySet()
                                          .stream()
                                          .allMatch(e -> e.getKey().matches("[0-9]+"));
    }
}
