package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CraftingManagerCS4Test
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_addRecipe_vanilla()
    {
        assertThrows(IllegalArgumentException.class, () ->
                CraftingManagerCS4.addRecipe(new ResourceLocation("minecraft:vanilla"),
                                             new DamageableShapelessOreRecipe(new ResourceLocation("group"),
                                                                              new int[] {1},
                                                                              new ItemStack(Items.APPLE),
                                                                              Ingredient.fromItem(Items.APPLE))));
    }
}