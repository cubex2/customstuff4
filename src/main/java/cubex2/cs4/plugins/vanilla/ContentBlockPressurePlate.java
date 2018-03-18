package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.EntitySelector;
import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import cubex2.cs4.plugins.vanilla.block.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

import java.util.Optional;

public class ContentBlockPressurePlate extends ContentBlockBaseNoSubtypes
{
    public int pressedTicks = 20;
    public EntitySelector selector = EntitySelector.EVERYTHING;
    public SoundEvent onSound = SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_ON;
    public SoundEvent offSound = SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_OFF;

    public ContentBlockPressurePlate()
    {
        isOpaqueCube = Attribute.constant(false);
        isFullCube = Attribute.constant(false);
        opacity = Attribute.constant(0);
    }

    @Override
    protected Optional<Item> createItem()
    {
        return Optional.of(new ItemBlock(block, this));
    }

    @Override
    public Block createBlock()
    {
        return BlockFactory.createPressurePlate(this);
    }
}
