package cubex2.cs4.plugins.vanilla.crafting;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class MachineRecipeDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test_deserializer_onlyItem()
    {
        MachineRecipeImpl recipe1 = gson.fromJson("{ \"recipeList\":\"minecraft:list1\" }", MachineRecipeImpl.class);
        MachineRecipeImpl recipe2 = gson.fromJson("{ \"recipeList\":\"minecraft:list2\" }", MachineRecipeImpl.class);
        MachineRecipeImpl recipe3 = gson.fromJson("{ \"recipeList\":\"minecraft:list1\" }", MachineRecipeImpl.class);

        assertNotSame(recipe1.getClass(), recipe2.getClass());
        assertSame(recipe1.getClass(), recipe3.getClass());
    }
}