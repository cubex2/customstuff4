package cubex2.cs4.plugins.vanilla.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.api.ItemModule;
import cubex2.cs4.api.ItemModuleSupplier;
import cubex2.cs4.plugins.vanilla.ContentItemBase;
import cubex2.cs4.plugins.vanilla.gui.CapabilityItemHandlerSupplier;
import cubex2.cs4.plugins.vanilla.gui.ItemHandlerSupplier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CapabilityProviderItem implements ICapabilityProvider, INBTSerializable<NBTTagCompound>, ItemHandlerSupplier
{
    private final ItemStack stack;
    private final ContentItemBase<?> content;
    private final LinkedHashMap<String, ItemModule> modules = Maps.newLinkedHashMap();

    public CapabilityProviderItem(ItemStack stack, ContentItemBase<?> content)
    {
        this.stack = stack;
        this.content = content;
        createModules();
    }

    private void createModules()
    {
        for (Map.Entry<String, ItemModuleSupplier> entry : content.modules.entrySet())
        {
            modules.put(entry.getKey(), entry.getValue().createModule(stack));
        }
    }

    @Override
    public Optional<IItemHandler> getItemHandler(String name)
    {
        ItemModule module = modules.get(name);
        if (module != null)
        {
            return Optional.ofNullable(module.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        }
        return Optional.empty();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandlerSupplier.ITEM_HANDLER_SUPPLIER_CAPABILITY)
            return true;

        return modules.values().stream()
                      .anyMatch(module -> module.hasCapability(capability, facing));
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandlerSupplier.ITEM_HANDLER_SUPPLIER_CAPABILITY)
        {
            return (T) this;
        }

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) getItemHandlerCapability(facing);
        }

        for (ItemModule module : modules.values())
        {
            if (module.hasCapability(capability, facing))
                return module.getCapability(capability, facing);
        }

        return null;
    }

    @Nullable
    private IItemHandlerModifiable getItemHandlerCapability(@Nullable EnumFacing facing)
    {
        Capability<IItemHandler> capability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

        List<IItemHandlerModifiable> handlers = Lists.newLinkedList();

        for (ItemModule module : modules.values())
        {
            if (module.hasCapability(capability, facing))
                handlers.add((IItemHandlerModifiable) module.getCapability(capability, facing));
        }

        if (handlers.size() == 1)
            return handlers.get(0);
        else if (handlers.size() > 1)
            return new CombinedInvWrapper(handlers.toArray(new IItemHandlerModifiable[handlers.size()]));
        else
            return null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        for (Map.Entry<String, ItemModule> entry : modules.entrySet())
        {
            ItemModule module = entry.getValue();
            NBTTagCompound moduleNbt = module.writeToNBT(new NBTTagCompound());

            nbt.setTag("Module_" + entry.getKey(), moduleNbt);
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        for (Map.Entry<String, ItemModule> entry : modules.entrySet())
        {
            NBTTagCompound moduleNbt = nbt.getCompoundTag("Module_" + entry.getKey());
            ItemModule module = entry.getValue();

            module.readFromNBT(moduleNbt);
        }
    }
}
