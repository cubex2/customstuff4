package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.item.EnumAction;

import java.lang.reflect.Type;
import java.util.Map;

class EnumActionDeserializer implements JsonDeserializer<EnumAction>
{
    private final Map<String, EnumAction> map = Maps.newHashMap();

    public EnumActionDeserializer()
    {
        map.put("none", EnumAction.NONE);
        map.put("eat", EnumAction.EAT);
        map.put("drink", EnumAction.DRINK);
        map.put("block", EnumAction.BLOCK);
        map.put("bow", EnumAction.BOW);
    }

    @Override
    public EnumAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return map.getOrDefault(json.getAsString(), EnumAction.NONE);
    }
}
