package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.crafting.CraftingManagerCS4;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ShapelessRecipeTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void testDeserialization()
    {
        ShapelessRecipe recipe = gson.fromJson("{ \"items\": [ \"minecraft:apple\", \"minecraft:bow\" ]," +
                                               "\"recipeList\": \"test:recipes\"," +
                                               "\"result\": \"minecraft:coal\", \"remove\":true }", ShapelessRecipe.class);


        RecipeInput input0 = recipe.items.get(0);
        RecipeInput input1 = recipe.items.get(1);

        assertSame(Items.APPLE, input0.getStack().getItemStack().getItem());
        assertSame(Items.BOW, input1.getStack().getItemStack().getItem());
        assertSame(Items.COAL, recipe.result.getItemStack().getItem());
        assertTrue(recipe.remove);
        assertEquals(new ResourceLocation("test:recipes"), recipe.recipeList);
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

    @Test
    public void test_addRecipe()
    {
        ShapelessRecipe recipe = new ShapelessRecipe();
        recipe.recipeList = new ResourceLocation("testmod:recipes");
        recipe.items.add(new RecipeInputImpl("stickWood"));
        recipe.result = new WrappedItemStackConstant(new ItemStack(Items.APPLE));

        int prevRecipes = CraftingManagerCS4.getRecipes(recipe.recipeList).size();

        recipe.addRecipe();

        int newRecipes = CraftingManagerCS4.getRecipes(recipe.recipeList).size();

        assertEquals(prevRecipes + 1, newRecipes);
    }

    @Test
    public void test_removeRecipe_onlyResult()
    {
        ShapelessRecipe recipe = gson.fromJson("{ \"result\": \"minecraft:apple\" }", ShapelessRecipe.class);

        assertTrue(recipe.removeRecipe(createTestRecipes(Items.APPLE)));
        assertFalse(recipe.removeRecipe(createTestRecipes(Items.DIAMOND_AXE)));
        assertTrue(recipe.removeRecipe(createTestRecipesOre(Items.APPLE)));
    }

    @Test
    public void test_removeRecipe_onlyInput()
    {
        ShapelessRecipe recipe = gson.fromJson("{ \"items\": [ \"minecraft:stone\", \"minecraft:log\" ] }", ShapelessRecipe.class);

        assertTrue(recipe.removeRecipe(createTestRecipes(Items.APPLE)));
        assertFalse(recipe.removeRecipe(createTestRecipesOre(Items.APPLE)));
    }

    @Test
    public void test_removeRecipe_both()
    {
        ShapelessRecipe recipe = gson.fromJson("{ \"items\": [ \"minecraft:stone\", \"minecraft:log\" ]," +
                                               "\"result\": \"minecraft:apple\" }", ShapelessRecipe.class);

        assertTrue(recipe.removeRecipe(createTestRecipes(Items.APPLE)));
        assertFalse(recipe.removeRecipe(createTestRecipes(Items.DIAMOND_AXE)));
        assertFalse(recipe.removeRecipe(createTestRecipesOre(Items.APPLE)));
    }

    @Test
    public void test_removeRecipe_onlyInput_ore()
    {
        ShapelessRecipe recipe = gson.fromJson("{ \"items\": [ \"minecraft:stone\", \"oreclass:stickWood\" ] }", ShapelessRecipe.class);

        assertFalse(recipe.removeRecipe(createTestRecipes(Items.APPLE)));
        assertTrue(recipe.removeRecipe(createTestRecipesOre(Items.APPLE)));
    }

    private List<IRecipe> createTestRecipes(Item result)
    {
        return Lists.newArrayList(new ShapelessRecipes("group", new ItemStack(result), NonNullList.from(Ingredient.EMPTY, Ingredient.fromItem(Items.ITEM_FRAME))),
                                  new ShapelessRecipes("group", new ItemStack(result), NonNullList.from(Ingredient.EMPTY, Ingredient.fromStacks(new ItemStack(Blocks.STONE)), Ingredient.fromStacks(new ItemStack(Blocks.LOG)))));
    }

    private List<IRecipe> createTestRecipesOre(Item result)
    {
        return Lists.newArrayList(new ShapelessOreRecipe(new ResourceLocation("group"), new ItemStack(result), new ItemStack(Items.ITEM_FRAME)),
                                  new ShapelessOreRecipe(new ResourceLocation("group"), new ItemStack(result), new ItemStack(Blocks.STONE), "stickWood"));
    }
}
