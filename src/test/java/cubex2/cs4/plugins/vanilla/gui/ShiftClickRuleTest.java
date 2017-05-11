package cubex2.cs4.plugins.vanilla.gui;

import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShiftClickRuleTest
{
    @Before
    public void setUp() throws Exception
    {
        Bootstrap.register();
    }

    @Test
    public void test_canApply() throws Exception
    {
        ShiftClickRule rule = new ShiftClickRule();
        rule.from = new int[] {0, 3};
        rule.to = new int[] {5, 10};

        assertFalse(rule.reverseDirection());
        assertEquals(5, rule.getToStart());
        assertEquals(10, rule.getToEnd());
        assertFalse(rule.canApply(-1, ItemStack.EMPTY));
        assertFalse(rule.canApply(4, ItemStack.EMPTY));
        assertFalse(rule.canApply(5, ItemStack.EMPTY));
        assertFalse(rule.canApply(10, ItemStack.EMPTY));
        assertFalse(rule.canApply(11, ItemStack.EMPTY));
        assertTrue(rule.canApply(0, ItemStack.EMPTY));
        assertTrue(rule.canApply(3, ItemStack.EMPTY));
    }

    @Test
    public void test_canApplyWithFilter() throws Exception
    {
        ShiftClickRule rule = new ShiftClickRule();
        rule.from = new int[] {0, 3};
        rule.to = new int[] {5, 10};
        rule.filter = stack -> stack.getItem() == Items.APPLE;

        assertTrue(rule.canApply(0, new ItemStack(Items.APPLE)));
        assertFalse(rule.canApply(0, new ItemStack(Items.STICK)));
    }

    @Test
    public void test_invertTo()
    {
        ShiftClickRule rule = new ShiftClickRule();
        rule.to = new int[] {10, 5};

        assertTrue(rule.reverseDirection());
        assertEquals(5, rule.getToStart());
        assertEquals(10, rule.getToEnd());
    }

}