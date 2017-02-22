package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.block.material.Material;

import java.lang.reflect.Type;
import java.util.Map;

public class MaterialDeserializer implements JsonDeserializer<Material>
{
    private static Map<String, Material> materialMap = Maps.newHashMap();

    @Override
    public Material deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return materialMap.get(json.getAsString());
    }

    static
    {
        materialMap.put("cactus", Material.CACTUS);
        materialMap.put("circuits", Material.CIRCUITS);
        materialMap.put("clay", Material.CLAY);
        materialMap.put("cloth", Material.CLOTH);
        materialMap.put("craftedSnow", Material.CRAFTED_SNOW);
        materialMap.put("fire", Material.FIRE);
        materialMap.put("glass", Material.GLASS);
        materialMap.put("grass", Material.GRASS);
        materialMap.put("ground", Material.GROUND);
        materialMap.put("ice", Material.ICE);
        materialMap.put("iron", Material.IRON);
        materialMap.put("lava", Material.LAVA);
        materialMap.put("leaves", Material.LEAVES);
        materialMap.put("plants", Material.PLANTS);
        materialMap.put("pumpkin", Material.GOURD);
        materialMap.put("redstoneLight", Material.REDSTONE_LIGHT);
        materialMap.put("rock", Material.ROCK);
        materialMap.put("sand", Material.SAND);
        materialMap.put("snow", Material.SNOW);
        materialMap.put("sponge", Material.SPONGE);
        materialMap.put("tnt", Material.TNT);
        materialMap.put("vine", Material.VINE);
        materialMap.put("water", Material.WATER);
        materialMap.put("wood", Material.WOOD);
        materialMap.put("air", Material.AIR);
    }
}
