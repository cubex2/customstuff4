package cubex2.cs4.compat.jei;

import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.jei.JEICraftingRecipe;
import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import cubex2.cs4.plugins.vanilla.ContentTileEntityBase;
import cubex2.cs4.plugins.vanilla.TileEntityRegistry;
import cubex2.cs4.plugins.vanilla.gui.SlotData;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleCrafting;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class CraftingRecipeCategory extends BlankRecipeCategory<IRecipeWrapper>
{
    private final String uid;
    private final String title;

    private final ContentGuiContainer gui;
    private final ContentTileEntityBase tileEntity;
    private final TileEntityModuleCrafting.Supplier module;
    private final String moduleName;
    private final JEICraftingRecipe recipe;

    private final IDrawable background;
    private final ICraftingGridHelper craftingGridHelper;

    public CraftingRecipeCategory(JEICraftingRecipe recipe, IGuiHelper guiHelper)
    {
        this.recipe = recipe;
        uid = recipe.getRecipeUid();
        title = recipe.title;

        gui = recipe.getGui();
        tileEntity = TileEntityRegistry.getContent(recipe.tileEntity);

        Pair<String, TileEntityModuleCrafting.Supplier> pair = getModule();
        this.moduleName = pair.getLeft();
        this.module = pair.getRight();

        background = guiHelper.createDrawable(gui.bg, recipe.bgX, recipe.bgY, recipe.bgWidth, recipe.bgHeight);
        craftingGridHelper = guiHelper.createCraftingGridHelper(0, module.rows * module.columns);
    }

    private Pair<String, TileEntityModuleCrafting.Supplier> getModule()
    {
        if (recipe.module == null)
        {
            for (Map.Entry<String, TileEntityModuleSupplier> entry : tileEntity.modules.entrySet())
            {
                if (entry.getValue() instanceof TileEntityModuleCrafting.Supplier)
                {
                    return Pair.of(entry.getKey(), (TileEntityModuleCrafting.Supplier) entry.getValue());
                }
            }
        } else
        {
            return Pair.of(recipe.module, (TileEntityModuleCrafting.Supplier) tileEntity.modules.get(recipe.module));
        }

        throw new RuntimeException("No machine module found");
    }

    @Override
    public String getUid()
    {
        return uid;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
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

    private void initItems(IGuiItemStackGroup stacks, boolean input, int slots, int startSlot)
    {
        for (int i = 0; i < slots; i++)
        {
            SlotData data = findModuleSlot(startSlot + i);
            if (data != null)
                stacks.init(startSlot + i, input, data.getX(data.getColumn(i)) - recipe.bgX - 1, data.getY(data.getRow(i)) - recipe.bgY - 1);
        }
    }

    @Nullable
    private SlotData findModuleSlot(int index)
    {
        for (SlotData data : gui.slots)
        {
            if (data.name.equals(moduleName) &&
                data.containsIndex(index))
            {
                return data;
            }
        }

        return null;
    }
}
