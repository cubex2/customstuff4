package cubex2.cs4.plugins.jei;

import net.minecraft.util.ResourceLocation;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Jei compat registry test")
public class JEICompatRegistryTest
{
    @Test
    @DisplayName("Shaped crafting recipe")
    public void test_getShapedCraftingRecipeClass() throws Exception
    {
        JEICompatRegistry.getShapedCraftingRecipeClass(new ResourceLocation("test_shaped"));
    }

    @Test
    @DisplayName("Shapeless crafting recipe")
    public void test_getShapelessCraftingRecipeClass() throws Exception
    {
        JEICompatRegistry.getShapelessCraftingRecipeClass(new ResourceLocation("test_shaped"));
    }
}