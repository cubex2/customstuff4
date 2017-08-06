package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BlockDropDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();
    }

    @Test
    public void test_fromString()
    {
        Map<String, BlockDrop> map = gson.fromJson("{ \"stack\":\"minecraft:stone\" }", new TypeToken<Map<String, BlockDrop>>() {}.getType());

        BlockDrop drop = map.get("stack");
        assertNotNull(drop);
    }

    @Test
    public void test_singleAmount()
    {
        BlockDrop stack = gson.fromJson("{ \"item\":\"minecraft:stone\",\"amount\":42,\"metadata\":10 }", BlockDrop.class);

        assertEquals(42, stack.getMinAmount());
        assertEquals(42, stack.getMaxAmount());
    }

    @Test
    public void test_amountRange()
    {
        BlockDrop stack = gson.fromJson("{ \"item\":\"minecraft:stone\",\"amount\":[1,42],\"metadata\":10 }", BlockDrop.class);

        assertEquals(1, stack.getMinAmount());
        assertEquals(42, stack.getMaxAmount());
    }
}