package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

class NamedMapDeserializer<T> implements JsonDeserializer<Map<String, T>>
{
    private final Class<T> clazz;

    NamedMapDeserializer(Class<T> clazz) {this.clazz = clazz;}

    @Override
    public Map<String, T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Map<String, T> map = Maps.newLinkedHashMap();

        JsonArray array = json.getAsJsonArray();
        for (JsonElement element : array)
        {
            JsonObject object = element.getAsJsonObject();
            String name = object.get("name").getAsString();

            map.put(name, context.deserialize(element, clazz));
        }

        return map;
    }
}
