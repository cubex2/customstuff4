package cubex2.cs4.plugins.vanilla;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cubex2.cs4.api.EntitySelector;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class EntitySelectorDeserializer implements JsonDeserializer<EntitySelector>
{
    private final EntitySelectorRegistry registry;

    public EntitySelectorDeserializer(EntitySelectorRegistry registry) {this.registry = registry;}

    @Override
    public EntitySelector deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        String type = json.getAsString();

        EntitySelector<?> selector = registry.getEntitySelector(type);
        if (selector != null)
        {
            return selector;
        }

        return new EntitySelectorFromMobName(new ResourceLocation(type));
    }
}
