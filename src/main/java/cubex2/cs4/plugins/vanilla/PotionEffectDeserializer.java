package cubex2.cs4.plugins.vanilla;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cubex2.cs4.api.WrappedPotionEffect;

import java.lang.reflect.Type;

public class PotionEffectDeserializer implements JsonDeserializer<WrappedPotionEffect>
{
    @Override
    public WrappedPotionEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return context.deserialize(json, WrappedPotionEffectImpl.class);
    }
}
