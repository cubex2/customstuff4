package cubex2.cs4.plugins.vanilla;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.lang.reflect.Type;

public class SoundEventDeserializer implements JsonDeserializer<SoundEvent>
{
    @Override
    public SoundEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        ResourceLocation key = context.deserialize(json, ResourceLocation.class);
        SoundEvent event = SoundEvent.REGISTRY.getObject(key);

        return event;
    }
}
