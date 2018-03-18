package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentBlockPressurePlateTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes()
    {
        ContentBlockPressurePlate content = new ContentBlockPressurePlate();
        content.createBlock();
    }
}