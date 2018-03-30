package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ContentBlockCarpetTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes()
    {
        new ContentBlockCarpet().createBlock();
    }

    @Test
    public void test_createBlock_subtypes()
    {
        ContentBlockCarpet content = new ContentBlockCarpet();
        content.subtypes = new int[] {0, 1};
        content.createBlock();
    }
}