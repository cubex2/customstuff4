package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import net.minecraftforge.common.EnumPlantType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertSame;

public class EnumPlantTypeDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();
    }

    @Test
    public void test() throws Exception
    {
        Map<String, EnumPlantType> map = gson.fromJson("{ \"plant\": \"Crop\" }", new TypeToken<Map<String, EnumPlantType>>() {}.getType());

        EnumPlantType plant = map.get("plant");

        assertSame(EnumPlantType.Crop, plant);
    }
}