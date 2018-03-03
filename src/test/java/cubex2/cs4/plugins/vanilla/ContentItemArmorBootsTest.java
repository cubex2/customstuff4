package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemArmor;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentItemArmorBootsTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createItem()
    {
        ContentItemArmorBoots content = new ContentItemArmorBoots();
        content.material = ItemArmor.ArmorMaterial.IRON;

        content.createItem();
    }
}