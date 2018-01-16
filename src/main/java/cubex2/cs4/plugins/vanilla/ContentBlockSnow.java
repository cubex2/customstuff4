package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import cubex2.cs4.plugins.vanilla.block.ItemSnow;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;

import java.util.Optional;

public class ContentBlockSnow extends ContentBlockBaseNoSubtypes
{
    public WrappedItemStack snowball = null;
    public int maxLight = 11;

    public ContentBlockSnow()
    {
        opacity = Attribute.constant(0);
        isOpaqueCube = Attribute.constant(false);
        isFullCube = Attribute.constant(false);
        soundType = Attribute.constant(SoundType.SNOW);
        harvestTool = Attribute.constant("shovel");
        harvestLevel = Attribute.constant(0);
    }

    @Override
    public Block createBlock()
    {
        return BlockFactory.createSnow(this);
    }

    @Override
    protected Optional<Item> createItem()
    {
        return Optional.of(new ItemSnow(block, this));
    }
}
