package cubex2.cs4.plugins.vanilla.crafting;

import com.google.gson.JsonDeserializer;
import cubex2.cs4.api.WrappedItemStack;

public class MachineResult
{
    public WrappedItemStack item;
    public float chance = 1.0f;

    public static final JsonDeserializer<MachineResult> DESERIALIZER = (json, typeOfT, context) ->
    {
        MachineResult result = new MachineResult();

        result.item = context.deserialize(json, WrappedItemStack.class);

        if (json.isJsonObject() && json.getAsJsonObject().has("chance"))
        {
            result.chance = json.getAsJsonObject().get("chance").getAsFloat();
        }

        return result;
    };
}
