package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import net.minecraft.util.BlockRenderLayer;

import java.lang.reflect.Type;

public class BlockRenderLayerDeserializer implements JsonDeserializer<BlockRenderLayer>
{
    @Override
    public BlockRenderLayer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive())
        {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString())
            {
                String string = primitive.getAsString();
                if (string.equals("solid"))
                    return BlockRenderLayer.SOLID;
                if (string.equals("mippedCutout"))
                    return BlockRenderLayer.CUTOUT_MIPPED;
                if (string.equals("cutout"))
                    return BlockRenderLayer.CUTOUT;
                if (string.equals("translucent"))
                    return BlockRenderLayer.TRANSLUCENT;
            }
        }

        throw new JsonParseException("Invalid block render layer: " + json);
    }
}
