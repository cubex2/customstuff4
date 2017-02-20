package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemSimple;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class ItemSimple extends Item
{
    private final ContentItemSimple content;
    private CreativeTabs[] tabs;

    public ItemSimple(ContentItemSimple content)
    {
        this.content = content;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (hasSubtypes)
        {
            return super.getUnlocalizedName(stack) + "." + stack.getMetadata();
        } else
        {
            return super.getUnlocalizedName(stack);
        }
    }

    @Override
    public CreativeTabs[] getCreativeTabs()
    {
        if (tabs == null)
        {
            tabs = createTabs();
        }

        return tabs;
    }

    private CreativeTabs[] createTabs()
    {
        return Arrays.stream(content.subtypes).mapToObj(content.creativeTab::get)
                     .filter(Objects::nonNull)
                     .map(ItemHelper::findCreativeTab)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .toArray(CreativeTabs[]::new);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs creativeTab, NonNullList<ItemStack> subItems)
    {
        if (itemIn.getHasSubtypes())
        {
            for (int meta : content.subtypes)
            {
                String tabLabel = content.creativeTab.get(meta);
                if (tabLabel != null && tabLabel.equals(creativeTab.getTabLabel()))
                {
                    subItems.add(new ItemStack(itemIn, 1, meta));
                }
            }
        } else
        {
            super.getSubItems(itemIn, creativeTab, subItems);
        }
    }
}
