package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.ResourceLocation;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResourceLocationDeserializerTests
{
    @Test
    public void test_fromString()
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(ResourceLocation.class, new ResourceLocationDeserializer()).create();
        Map<String, ResourceLocation> map = gson.fromJson("{\"location\":\"minecraft:air\"}", new TypeToken<Map<String, ResourceLocation>>() {}.getType());

        ResourceLocation location = map.get("location");
        assertEquals("minecraft", location.getResourceDomain());
        assertEquals("air", location.getResourcePath());
    }

    @Test
    public void test_fromObject()
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(ResourceLocation.class, new ResourceLocationDeserializer()).create();
        ResourceLocation location = gson.fromJson("{\"domain\":\"minecraft\",\"path\":\"air\"}", ResourceLocation.class);

        assertEquals("minecraft", location.getResourceDomain());
        assertEquals("air", location.getResourcePath());
    }
}
