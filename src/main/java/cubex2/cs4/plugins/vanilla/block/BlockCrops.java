package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.BlockDrop;
import cubex2.cs4.plugins.vanilla.ContentBlockCrops;
import cubex2.cs4.util.BlockHelper;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.Optional;
import java.util.Random;

public abstract class BlockCrops extends net.minecraft.block.BlockCrops implements CSBlock<ContentBlockCrops>
{
    private final ContentBlockCrops content;
    private final AxisAlignedBB[] aabbs;
    private PropertyInteger ageProperty;

    public BlockCrops(Material material, ContentBlockCrops content)
    {
        this.content = content;

        aabbs = createCropsAABB();
    }

    private AxisAlignedBB[] createCropsAABB()
    {
        AxisAlignedBB[] bounds = new AxisAlignedBB[content.maxAge + 1];

        if (content.heights.length == 0)
        {
            for (int i = 0; i < bounds.length; i++)
            {
                bounds[i] = new AxisAlignedBB(0, 0, 0, 1, (i + 1) / (double) bounds.length, 1);
            }
        } else
        {
            for (int i = 0; i < bounds.length; i++)
            {
                bounds[i] = new AxisAlignedBB(0, 0, 0, 1, content.heights[i], 1);
            }

        }
        return bounds;
    }

    @Override
    public int getMaxAge()
    {
        return content.maxAge;
    }

    @Override
    protected PropertyInteger getAgeProperty()
    {
        return ageProperty;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return aabbs[state.getValue(getAgeProperty())];
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Optional<BlockDrop[]> blockDrops = getContent().drop.get(getSubtype(state));
        if (blockDrops.isPresent())
        {
            drops.addAll(ItemHelper.getDroppedStacks(blockDrops.get(), fortune));
        }

        int age = getAge(state);
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        if (age >= getMaxAge())
        {
            for (BlockDrop seed : content.seeds)
            {
                ItemStack stack = seed.getItem().getItemStack();
                if (!stack.isEmpty())
                {
                    for (int i = 0; i < 3 + fortune; ++i)
                    {
                        if (rand.nextInt(2 * getMaxAge()) <= age)
                        {
                            ItemStack drop = stack.copy();
                            drop.setCount(seed.getAmount(fortune));
                            drops.add(drop);
                        }
                    }
                }
            }

            drops.addAll(ItemHelper.getDroppedStacks(content.crops, fortune));
        } else
        {
            drops.addAll(ItemHelper.getDroppedStacks(content.seeds, fortune));
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        checkAndDropBlock(worldIn, pos, state);

        if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            int i = this.getAge(state);

            if (i < this.getMaxAge())
            {
                float f = getGrowthChance(this, worldIn, pos);

                int growthChance = (int) ((25.0F / f + 1) / content.growthFactor);
                boolean shouldGrow = rand.nextInt(Math.max(growthChance, 1)) == 0;
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, shouldGrow))
                {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        if (content.seeds.length > 0)
            return content.seeds[0].getItem().getItemStack();
        else
            return ItemStack.EMPTY;
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockCrops getContent()
    {
        return content;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        if (ageProperty == null)
            ageProperty = BlockHelper.getCropAgeProperty(ContentBlockCrops.activeMaxAge);

        return new BlockStateContainer(this, ageProperty);
    }
}
