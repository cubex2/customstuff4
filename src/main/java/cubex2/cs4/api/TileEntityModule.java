package cubex2.cs4.api;

import cubex2.cs4.plugins.vanilla.tileentity.FieldSupplier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public interface TileEntityModule extends FieldSupplier
{
    void readFromNBT(NBTTagCompound compound);

    NBTTagCompound writeToNBT(NBTTagCompound compound);

    default NBTTagCompound writeToUpdateTag(NBTTagCompound compound)
    {
        return compound;
    }

    default void update()
    {

    }

    default void setWorld(World world)
    {

    }

    default int getFieldCount()
    {
        return 0;
    }

    default int getField(int id)
    {
        return 0;
    }

    default void setField(int id, int value)
    {

    }

    default boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return false;
    }

    @Nullable
    default <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return null;
    }
}
