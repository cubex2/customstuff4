package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;
import cubex2.cs4.api.TileEntityModuleSupplier;

import java.lang.reflect.Type;

class TileEntityModuleSupplierDeserializer implements JsonDeserializer<TileEntityModuleSupplier>
{
    private final TileEntityModuleRegistry registry;

    TileEntityModuleSupplierDeserializer(TileEntityModuleRegistry registry) {this.registry = registry;}

    @Override
    public TileEntityModuleSupplier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();

        String type = jsonObject.get("type").getAsString();

        Class<? extends TileEntityModuleSupplier> clazz = registry.getTileEntityModuleClass(type);

        return context.deserialize(json, clazz);
    }
}
