package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToolTipTests
{
    @Test
    public void testModifyToolTip()
    {
        ToolTip toolTip = createToolTip();

        List<String> lines = Lists.newArrayList("C");
        toolTip.modifyToolTip(lines);

        assertEquals(3, lines.size());
        assertEquals(lines, Lists.newArrayList("C", "A", "B"));
    }

    @Test
    public void testModifyToolTip_clearExisting()
    {
        ToolTip toolTip = createToolTip();
        toolTip.clearExisting = true;

        List<String> lines = Lists.newArrayList("C");
        toolTip.modifyToolTip(lines);

        assertEquals(2, lines.size());
        assertEquals(lines, Lists.newArrayList("A", "B"));
    }

    private ToolTip createToolTip()
    {
        ToolTip toolTip = new ToolTip();

        WrappedItemStackImpl stack = new WrappedItemStackImpl();
        stack.item = new ResourceLocation("minecraft:coal");
        toolTip.item = stack;

        toolTip.text = new String[] {"A", "B"};

        return toolTip;
    }
}
