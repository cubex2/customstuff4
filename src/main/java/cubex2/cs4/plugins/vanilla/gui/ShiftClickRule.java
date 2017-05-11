package cubex2.cs4.plugins.vanilla.gui;

import net.minecraft.item.ItemStack;

public class ShiftClickRule
{
    int[] from = new int[] {-1, -1};
    int[] to = new int[] {-1, -1};
    ItemFilter filter = ItemFilter.EVERYTHING;

    boolean canApply(int index, ItemStack stack)
    {
        int start = from[0];
        int end = from[1];
        if (start > end)
        {
            int tmp = end;
            end = start;
            start = tmp;
        }

        return index >= start && index <= end && filter.accepts(stack);
    }

    boolean reverseDirection()
    {
        return to[0] > to[1];
    }

    int getToStart()
    {
        return reverseDirection() ? to[1] : to[0];
    }

    int getToEnd()
    {
        return reverseDirection() ? to[0] : to[1];
    }
}
