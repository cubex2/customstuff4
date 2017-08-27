package cubex2.cs4.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.plugins.vanilla.Attribute;
import cubex2.cs4.plugins.vanilla.RecipeInputImpl;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.*;

public class ItemHelperTests
{
    @BeforeClass
    public static void setup()
    {
        Bootstrap.register();
    }

    @Test
    public void test_findTabLabel()
    {
        Optional<CreativeTabs> tab = ItemHelper.findCreativeTab("tools");

        assertTrue(tab.isPresent());
        assertSame(CreativeTabs.TOOLS, tab.get());
    }

    @Test
    public void test_findTabLabel_invalid()
    {
        Optional<CreativeTabs> tab = ItemHelper.findCreativeTab("invalidtabname");

        assertFalse(tab.isPresent());
    }

    @Test
    public void test_createCreativeTabs()
    {
        HashMap<Integer, String> map = Maps.newHashMap();
        map.put(0, "tools");
        map.put(1, "invalidtab");
        map.put(2, "redstone");

        CreativeTabs[] tabs = ItemHelper.createCreativeTabs(Attribute.map(map), new int[] {0, 1, 2});

        assertEquals(2, tabs.length);
        assertSame(CreativeTabs.TOOLS, tabs[0]);
        assertSame(CreativeTabs.REDSTONE, tabs[1]);
    }

    @Test
    public void test_createCreativeTabs_removesDuplicates()
    {
        HashMap<Integer, String> map = Maps.newHashMap();
        map.put(0, "tools");
        map.put(1, "tools");

        CreativeTabs[] tabs = ItemHelper.createCreativeTabs(Attribute.map(map), new int[] {0, 1});

        assertEquals(1, tabs.length);
        assertSame(CreativeTabs.TOOLS, tabs[0]);
    }

    @Test
    public void test_createSubItems_differentTabs()
    {
        HashMap<Integer, String> map = Maps.newHashMap();
        map.put(0, "tools");
        map.put(1, "redstone");

        Attribute<String> tabLabels = Attribute.map(map);
        int[] subtypes = new int[] {0, 1};

        Item item = new Item();
        item.setHasSubtypes(true);

        NonNullList<ItemStack> subItems = ItemHelper.createSubItems(item, CreativeTabs.TOOLS, tabLabels, subtypes);

        assertSame(1, subItems.size());
        assertSame(0, subItems.get(0).getItemDamage());
    }

    @Test
    public void test_createSubItems_search_tab()
    {
        HashMap<Integer, String> map = Maps.newHashMap();
        map.put(0, "tools");
        map.put(1, "redstone");

        Attribute<String> tabLabels = Attribute.map(map);
        int[] subtypes = new int[] {0, 1};

        Item item = new Item();
        item.setHasSubtypes(true);

        NonNullList<ItemStack> subItems = ItemHelper.createSubItems(item, CreativeTabs.SEARCH, tabLabels, subtypes);

        assertEquals(2, subItems.size());
        assertEquals(0, subItems.get(0).getItemDamage());
        assertEquals(1, subItems.get(1).getItemDamage());
    }

    @Test
    public void test_createSubItems_sameTabs()
    {
        HashMap<Integer, String> map = Maps.newHashMap();
        map.put(0, "tools");
        map.put(1, "tools");

        Attribute<String> tabLabels = Attribute.map(map);
        int[] subtypes = new int[] {0, 1};

        Item item = new Item();
        item.setHasSubtypes(true);

        NonNullList<ItemStack> subItems = ItemHelper.createSubItems(item, CreativeTabs.TOOLS, tabLabels, subtypes);

        assertSame(2, subItems.size());
        assertSame(0, subItems.get(0).getItemDamage());
        assertSame(1, subItems.get(1).getItemDamage());
    }

    @Test
    public void test_createSubItems_wrongTab()
    {
        HashMap<Integer, String> map = Maps.newHashMap();
        map.put(0, "tools");
        map.put(1, "tools");

        Attribute<String> tabLabels = Attribute.map(map);
        int[] subtypes = new int[] {0, 1};

        Item item = new Item();
        item.setHasSubtypes(true);

        NonNullList<ItemStack> subItems = ItemHelper.createSubItems(item, CreativeTabs.DECORATIONS, tabLabels, subtypes);

        assertSame(0, subItems.size());
    }

    @Test
    public void test_isSameRecipeInput()
    {
        assertTrue(ItemHelper.isSameRecipeInput(new OreIngredient("stickWood"), "stickWood"));
        assertFalse(ItemHelper.isSameRecipeInput(new OreIngredient("stickWood"), "oreIron"));

        assertTrue(ItemHelper.isSameRecipeInput(Ingredient.fromItem(Items.APPLE), new ItemStack(Items.APPLE)));
        assertFalse(ItemHelper.isSameRecipeInput(Ingredient.fromItem(Items.APPLE), new ItemStack(Items.DIAMOND_SWORD)));

        NonNullList<ItemStack> stickWoodList = OreDictionary.getOres("stickWood");
        ItemStack[] stickWood = stickWoodList.toArray(new ItemStack[0]);

        assertTrue(ItemHelper.isSameRecipeInput(Ingredient.fromStacks(stickWood), stickWoodList));
        assertFalse(ItemHelper.isSameRecipeInput(Ingredient.fromStacks(stickWood), OreDictionary.getOres("ingotIron")));

        assertTrue(ItemHelper.isSameRecipeInput(Ingredient.fromStacks(stickWood), new ItemStack(Items.STICK)));
        assertTrue(ItemHelper.isSameRecipeInput(Ingredient.fromItem(Items.STICK), stickWoodList));
        assertFalse(ItemHelper.isSameRecipeInput(Ingredient.fromStacks(stickWood), new ItemStack(Items.DIAMOND_PICKAXE)));
    }

    @Test
    public void isSameStackForMachineInput()
    {
        assertTrue(ItemHelper.stackMatchesStackOrOreClass(new ItemStack(Items.STICK), "stickWood"));
        assertTrue(ItemHelper.stackMatchesStackOrOreClass(new ItemStack(Items.STICK), new ItemStack(Items.STICK)));

        assertFalse(ItemHelper.stackMatchesStackOrOreClass(new ItemStack(Items.STICK), "stickStone"));
        assertFalse(ItemHelper.stackMatchesStackOrOreClass(new ItemStack(Items.STICK), new ItemStack(Items.APPLE)));
    }

    @Test
    public void test_removeInputsFromInventory_stacks()
    {
        ItemStackHandler inv = new ItemStackHandler(2);
        inv.setStackInSlot(0, new ItemStack(Items.APPLE));
        inv.setStackInSlot(1, new ItemStack(Items.STICK, 3));

        RecipeInputImpl input1 = RecipeInputImpl.create(new ItemStack(Items.STICK, 2));
        RecipeInputImpl input2 = RecipeInputImpl.create(new ItemStack(Items.APPLE, 1));
        ItemHelper.removeInputsFromInventory(Lists.newArrayList(input1, input2), inv, 0, 2);

        assertTrue(inv.getStackInSlot(0).isEmpty());
        assertEquals(1, inv.getStackInSlot(1).getCount());
    }

    @Test
    public void test_removeInputsFromInventory_ore()
    {
        ItemStackHandler inv = new ItemStackHandler(2);
        inv.setStackInSlot(0, new ItemStack(Items.APPLE));
        inv.setStackInSlot(1, new ItemStack(Items.STICK, 3));

        RecipeInputImpl input1 = new RecipeInputImpl("stickWood", 2);
        RecipeInputImpl input2 = RecipeInputImpl.create(new ItemStack(Items.APPLE, 1));
        ItemHelper.removeInputsFromInventory(Lists.newArrayList(input1, input2), inv, 0, 2);

        assertTrue(inv.getStackInSlot(0).isEmpty());
        assertEquals(1, inv.getStackInSlot(1).getCount());
    }

    @Test
    public void test_fluidStackEqual()
    {
        assertTrue(ItemHelper.fluidStackEqual(null, null, false));
        assertFalse(ItemHelper.fluidStackEqual(new FluidStack(FluidRegistry.WATER, 10), null, false));
        assertFalse(ItemHelper.fluidStackEqual(null, new FluidStack(FluidRegistry.WATER, 10), false));
        assertFalse(ItemHelper.fluidStackEqual(new FluidStack(FluidRegistry.WATER, 1), new FluidStack(FluidRegistry.WATER, 10), true));
        assertTrue(ItemHelper.fluidStackEqual(new FluidStack(FluidRegistry.WATER, 1), new FluidStack(FluidRegistry.WATER, 10), false));

        assertFalse(ItemHelper.fluidStackEqual(new FluidStack(FluidRegistry.LAVA, 10), new FluidStack(FluidRegistry.WATER, 10), true));
        assertFalse(ItemHelper.fluidStackEqual(new FluidStack(FluidRegistry.LAVA, 10), new FluidStack(FluidRegistry.WATER, 10), false));

        assertFalse(ItemHelper.fluidStackEqual(new FluidStack(FluidRegistry.WATER, 10), new FluidStack(FluidRegistry.LAVA, 10), true));
        assertFalse(ItemHelper.fluidStackEqual(new FluidStack(FluidRegistry.WATER, 10), new FluidStack(FluidRegistry.LAVA, 10), false));
    }

    @Test
    public void test_extractFluidsFromTanks()
    {
        FluidTank tank1 = new FluidTank(FluidRegistry.WATER, 1000, 10000);
        FluidTank tank2 = new FluidTank(FluidRegistry.LAVA, 1000, 10000);

        ItemHelper.extractFluidsFromTanks(Lists.newArrayList(tank1, tank2),
                                          Lists.newArrayList(FluidRegistry.getFluidStack("water", 400),
                                                             FluidRegistry.getFluidStack("lava", 300)));

        assertEquals(600, tank1.getFluidAmount());
        assertEquals(700, tank2.getFluidAmount());

        ItemHelper.extractFluidsFromTanks(Lists.newArrayList(tank1, tank2),
                                          Lists.newArrayList(FluidRegistry.getFluidStack("lava", 400),
                                                             FluidRegistry.getFluidStack("water", 300)));

        assertEquals(300, tank1.getFluidAmount());
        assertEquals(300, tank2.getFluidAmount());
    }

    @Test
    public void test_copyStack()
    {
        ItemStack stack = new ItemStack(Items.STICK, 2, 0);
        ItemStack copy = ItemHelper.copyStack(stack, 42);

        assertSame(stack.getItem(), copy.getItem());
        assertEquals(42, copy.getCount());
    }
}
