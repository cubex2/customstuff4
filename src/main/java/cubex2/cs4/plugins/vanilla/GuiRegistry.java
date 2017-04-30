package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;

public class GuiRegistry
{
    private static final Map<Integer, ContentGuiBase> idMap = Maps.newHashMap();
    private static final Map<ResourceLocation, ContentGuiBase> keyMap = Maps.newHashMap();

    public static int register(ContentGuiBase content)
    {
        int id = idMap.size();
        idMap.put(id, content);
        keyMap.put(content.getKey(), content);
        return id;
    }

    @Nullable
    public static ContentGuiBase get(int id)
    {
        return idMap.get(id);
    }

    @Nullable
    public static ContentGuiBase get(ResourceLocation key)
    {
        return keyMap.get(key);
    }
}
