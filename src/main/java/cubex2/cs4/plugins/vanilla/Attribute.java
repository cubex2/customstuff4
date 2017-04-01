package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

public interface Attribute<T>
{
    Optional<T> get(int meta);

    boolean hasEntry(int meta);

    class FromMap<T> implements Attribute<T>
    {
        private final Map<Integer, T> map;

        FromMap()
        {
            this(Maps.newHashMap());
        }

        FromMap(Map<Integer, T> map)
        {
            this.map = map;
        }

        public Optional<T> get(int meta)
        {
            return Optional.ofNullable(map.get(meta));
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

    class Constant<T> implements Attribute<T>
    {
        private final T value;

        Constant(@Nullable T value) {this.value = value;}

        @Override
        public Optional<T> get(int meta)
        {
            return Optional.ofNullable(value);
        }

        @Override
        public boolean hasEntry(int meta)
        {
            return true;
        }
    }

    static <T> Attribute<T> map()
    {
        return new FromMap<>();
    }

    static <T> Attribute<T> map(Map<Integer, T> map)
    {
        return new FromMap<>(map);
    }

    static <T> Attribute<T> constant(@Nullable T value)
    {
        return new Constant<>(value);
    }

    static <T> JsonDeserializer<Attribute<T>> deserializer(Type elementType)
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
