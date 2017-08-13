package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class ContentBlockFluid extends ContentBlockBaseNoSubtypes
{
    public int density = 1000;
    public int temperature = 300;
    public int viscosity = 1000;
    public int flowLength = 8;
    public boolean isGaseous = false;
    public boolean canCreateSource = false;
    public boolean addUniversalBucket = true;
    public ResourceLocation texStill;
    public ResourceLocation texFlowing;

    public ContentBlockFluid()
    {
        material = Material.WATER;
        isFullCube = Attribute.constant(false);
        isOpaqueCube = Attribute.constant(false);
        opacity = Attribute.constant(3);
    }

    @Override
    public Block createBlock()
    {
        return BlockFactory.createFluid(this);
    }
}
