package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;

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
            Pair<String, Integer> pair = parseItemPart(primitive.getAsString());

            WrappedItemStackImpl stack = new WrappedItemStackImpl();
            stack.item = context.deserialize(new JsonPrimitive(pair.getLeft()), ResourceLocation.class);
            stack.metadata = pair.getRight();
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
        {
            JsonElement element = jsonObject.get("item");
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString())
            {
                Pair<String, Integer> pair = parseItemPart(element.getAsString());
                element = new JsonPrimitive(pair.getLeft());
                stack.metadata = pair.getRight();
            }

            stack.item = context.deserialize(element, ResourceLocation.class);
        }

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

        if (jsonObject.has("nbt"))
        {
            stack.nbt = context.deserialize(jsonObject.get("nbt"), NBTTagCompound.class);
        }

        return stack;
    }

    private Pair<String, Integer> parseItemPart(String input)
    {
        int meta = 0;

        if (input.contains("@"))
        {
            String metaPart = input.substring(input.lastIndexOf('@') + 1);
            input = input.substring(0, input.lastIndexOf('@'));

            if (metaPart.equalsIgnoreCase(WILDCARD_STRING))
            {
                meta = OreDictionary.WILDCARD_VALUE;
            } else
            {
                meta = Integer.parseInt(metaPart);
            }
        }

        return Pair.of(input, meta);
    }
}
