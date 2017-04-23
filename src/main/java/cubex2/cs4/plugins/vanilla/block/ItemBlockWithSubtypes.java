package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockBaseWithSubtypes;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemBlockWithSubtypes extends net.minecraft.item.ItemBlock
{
    private final ContentBlockBaseWithSubtypes content;
    private CreativeTabs[] tabs;

    public ItemBlockWithSubtypes(Block block, ContentBlockBaseWithSubtypes content)
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

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return content.maxStack.get(stack.getMetadata()).orElse(64);
    }
}
