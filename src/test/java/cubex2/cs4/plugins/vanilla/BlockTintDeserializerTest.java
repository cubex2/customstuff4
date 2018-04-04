package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.BlockTint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTintDeserializerTest
{
    private static Gson gson;

    @BeforeAll
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test()
    {
        Map<String, BlockTint> map = gson.fromJson("{ \"tint\":\"ffffff\" }", new TypeToken<Map<String, BlockTint>>() {}.getType());

        assertEquals(0xffffffff, map.get("tint").getMultiplier(null, null));
    }
}