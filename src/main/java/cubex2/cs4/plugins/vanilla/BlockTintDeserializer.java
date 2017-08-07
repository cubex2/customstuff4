package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.BlockTint;
import cubex2.cs4.api.Color;

import java.lang.reflect.Type;

class BlockTintDeserializer implements JsonDeserializer<BlockTint>
{
    private final BlockTintRegistry registry;

    BlockTintDeserializer(BlockTintRegistry registry) {this.registry = registry;}

    @Override
    public BlockTint deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive())
        {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString())
            {
                String string = primitive.getAsString();
                BlockTint tint = registry.getBlockTint(string);
                if (tint != null) return tint;

                Color color = context.deserialize(json, Color.class);
                if (color != null)
                {
                    int rgb = color.getRGB();
                    return (world, pos) -> rgb;
                }
            }
        }

        throw new JsonParseException("Invalid block tint: " + json);
    }
}
