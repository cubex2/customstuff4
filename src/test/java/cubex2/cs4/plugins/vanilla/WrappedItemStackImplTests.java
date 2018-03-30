package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WrappedItemStackImplTests
{
    @BeforeAll
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
