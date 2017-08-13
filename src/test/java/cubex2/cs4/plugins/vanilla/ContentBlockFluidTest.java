package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentBlockFluidTest
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes() throws Exception
    {
        ContentBlockFluid fluid = new ContentBlockFluid();
        fluid.id = "TheFluidId";
        fluid.createBlock();
    }
}