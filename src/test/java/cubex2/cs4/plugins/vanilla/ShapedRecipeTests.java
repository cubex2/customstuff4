package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.RecipeInput;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ShapedRecipeTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();
    }

    @Test
    public void testDeserialization()
    {
        ShapedRecipe recipe = gson.fromJson("{ \"shape\": [ \"AA\", \"BB\" ]," +
                                            "\"items\": { \"A\":\"minecraft:stone\", \"B\": { \"item\":\"minecraft:log\" } }," +
                                            "\"result\": \"mincraft:obsidian\"," +
                                            "\"remove\": true," +
                                            "\"mirrored\": false }", ShapedRecipe.class);

        RecipeInput inputA = recipe.items.get('A');
        RecipeInput inputB = recipe.items.get('B');

        assertArrayEquals(new String[] {"AA", "BB"}, recipe.shape);
        assertTrue(inputA.isItemStack());
        assertTrue(inputB.isItemStack());
        assertNotNull(recipe.result);
        assertFalse(recipe.mirrored);
        assertTrue(recipe.remove);
    }

    @Test
    public void testDeserialization_shapeSingleString()
    {
        ShapedRecipe recipe = gson.fromJson("{ \"shape\": \"BB\" }", ShapedRecipe.class);

        assertArrayEquals(new String[] {"BB"}, recipe.shape);
    }

    @Test
    public void testGetInputForRecipe()
    {
        ShapedRecipe recipe = gson.fromJson("{ \"shape\": [ \"AA\", \"BB\" ]," +
                                            "\"items\": { \"A\":\"minecraft:stone\", \"B\": { \"item\":\"minecraft:log\" } }," +
                                            "\"result\": \"mincraft:obsidian\"," +
                                            "\"mirrored\": false }", ShapedRecipe.class);

        Object[] input = recipe.getInputForRecipe();

        assertEquals(6, input.length);
        assertEquals("AA", input[0]);
        assertEquals("BB", input[1]);
        assertEquals('A', input[2]);
        assertSame(Item.getItemFromBlock(Blocks.STONE), ((ItemStack) input[3]).getItem());
        assertEquals('B', input[4]);
        assertSame(Item.getItemFromBlock(Blocks.LOG), ((ItemStack) input[5]).getItem());
    }

    @Test
    public void test_removeRecipe_onlyResult()
    {
        ShapedRecipe recipe = new ShapedRecipe();
        recipe.result = new WrappedItemStackImpl(new ResourceLocation("minecraft:apple"), 0, 1);

        assertTrue(recipe.removeRecipe(createTestRecipes(Items.APPLE)));
        assertFalse(recipe.removeRecipe(createTestRecipes(Items.DIAMOND_AXE)));
        assertTrue(recipe.removeRecipe(createTestRecipesOre(Items.APPLE)));
    }

    @Test
    public void test_removeRecipe_onlyInput()
    {
        ShapedRecipe recipe = gson.fromJson("{ \"shape\": [ \"AA\", \"BB\" ]," +
                                            "\"items\": { \"A\":\"minecraft:stone\", \"B\": { \"item\":\"minecraft:log\" } } }", ShapedRecipe.class);

        assertTrue(recipe.removeRecipe(createTestRecipes(Items.APPLE)));
        assertFalse(recipe.removeRecipe(createTestRecipesOre(Items.APPLE)));
    }

    @Test
    public void test_removeRecipe_both()
    {
        ShapedRecipe recipe = gson.fromJson("{ \"shape\": [ \"AA\", \"BB\" ]," +
                                            "\"items\": { \"A\":\"minecraft:stone\", \"B\": { \"item\":\"minecraft:log\" } }," +
                                            "\"result\": \"minecraft:apple\" }", ShapedRecipe.class);

        assertTrue(recipe.removeRecipe(createTestRecipes(Items.APPLE)));
        assertFalse(recipe.removeRecipe(createTestRecipes(Items.DIAMOND_AXE)));
        assertFalse(recipe.removeRecipe(createTestRecipesOre(Items.APPLE)));
    }

    @Test
    public void test_removeRecipe_onlyInput_ore()
    {
        ShapedRecipe recipe = gson.fromJson("{ \"shape\": [ \"AA\", \"BB\" ]," +
                                            "\"items\": { \"A\":\"minecraft:stone\", \"B\": \"oreclass:stickWood\" } }", ShapedRecipe.class);

        assertFalse(recipe.removeRecipe(createTestRecipes(Items.APPLE)));
        assertTrue(recipe.removeRecipe(createTestRecipesOre(Items.APPLE)));
    }

    private List<IRecipe> createTestRecipes(Item result)
    {
        return Lists.newArrayList(new ShapedRecipes(1, 1, new ItemStack[] {new ItemStack(Items.ITEM_FRAME)}, new ItemStack(result)),
                                  new ShapedRecipes(2, 2,
                                                    new ItemStack[] {
                                                            new ItemStack(Blocks.STONE), new ItemStack(Blocks.STONE),
                                                            new ItemStack(Blocks.LOG), new ItemStack(Blocks.LOG)},
                                                    new ItemStack(result)));
    }

    private List<IRecipe> createTestRecipesOre(Item result)
    {
        return Lists.newArrayList(new ShapedOreRecipe(result, "x", 'x', new ItemStack(Items.ITEM_FRAME)),
                                  new ShapedOreRecipe(result, "aa", "bb", 'a', new ItemStack(Blocks.STONE), 'b', "stickWood"));
    }
}
