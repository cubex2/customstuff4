package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ContentBlockWallTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes()
    {
        ContentBlockWall content = new ContentBlockWall();
        content.createBlock();
    }

    @Test
    public void test_createBlock_subtypes()
    {
        ContentBlockWall content = new ContentBlockWall();
        content.subtypes = new int[] {0, 1};
        content.createBlock();
    }
}