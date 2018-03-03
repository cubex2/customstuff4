package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemArmor;

import java.lang.reflect.Type;
import java.util.Map;

public class ArmorMaterialDeserializer implements JsonDeserializer<ItemArmor.ArmorMaterial>
{
    @Override
    public ItemArmor.ArmorMaterial deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (!json.isJsonPrimitive() || !json.getAsJsonPrimitive().isString())
            throw new JsonParseException("Invalid armor material:" + json.toString());

        String materialName = json.getAsJsonPrimitive().getAsString();
        if (vanillaMaterials.containsKey(materialName))
        {
            return vanillaMaterials.get(materialName);
        }

        for (ItemArmor.ArmorMaterial material : ItemArmor.ArmorMaterial.values())
        {
            if (material.name().equalsIgnoreCase(materialName))
                return material;
        }

        throw new JsonParseException("Unknown armor material: " + materialName);
    }

    private static final Map<String, ItemArmor.ArmorMaterial> vanillaMaterials = Maps.newHashMap();

    static
    {
        vanillaMaterials.put("leather", ItemArmor.ArmorMaterial.LEATHER);
        vanillaMaterials.put("chain", ItemArmor.ArmorMaterial.CHAIN);
        vanillaMaterials.put("iron", ItemArmor.ArmorMaterial.IRON);
        vanillaMaterials.put("gold", ItemArmor.ArmorMaterial.GOLD);
        vanillaMaterials.put("diamond", ItemArmor.ArmorMaterial.DIAMOND);
    }
}
