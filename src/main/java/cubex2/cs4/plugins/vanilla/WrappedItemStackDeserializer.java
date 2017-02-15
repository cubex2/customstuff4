package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

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
            WrappedItemStackImpl stack = new WrappedItemStackImpl();
            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("item"))
                stack.item = context.deserialize(jsonObject.get("item"), ResourceLocation.class);

            if (jsonObject.has("amount"))
                stack.amount = jsonObject.get("amount").getAsInt();

            if (jsonObject.has("metadata"))
            {
                JsonPrimitive metadata = jsonObject.getAsJsonPrimitive("metadata");
                if (metadata.isString())
                {
                    if (metadata.getAsString().toLowerCase().equals("all"))
                    {
                        stack.metadata = OreDictionary.WILDCARD_VALUE;
                    } else
                    {
                        throw new JsonParseException("Invalid value for metadata: " + metadata.getAsString());
                    }
                } else
                {
                    stack.metadata = metadata.getAsInt();
                }
            }

            return stack;
        }
    }
}
