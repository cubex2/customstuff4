package cubex2.cs4.plugins.vanilla.crafting;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
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
    public void test_deserializer_eachListIsDifferentClass()
    {
        MachineRecipeImpl recipe1 = gson.fromJson("{ \"recipeList\":\"minecraft:list1\" }", MachineRecipeImpl.class);
        MachineRecipeImpl recipe2 = gson.fromJson("{ \"recipeList\":\"minecraft:list2\" }", MachineRecipeImpl.class);
        MachineRecipeImpl recipe3 = gson.fromJson("{ \"recipeList\":\"minecraft:list1\" }", MachineRecipeImpl.class);

        assertSame(recipe1.getClass(), recipe2.getClass());
        assertSame(recipe1.getClass(), recipe3.getClass());
    }

    @Test
    public void test_deserializer()
    {
        MachineRecipeImpl recipe = gson.fromJson("{" +
                                                 "\"recipeList\": \"cs4examplemod:machine\"," +
                                                 "\"input\": [\"minecraft:apple\", \"minecraft:gold_ingot\"]," +
                                                 "\"output\": \"minecraft:golden_apple\"," +
                                                 "\"inputFluid\": [\"water@500\"]," +
                                                 "\"cookTime\": 400" + "}", MachineRecipeImpl.class);
        recipe.init(InitPhase.PRE_INIT, null);

        List<RecipeInput> inputItems = recipe.getRecipeInput();
        List<FluidStack> inputFluids = recipe.getFluidRecipeInput();
        NonNullList<MachineRecipeOutput> outputs = recipe.getOutputs();
        MachineRecipeOutput output = outputs.get(0);

        assertEquals(400, recipe.getCookTime());
        assertEquals(2, inputItems.size());
        assertEquals(1, inputFluids.size());
        assertEquals(1, outputs.size());
        assertEquals(1, output.getWeight());
        assertEquals(1, output.getOutputItems().size());
        assertEquals(0, output.getOutputFluids().size());
    }

    @Test
    public void test_multipleOutputs_noWeight()
    {
        MachineRecipeImpl recipe = gson.fromJson("{" +
                                                 "\"recipeList\": \"cs4examplemod:machine\"," +
                                                 "\"output\": [" +
                                                 "\"minecraft:golden_apple\"," +
                                                 "\"minecraft:iron_ingot\"" +
                                                 "]" + "}", MachineRecipeImpl.class);
        recipe.init(InitPhase.PRE_INIT, null);

        NonNullList<MachineRecipeOutput> outputs = recipe.getOutputs();
        assertEquals(2, outputs.size());

        MachineRecipeOutput output0 = outputs.get(0);
        MachineRecipeOutput output1 = outputs.get(1);

        assertEquals(1, output0.getOutputItems().size());
        assertEquals(1, output1.getOutputItems().size());
        assertEquals(0, output0.getOutputFluids().size());
        assertEquals(0, output1.getOutputFluids().size());
        assertEquals(1, output0.getWeight());
        assertEquals(1, output1.getWeight());
    }

    @Test
    public void test_multipleOutputs_withWeight()
    {
        MachineRecipeImpl recipe = gson.fromJson("{" +
                                                 "\"recipeList\": \"cs4examplemod:machine\"," +
                                                 "\"output\": [" +
                                                 "{ " +
                                                 "\"items\": [\"minecraft:golden_apple\", \"minecraft:gold_nugget\"]," +
                                                 "\"weight\": 25" +
                                                 "}," +
                                                 "\"minecraft:iron_ingot\"" +
                                                 "]" + "}", MachineRecipeImpl.class);
        recipe.init(InitPhase.PRE_INIT, null);

        NonNullList<MachineRecipeOutput> outputs = recipe.getOutputs();
        assertEquals(2, outputs.size());

        MachineRecipeOutput output0 = outputs.get(0);
        MachineRecipeOutput output1 = outputs.get(1);

        assertEquals(2, output0.getOutputItems().size());
        assertEquals(1, output1.getOutputItems().size());
        assertEquals(0, output0.getOutputFluids().size());
        assertEquals(0, output1.getOutputFluids().size());
        assertEquals(25, output0.getWeight());
        assertEquals(1, output1.getWeight());
    }

    @Test
    public void test_oneInputItem()
    {
        MachineRecipeImpl recipe = gson.fromJson("{\n" +
                                                 "\"recipeList\": \"zcm_alterlite:makeshiftfurnace\",\n" +
                                                 "\"input\": \"minecraft:cobblestone\"\n" +
                                                 "}", MachineRecipeImpl.class);
        recipe.init(InitPhase.PRE_INIT, null);

        assertEquals(1, recipe.getInputStacks());
    }
}