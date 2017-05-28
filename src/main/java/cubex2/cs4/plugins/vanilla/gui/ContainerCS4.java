package cubex2.cs4.plugins.vanilla.gui;

import com.google.common.collect.Lists;
import cubex2.cs4.CustomStuff4;
import cubex2.cs4.plugins.vanilla.network.PacketSyncContainerFluid;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ContainerCS4 extends Container
{
    private final List<IFluidTank> tanks = Lists.newArrayList();
    private final List<FluidStack> fluidStacks = Lists.newArrayList();

    protected void addTank(IFluidTank tank)
    {
        tanks.add(tank);
        fluidStacks.add(null);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < tanks.size(); i++)
        {
            IFluidTank tank = tanks.get(i);

            FluidStack prev = fluidStacks.get(i);
            FluidStack current = tank.getFluid();

            if (!ItemHelper.fluidStackEqual(prev, current))
            {
                PacketSyncContainerFluid packet = new PacketSyncContainerFluid(windowId, i, current);
                for (IContainerListener listener : listeners)
                {
                    if (listener instanceof EntityPlayerMP)
                    {
                        EntityPlayerMP player = (EntityPlayerMP) listener;
                        CustomStuff4.network.sendTo(packet, player);
                    }
                }

                fluidStacks.set(i, current == null ? null : current.copy());
            }
        }
    }

    public void putFluidInTank(int tankId, @Nullable FluidStack fluid)
    {
        IFluidTank tank = tanks.get(tankId);
        tank.drain(Integer.MAX_VALUE, true);
        tank.fill(fluid, true);
    }
}
