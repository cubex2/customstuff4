package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemArmor;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentItemArmorChestTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createItem()
    {
        ContentItemArmorChest content = new ContentItemArmorChest();
        content.material = ItemArmor.ArmorMaterial.IRON;

        content.createItem();
    }
}