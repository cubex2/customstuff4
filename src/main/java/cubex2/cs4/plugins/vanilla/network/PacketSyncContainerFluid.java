package cubex2.cs4.plugins.vanilla.network;

import cubex2.cs4.plugins.vanilla.gui.ContainerCS4;
import cubex2.cs4.util.NetworkHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncContainerFluid implements IMessage
{
    private int windowId;
    private int tank;
    private FluidStack fluid;

    public PacketSyncContainerFluid(int windowId, int tank, FluidStack fluid)
    {
        this.windowId = windowId;
        this.tank = tank;
        this.fluid = fluid;
    }

    public PacketSyncContainerFluid()
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        windowId = buf.readByte();
        tank = buf.readByte();

        NBTTagCompound fluidNbt = ByteBufUtils.readTag(buf);
        fluid = FluidStack.loadFluidStackFromNBT(fluidNbt);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(windowId);
        buf.writeByte(tank);
        NBTTagCompound fluidNbt = fluid == null ? null : fluid.writeToNBT(new NBTTagCompound());
        ByteBufUtils.writeTag(buf, fluidNbt);
    }

    public static class Handler implements IMessageHandler<PacketSyncContainerFluid, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSyncContainerFluid message, MessageContext ctx)
        {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (NetworkHelper.checkThreadAndEnqueue(message, this, ctx, Minecraft.getMinecraft()))
                return null;

            if (player.openContainer.windowId == message.windowId && (player.openContainer instanceof ContainerCS4))
            {
                ContainerCS4 container = (ContainerCS4) player.openContainer;
                container.putFluidInTank(message.tank, message.fluid);
            }

            return null;
        }
    }
}