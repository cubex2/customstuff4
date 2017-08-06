package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.plugins.vanilla.block.BlockMixin;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;

public class AxisAlignedBBDeserializer implements JsonDeserializer<AxisAlignedBB>
{
    @Nullable
    @Override
    public AxisAlignedBB deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonObject())
        {
            JsonObject object = json.getAsJsonObject();
            if (object.has("from") && object.has("to"))
            {
                return fromTo(object, context);
            }

            if (object.has("cube"))
            {
                return cube(object, context);
            }
        } else if (json.isJsonPrimitive())
        {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString())
            {
                String string = primitive.getAsString();

                if (string.equals("empty") || string.equals("null"))
                {
                    return null;
                }

                if (string.equals("default"))
                {
                    return BlockMixin.DEFAULT_AABB_MARKER;
                }
            }
        }

        throw new JsonParseException("Invalid bounding box: " + json);
    }

    private AxisAlignedBB cube(JsonObject object, JsonDeserializationContext context)
    {
        float size = object.get("cube").getAsFloat();
        float[] offset = getOffset(object, context);

        return new AxisAlignedBB(offset[0], offset[1], offset[2],
                                 size + offset[0], size + offset[1], size + offset[2]);
    }

    private AxisAlignedBB fromTo(JsonObject object, JsonDeserializationContext context) throws JsonParseException
    {
        float[] from = context.deserialize(object.get("from"), float[].class);
        float[] to = context.deserialize(object.get("to"), float[].class);

        if (from.length != 3)
            throw new JsonParseException("from has " + from.length + " elements, expected 3");
        if (to.length != 3)
            throw new JsonParseException("to has " + to.length + " elements, expected 3");

        float[] offset = getOffset(object, context);

        float minX = Math.min(from[0], to[0]) + offset[0];
        float minY = Math.min(from[1], to[1]) + offset[1];
        float minZ = Math.min(from[2], to[2]) + offset[2];

        float maxX = Math.max(from[0], to[0]) + offset[0];
        float maxY = Math.max(from[1], to[1]) + offset[1];
        float maxZ = Math.max(from[2], to[2]) + offset[2];

        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Nonnull
    private float[] getOffset(JsonObject object, JsonDeserializationContext context)
    {
        if (object.has("offset"))
        {
            float[] offset = context.deserialize(object.get("offset"), float[].class);

            if (offset.length != 3)
                throw new JsonParseException("offset has " + offset.length + " elements, expected 3");

            return offset;
        }

        return new float[3];
    }
}
