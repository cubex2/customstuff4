package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.util.IntRange;
import net.minecraft.block.material.MapColor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MapColorDeserializerTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();
    }

    @Test
    public void test_noArray()
    {
        Map<String, MapColor> map = gson.fromJson("{ \"color\": \"light_blue\" }", new TypeToken<Map<String, MapColor>>() {}.getType());

        MapColor color = map.get("color");
        assertNotNull(color);
        assertSame(MapColor.LIGHT_BLUE, color);
    }
}
