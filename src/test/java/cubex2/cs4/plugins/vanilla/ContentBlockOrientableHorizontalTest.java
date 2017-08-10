package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentBlockOrientableHorizontalTest
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes() throws Exception
    {
        new ContentBlockOrientableHorizontal().createBlock();
    }

    @Test
    public void test_createBlock_subtypes() throws Exception
    {
        ContentBlockOrientableHorizontal content = new ContentBlockOrientableHorizontal();
        content.subtypes = new int[] {0, 1};
        content.createBlock();
    }
}