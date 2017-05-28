package cubex2.cs4.util;

import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class NetworkHelper
{
    public static <T extends IMessage> boolean checkThreadAndEnqueue(final T message, final IMessageHandler<T, IMessage> handler, final MessageContext ctx, IThreadListener listener)
    {
        if (!listener.isCallingFromMinecraftThread())
        {
            listener.addScheduledTask(() -> handler.onMessage(message, ctx));
            return true;
        }

        return false;
    }
}
