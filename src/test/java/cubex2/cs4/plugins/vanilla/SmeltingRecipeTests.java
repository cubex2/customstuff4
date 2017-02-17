package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
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
        gson = new GsonBuilder().registerTypeAdapter(WrappedItemStack.class, new WrappedItemStackDeserializer())
                                .registerTypeAdapter(ResourceLocation.class, new ResourceLocationDeserializer())
                                .create();

        Bootstrap.register();
    }

    @Test
    public void testDeserialization()
    {
        SmeltingRecipe recipe = gson.fromJson("{ \"input\": \"minecraft:coal\", \"result\": \"minecraft:apple\", \"xp\": 1.2 }", SmeltingRecipe.class);

        assertSame(Items.COAL, recipe.input.createItemStack().getItem());
        assertSame(Items.APPLE, recipe.result.createItemStack().getItem());
        assertEquals(1.2f, recipe.xp, 0.0001f);
    }
}
