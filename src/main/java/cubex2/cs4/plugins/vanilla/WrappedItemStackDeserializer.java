package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

class WrappedItemStackDeserializer implements JsonDeserializer<WrappedItemStack>
{
    @Override
    public WrappedItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive())
        {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString())
            {
                WrappedItemStackImpl stack = new WrappedItemStackImpl();
                stack.item = context.deserialize(json, ResourceLocation.class);
                return stack;
            } else
            {
                throw new JsonParseException("Invalid element for stack.");
            }
        } else
        {
            return context.deserialize(json, WrappedItemStackImpl.class);
        }
    }
}
