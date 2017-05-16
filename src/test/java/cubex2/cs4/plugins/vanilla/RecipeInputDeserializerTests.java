package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.RecipeInput;
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
        gson = TestUtil.createGsonBuilder().create();
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
        assertEquals("air", input.getOreClass().getOreName());
    }

    @Test
    public void test_fromString_ore()
    {
        Map<String, RecipeInput> map = gson.fromJson("{\"input\":\"ore:air\"}", new TypeToken<Map<String, RecipeInput>>() {}.getType());

        RecipeInput input = map.get("input");
        assertTrue(input.isOreClass());
        assertEquals("air", input.getOreClass().getOreName());
    }

    @Test
    public void test_fromObject_ore()
    {
        RecipeInput input = gson.fromJson("{\"ore\":\"stickWood\", \"amount\":5}", RecipeInput.class);

        assertTrue(input.isOreClass());
        assertEquals("stickWood", input.getOreClass().getOreName());
        assertEquals(5, input.getOreClass().getAmount());
    }

    @Test
    public void test_fromObject_ore_without_amount()
    {
        RecipeInput input = gson.fromJson("{\"ore\":\"stickWood\"}", RecipeInput.class);

        assertTrue(input.isOreClass());
        assertEquals("stickWood", input.getOreClass().getOreName());
        assertEquals(1, input.getOreClass().getAmount());
    }

    @Test
    public void test_fromObject()
    {
        RecipeInput input = gson.fromJson("{\"item\":\"minecraft:air\"}", RecipeInput.class);

        assertTrue(input.isItemStack());
    }

    @Test(expected = IllegalStateException.class)
    public void test_getOreClassWhenIsStack_ShouldThrow()
    {
        RecipeInput input = gson.fromJson("{\"item\":\"minecraft:air\"}", RecipeInput.class);

        input.getOreClass();
    }

    @Test(expected = IllegalStateException.class)
    public void test_getStackWhenIsoreClass_ShouldThrow()
    {
        Map<String, RecipeInput> map = gson.fromJson("{\"input\":\"oreclass:air\"}", new TypeToken<Map<String, RecipeInput>>() {}.getType());

        RecipeInput input = map.get("input");

        input.getStack();
    }
}
