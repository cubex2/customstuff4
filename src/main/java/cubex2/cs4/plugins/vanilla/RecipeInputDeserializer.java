package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
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
                    input.oreClass = string.substring("oreclass:".length());
                } else
                {
                    input.stack = context.deserialize(json, WrappedItemStack.class);
                }

                return input;
            } else
            {
                throw new JsonParseException("Invalid element for stack.");
            }
        } else
        {
            input.stack = context.deserialize(json, WrappedItemStack.class);
            return input;
        }
    }
}
