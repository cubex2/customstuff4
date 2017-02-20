package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import net.minecraft.util.ResourceLocation;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MetadataAttributeTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();
    }

    @Test
    public void testDeserializer_fromMap()
    {
        MetadataAttribute<ResourceLocation> map = gson.fromJson("{ \"0\": \"minecraft:coal\", \"5\": \"minecraft:stone\" }", new TypeToken<MetadataAttribute<ResourceLocation>>() {}.getType());

        assertTrue(map.hasEntry(0));
        assertTrue(map.hasEntry(5));
        assertFalse(map.hasEntry(3));
        assertEquals(new ResourceLocation("minecraft:coal"), map.get(0));
        assertEquals(new ResourceLocation("minecraft:stone"), map.get(5));
    }

    @Test
    public void testDeserializer_fromSingleValue()
    {
        Map<String, MetadataAttribute<ResourceLocation>> map = gson.fromJson("{ \"map\": \"minecraft:coal\" }", new TypeToken<Map<String, MetadataAttribute<ResourceLocation>>>() {}.getType());

        MetadataAttribute<ResourceLocation> metaMap = map.get("map");

        assertTrue(metaMap.hasEntry(0));
        assertTrue(metaMap.hasEntry(7));
        assertTrue(metaMap.hasEntry(56));
        assertEquals(new ResourceLocation("minecraft:coal"), metaMap.get(0));
    }
}
