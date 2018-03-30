package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import net.minecraftforge.common.EnumPlantType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;

public class EnumPlantTypeDeserializerTest
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
        Map<String, EnumPlantType> map = gson.fromJson("{ \"plant\": \"Crop\" }", new TypeToken<Map<String, EnumPlantType>>() {}.getType());

        EnumPlantType plant = map.get("plant");

        assertSame(EnumPlantType.Crop, plant);
    }
}