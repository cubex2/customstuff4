package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.WrappedFluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Type;

class WrappedFluidStackDeserializer implements JsonDeserializer<WrappedFluidStack>
{
    @Override
    public WrappedFluidStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive())
        {
            return fromString(json, context);
        } else
        {
            return fromObject(json, context);
        }
    }

    private WrappedFluidStack fromString(JsonElement json, JsonDeserializationContext context)
    {
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        if (primitive.isString())
        {
            Pair<String, Integer> pair = parseFluidPart(primitive.getAsString());

            return new WrappedFluidStackImpl(pair.getLeft(), pair.getRight());
        } else
        {
            throw new JsonParseException("Invalid element for stack.");
        }
    }

    private WrappedFluidStack fromObject(JsonElement json, JsonDeserializationContext context)
    {
        WrappedFluidStackImpl stack = new WrappedFluidStackImpl();
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("fluid"))
        {
            JsonElement element = jsonObject.get("fluid");
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString())
            {
                Pair<String, Integer> pair = parseFluidPart(element.getAsString());
                element = new JsonPrimitive(pair.getLeft());
                stack.amount = pair.getRight();
            }

            stack.fluid = context.deserialize(element, String.class);
        }

        if (jsonObject.has("amount"))
            stack.amount = jsonObject.get("amount").getAsInt();

        return stack;
    }

    private Pair<String, Integer> parseFluidPart(String input)
    {
        int amount = 1000;

        if (input.contains("@"))
        {
            String amountPart = input.substring(input.lastIndexOf('@') + 1);
            input = input.substring(0, input.lastIndexOf('@'));

            amount = Integer.parseInt(amountPart);
        }

        return Pair.of(input, amount);
    }
}
