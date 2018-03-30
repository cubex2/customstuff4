package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ContentBlockOrientableDirectionalTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes()
    {
        new ContentBlockOrientableDirectional().createBlock();
    }

    @Test
    public void test_createBlock_subtypes()
    {
        ContentBlockOrientableDirectional content = new ContentBlockOrientableDirectional();
        content.subtypes = new int[] {0, 1};
        content.createBlock();
    }
}