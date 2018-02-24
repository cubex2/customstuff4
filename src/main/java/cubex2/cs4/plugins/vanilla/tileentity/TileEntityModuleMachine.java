package cubex2.cs4.plugins.vanilla.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.api.TileEntityModule;
import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.vanilla.crafting.*;
import cubex2.cs4.plugins.vanilla.gui.FluidSource;
import cubex2.cs4.plugins.vanilla.gui.ProgressBarSource;
import cubex2.cs4.util.CollectionHelper;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class TileEntityModuleMachine implements TileEntityModule, ProgressBarSource
{
    private final ItemHandlerMachine invHandler;
    private final Supplier supplier;
    private final TileEntity tile;
    private final FluidSource fluidSource;

    private int burnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;

    private final Map<EnumFacing, IItemHandler> itemHandlers = Maps.newHashMap();

    public TileEntityModuleMachine(TileEntity tile, Supplier supplier)
    {
        this.invHandler = new ItemHandlerMachine(supplier.inputSlots, supplier.outputSlots, supplier.fuelSlots, tile);
        this.tile = tile;
        this.supplier = supplier;

        fluidSource = tile instanceof FluidSource
                      ? n -> Optional.ofNullable(((FluidSource) tile).getFluidTank(n)).orElse(EmptyFluidHandler.INSTANCE)
                      : n -> EmptyFluidHandler.INSTANCE;

        for (EnumFacing facing : EnumFacing.values())
        {
            handlerForSide(facing).ifPresent(h -> itemHandlers.put(facing, h));
        }
    }

    private Optional<IItemHandler> handlerForSide(EnumFacing facing)
    {
        List<IItemHandlerModifiable> handlers = Lists.newArrayList();
        if (ArrayUtils.contains(supplier.sidesInput, facing))
            handlers.add(invHandler.getInputHandler());
        if (ArrayUtils.contains(supplier.sidesOutput, facing))
            handlers.add(invHandler.getOutputHandler());
        if (ArrayUtils.contains(supplier.sidesFuel, facing))
            handlers.add(invHandler.getFuelHandler());

        return Optional.ofNullable(handlers.isEmpty() ? null : new CombinedInvWrapper(handlers.toArray(new IItemHandlerModifiable[handlers.size()])));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        invHandler.deserializeNBT(compound);
        burnTime = compound.getInteger("BurnTime");
        cookTime = compound.getInteger("CookTime");
        totalCookTime = compound.getInteger("TotalCookTime");
        currentItemBurnTime = getActiveFuel().getBurnTime();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = invHandler.serializeNBT();
        compound.setInteger("BurnTime", burnTime);
        compound.setInteger("CookTime", cookTime);
        compound.setInteger("TotalCookTime", totalCookTime);

        return compound;
    }

    @Override
    public void update()
    {
        if (!tile.getWorld().isRemote)
        {
            if (isBurning() && needsFuel())
            {
                burnTime--;
            }

            if (!isBurning() && canSmelt() && needsFuel())
            {
                burnFuel();
            }

            if ((isBurning() || !needsFuel()) && canSmelt())
            {
                ++cookTime;

                if (totalCookTime == 0)
                    totalCookTime = getCookTime();

                if (cookTime >= totalCookTime)
                {
                    cookTime = 0;
                    totalCookTime = getCookTime();
                    smelt();
                }
            } else
            {
                cookTime = 0;
            }
        }
    }

    private void smelt()
    {
        if (canSmelt())
        {
            MachineRecipe recipe = getActiveRecipe();
            MachineRecipeOutput output = selectRecipeOutput(recipe);
            smeltItems(recipe, output);
            smeltFluids(recipe, output);
        }
    }

    private MachineRecipeOutput selectRecipeOutput(MachineRecipe recipe)
    {
        List<MachineRecipeOutput> available = recipe.getOutputs().stream()
                                                    .filter(this::doesRecipeOutputFitInMachine)
                                                    .collect(Collectors.toList());

        Optional<MachineRecipeOutput> output = CollectionHelper.randomElement(available, MachineRecipeOutput::getWeight);
        return output.orElse(MachineRecipeOutput.EMPTY);
    }

    private void smeltFluids(MachineRecipe recipe, MachineRecipeOutput output)
    {
        List<FluidStack> resultFluids = output.getResultFluids();

        for (int i = 0; i < resultFluids.size(); i++)
        {
            FluidStack stack = resultFluids.get(i);
            fluidSource.getFluidTank(supplier.outputTanks[i]).fill(stack, true);
        }

        extractInputFluids(recipe);
    }

    private void extractInputFluids(MachineRecipe recipe)
    {
        List<IFluidTank> remaining = Arrays.stream(supplier.inputTanks)
                                           .map(fluidSource::getFluidTank)
                                           .collect(Collectors.toCollection(LinkedList::new));

        ItemHelper.extractFluidsFromTanks(remaining, recipe.getFluidRecipeInput());
    }

    private void smeltItems(MachineRecipe recipe, MachineRecipeOutput output)
    {
        NonNullList<ItemStack> resultItems = output.getResultItems();
        for (int i = 0; i < resultItems.size(); i++)
        {
            ItemStack stack = resultItems.get(i);
            invHandler.insertOutput(i, stack, false);
        }

        for (ItemStack stack : invHandler.getInputStacks())
        {
            if (isWetSponge(stack))
            {
                fillBucketWithWater();
            }
        }

        invHandler.removeInputsFromInput(recipe.getRecipeInput());
    }

    private void fillBucketWithWater()
    {
        for (int i = 0; i < supplier.fuelSlots; i++)
        {
            ItemStack extracted = invHandler.getFuelHandler().extractItem(i, 1, true);
            if (!extracted.isEmpty() && extracted.getItem() == Items.BUCKET)
            {
                invHandler.getFuelHandler().setStackInSlot(i, new ItemStack(Items.WATER_BUCKET));
            }
        }
    }

    private boolean isWetSponge(ItemStack stack)
    {
        return stack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && stack.getMetadata() == 1;
    }

    private boolean needsFuel()
    {
        return supplier.fuelSlots > 0;
    }

    private void burnFuel()
    {
        MachineFuel fuel = getActiveFuel();

        burnTime = fuel.getBurnTime();
        currentItemBurnTime = burnTime;

        if (isBurning())
        {
            invHandler.removeInputsFromFuel(fuel.getFuelInput());
        }
    }

    private MachineFuel getActiveFuel()
    {
        return MachineManager.findMatchingFuel(supplier.fuelList, invHandler.getFuelStacks());
    }

    private boolean canSmelt()
    {
        MachineRecipe recipe = getActiveRecipe();
        NonNullList<MachineRecipeOutput> outputs = recipe.getOutputs();

        return outputs.stream().anyMatch(this::doesRecipeOutputFitInMachine);
    }

    private boolean doesRecipeOutputFitInMachine(MachineRecipeOutput output)
    {
        NonNullList<ItemStack> recipeItems = output.getOutputItems();
        List<FluidStack> recipeFluids = output.getOutputFluids();

        if (recipeItems.isEmpty() && recipeFluids.isEmpty())
        {
            return false;
        } else
        {
            for (int i = 0; i < recipeItems.size(); i++)
            {
                ItemStack stack = recipeItems.get(i);
                ItemStack inserted = invHandler.insertOutput(i, stack, true);
                if (!inserted.isEmpty())
                    return false;
            }

            for (int i = 0; i < recipeFluids.size(); i++)
            {
                FluidStack stack = recipeFluids.get(i);
                if (stack != null)
                {
                    int inserted = fluidSource.getFluidTank(supplier.outputTanks[i]).fill(stack, false);
                    if (inserted != stack.amount)
                        return false;
                }
            }

            return true;
        }
    }

    private MachineRecipe getActiveRecipe()
    {
        NonNullList<ItemStack> input = invHandler.getInputStacks();
        List<FluidStack> inputFluid = Arrays.stream(supplier.inputTanks)
                                            .map(fluidSource::getFluidTank)
                                            .map(IFluidTank::getFluid)
                                            .collect(Collectors.toList());

        return MachineManager.findMatchingRecipe(supplier.recipeList, input, inputFluid, tile.getWorld());
    }

    private boolean isBurning()
    {
        return burnTime > 0;
    }

    private int getCookTime()
    {
        int recipeTime = getActiveRecipe().getCookTime();
        return recipeTime <= 0 ? supplier.cookTime : recipeTime;
    }

    @Override
    public int getFieldCount()
    {
        return 4;
    }

    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.burnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.burnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public float getProgress(String name)
    {
        if (name.equals("cookTime"))
        {
            if (totalCookTime > 0)
            {
                return cookTime / (float) totalCookTime;
            }
        } else if (name.equals("burnTime"))
        {
            if (currentItemBurnTime > 0)
            {
                return burnTime / (float) currentItemBurnTime;
            }
        }

        return 0;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
               (facing == null || itemHandlers.containsKey(facing));
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (facing == null)
            {
                return (T) invHandler;
            } else
            {
                return (T) itemHandlers.get(facing);
            }
        }

        return null;
    }

    public static class Supplier implements TileEntityModuleSupplier
    {
        public int inputSlots = 1;
        public int outputSlots = 1;
        public int fuelSlots = 1;
        public int cookTime = 200;

        public String[] inputTanks = new String[0];
        public String[] outputTanks = new String[0];

        public ResourceLocation recipeList = new ResourceLocation("minecraft", "vanilla");
        public ResourceLocation fuelList = new ResourceLocation("minecraft", "vanilla");

        public EnumFacing[] sidesInput = new EnumFacing[] {EnumFacing.UP};
        public EnumFacing[] sidesOutput = new EnumFacing[] {EnumFacing.DOWN};
        public EnumFacing[] sidesFuel = new EnumFacing[] {EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST};

        @Override
        public TileEntityModule createModule(TileEntity tileEntity)
        {
            return new TileEntityModuleMachine(tileEntity, this);
        }
    }
}
