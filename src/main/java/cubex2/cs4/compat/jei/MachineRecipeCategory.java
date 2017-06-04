package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.jei.JEIMachineRecipe;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleMachine;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class MachineRecipeCategory extends BaseRecipeCategory<MachineRecipeWrapper, TileEntityModuleMachine.Supplier>
{
    public MachineRecipeCategory(JEIMachineRecipe recipe, IGuiHelper guiHelper)
    {
        super(recipe, guiHelper, TileEntityModuleMachine.Supplier.class);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MachineRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        super.setRecipe(recipeLayout, recipeWrapper, ingredients);

        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();

        List<List<ItemStack>> inputItems = ingredients.getInputs(ItemStack.class);
        List<List<FluidStack>> inputFluids = ingredients.getInputs(FluidStack.class);
        List<ItemStack> outputItems = ingredients.getOutputs(ItemStack.class);
        List<FluidStack> outputFluids = ingredients.getOutputs(FluidStack.class);

        initItems(stacks, true, module.inputSlots, 0);
        initItems(stacks, false, module.outputSlots, module.inputSlots);

        initFluids(fluids, true, module.inputTanks, 0);
        initFluids(fluids, false, module.outputTanks, module.inputTanks.length);

        for (int i = 0; i < inputItems.size(); i++)
        {
            stacks.set(i, inputItems.get(i));
        }

        for (int i = 0; i < outputItems.size(); i++)
        {
            stacks.set(module.inputSlots + i, outputItems.get(i));
        }

        for (int i = 0; i < inputFluids.size(); i++)
        {
            fluids.set(i, inputFluids.get(i));
        }

        for (int i = 0; i < outputFluids.size(); i++)
        {
            fluids.set(module.inputTanks.length + i, outputFluids.get(i));
        }
    }
}
