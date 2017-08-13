package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentBlockStairsTest
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes() throws Exception
    {
        ContentBlockStairs stairs = new ContentBlockStairs();
        stairs.modelState = Blocks.DIRT::getDefaultState;
        stairs.createBlock();
    }
}