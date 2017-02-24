package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.RecipeInput;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ShapelessRecipeTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();
    }

    @Test
    public void testDeserialization()
    {
        ShapelessRecipe recipe = gson.fromJson("{ \"items\": [ \"minecraft:apple\", \"minecraft:bow\" ]," +
                                               "\"result\": \"minecraft:coal\" }", ShapelessRecipe.class);


        RecipeInput input0 = recipe.items.get(0);
        RecipeInput input1 = recipe.items.get(1);

        assertSame(Items.APPLE, input0.getStack().createItemStack().getItem());
        assertSame(Items.BOW, input1.getStack().createItemStack().getItem());
        assertSame(Items.COAL, recipe.result.createItemStack().getItem());
    }

    @Test
    public void testGetInputForRecipe()
    {
        ShapelessRecipe recipe = gson.fromJson("{ \"items\": [ \"minecraft:apple\", \"minecraft:bow\" ]," +
                                               "\"result\": \"minecraft:coal\" }", ShapelessRecipe.class);

        Object[] input = recipe.getInputForRecipe();

        assertEquals(2, input.length);
        assertSame(Items.APPLE, ((ItemStack) input[0]).getItem());
        assertSame(Items.BOW, ((ItemStack) input[1]).getItem());
    }
}
