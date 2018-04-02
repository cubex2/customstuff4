package cubex2.cs4.script;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

/**
 * @author <a href="mailto:kisandrasgabor@gmail.com">András Kis</a>
 */

@EventBusSubscriber
public class EventHandler {

    Collection<ScriptObjectMirror> pickupItem;
    @SubscribeEvent
    public void EntityItemPickupEvent(EntityItemPickupEvent event) {
        for (ScriptObjectMirror e : pickupItem) {
            e.call(e, event);
        }
    }

    @SubscribeEvent
    public static void minecartUpdate(MinecartUpdateEvent event) {

    }


}
