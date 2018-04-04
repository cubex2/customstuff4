package cubex2.cs4.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.plugins.vanilla.Attribute;
import cubex2.cs4.plugins.vanilla.BlockDrop;
import cubex2.cs4.plugins.vanilla.RecipeInputImpl;
import cubex2.cs4.plugins.vanilla.WrappedItemStackConstant;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ItemHelperTests {
    @BeforeAll
    public static void setup() {
        Bootstrap.register();
    }

    @Nested
    @DisplayName("Creative tabs")
    class CreativeTab {
        @Nested
        @DisplayName("can be found")
        class FindTabs {
            @Test
            @DisplayName("can be retrieved")
            public void test_findTabLabel() {
                Optional<CreativeTabs> tab = ItemHelper.findCreativeTab("tools");
                assertTrue(tab.isPresent());
                assertSame(CreativeTabs.TOOLS, tab.get());
            }

            @Test
            @DisplayName("can not be retrieved if the name is invalid")
            public void test_findTabLabel_invalid() {
                Optional<CreativeTabs> tab = ItemHelper.findCreativeTab("invalidtabname");

                assertFalse(tab.isPresent());
            }
        }

        @Nested
        @DisplayName("can be created")
        class CreateTab {
            @Test
            @DisplayName("can be created")
            public void test_createCreativeTabs() {
                HashMap<Integer, String> map = Maps.newHashMap();
                map.put(0, "tools");
                map.put(1, "invalidtab");
                map.put(2, "redstone");

                CreativeTabs[] tabs = ItemHelper.createCreativeTabs(Attribute.map(map), new int[]{0, 1, 2});

                assertEquals(2, tabs.length);
                assertSame(CreativeTabs.TOOLS, tabs[0]);
                assertSame(CreativeTabs.REDSTONE, tabs[1]);
            }

            @Test
            @DisplayName("Diplicates are not added")
            public void test_createCreativeTabs_removesDuplicates() {
                HashMap<Integer, String> map = Maps.newHashMap();
                map.put(0, "tools");
                map.put(1, "tools");

                CreativeTabs[] tabs = ItemHelper.createCreativeTabs(Attribute.map(map), new int[]{0, 1});

                assertEquals(1, tabs.length);
                assertSame(CreativeTabs.TOOLS, tabs[0]);
            }


        }
    }

    @Nested
    @DisplayName("Create subitems on")
    class SubItems {

        @Test
        @DisplayName("different tabs")
        public void test_createSubItems_differentTabs() {
            HashMap<Integer, String> map = Maps.newHashMap();
            map.put(0, "tools");
            map.put(1, "redstone");

            Attribute<String> tabLabels = Attribute.map(map);
            int[] subtypes = new int[]{0, 1};

            Item item = new Item();
            item.setHasSubtypes(true);

            NonNullList<ItemStack> subItems = ItemHelper.createSubItems(item, CreativeTabs.TOOLS, tabLabels, subtypes);

            assertSame(1, subItems.size());
            assertSame(0, subItems.get(0).getItemDamage());
        }

        @Test
        @DisplayName("the search tab")
        public void test_createSubItems_search_tab() {
            HashMap<Integer, String> map = Maps.newHashMap();
            map.put(0, "tools");
            map.put(1, "redstone");

            Attribute<String> tabLabels = Attribute.map(map);
            int[] subtypes = new int[]{0, 1};

            Item item = new Item();
            item.setHasSubtypes(true);

            NonNullList<ItemStack> subItems = ItemHelper.createSubItems(item, CreativeTabs.SEARCH, tabLabels, subtypes);

            assertEquals(2, subItems.size());
            assertEquals(0, subItems.get(0).getItemDamage());
            assertEquals(1, subItems.get(1).getItemDamage());
        }


        @Test
        @DisplayName("the same tab")
        public void test_createSubItems_sameTabs() {
            HashMap<Integer, String> map = Maps.newHashMap();
            map.put(0, "tools");
            map.put(1, "tools");

            Attribute<String> tabLabels = Attribute.map(map);
            int[] subtypes = new int[]{0, 1};

            Item item = new Item();
            item.setHasSubtypes(true);

            NonNullList<ItemStack> subItems = ItemHelper.createSubItems(item, CreativeTabs.TOOLS, tabLabels, subtypes);

            assertSame(2, subItems.size());
            assertSame(0, subItems.get(0).getItemDamage());
            assertSame(1, subItems.get(1).getItemDamage());
        }

        @Test
        @DisplayName("a wrong tab")
        public void test_createSubItems_wrongTab() {
            HashMap<Integer, String> map = Maps.newHashMap();
            map.put(0, "tools");
            map.put(1, "tools");

            Attribute<String> tabLabels = Attribute.map(map);
            int[] subtypes = new int[]{0, 1};

            Item item = new Item();
            item.setHasSubtypes(true);

            NonNullList<ItemStack> subItems = ItemHelper.createSubItems(item, CreativeTabs.DECORATIONS, tabLabels, subtypes);

            assertSame(0, subItems.size());
        }
    }

    @Nested
    @DisplayName("Dropped stacks")
    class DroppedStacks {

        @Nested
        @DisplayName("drops nothing")
        class DropsNothing {
            @Test
            @DisplayName("drops nothing")
            public void test_getDroppedStacks_nothing() {
                List<ItemStack> drops = ItemHelper.getDroppedStacks(new BlockDrop[0], 0);
                assertTrue(drops.isEmpty());
            }

            @Test
            @DisplayName("drops empty stack")
            public void test_getDroppedStacks_emptyStack() {
                BlockDrop drop = new BlockDrop(new WrappedItemStackConstant(new ItemStack(Items.APPLE)), IntRange.create(0, 0));
                List<ItemStack> drops = ItemHelper.getDroppedStacks(new BlockDrop[]{drop}, 0);
                assertTrue(drops.isEmpty());
            }
        }

        @Test
        @DisplayName("simple drops")
        public void test_getDroppedStacks_simple() {
            BlockDrop drop1 = new BlockDrop(new WrappedItemStackConstant(new ItemStack(Items.APPLE)), IntRange.create(1, 1));
            BlockDrop drop2 = new BlockDrop(new WrappedItemStackConstant(new ItemStack(Items.STICK)), IntRange.create(2, 2));

            List<ItemStack> drops = ItemHelper.getDroppedStacks(new BlockDrop[]{drop1, drop2}, 0);
            ItemStack stack1 = drops.get(0);
            ItemStack stack2 = drops.get(1);
            assertSame(Items.APPLE, stack1.getItem());
            assertEquals(1, stack1.getCount());
            assertSame(Items.STICK, stack2.getItem());
            assertEquals(2, stack2.getCount());
        }


        @Test
        @DisplayName("dropped stacks are in range")
        public void test_getDroppedStacks_range() {
            BlockDrop drop1 = new BlockDrop(new WrappedItemStackConstant(new ItemStack(Items.APPLE)), IntRange.create(1, 10));
            BlockDrop drop2 = new BlockDrop(new WrappedItemStackConstant(new ItemStack(Items.STICK)), IntRange.create(11, 20));

            List<ItemStack> drops = ItemHelper.getDroppedStacks(new BlockDrop[]{drop1, drop2}, 0);
            ItemStack stack1 = drops.get(0);
            ItemStack stack2 = drops.get(1);

            assertSame(Items.APPLE, stack1.getItem());
            assertTrue(stack1.getCount() >= 1 && stack1.getCount() <= 10);

            assertSame(Items.STICK, stack2.getItem());
            assertTrue(stack2.getCount() >= 11 && stack2.getCount() <= 20);
        }

        @Test
        @DisplayName("fortune works")
        public void test_getDroppedStacks_withFortune() {
            BlockDrop drop1 = new BlockDrop(new WrappedItemStackConstant(new ItemStack(Items.APPLE)), IntRange.create(1, 1), IntRange.create(3, 3));

            List<ItemStack> drops = ItemHelper.getDroppedStacks(new BlockDrop[]{drop1}, 2);
            ItemStack stack1 = drops.get(0);

            assertEquals(7, stack1.getCount());
        }
    }

    @Nested
    @DisplayName("Matching stacks")
    class Matcher {

        @Test
        @DisplayName("The same stack returns true")
        public void doMatch() {
            assertTrue(ItemHelper.stackMatchesStackOrOreClass(new ItemStack(Items.STICK), "stickWood"));
            assertTrue(ItemHelper.stackMatchesStackOrOreClass(new ItemStack(Items.STICK), new ItemStack(Items.STICK)));
        }

        @Test
        @DisplayName("different stacks return false")
        public void dontmatch() {
            assertFalse(ItemHelper.stackMatchesStackOrOreClass(new ItemStack(Items.STICK), "stickStone"));
            assertFalse(ItemHelper.stackMatchesStackOrOreClass(new ItemStack(Items.STICK), new ItemStack(Items.APPLE)));
        }
    }

    @Nested
    @DisplayName("Recipes")
    class Recipes {


    }

    @Nested
    @DisplayName("Fluid Stacks")
    class FluidStacks {

        @Test
        @DisplayName("same stacks are equal")
        public void test_fluidStackEqual() {
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
        @DisplayName("can exract fluid from tanks")
        public void test_extractFluidsFromTanks() {
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

    }

    @Test
    @DisplayName("stack copying")
    public void test_copyStack() {
        ItemStack stack = new ItemStack(Items.STICK, 2, 0);
        ItemStack copy = ItemHelper.copyStack(stack, 42);

        assertSame(stack.getItem(), copy.getItem());
        assertEquals(42, copy.getCount());
    }


    //Todo descriptions for the remaining tests
    @Test
    public void test_isSameRecipeInput() {
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
    public void test_removeInputsFromInventory_stacks() {
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
    public void test_removeInputsFromInventory_ore() {
        ItemStackHandler inv = new ItemStackHandler(2);
        inv.setStackInSlot(0, new ItemStack(Items.APPLE));
        inv.setStackInSlot(1, new ItemStack(Items.STICK, 3));

        RecipeInputImpl input1 = new RecipeInputImpl("stickWood", 2);
        RecipeInputImpl input2 = RecipeInputImpl.create(new ItemStack(Items.APPLE, 1));
        ItemHelper.removeInputsFromInventory(Lists.newArrayList(input1, input2), inv, 0, 2);

        assertTrue(inv.getStackInSlot(0).isEmpty());
        assertEquals(1, inv.getStackInSlot(1).getCount());
    }


}
