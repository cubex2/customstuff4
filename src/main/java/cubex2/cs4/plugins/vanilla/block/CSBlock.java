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

    default IProperty[] getProperties()
    {
        return new IProperty[0];
    }
}
