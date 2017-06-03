package cubex2.cs4.compat.jei;

import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.jei.JEIMachineRecipe;
import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import cubex2.cs4.plugins.vanilla.ContentTileEntityBase;
import cubex2.cs4.plugins.vanilla.TileEntityRegistry;
import cubex2.cs4.plugins.vanilla.gui.FluidDisplay;
import cubex2.cs4.plugins.vanilla.gui.SlotData;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleMachine;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleTank;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class MachineRecipeCategory extends BlankRecipeCategory<MachineRecipeWrapper>
{
    private final String uid;
    private final String title;

    private final ContentGuiContainer gui;
    private final ContentTileEntityBase tileEntity;
    private final TileEntityModuleMachine.Supplier module;
    private final String moduleName;
    private final JEIMachineRecipe recipe;

    private final IDrawable background;


    public MachineRecipeCategory(JEIMachineRecipe recipe, IGuiHelper guiHelper)
    {
        this.recipe = recipe;
        uid = recipe.getRecipeUid();
        title = recipe.title;

        gui = recipe.getGui();
        tileEntity = TileEntityRegistry.getContent(recipe.tileEntity);

        Pair<String, TileEntityModuleMachine.Supplier> pair = getModule();
        this.moduleName = pair.getLeft();
        this.module = pair.getRight();

        background = guiHelper.createDrawable(gui.bg, recipe.bgX, recipe.bgY, recipe.bgWidth, recipe.bgHeight);
    }

    private Pair<String, TileEntityModuleMachine.Supplier> getModule()
    {
        if (recipe.module == null)
        {
            for (Map.Entry<String, TileEntityModuleSupplier> entry : tileEntity.modules.entrySet())
            {
                if (entry.getValue() instanceof TileEntityModuleMachine.Supplier)
                {
                    return Pair.of(entry.getKey(), (TileEntityModuleMachine.Supplier) entry.getValue());
                }
            }
        } else
        {
            return Pair.of(recipe.module, (TileEntityModuleMachine.Supplier) tileEntity.modules.get(recipe.module));
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
    public void setRecipe(IRecipeLayout recipeLayout, MachineRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();

        List<List<ItemStack>> inputItems = ingredients.getInputs(ItemStack.class);
        List<List<FluidStack>> inputFluids = ingredients.getInputs(FluidStack.class);
        List<List<ItemStack>> outputItems = ingredients.getOutputs(ItemStack.class);
        List<List<FluidStack>> outputFluids = ingredients.getOutputs(FluidStack.class);

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

    private void initFluids(IGuiFluidStackGroup fluids, boolean input, String[] tanks, int startSlot)
    {
        for (int i = 0; i < tanks.length; i++)
        {
            TileEntityModuleTank.Supplier tank = findTank(tanks[i]);
            FluidDisplay display = findFluidDisplay(tanks[i]);
            if (tank != null && display != null)
                fluids.init(startSlot + i, input, display.x - recipe.bgX, display.y - recipe.bgY, display.width, display.height, tank.capacity, false, null);
        }
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
