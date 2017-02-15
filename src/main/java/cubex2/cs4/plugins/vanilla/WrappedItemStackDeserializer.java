package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Type;

class WrappedItemStackDeserializer implements JsonDeserializer<WrappedItemStack>
{
    private static final String WILDCARD_STRING = "all";

    @Override
    public WrappedItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive())
        {
            return fromString(json, context);
        } else
        {
            return fromObject(json, context);
        }
    }

    private WrappedItemStack fromString(JsonElement json, JsonDeserializationContext context)
    {
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        if (primitive.isString())
        {
            String string = primitive.getAsString();
            int meta = 0;

            if (string.contains("@"))
            {
                String metaPart = string.substring(string.lastIndexOf('@') + 1);
                string = string.substring(0, string.lastIndexOf('@'));

                if (metaPart.equalsIgnoreCase(WILDCARD_STRING))
                {
                    meta = OreDictionary.WILDCARD_VALUE;
                } else
                {
                    meta = Integer.parseInt(metaPart);
                }
            }

            WrappedItemStackImpl stack = new WrappedItemStackImpl();
            stack.item = context.deserialize(new JsonPrimitive(string), ResourceLocation.class);
            stack.metadata = meta;
            return stack;
        } else
        {
            throw new JsonParseException("Invalid element for stack.");
        }
    }

    private WrappedItemStack fromObject(JsonElement json, JsonDeserializationContext context)
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
                if (metadata.getAsString().toLowerCase().equals(WILDCARD_STRING))
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
