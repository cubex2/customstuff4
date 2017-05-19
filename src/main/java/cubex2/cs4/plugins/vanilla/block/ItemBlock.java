package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockBase;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemBlock extends net.minecraft.item.ItemBlock
{
    private final ContentBlockBase content;
    private CreativeTabs[] tabs;

    public ItemBlock(Block block, ContentBlockBase content)
    {
        super(block);

        this.content = content;
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
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
            tabs = ItemHelper.createCreativeTabs(content.creativeTab, new int[] {0});
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
    public int getItemStackLimit(ItemStack stack)
    {
        return content.maxStack.get(0).orElse(64);
    }
}
