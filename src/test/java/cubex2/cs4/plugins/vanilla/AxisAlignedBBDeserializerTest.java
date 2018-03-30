package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import cubex2.cs4.plugins.vanilla.block.BlockMixin;
import net.minecraft.util.math.AxisAlignedBB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AxisAlignedBBDeserializerTest
{
    private static Gson gson;

    @BeforeAll
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test_fromTo()
    {
        AxisAlignedBB bb = gson.fromJson("{ \"from\": [1,2,3], \"to\": [4,5.5,6] }", AxisAlignedBB.class);

        assertEquals(1, bb.minX, 0.001);
        assertEquals(2, bb.minY, 0.001);
        assertEquals(3, bb.minZ, 0.001);
        assertEquals(4, bb.maxX, 0.001);
        assertEquals(5.5, bb.maxY, 0.001);
        assertEquals(6, bb.maxZ, 0.001);
    }

    @Test
    public void test_fromTo_with_offset()
    {
        AxisAlignedBB bb = gson.fromJson("{ \"from\": [1,2,3], \"to\": [4,5.5,6], \"offset\": [1,2,3] }", AxisAlignedBB.class);

        assertEquals(2, bb.minX, 0.001);
        assertEquals(4, bb.minY, 0.001);
        assertEquals(6, bb.minZ, 0.001);
        assertEquals(5, bb.maxX, 0.001);
        assertEquals(7.5, bb.maxY, 0.001);
        assertEquals(9, bb.maxZ, 0.001);
    }

    @Test
    public void test_fromTo_to_smaller_than_from()
    {
        AxisAlignedBB bb = gson.fromJson("{ \"from\": [4,5.5,6], \"to\": [1,2,3] }", AxisAlignedBB.class);

        assertEquals(1, bb.minX, 0.001);
        assertEquals(2, bb.minY, 0.001);
        assertEquals(3, bb.minZ, 0.001);
        assertEquals(4, bb.maxX, 0.001);
        assertEquals(5.5, bb.maxY, 0.001);
        assertEquals(6, bb.maxZ, 0.001);
    }

    @Test
    public void test_cube()
    {
        AxisAlignedBB bb = gson.fromJson("{ \"cube\": 5, \"offset\": [1,0,1] }", AxisAlignedBB.class);

        assertEquals(1, bb.minX, 0.001);
        assertEquals(0, bb.minY, 0.001);
        assertEquals(1, bb.minZ, 0.001);
        assertEquals(6, bb.maxX, 0.001);
        assertEquals(5, bb.maxY, 0.001);
        assertEquals(6, bb.maxZ, 0.001);
    }

    @Test
    public void test_empty()
    {
        Map<String, AxisAlignedBB> map = gson.fromJson("{ \"empty\": \"empty\", \"null\": \"null\" }", new TypeToken<Map<String, AxisAlignedBB>>() {}.getType());

        assertTrue(map.containsKey("empty"));
        assertTrue(map.containsKey("null"));
        assertNull(map.get("empty"));
        assertNull(map.get("null"));
    }

    @Test
    public void test_default()
    {
        Map<String, AxisAlignedBB> map = gson.fromJson("{ \"default\": \"default\" }", new TypeToken<Map<String, AxisAlignedBB>>() {}.getType());

        assertTrue(map.containsKey("default"));
        assertSame(BlockMixin.DEFAULT_AABB_MARKER, map.get("default"));
    }
}