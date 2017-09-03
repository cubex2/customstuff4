package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.util.IntRange;

import java.lang.reflect.Type;

class IntRangeDeserializer implements JsonDeserializer<IntRange>
{
    @Override
    public IntRange deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonArray())
        {
            JsonArray array = json.getAsJsonArray();

            if (array.size() != 2)
                throw new JsonSyntaxException("Invalid range. Array has " + array.size() + " elements. Expected 2.");

            int min = array.get(0).getAsInt();
            int max = array.get(1).getAsInt();

            return IntRange.create(min, max);
        } else
        {
            int value = json.getAsInt();
            return IntRange.create(value, value);
        }
    }
}
