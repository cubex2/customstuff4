package cubex2.cs4.compat.jei;

import com.google.common.collect.Lists;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class MachineRecipeWrapper implements IRecipeWrapper
{
    private final MachineRecipe recipe;
    private final IJeiHelpers jeiHelpers;

    private final String cookTimeString;

    public MachineRecipeWrapper(MachineRecipe recipe, IJeiHelpers jeiHelpers)
    {
        this.recipe = recipe;
        this.jeiHelpers = jeiHelpers;

        if (recipe.getCookTime() > 0)
            cookTimeString = I18n.format("gui.cs4.jei.machineRecipe.cookTime", recipe.getCookTime());
        else
            cookTimeString = "";
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        //minecraft.fontRendererObj.drawString(cookTimeString, 0, recipeHeight - 8, Color.gray.getRGB());
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<Object> inputs = Lists.newArrayList();
        for (RecipeInput input : recipe.getRecipeInput())
        {
            if (input.isOreClass())
                inputs.add(input.getOreClass().getOreName());
            else
                inputs.add(input.getStack().getItemStack().copy());
        }

        ingredients.setInputLists(ItemStack.class, jeiHelpers.getStackHelper().expandRecipeItemStackInputs(inputs));
        ingredients.setInputs(FluidStack.class, recipe.getFluidRecipeInput());

        ingredients.setOutputs(ItemStack.class, recipe.getRecipeOutput());
        ingredients.setOutputs(FluidStack.class, recipe.getFluidRecipeOutput());
    }
}
