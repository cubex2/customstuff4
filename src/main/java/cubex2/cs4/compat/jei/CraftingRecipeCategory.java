package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.jei.JEICraftingRecipe;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleCrafting;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CraftingRecipeCategory extends BaseRecipeCategory<IRecipeWrapper, TileEntityModuleCrafting.Supplier>
{
    private final ICraftingGridHelper craftingGridHelper;

    public CraftingRecipeCategory(JEICraftingRecipe recipe, IGuiHelper guiHelper)
    {
        super(recipe, guiHelper, TileEntityModuleCrafting.Supplier.class);

        craftingGridHelper = guiHelper.createCraftingGridHelper(0, module.rows * module.columns);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

        List<List<ItemStack>> inputItems = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputItems = ingredients.getOutputs(ItemStack.class);

        initItems(stacks, true, module.rows * module.columns, 0);
        initItems(stacks, false, 1, module.rows * module.columns);

        if (recipeWrapper instanceof IShapedCraftingRecipeWrapper)
        {
            IShapedCraftingRecipeWrapper wrapper = (IShapedCraftingRecipeWrapper) recipeWrapper;
            craftingGridHelper.setInputs(stacks, inputItems, wrapper.getWidth(), wrapper.getHeight());
        } else
        {
            craftingGridHelper.setInputs(stacks, inputItems);
            recipeLayout.setShapeless();
        }

        stacks.set(module.rows * module.columns, outputItems.get(0));
    }
}
