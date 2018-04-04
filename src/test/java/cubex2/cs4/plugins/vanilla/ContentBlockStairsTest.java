package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ContentBlockStairsTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes()
    {
        ContentBlockStairs stairs = new ContentBlockStairs();
        stairs.modelState = Blocks.DIRT::getDefaultState;
        stairs.createBlock();
    }
}