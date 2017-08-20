package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemWithSubtypes;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public abstract class ItemWithSubtypesMixin extends Item implements ItemWithSubtypes
{
    private CreativeTabs[] tabs;

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
    public int getItemStackLimit(ItemStack stack)
    {
        return getContent().maxStack.get(stack.getMetadata()).orElse(64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        String[] lines = getContent().information.get(stack.getMetadata()).orElse(new String[0]);
        tooltip.addAll(Arrays.asList(lines));
    }

    @Override
    public CreativeTabs[] getCreativeTabs()
    {
        if (tabs == null)
        {
            tabs = ItemHelper.createCreativeTabs(getContent().creativeTab, getContent().subtypes);
        }

        return tabs;
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        CreativeTabs[] tabs = getCreativeTabs();
        return tabs.length == 0 ? null : tabs[0];
    }

    @Override
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> subItems)
    {
        if (isInCreativeTab(creativeTab))
        {
            subItems.addAll(ItemHelper.createSubItems(this, creativeTab, getContent().creativeTab, getContent().subtypes));
        }
    }

    @Override
    public ContentItemWithSubtypes<?> getContent()
    {
        return null;
    }
}
