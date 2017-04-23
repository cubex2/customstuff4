package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import com.google.gson.*;
import cubex2.cs4.api.WrappedBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

class WrappedBlockStateDeserializer implements JsonDeserializer<WrappedBlockState>
{
    @Override
    public WrappedBlockState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        ResourceLocation block;
        List<Tuple<String, String>> properties = Lists.newArrayList();

        if (json.isJsonObject())
        {
            JsonObject jsonObject = json.getAsJsonObject();
            block = context.deserialize(jsonObject.get("block"), ResourceLocation.class);

            if (jsonObject.has("properties"))
            {
                JsonObject jsonProperties = jsonObject.getAsJsonObject("properties");
                for (Map.Entry<String, JsonElement> entry : jsonProperties.entrySet())
                {
                    String name = entry.getKey();
                    String value = entry.getValue().getAsString();

                    properties.add(new Tuple<>(name, value));
                }
            }
        } else
        {
            block = context.deserialize(json, ResourceLocation.class);
        }

        return new WrappedBlockStateImpl(block, properties);
    }
}
