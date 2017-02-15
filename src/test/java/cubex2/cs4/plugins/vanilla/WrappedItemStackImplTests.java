package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WrappedItemStackImplTests
{
    @BeforeClass
    public static void setup()
    {
        Bootstrap.register();
    }

    @Test
    public void test_isLoaded_shouldBeLoaded()
    {
        WrappedItemStackImpl stack = new WrappedItemStackImpl();
        stack.item = new ResourceLocation("minecraft:stone");

        assertTrue(stack.isItemLoaded());
    }

    @Test
    public void test_isLoaded_shouldNotBeLoaded()
    {
        WrappedItemStackImpl stack = new WrappedItemStackImpl();
        stack.item = new ResourceLocation("minecraft:nonexistingitem");

        assertFalse(stack.isItemLoaded());
    }
}
