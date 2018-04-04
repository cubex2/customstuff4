package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ColorDeserializerTest
{
    private static Gson gson;

    @BeforeAll
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test_fromName()
    {
        Map<String, Color> map = gson.fromJson("{ \"color\":\"navy\" }", new TypeToken<Map<String,Color>>(){}.getType());

        int color = map.get("color").getRGB();

        assertEquals(0xff000080,color);
    }

    @Test
    public void test_fromHex()
    {
        Map<String, Color> map = gson.fromJson("{ \"color\":\"ff55ff\" }", new TypeToken<Map<String,Color>>(){}.getType());

        int color = map.get("color").getRGB();

        assertEquals(0xffff55ff,color);
    }

    @Test
    public void test_fromInt()
    {
        Map<String, Color> map = gson.fromJson("{ \"color\":1234 }", new TypeToken<Map<String,Color>>(){}.getType());

        int color = map.get("color").getRGB();

        assertEquals(1234,color);
    }
}