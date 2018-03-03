package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.util.IntRange;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

public class BlockDropDeserializer implements JsonDeserializer<BlockDrop>
{
    @Override
    public BlockDrop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonElement amountElem = getAndRemoveElem(json, "amount");
        JsonElement fortuneElem = getAndRemoveElem(json, "fortuneAmount");

        WrappedItemStack item = context.deserialize(json, WrappedItemStack.class);
        IntRange amount = amountElem != null ? context.deserialize(amountElem, IntRange.class)
                                             : IntRange.create(1, 1);
        IntRange fortuneAmount = fortuneElem != null ? context.deserialize(fortuneElem, IntRange.class)
                                                     : IntRange.ZERO;

        return new BlockDrop(item, amount, fortuneAmount);
    }


    @Nullable
    private JsonElement getAndRemoveElem(JsonElement json, String elemName)
    {
        JsonElement elem = null;
        if (json.isJsonObject())
        {
            JsonObject object = json.getAsJsonObject();
            if (object.has(elemName))
            {
                elem = object.get(elemName);
                object.remove(elemName);
            }
        }

        return elem;
    }
}
