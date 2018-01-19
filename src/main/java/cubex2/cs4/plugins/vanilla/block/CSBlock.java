package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

public interface CSBlock<T extends ContentBlockBase>
{
    int getSubtype(IBlockState state);

    T getContent();

    default int[] getSubtypes()
    {
        return new int[] {0};
    }

    /**
     * Gets all properties that this block is using except for the subtype property. If using with BlockMixin, this must
     * also not include the properties of the superclass.
     */
    default IProperty<?>[] getProperties()
    {
        return new IProperty[0];
    }
}
