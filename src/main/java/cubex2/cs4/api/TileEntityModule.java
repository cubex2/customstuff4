package cubex2.cs4.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public interface TileEntityModule
{
    void readFromNBT(NBTTagCompound compound);

    NBTTagCompound writeToNBT(NBTTagCompound compound);

    default void setWorld(World world)
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
