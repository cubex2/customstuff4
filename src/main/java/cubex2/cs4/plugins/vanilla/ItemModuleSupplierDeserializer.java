package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.ItemModuleSupplier;

import java.lang.reflect.Type;

class ItemModuleSupplierDeserializer implements JsonDeserializer<ItemModuleSupplier>
{
    private final ItemModuleRegistry registry;

    ItemModuleSupplierDeserializer(ItemModuleRegistry registry) {this.registry = registry;}

    @Override
    public ItemModuleSupplier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();

        String type = jsonObject.get("type").getAsString();

        Class<? extends ItemModuleSupplier> clazz = registry.getItemModuleClass(type);

        return context.deserialize(json, clazz);
    }
}
