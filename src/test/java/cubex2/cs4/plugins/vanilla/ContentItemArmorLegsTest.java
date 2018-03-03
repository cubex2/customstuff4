package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemArmor;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentItemArmorLegsTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createItem()
    {
        ContentItemArmorLegs content = new ContentItemArmorLegs();
        content.material = ItemArmor.ArmorMaterial.IRON;

        content.createItem();
    }
}