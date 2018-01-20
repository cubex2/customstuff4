package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedBlockState;
import cubex2.cs4.plugins.vanilla.item.ItemSeeds;

import static com.google.common.base.Preconditions.checkState;

public class ContentItemSeeds extends ContentItemNoSubtypes<ItemSeeds>
{
    public WrappedBlockState plant = null;

    public ContentItemSeeds()
    {
        creativeTab = "materials";
    }

    @Override
    protected ItemSeeds createItem()
    {
        checkState(plant != null, "No value for plant!");

        return new ItemSeeds(this);
    }
}
