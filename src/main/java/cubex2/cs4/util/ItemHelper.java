package cubex2.cs4.util;

import cubex2.cs4.plugins.vanilla.MetadataAttribute;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class ItemHelper
{
    public static Optional<CreativeTabs> findCreativeTab(String tabLabel)
    {
        return Arrays.stream(CreativeTabs.CREATIVE_TAB_ARRAY)
                     .filter(tab -> tab != null && tab.getTabLabel().equals(tabLabel))
                     .findFirst();
    }

    public static CreativeTabs[] createCreativeTabs(MetadataAttribute<String> tabLabels, int[] subtypes)
    {
        return Arrays.stream(subtypes).mapToObj(tabLabels::get)
                     .filter(Objects::nonNull)
                     .map(ItemHelper::findCreativeTab)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .distinct()
                     .toArray(CreativeTabs[]::new);
    }

    public static NonNullList<ItemStack> createSubItems(Item item, CreativeTabs creativeTab, MetadataAttribute<String> tabLabels, int[] subtypes)
    {
        NonNullList<ItemStack> list = NonNullList.create();

        if (item.getHasSubtypes())
        {
            for (int meta : subtypes)
            {
                String tabLabel = tabLabels.get(meta);
                if (tabLabel != null && tabLabel.equals(creativeTab.getTabLabel()))
                {
                    list.add(new ItemStack(item, 1, meta));
                }
            }
        } else
        {
            list.add(new ItemStack(item, 1, 0));
        }

        return list;
    }
}
