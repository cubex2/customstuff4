package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.util.IntRange;
import net.minecraft.block.SoundType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class IntRangeDeserializerTests
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
        Map<String, IntRange> map = gson.fromJson("{ \"range\": 1 }", new TypeToken<Map<String, IntRange>>() {}.getType());

        IntRange range = map.get("range");
        assertNotNull(range);
        assertEquals(1, range.getMin());
        assertSame(range.getMax(), range.getMin());
    }

    @Test
    public void test_withArray()
    {
        Map<String, IntRange> map = gson.fromJson("{ \"range\": [1, 5] }", new TypeToken<Map<String, IntRange>>() {}.getType());

        IntRange range = map.get("range");
        assertNotNull(range);
        assertEquals(1, range.getMin());
        assertEquals(5, range.getMax());
    }
}
