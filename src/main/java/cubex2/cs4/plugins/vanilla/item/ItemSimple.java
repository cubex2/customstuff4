package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemSimple;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Arrays;
import java.util.List;

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
            tabs = ItemHelper.createCreativeTabs(content.creativeTab, content.subtypes);
        }

        return tabs;
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return content.maxStack.get(stack.getMetadata()).orElse(64);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        String[] lines = content.information.get(stack.getMetadata()).orElse(new String[0]);
        tooltip.addAll(Arrays.asList(lines));
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs creativeTab, NonNullList<ItemStack> subItems)
    {
        subItems.addAll(ItemHelper.createSubItems(itemIn, creativeTab, content.creativeTab, content.subtypes));
    }
}
