package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cubex2.cs4.api.Color;

import java.lang.reflect.Type;
import java.util.Map;

class ColorDeserializer implements JsonDeserializer<Color>
{
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
                return new ColorImpl(Integer.parseInt(s, 16));
            } else if (colorLookup.containsKey(s.toLowerCase()))
            {
                return new ColorImpl(colorLookup.get(s.toLowerCase()));
            }
        }

        throw new JsonParseException("Invalid element for color: " + json.toString());
    }

    private static final Map<String, Integer> colorLookup = Maps.newHashMap();

    static
    {
        colorLookup.put("black", 0x000000);
        colorLookup.put("white", 0xffffff);
        colorLookup.put("red", 0xff0000);
        colorLookup.put("lime", 0x00ff00);
        colorLookup.put("blue", 0x0000FF);
        colorLookup.put("yellow", 0xffff00);
        colorLookup.put("aqua", 0x00ffff);
        colorLookup.put("magenta", 0xff00ff);
        colorLookup.put("silver", 0xc0c0c0);
        colorLookup.put("gray", 0x808080);
        colorLookup.put("maroon", 0x800000);
        colorLookup.put("olive", 0x808000);
        colorLookup.put("green", 0x008000);
        colorLookup.put("purple", 0x800080);
        colorLookup.put("teal", 0x008080);
        colorLookup.put("navy", 0x000080);
    }
}
