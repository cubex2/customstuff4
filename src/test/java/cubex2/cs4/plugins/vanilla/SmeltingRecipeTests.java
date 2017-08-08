package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import net.minecraft.init.Items;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class SmeltingRecipeTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void testDeserialization()
    {
        SmeltingRecipe recipe = gson.fromJson("{ \"input\": \"minecraft:coal\", \"result\": \"minecraft:apple\", \"xp\": 1.2 }", SmeltingRecipe.class);

        assertSame(Items.COAL, recipe.input.getItemStack().getItem());
        assertSame(Items.APPLE, recipe.result.getItemStack().getItem());
        assertEquals(1.2f, recipe.xp, 0.0001f);
    }
}
