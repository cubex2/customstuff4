package cubex2.cs4.plugins.vanilla.gui;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityItemHandlerSupplier
{
    @CapabilityInject(ItemHandlerSupplier.class)
    public static Capability<ItemHandlerSupplier> ITEM_HANDLER_SUPPLIER_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ItemHandlerSupplier.class, new Capability.IStorage<ItemHandlerSupplier>()
        {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<ItemHandlerSupplier> capability, ItemHandlerSupplier instance, EnumFacing side)
            {
                return null;
            }

            @Override
            public void readNBT(Capability<ItemHandlerSupplier> capability, ItemHandlerSupplier instance, EnumFacing side, NBTBase nbt)
            {

            }
        }, () -> ItemHandlerSupplier.EMPTY);
    }

}
