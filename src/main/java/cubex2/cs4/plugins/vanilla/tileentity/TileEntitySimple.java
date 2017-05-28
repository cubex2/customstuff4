package cubex2.cs4.plugins.vanilla.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.api.TileEntityModule;
import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.vanilla.ContentTileEntitySimple;
import cubex2.cs4.plugins.vanilla.TileEntityRegistry;
import cubex2.cs4.plugins.vanilla.gui.FluidSource;
import cubex2.cs4.plugins.vanilla.gui.ItemHandlerSupplier;
import cubex2.cs4.plugins.vanilla.gui.ProgressBarSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TileEntitySimple extends TileEntity implements CSTileEntity<ContentTileEntitySimple>, ItemHandlerSupplier, FieldSupplier, ProgressBarSource, ITickable, FluidSource
{
    private final ContentTileEntitySimple content;
    private final LinkedHashMap<String, TileEntityModule> modules = Maps.newLinkedHashMap();

    public TileEntitySimple(String contentId)
    {
        content = (ContentTileEntitySimple) TileEntityRegistry.getContent(new ResourceLocation(contentId));
        createModules();
    }

    private void createModules()
    {
        for (Map.Entry<String, TileEntityModuleSupplier> entry : content.modules.entrySet())
        {
            modules.put(entry.getKey(), entry.getValue().createModule(this));
        }
    }

    @Override
    protected void func_190201_b(World worldIn)
    {
        super.func_190201_b(worldIn);

        modules.values().forEach(m -> m.setWorld(worldIn));
    }

    @Override
    public ContentTileEntitySimple getContent()
    {
        return content;
    }

    @Override
    public void update()
    {
        modules.values().forEach(TileEntityModule::update);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        for (Map.Entry<String, TileEntityModule> entry : modules.entrySet())
        {
            NBTTagCompound moduleNbt = compound.getCompoundTag("Module_" + entry.getKey());
            TileEntityModule module = entry.getValue();

            module.readFromNBT(moduleNbt);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        for (Map.Entry<String, TileEntityModule> entry : modules.entrySet())
        {
            TileEntityModule module = entry.getValue();
            NBTTagCompound moduleNbt = module.writeToNBT(new NBTTagCompound());

            compound.setTag("Module_" + entry.getKey(), moduleNbt);
        }

        return super.writeToNBT(compound);
    }

    @Override
    public Optional<IItemHandler> getItemHandler(String name)
    {
        TileEntityModule module = modules.get(name);

        if (module != null)
        {
            return Optional.ofNullable(module.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        }
        return Optional.empty();
    }

    @Override
    public int getFieldCount()
    {
        return modules.values().stream()
                      .mapToInt(TileEntityModule::getFieldCount)
                      .sum();
    }

    @Override
    public int getField(int id)
    {
        return moduleForField(id)
                .map(p -> p.getLeft().getField(p.getRight()))
                .orElse(0);
    }

    @Override
    public void setField(int id, int value)
    {
        moduleForField(id).ifPresent(p -> p.getLeft().setField(p.getRight(), value));
    }

    private Optional<Pair<TileEntityModule, Integer>> moduleForField(int id)
    {
        for (TileEntityModule module : modules.values())
        {
            if (id >= module.getFieldCount())
            {
                id -= module.getFieldCount();
            } else
            {
                return Optional.of(Pair.of(module, id));
            }
        }

        return Optional.empty();
    }

    @Override
    public float getProgress(String name)
    {
        String moduleName;
        String sourceName;
        TileEntityModule module;

        if (name.contains(":"))
        {
            String[] split = name.split(":");
            moduleName = split[0];
            sourceName = split[1];
        } else
        {
            moduleName = name;
            sourceName = name;
        }

        module = modules.get(moduleName);
        if (module instanceof ProgressBarSource)
            return ((ProgressBarSource) module).getProgress(sourceName);

        return 0.0f;
    }

    @Nullable
    @Override
    public IFluidTank getFluidTank(String name)
    {
        String moduleName;
        String sourceName;
        TileEntityModule module;

        if (name.contains(":"))
        {
            String[] split = name.split(":");
            moduleName = split[0];
            sourceName = split[1];
        } else
        {
            moduleName = name;
            sourceName = name;
        }

        module = modules.get(moduleName);
        if (module instanceof FluidSource)
            return ((FluidSource) module).getFluidTank(sourceName);

        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        for (TileEntityModule module : modules.values())
        {
            if (module.hasCapability(capability, facing))
                return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) getItemHandlerCapability(facing);
        }

        for (TileEntityModule module : modules.values())
        {
            if (module.hasCapability(capability, facing))
                return module.getCapability(capability, facing);
        }

        return super.getCapability(capability, facing);
    }

    @Nullable
    private IItemHandlerModifiable getItemHandlerCapability(@Nullable EnumFacing facing)
    {
        Capability<IItemHandler> capability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

        List<IItemHandlerModifiable> handlers = Lists.newLinkedList();

        for (TileEntityModule module : modules.values())
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
}
