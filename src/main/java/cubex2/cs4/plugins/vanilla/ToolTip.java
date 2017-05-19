package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

class ToolTip extends SimpleContent
{
    private static final String MODE_ALL = "all";
    private static final String MODE_CTRL = "ctrl";
    private static final String MODE_SHIFT = "shift";
    private static final String MODE_ALT = "alt";
    private static final String MODE_NO_CTRL = "noctrl";
    private static final String MODE_NO_SHIFT = "noshift";
    private static final String MODE_NO_ALT = "noalt";

    WrappedItemStack item;
    String[] text;
    String mode = MODE_ALL;
    boolean clearExisting = false;

    private transient ItemStack stack;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        stack = item.getItemStack();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    void onToolTip(ItemTooltipEvent event)
    {
        if (isCorrectModeActive() && isCorrectItem(event.getItemStack()))
        {
            List<String> toolTip = event.getToolTip();

            modifyToolTip(toolTip);
        }
    }

    void modifyToolTip(List<String> toolTip)
    {
        if (clearExisting)
        {
            toolTip.clear();
        }

        Collections.addAll(toolTip, text);
    }

    private boolean isCorrectItem(ItemStack eventStack)
    {
        boolean itemEqual = eventStack.getMetadata() == OreDictionary.WILDCARD_VALUE
                            ? stack.isItemEqualIgnoreDurability(eventStack)
                            : stack.isItemEqual(eventStack);

        boolean nbtEqual = !eventStack.hasTagCompound() || ItemStack.areItemStackTagsEqual(eventStack, stack);

        return itemEqual && nbtEqual;
    }

    private boolean isCorrectModeActive()
    {
        switch (mode.toLowerCase())
        {
            case MODE_SHIFT:
                return GuiScreen.isShiftKeyDown();
            case MODE_CTRL:
                return GuiScreen.isCtrlKeyDown();
            case MODE_ALT:
                return GuiScreen.isAltKeyDown();
            case MODE_NO_SHIFT:
                return !GuiScreen.isShiftKeyDown();
            case MODE_NO_CTRL:
                return !GuiScreen.isCtrlKeyDown();
            case MODE_NO_ALT:
                return !GuiScreen.isAltKeyDown();
            default:
                return true;
        }
    }

    @Override
    protected boolean isReady()
    {
        return item.isItemLoaded();
    }
}
