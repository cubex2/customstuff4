package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

public class ContentBlockCrops extends ContentBlockBaseNoSubtypes
{
    public int maxAge = 7;
    public float growthFactor = 1f;
    public float[] heights = new float[0];
    public BlockDrop[] seeds = new BlockDrop[0];
    public BlockDrop[] crops = new BlockDrop[0];

    /** Used to make maxAge available during block state creation */
    public static int activeMaxAge = -1;

    public ContentBlockCrops()
    {
        opacity = Attribute.constant(0);
        isFullCube = Attribute.constant(false);
        isOpaqueCube = Attribute.constant(false);
        hardness = Attribute.constant(0f);
        soundType = Attribute.constant(SoundType.PLANT);
    }

    @Override
    public Block createBlock()
    {
        activeMaxAge = maxAge;
        return BlockFactory.createCrops(this);
    }
}
