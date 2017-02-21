package cubex2.cs4.plugins.vanilla;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cubex2.cs4.api.WrappedToolMaterial;

import java.lang.reflect.Type;

class ToolMaterialDeserializer implements JsonDeserializer<WrappedToolMaterial>
{
    @Override
    public WrappedToolMaterial deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return new WrappedToolMaterialImpl(json.getAsString());
    }
}
