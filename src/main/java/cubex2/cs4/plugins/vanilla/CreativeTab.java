package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab extends SimpleContent
{
    String id;
    WrappedItemStack icon;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        new CreativeTabs(id)
        {
            @Override
            public Item getTabIconItem()
            {
                return icon.getItemStack().getItem();
            }

            @Override
            public ItemStack getIconItemStack()
            {
                return icon.getItemStack().copy();
            }
        };
    }

    @Override
    protected boolean isReady()
    {
        return true;
    }
}
