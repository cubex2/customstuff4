package cubex2.cs4.plugins.vanilla.tileentity;

import com.google.common.collect.Maps;
import cubex2.cs4.api.TileEntityModule;
import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.vanilla.ContentTileEntitySimple;
import cubex2.cs4.plugins.vanilla.TileEntityRegistry;
import cubex2.cs4.plugins.vanilla.gui.ItemHandlerSupplier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public abstract class TileEntitySimple extends TileEntity implements CSTileEntity<ContentTileEntitySimple>, ItemHandlerSupplier
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
    protected void setWorldCreate(World worldIn)
    {
        super.setWorldCreate(worldIn);

        modules.values().forEach(m -> m.setWorld(worldIn));
    }

    @Override
    public ContentTileEntitySimple getContent()
    {
        return content;
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
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        for (TileEntityModule module : modules.values())
        {
            if (module.hasCapability(capability, facing))
                return module.getCapability(capability, facing);
        }
        return super.getCapability(capability, facing);
    }
}
