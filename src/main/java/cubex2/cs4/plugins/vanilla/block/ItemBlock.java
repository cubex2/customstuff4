package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemBlock extends net.minecraft.item.ItemBlock
{
    private final ContentBlockSimple content;
    private CreativeTabs[] tabs;

    public ItemBlock(Block block, ContentBlockSimple content)
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
            tabs = ItemHelper.createCreativeTabs(content.creativeTab, content.subtypes);
        }

        return tabs;
    }
}
