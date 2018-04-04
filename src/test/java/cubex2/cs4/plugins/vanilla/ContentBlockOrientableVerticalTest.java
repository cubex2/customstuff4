package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ContentBlockOrientableVerticalTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes()
    {
        new ContentBlockOrientableVertical().createBlock();
    }

    @Test
    public void test_createBlock_subtypes()
    {
        ContentBlockOrientableVertical content = new ContentBlockOrientableVertical();
        content.subtypes = new int[] {0, 1};
        content.createBlock();
    }
}