package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.util.ResourceLocation;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecipeInputDeserializerTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = new GsonBuilder().registerTypeAdapter(WrappedItemStack.class, new WrappedItemStackDeserializer())
                                .registerTypeAdapter(RecipeInput.class, new RecipeInputDeserializer())
                                .registerTypeAdapter(ResourceLocation.class, new ResourceLocationDeserializer())
                                .create();
    }

    @Test
    public void test_fromString_item()
    {
        Map<String, RecipeInput> map = gson.fromJson("{\"input\":\"minecraft:air\"}", new TypeToken<Map<String, RecipeInput>>() {}.getType());

        RecipeInput input = map.get("input");
        assertTrue(input.isItemStack());
    }

    @Test
    public void test_fromString_oreclass()
    {
        Map<String, RecipeInput> map = gson.fromJson("{\"input\":\"oreclass:air\"}", new TypeToken<Map<String, RecipeInput>>() {}.getType());

        RecipeInput input = map.get("input");
        assertTrue(input.isOreClass());
        assertEquals("air", input.getInput());
    }

    @Test
    public void test_fromObject()
    {
        RecipeInput input = gson.fromJson("{\"item\":\"minecraft:air\"}", RecipeInput.class);

        assertTrue(input.isItemStack());
    }
}
