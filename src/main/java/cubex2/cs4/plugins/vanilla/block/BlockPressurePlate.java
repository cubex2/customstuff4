package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockPressurePlate;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockPressurePlate extends BlockBasePressurePlate implements CSBlock<ContentBlockPressurePlate>
{
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    private final ContentBlockPressurePlate content;

    public BlockPressurePlate(Material material, ContentBlockPressurePlate content)
    {
        super(material);
        setDefaultState(blockState.getBaseState().withProperty(POWERED, false));
        this.content = content;
    }

    @Override
    public int tickRate(World worldIn)
    {
        return content.pressedTicks;
    }

    @Override
    protected void playClickOnSound(World worldIn, BlockPos pos)
    {
        worldIn.playSound(null, pos, content.onSound, SoundCategory.BLOCKS, 0.3F, 0.8F);
    }

    @Override
    protected void playClickOffSound(World worldIn, BlockPos pos)
    {
        worldIn.playSound(null, pos, content.offSound, SoundCategory.BLOCKS, 0.3F, 0.7F);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected int computeRedstoneStrength(World worldIn, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
        List<? extends Entity> list;

        Class<? extends Entity> selectedEntityClass = content.selector.getEntityClass();
        if (selectedEntityClass == null)
        {
            list = worldIn.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
        } else
        {
            list = worldIn.getEntitiesWithinAABB(selectedEntityClass, axisalignedbb);
        }

        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                if (!entity.doesEntityNotTriggerPressurePlate() && content.selector.isValidEntity(entity))
                {
                    return 15;
                }
            }
        }

        return 0;
    }

    @Override
    protected int getRedstoneStrength(IBlockState state)
    {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected IBlockState setRedstoneStrength(IBlockState state, int strength)
    {
        return state.withProperty(POWERED, strength > 0);
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockPressurePlate getContent()
    {
        return content;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(POWERED, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(POWERED) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, POWERED);
    }
}
