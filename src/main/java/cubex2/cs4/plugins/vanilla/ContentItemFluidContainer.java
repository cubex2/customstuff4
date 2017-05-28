package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.item.ItemFluidContainer;

public class ContentItemFluidContainer extends ContentItemNoSubtypes<ItemFluidContainer>
{
    public int capacity = 1000;

    @Override
    protected ItemFluidContainer createItem()
    {
        return new ItemFluidContainer(this);
    }
}
