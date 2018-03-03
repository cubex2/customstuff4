package cubex2.cs4.plugins.vanilla;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cubex2.cs4.api.WrappedArmorMaterial;

import java.lang.reflect.Type;

public class WrappedArmorMaterialDeserializer implements JsonDeserializer<WrappedArmorMaterial>
{
    @Override
    public WrappedArmorMaterial deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (!json.isJsonPrimitive() || !json.getAsJsonPrimitive().isString())
            throw new JsonParseException("Invalid armor material:" + json.toString());

        return new WrappedArmorMaterialImpl(json.getAsJsonPrimitive().getAsString());
    }
}
