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
                int col = (int) Long.parseLong(s, 16);
                return new ColorImpl(s.length() <= 6 ? 0xff000000 | col : col);
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
        colorLookup.put("black", 0xff000000);
        colorLookup.put("white", 0xffffffff);
        colorLookup.put("red", 0xffff0000);
        colorLookup.put("lime", 0xff00ff00);
        colorLookup.put("blue", 0xff0000FF);
        colorLookup.put("yellow", 0xffffff00);
        colorLookup.put("aqua", 0xff00ffff);
        colorLookup.put("magenta", 0xffff00ff);
        colorLookup.put("silver", 0xffc0c0c0);
        colorLookup.put("gray", 0xff808080);
        colorLookup.put("maroon", 0xff800000);
        colorLookup.put("olive", 0xff808000);
        colorLookup.put("green", 0xff008000);
        colorLookup.put("purple", 0xff800080);
        colorLookup.put("teal", 0xff008080);
        colorLookup.put("navy", 0xff000080);
        colorLookup.put("foliagePine", 0x619961);
        colorLookup.put("foliageBirch", 0x80a755);
        colorLookup.put("foliageBasic", 0x48b518);
    }
}
