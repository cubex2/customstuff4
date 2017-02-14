package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

class ResourceLocationDeserializer implements JsonDeserializer<ResourceLocation>
{
    @Override
    public ResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        ResourceLocation location = null;

        if (json.isJsonPrimitive())
        {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString())
            {
                location = new ResourceLocation(primitive.getAsString());
            }
        } else if (json.isJsonObject())
        {
            JsonObject object = json.getAsJsonObject();
            if (object.has("domain") && object.has("path"))
            {
                String domain = object.getAsJsonPrimitive("domain").getAsString();
                String path = object.getAsJsonPrimitive("path").getAsString();

                location = new ResourceLocation(domain, path);
            }
        }

        if (location == null)
            throw new JsonParseException("Invalid element for resource location");
        else
            return location;
    }
}
