package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class VanillaFurnaceRecipeTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void getRecipeInput()
    {
        VanillaFurnaceRecipe recipe = new VanillaFurnaceRecipe(new ItemStack(Blocks.IRON_ORE));
        List<RecipeInput> input = recipe.getRecipeInput();

        assertEquals(1, input.size());
        assertSame(Item.getItemFromBlock(Blocks.IRON_ORE), input.get(0).getStack().getItemStack().getItem());
    }

    @Test
    public void getRecipeOutput()
    {
        VanillaFurnaceRecipe recipe = new VanillaFurnaceRecipe(new ItemStack(Blocks.IRON_ORE));
        MachineRecipeOutput output = recipe.getOutputs().get(0);

        assertSame(Items.IRON_INGOT, output.getOutputItems().get(0).getItem());
    }
}