package cubex2.cs4.compat.jei;

import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.jei.JEIRecipe;
import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import cubex2.cs4.plugins.vanilla.ContentTileEntityBase;
import cubex2.cs4.plugins.vanilla.TileEntityRegistry;
import cubex2.cs4.plugins.vanilla.gui.FluidDisplay;
import cubex2.cs4.plugins.vanilla.gui.SlotData;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleTank;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class BaseRecipeCategory<T extends IRecipeWrapper, M extends TileEntityModuleSupplier> extends BlankRecipeCategory<T>
{
    private final String uid;
    private final String title;
    private final IDrawable background;

    private final ContentGuiContainer gui;
    protected final ContentTileEntityBase tileEntity;
    protected final M module;
    private final String moduleName;

    private final JEIRecipe recipe;

    protected BaseRecipeCategory(JEIRecipe recipe, IGuiHelper guiHelper, Class<M> moduleClass)
    {
        this.recipe = recipe;

        uid = recipe.getUid();
        title = recipe.title;
        gui = recipe.getGui();
        tileEntity = TileEntityRegistry.getContent(recipe.tileEntity);

        Pair<String, M> pair = getModule(moduleClass);
        this.moduleName = pair.getLeft();
        this.module = pair.getRight();

        background = guiHelper.createDrawable(gui.bg, recipe.bgX, recipe.bgY, recipe.bgWidth, recipe.bgHeight);
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public M getModule()
    {
        return module;
    }

    @SuppressWarnings("unchecked")
    private Pair<String, M> getModule(Class<M> clazz)
    {
        if (recipe.module == null)
        {
            for (Map.Entry<String, TileEntityModuleSupplier> entry : tileEntity.modules.entrySet())
            {
                if (clazz.isAssignableFrom(entry.getValue().getClass()))
                {
                    return Pair.of(entry.getKey(), (M) entry.getValue());
                }
            }
        } else
        {
            return Pair.of(recipe.module, (M) tileEntity.modules.get(recipe.module));
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
    public void setRecipe(IRecipeLayout recipeLayout, T recipeWrapper, IIngredients ingredients)
    {
        if (recipe.transferButtonX >= 0 && recipe.transferButtonY >= 0)
        {
            recipeLayout.setRecipeTransferButton(recipe.transferButtonX - recipe.bgX, recipe.transferButtonY - recipe.bgY);
        }
    }

    protected void initFluids(IGuiFluidStackGroup fluids, boolean input, String[] tanks, int startSlot)
    {
        for (int i = 0; i < tanks.length; i++)
        {
            TileEntityModuleTank.Supplier tank = findTank(tanks[i]);
            FluidDisplay display = findFluidDisplay(tanks[i]);
            if (tank != null && display != null)
                fluids.init(startSlot + i, input, display.x - recipe.bgX, display.y - recipe.bgY, display.width, display.height, tank.capacity, false, null);
        }
    }

    protected void initItems(IGuiItemStackGroup stacks, boolean input, int slots, int startSlot)
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

    @Nullable
    private TileEntityModuleTank.Supplier findTank(String name)
    {
        TileEntityModuleSupplier module = tileEntity.modules.get(name);
        if (module instanceof TileEntityModuleTank.Supplier)
        {
            return (TileEntityModuleTank.Supplier) module;
        }

        return null;
    }

    @Nullable
    private FluidDisplay findFluidDisplay(String name)
    {
        for (FluidDisplay display : gui.fluidDisplays)
        {
            if (display.source.equals(name))
            {
                return display;
            }
        }

        return null;
    }
}
