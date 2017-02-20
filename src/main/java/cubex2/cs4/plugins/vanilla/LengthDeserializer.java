package cubex2.cs4.plugins.vanilla;

import com.google.common.primitives.Floats;
import com.google.gson.*;

import java.lang.reflect.Type;

public class LengthDeserializer implements JsonDeserializer<Length>
{
    @Override
    public Length deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonArray())
        {
            JsonArray array = json.getAsJsonArray();

            Length left = deserializeSingleElement(array.get(0));
            Length right = deserializeSingleElement(array.get(1));

            return sum(left, right);
        } else
        {
            return deserializeSingleElement(json);
        }
    }

    private Length deserializeSingleElement(JsonElement json)
    {
        if (json.isJsonPrimitive())
        {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString())
            {
                String value = primitive.getAsString();
                if (value.endsWith("%"))
                {
                    value = value.substring(0, value.length() - 1);
                    Float percentValue = Floats.tryParse(value);
                    if (percentValue != null)
                        return relative(percentValue / 100f);
                }
            } else
            {
                return absolute(json.getAsInt());
            }
        }

        return Length.ZERO;
    }

    private static Length sum(Length left, Length right)
    {
        return parent -> left.getLength(parent) + right.getLength(parent);
    }

    private static Length relative(float relValue)
    {
        return parent -> (int) (relValue * parent);
    }

    private static Length absolute(int value)
    {
        return parent -> value;
    }
}
