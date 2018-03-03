package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import net.minecraft.item.ItemArmor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertSame;

public class ArmorMaterialDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void fromString()
    {
        Map<String, ItemArmor.ArmorMaterial> map = gson.fromJson("{ \"material\":\"iron\" }", new TypeToken<Map<String, ItemArmor.ArmorMaterial>>() {}.getType());

        ItemArmor.ArmorMaterial material = map.get("material");
        assertSame(ItemArmor.ArmorMaterial.IRON, material);
    }
}