package cubex2.cs4.plugins.vanilla;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cubex2.cs4.api.Color;

import java.lang.reflect.Type;

class ColorDeserializer implements JsonDeserializer<Color>
{
    private final ColorRegistry registry;

    public ColorDeserializer(ColorRegistry registry)
    {
        this.registry = registry;
    }

    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber())
        {
            return new ColorImpl(json.getAsInt());
        } else if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString())
        {
            String s = json.getAsJsonPrimitive().getAsString();

            if (s.matches("[0-9a-fA-F]+"))
            {
                int col = (int) Long.parseLong(s, 16);
                return new ColorImpl(s.length() <= 6 ? 0xff000000 | col : col);
            } else
            {
                return new ColorImpl(registry.getColor(s));
            }
        }

        throw new JsonParseException("Invalid element for color: " + json.toString());
    }
}
