package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.OreClass;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;

import java.lang.reflect.Type;

class RecipeInputDeserializer implements JsonDeserializer<RecipeInput>
{
    @Override
    public RecipeInput deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        RecipeInputImpl input = new RecipeInputImpl();

        if (json.isJsonPrimitive())
        {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString())
            {
                String string = primitive.getAsString();

                if (string.toLowerCase().startsWith("oreclass:"))
                {
                    input.oreClass = new OreClass(string.substring("oreclass:".length()), 1);
                } else if (string.toLowerCase().startsWith("ore:"))
                {
                    input.oreClass = new OreClass(string.substring("ore:".length()), 1);
                }
                {
                    input.stack = context.deserialize(json, WrappedItemStack.class);
                }

                return input;
            } else
            {
                throw new JsonParseException("Invalid element for stack.");
            }
        } else if (json.isJsonObject() && json.getAsJsonObject().has("ore"))
        {
            JsonObject object = json.getAsJsonObject();
            String ore = object.get("ore").getAsString();
            int amount = object.has("amount") ? object.get("amount").getAsInt() : 1;

            input.oreClass = new OreClass(ore, amount);
            return input;
        } else
        {
            input.stack = context.deserialize(json, WrappedItemStack.class);
            return input;
        }
    }
}
