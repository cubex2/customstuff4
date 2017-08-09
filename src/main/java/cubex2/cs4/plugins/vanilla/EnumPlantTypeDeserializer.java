package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import net.minecraftforge.common.EnumPlantType;

import java.lang.reflect.Type;

public class EnumPlantTypeDeserializer implements JsonDeserializer<EnumPlantType>
{
    @Override
    public EnumPlantType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive())
        {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString())
            {
                return EnumPlantType.getPlantType(json.getAsString());
            }
        }

        throw new JsonParseException("Invalid plant type: " + json);
    }
}
