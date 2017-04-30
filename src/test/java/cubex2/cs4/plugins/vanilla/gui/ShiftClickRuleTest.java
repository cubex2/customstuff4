package cubex2.cs4.plugins.vanilla.gui;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShiftClickRuleTest
{
    @Test
    public void test_canApply() throws Exception
    {
        ShiftClickRule rule = new ShiftClickRule();
        rule.from = new int[] {0,3};
        rule.to = new int[] {5,10};

        assertFalse(rule.reverseDirection());
        assertEquals(5, rule.getToStart());
        assertEquals(10, rule.getToEnd());
        assertFalse(rule.canApply(-1));
        assertFalse(rule.canApply(4));
        assertFalse(rule.canApply(5));
        assertFalse(rule.canApply(10));
        assertFalse(rule.canApply(11));
        assertTrue(rule.canApply(0));
        assertTrue(rule.canApply(3));
    }

    @Test
    public void test_invertTo()
    {
        ShiftClickRule rule = new ShiftClickRule();
        rule.to = new int[] {10,5};

        assertTrue(rule.reverseDirection());
        assertEquals(5, rule.getToStart());
        assertEquals(10, rule.getToEnd());
    }

}