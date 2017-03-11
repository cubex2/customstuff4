package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.ItemBlock;
import net.minecraft.item.Item;

import java.util.Optional;

public abstract class ContentBlockOrientable extends ContentBlockSimple
{
    public Attribute<Boolean> faceBySide = Attribute.constant(false);

    @Override
    protected Optional<Item> createItem(boolean hasSubtypes)
    {
        return Optional.of(new ItemBlock(block, this));
    }
}
