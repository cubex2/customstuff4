package cubex2.cs4.plugins.vanilla.gui;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.RecipeInputImpl;
import cubex2.cs4.plugins.vanilla.crafting.MachineFuel;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemFilterDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test_ore()
    {
        Map<String, ItemFilter> map = gson.fromJson("{\"filter\":\"ore:ingotIron\"}", new TypeToken<Map<String, ItemFilter>>() {}.getType());

        ItemFilter filter = map.get("filter");

        assertTrue(filter.accepts(new ItemStack(Items.IRON_INGOT)));
        assertFalse(filter.accepts(new ItemStack(Items.GOLD_INGOT)));
    }

    @Test
    public void test_item()
    {
        Map<String, ItemFilter> map = gson.fromJson("{\"filter\":\"minecraft:apple\"}", new TypeToken<Map<String, ItemFilter>>() {}.getType());

        ItemFilter filter = map.get("filter");

        assertTrue(filter.accepts(new ItemStack(Items.APPLE)));
        assertFalse(filter.accepts(new ItemStack(Items.GOLD_INGOT)));
    }

    @Test
    public void test_machineInput()
    {
        MachineManager.addRecipe(new ResourceLocation("someMachine"), new TestMachineRecipe());
        Map<String, ItemFilter> map = gson.fromJson("{\"filter\": \"machineInput:someMachine\"}", new TypeToken<Map<String, ItemFilter>>() {}.getType());

        ItemFilter filter = map.get("filter");

        assertTrue(filter.accepts(new ItemStack(Items.STICK)));
        assertTrue(filter.accepts(new ItemStack(Items.IRON_INGOT)));
        assertFalse(filter.accepts(new ItemStack(Items.GOLD_INGOT)));
    }

    @Test
    public void test_machineInput_vanilla()
    {
        Map<String, ItemFilter> map = gson.fromJson("{\"filter\": \"machineInput:vanilla\"}", new TypeToken<Map<String, ItemFilter>>() {}.getType());

        ItemFilter filter = map.get("filter");

        assertTrue(filter.accepts(new ItemStack(Blocks.IRON_ORE)));
        assertFalse(filter.accepts(new ItemStack(Items.GOLD_INGOT)));
    }

    @Test
    public void test_machineFuel()
    {
        MachineManager.addFuel(new ResourceLocation("someFuel"), new TestMachineFuel());
        Map<String, ItemFilter> map = gson.fromJson("{\"filter\": \"machineFuel:someFuel\"}", new TypeToken<Map<String, ItemFilter>>() {}.getType());

        ItemFilter filter = map.get("filter");

        assertTrue(filter.accepts(new ItemStack(Items.STICK)));
        assertTrue(filter.accepts(new ItemStack(Items.IRON_INGOT)));
        assertFalse(filter.accepts(new ItemStack(Items.GOLD_INGOT)));
    }

    @Test
    public void test_machineFuel_vanilla()
    {
        Map<String, ItemFilter> map = gson.fromJson("{\"filter\": \"machineFuel:vanilla\"}", new TypeToken<Map<String, ItemFilter>>() {}.getType());

        ItemFilter filter = map.get("filter");

        assertTrue(filter.accepts(new ItemStack(Items.STICK)));
        assertFalse(filter.accepts(new ItemStack(Items.GOLD_INGOT)));
    }

    @Test
    public void test_array()
    {
        Map<String, ItemFilter> map = gson.fromJson("{\"filter\": [\"minecraft:apple\", \"ore:ingotIron\"]}", new TypeToken<Map<String, ItemFilter>>() {}.getType());

        ItemFilter filter = map.get("filter");

        assertTrue(filter.accepts(new ItemStack(Items.APPLE)));
        assertTrue(filter.accepts(new ItemStack(Items.IRON_INGOT)));
        assertFalse(filter.accepts(new ItemStack(Items.GOLD_INGOT)));
    }

    @Test
    public void test_stackFromJsonObject()
    {
        ItemFilter filter = gson.fromJson("{\"item\":\"minecraft:apple\"}", ItemFilter.class);

        assertTrue(filter.accepts(new ItemStack(Items.APPLE)));
        assertFalse(filter.accepts(new ItemStack(Items.GOLD_INGOT)));
    }

    private static class TestMachineRecipe implements MachineRecipe
    {

        @Override
        public List<RecipeInput> getRecipeInput()
        {
            return Lists.newArrayList(new RecipeInputImpl("ingotIron"), new RecipeInputImpl("stickWood"));
        }

        @Override
        public List<ItemStack> getResult()
        {
            return null;
        }

        @Override
        public List<ItemStack> getRecipeOutput()
        {
            return null;
        }

        @Override
        public boolean matches(List<ItemStack> input, List<FluidStack> inputFluid, World world)
        {
            return false;
        }

        @Override
        public List<FluidStack> getFluidRecipeInput()
        {
            return null;
        }

        @Override
        public List<FluidStack> getFluidResult()
        {
            return null;
        }

        @Override
        public List<FluidStack> getFluidRecipeOutput()
        {
            return null;
        }

        @Override
        public int getFluidStacks()
        {
            return 0;
        }

        @Override
        public int getInputStacks()
        {
            return 0;
        }
    }

    private static class TestMachineFuel implements MachineFuel
    {
        @Override
        public int getBurnTime()
        {
            return 0;
        }

        @Override
        public boolean matches(List<ItemStack> items)
        {
            return false;
        }

        @Override
        public List<RecipeInput> getFuelInput()
        {
            return Lists.newArrayList(new RecipeInputImpl("ingotIron"), new RecipeInputImpl("stickWood"));
        }
    }
}