package cubex2.cs4.plugins.jei;

import net.minecraft.util.ResourceLocation;
import org.junit.jupiter.api.Test;

public class JEICompatRegistryTest
{
    @Test
    public void test_getShapedCraftingRecipeClass()
    {
        JEICompatRegistry.getShapedCraftingRecipeClass(new ResourceLocation("test_shaped"));
    }

    @Test
    public void test_getShapelessCraftingRecipeClass()
    {
        JEICompatRegistry.getShapelessCraftingRecipeClass(new ResourceLocation("test_shaped"));
    }
}