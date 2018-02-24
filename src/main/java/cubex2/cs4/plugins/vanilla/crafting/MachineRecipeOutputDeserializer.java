package cubex2.cs4.plugins.vanilla.crafting;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.api.WrappedFluidStack;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class MachineRecipeOutputDeserializer implements JsonDeserializer<MachineRecipeOutput>
{
    @Override
    public MachineRecipeOutput deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString())
        {
            return fromString(json.getAsJsonPrimitive(), context);
        } else if (json.isJsonObject())
        {
            return fromObject(json.getAsJsonObject(), context);
        }

        throw new JsonParseException("Invalid machine recipe output: " + json.toString());
    }

    private MachineRecipeOutput fromString(JsonPrimitive s, JsonDeserializationContext context)
    {
        List<MachineResult> outputItems = Collections.singletonList(context.deserialize(s, MachineResult.class));
        List<WrappedFluidStack> outputFluids = Collections.emptyList();
        int weight = 1;

        return new MachineRecipeOutputImpl(outputItems, outputFluids, weight);
    }

    private MachineRecipeOutput fromObject(JsonObject jsonObject, JsonDeserializationContext context)
    {
        List<MachineResult> outputItems = Collections.emptyList();
        List<WrappedFluidStack> outputFluids = Collections.emptyList();
        int weight = 1;

        if (jsonObject.has("items"))
        {
            outputItems = context.deserialize(jsonObject.get("items"), new TypeToken<List<MachineResult>>() {}.getType());
        }

        if (jsonObject.has("fluids"))
        {
            outputFluids = context.deserialize(jsonObject.get("fluids"), new TypeToken<List<WrappedFluidStack>>() {}.getType());
        }

        if (jsonObject.has("weight"))
        {
            weight = context.deserialize(jsonObject.get("weight"), int.class);
        }

        return new MachineRecipeOutputImpl(outputItems, outputFluids, weight);
    }
}
