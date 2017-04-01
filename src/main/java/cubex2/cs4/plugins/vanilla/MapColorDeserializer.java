package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.block.material.MapColor;

import java.lang.reflect.Type;
import java.util.Map;

import static net.minecraft.block.material.MapColor.*;

class MapColorDeserializer implements JsonDeserializer<MapColor>
{
    @Override
    public MapColor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return colorsByName.getOrDefault(json.getAsString(), AIR);
    }

    private static final Map<String, MapColor> colorsByName = Maps.newHashMap();

    private static void add(String name, MapColor color)
    {
        colorsByName.put(name, color);
    }

    static
    {
        add("air", AIR);
        add("grass", GRASS);
        add("sand", SAND);
        add("cloth", CLOTH);
        add("tnt", TNT);
        add("ice", ICE);
        add("iron", IRON);
        add("foliage", FOLIAGE);
        add("snow", SNOW);
        add("clay", CLAY);
        add("dirt", DIRT);
        add("stone", STONE);
        add("water", WATER);
        add("wood", WOOD);
        add("quartz", QUARTZ);
        add("adobe", ADOBE);
        add("magenta", MAGENTA);
        add("light_blue", LIGHT_BLUE);
        add("yellow", YELLOW);
        add("lime", LIME);
        add("pink", PINK);
        add("gray", GRAY);
        add("silver", SILVER);
        add("cyan", CYAN);
        add("purple", PURPLE);
        add("blue", BLUE);
        add("brown", BROWN);
        add("green", GREEN);
        add("red", RED);
        add("black", BLACK);
        add("gold", GOLD);
        add("diamond", DIAMOND);
        add("lapis", LAPIS);
        add("emerald", EMERALD);
        add("obsidian", OBSIDIAN);
        add("netherrack", NETHERRACK);
    }
}
