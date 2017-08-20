package cubex2.cs4.plugins.jei;

import net.minecraft.util.ResourceLocation;
import org.junit.Test;

public class JEICompatRegistryTest
{
    @Test
    public void test_getShapedCraftingRecipeClass() throws Exception
    {
        JEICompatRegistry.getShapedCraftingRecipeClass(new ResourceLocation("test_shaped"));
    }

    @Test
    public void test_getShapelessCraftingRecipeClass() throws Exception
    {
        JEICompatRegistry.getShapelessCraftingRecipeClass(new ResourceLocation("test_shaped"));
    }
}