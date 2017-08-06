package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.util.IntRange;

import java.lang.reflect.Type;

public class BlockDropDeserializer implements JsonDeserializer<BlockDrop>
{
    @Override
    public BlockDrop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonElement amountElem = null;
        if (json.isJsonObject())
        {
            JsonObject object = json.getAsJsonObject();
            if (object.has("amount"))
            {
                amountElem = object.get("amount");
                object.remove("amount");
            }
        }

        WrappedItemStack item = context.deserialize(json, WrappedItemStack.class);
        IntRange amount = context.deserialize(amountElem, IntRange.class);

        return new BlockDrop(item, amount);
    }
}
