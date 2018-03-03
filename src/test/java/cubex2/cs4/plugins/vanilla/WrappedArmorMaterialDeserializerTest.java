package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.WrappedArmorMaterial;
import net.minecraft.item.ItemArmor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertSame;

public class WrappedArmorMaterialDeserializerTest
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
        Map<String, WrappedArmorMaterial> map = gson.fromJson("{ \"material\":\"iron\" }", new TypeToken<Map<String, WrappedArmorMaterial>>() {}.getType());

        ItemArmor.ArmorMaterial material = map.get("material").getArmorMaterial();
        assertSame(ItemArmor.ArmorMaterial.IRON, material);
    }
}