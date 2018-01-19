package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockFluid extends BlockFluidClassic implements CSBlock<ContentBlockFluid>
{
    private final ContentBlockFluid content;

    public BlockFluid(Material material, ContentBlockFluid content)
    {
        super(createFluid(content), material);
        this.content = content;
        setQuantaPerBlock(content.flowLength);

        if (content.addUniversalBucket)
            FluidRegistry.addBucketForFluid(getFluid());
    }

    static Fluid createFluid(ContentBlockFluid content)
    {
        Fluid fluid = new Fluid(content.id, content.texStill, content.texFlowing);
        fluid.setDensity(content.density);
        fluid.setTemperature(content.temperature);
        fluid.setGaseous(content.isGaseous);
        fluid.setViscosity(content.viscosity);
        fluid.setLuminosity(content.light.get(0).orElse(0));

        FluidRegistry.registerFluid(fluid);
        return fluid;
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand)
    {
        super.updateTick(world, pos, state, rand);
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos)
    {
        return super.getLightValue(state, world, pos);
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockFluid getContent()
    {
        return content;
    }

    @Override
    public Fluid getFluid()
    {
        return definedFluid;
    }
}
