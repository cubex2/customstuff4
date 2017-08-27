package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.CSBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class EventHandler
{
    @SubscribeEvent
    public static void canCreateFluidSource(BlockEvent.CreateFluidSourceEvent event)
    {
        if (event.getResult() != Event.Result.DEFAULT)
            return;

        if (applyModifiers(event))
            return;

        Block block = event.getState().getBlock();

        if (block instanceof CSBlock)
        {
            ContentBlockBase content = ((CSBlock) block).getContent();
            if (content instanceof ContentBlockFluid)
            {
                ContentBlockFluid fluid = (ContentBlockFluid) content;
                event.setResult(fluid.canCreateSource ? Event.Result.ALLOW : Event.Result.DENY);
            }
        }
    }

    private static boolean applyModifiers(BlockEvent.CreateFluidSourceEvent event)
    {
        Block block = event.getState().getBlock();

        for (FluidModifier modifier : FluidModifier.getModifiers())
        {
            if (block.getRegistryName() != null && block.getRegistryName().equals(modifier.block))
            {
                if (modifier.canCreateSource != null)
                {
                    event.setResult(modifier.canCreateSource ? Event.Result.ALLOW : Event.Result.DENY);
                    return true;
                }
            }
        }

        return false;
    }

    @SubscribeEvent
    public static void furnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event)
    {
        for (Fuel fuel : Fuel.INSTANCES)
        {
            if (fuel.appliesToStack(event.getItemStack()))
            {
                event.setBurnTime(fuel.burnTime);
                break;
            }
        }
    }
}
