package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import com.google.gson.*;
import cubex2.cs4.api.WrappedBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                properties = deserializeProperties(jsonObject.get("properties"));
            }
        } else
        {
            block = context.deserialize(json, ResourceLocation.class);
        }

        return new WrappedBlockStateImpl(block, properties);
    }

    private List<Tuple<String, String>> deserializeProperties(JsonElement element)
    {
        if (element.isJsonObject())
        {
            return element.getAsJsonObject().entrySet().stream()
                          .map(e -> new Tuple<>(e.getKey(), e.getValue().getAsString()))
                          .collect(Collectors.toList());
        } else
        {
            return Arrays.stream(element.getAsString().split(","))
                         .map(s -> s.split("="))
                         .map(a -> new Tuple<>(a[0], a[1]))
                         .collect(Collectors.toList());
        }
    }
}
