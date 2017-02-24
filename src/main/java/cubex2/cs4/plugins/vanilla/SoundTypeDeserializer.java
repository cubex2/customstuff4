package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.block.SoundType;

import java.lang.reflect.Type;
import java.util.Map;

class SoundTypeDeserializer implements JsonDeserializer<SoundType>
{
    private static Map<String, SoundType> stepSoundMap = Maps.newHashMap();

    @Override
    public SoundType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return stepSoundMap.get(json.getAsString());
    }

    static
    {
        stepSoundMap.put("anvil", SoundType.ANVIL);
        stepSoundMap.put("cloth", SoundType.CLOTH);
        stepSoundMap.put("glass", SoundType.GLASS);
        stepSoundMap.put("grass", SoundType.PLANT);
        stepSoundMap.put("gravel", SoundType.GROUND);
        stepSoundMap.put("ladder", SoundType.LADDER);
        stepSoundMap.put("metal", SoundType.METAL);
        stepSoundMap.put("sand", SoundType.SAND);
        stepSoundMap.put("slime", SoundType.SLIME);
        stepSoundMap.put("snow", SoundType.SNOW);
        stepSoundMap.put("stone", SoundType.STONE);
        stepSoundMap.put("wood", SoundType.WOOD);
    }
}
