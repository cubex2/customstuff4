package cubex2.cs4.util;

import net.minecraft.creativetab.CreativeTabs;

import java.util.Arrays;
import java.util.Optional;

public class ItemHelper
{
    public static Optional<CreativeTabs> findCreativeTab(String tabLabel)
    {
        return Arrays.stream(CreativeTabs.CREATIVE_TAB_ARRAY)
                     .filter(tab -> tab != null && tab.getTabLabel().equals(tabLabel))
                     .findFirst();
    }
}
