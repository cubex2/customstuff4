package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.api.ItemModule;
import cubex2.cs4.api.ItemModuleSupplier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ItemModuleInventory implements ItemModule
{
    private final ItemStackHandler invHandler;

    public ItemModuleInventory(Supplier supplier)
    {
        invHandler = new ItemStackHandler(supplier.size);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        invHandler.deserializeNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        return invHandler.serializeNBT();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invHandler);
        }

        return null;
    }

    public static class Supplier implements ItemModuleSupplier
    {
        public int size;

        @Override
        public ItemModule createModule(ItemStack stack)
        {
            return new ItemModuleInventory(this);
        }
    }
}
