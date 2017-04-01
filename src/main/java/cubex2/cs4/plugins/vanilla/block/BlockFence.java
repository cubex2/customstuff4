package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockFence extends net.minecraft.block.BlockFence implements CSBlock<ContentBlockFence>
{
    private final ContentBlockFence content;

    BlockFence(Material material, ContentBlockFence content)
    {
        super(material, MapColor.AIR); // that map color is being overridden

        this.content = content;
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public ContentBlockFence getContent()
    {
        return content;
    }

    @Override
    public int[] getSubtypes()
    {
        return content.subtypes;
    }
}
