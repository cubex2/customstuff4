package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentBlockButtonTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes()
    {
        ContentBlockButton block = new ContentBlockButton();
        block.createBlock();
    }
}