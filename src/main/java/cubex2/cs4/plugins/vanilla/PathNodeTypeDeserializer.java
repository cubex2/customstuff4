package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.pathfinding.PathNodeType;

import java.lang.reflect.Type;
import java.util.Map;

public class PathNodeTypeDeserializer implements JsonDeserializer<PathNodeType>
{
    private static final Map<String, PathNodeType> pathNodeTypes = Maps.newHashMap();

    @Override
    public PathNodeType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString())
        {
            String key = json.getAsString();
            if (!pathNodeTypes.containsKey(key))
                throw new JsonParseException("Invalid path node type: " + key);

            return pathNodeTypes.get(key);
        }

        throw new JsonParseException("Invalid path node type: " + json.toString());
    }

    static
    {
        pathNodeTypes.put("blocked", PathNodeType.BLOCKED);
        pathNodeTypes.put("open", PathNodeType.OPEN);
        pathNodeTypes.put("walkable", PathNodeType.WALKABLE);
        pathNodeTypes.put("trapdoor", PathNodeType.TRAPDOOR);
        pathNodeTypes.put("fence", PathNodeType.FENCE);
        pathNodeTypes.put("lava", PathNodeType.LAVA);
        pathNodeTypes.put("water", PathNodeType.WATER);
        pathNodeTypes.put("rail", PathNodeType.RAIL);
        pathNodeTypes.put("dangerFire", PathNodeType.DANGER_FIRE);
        pathNodeTypes.put("damageFire", PathNodeType.DAMAGE_FIRE);
        pathNodeTypes.put("dangerCactus", PathNodeType.DANGER_CACTUS);
        pathNodeTypes.put("damageCactus", PathNodeType.DAMAGE_CACTUS);
        pathNodeTypes.put("dangerOther", PathNodeType.DANGER_OTHER);
        pathNodeTypes.put("damageOther", PathNodeType.DAMAGE_OTHER);
        pathNodeTypes.put("doorOpen", PathNodeType.DOOR_OPEN);
        pathNodeTypes.put("doorWoodClosed", PathNodeType.DOOR_WOOD_CLOSED);
        pathNodeTypes.put("doorIronClosed", PathNodeType.DOOR_IRON_CLOSED);
    }
}
