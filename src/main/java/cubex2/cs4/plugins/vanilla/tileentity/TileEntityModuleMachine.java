package cubex2.cs4.plugins.vanilla.tileentity;

import cubex2.cs4.api.TileEntityModule;
import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.vanilla.crafting.ItemHandlerMachine;
import cubex2.cs4.plugins.vanilla.crafting.MachineFuel;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import cubex2.cs4.plugins.vanilla.gui.ProgressBarSource;
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
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class TileEntityModuleMachine implements TileEntityModule, ProgressBarSource
{
    private final ItemHandlerMachine invHandler;
    private final Supplier supplier;
    private final TileEntity tile;

    private int burnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;

    public TileEntityModuleMachine(TileEntity tile, Supplier supplier)
    {
        this.invHandler = new ItemHandlerMachine(supplier.inputSlots, supplier.outputSlots, supplier.fuelSlots, tile);
        this.tile = tile;
        this.supplier = supplier;
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
                    smeltItems();
                }
            } else
            {
                cookTime = 0;
            }
        }
    }

    private void smeltItems()
    {
        if (canSmelt())
        {
            MachineRecipe recipe = getActiveRecipe();

            NonNullList<ItemStack> resultItems = recipe.getResult();
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
    }

    private void fillBucketWithWater()
    {
        for (int i = 0; i < supplier.fuelSlots; i++)
        {
            ItemStack extracted = invHandler.extractFuel(i, 1, true);
            if (!extracted.isEmpty() && extracted.getItem() == Items.BUCKET)
            {
                invHandler.setFuelSlot(i, new ItemStack(Items.WATER_BUCKET));
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
        NonNullList<ItemStack> recipe = getActiveRecipe().getRecipeOutput();

        if (recipe.isEmpty())
        {
            return false;
        } else
        {
            for (int i = 0; i < recipe.size(); i++)
            {
                ItemStack stack = recipe.get(i);
                ItemStack inserted = invHandler.insertOutput(i, stack, true);
                if (!inserted.isEmpty())
                    return false;
            }

            return true;
        }
    }

    private MachineRecipe getActiveRecipe()
    {
        NonNullList<ItemStack> input = invHandler.getInputStacks();
        return MachineManager.findMatchingRecipe(supplier.recipeList, input, tile.getWorld());
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
        //if (true)
        //return 1f;
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
               (facing == null);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
            (facing == null))
        {
            return (T) invHandler;
        }

        return null;
    }

    public static class Supplier implements TileEntityModuleSupplier
    {
        public int inputSlots = 1;
        public int outputSlots = 1;
        public int fuelSlots = 1;
        public int cookTime = 200;

        public ResourceLocation recipeList = new ResourceLocation("minecraft", "vanilla");
        public ResourceLocation fuelList = new ResourceLocation("minecraft", "vanilla");

        @Override
        public TileEntityModule createModule(TileEntity tileEntity)
        {
            return new TileEntityModuleMachine(tileEntity, this);
        }
    }
}
