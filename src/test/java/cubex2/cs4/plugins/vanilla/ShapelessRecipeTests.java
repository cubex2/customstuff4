package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
        gson = new GsonBuilder().registerTypeAdapter(WrappedItemStack.class, new WrappedItemStackDeserializer())
                                .registerTypeAdapter(RecipeInput.class, new RecipeInputDeserializer())
                                .registerTypeAdapter(ResourceLocation.class, new ResourceLocationDeserializer())
                                .registerTypeAdapter(ShapelessRecipe.class, ShapelessRecipe.DESERIALIZER)
                                .create();

        Bootstrap.register();
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
