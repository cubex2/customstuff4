package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.plugins.vanilla.*;
import cubex2.cs4.util.IntRange;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class BlockMixin extends Block implements CSBlock<ContentBlockBase>
{
    public BlockMixin(Material materialIn)
    {
        super(materialIn);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getSubtype(state);
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return getContent().hardness.get(getSubtype(blockState)).orElse(1f);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
    {
        IBlockState blockState = world.getBlockState(pos);
        return getContent().resistance.get(getSubtype(blockState)).orElse(0f) / 5f;
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
    {
        return getContent().soundType.get(getSubtype(state)).orElse(SoundType.STONE);
    }

    @Override
    public int getLightOpacity(IBlockState state)
    {
        return getContent().opacity.get(getSubtype(state)).orElse(255);
    }

    @Override
    public int getLightValue(IBlockState state)
    {
        return getContent().light.get(getSubtype(state)).orElse(0);
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        IBlockState state = world.getBlockState(pos);
        return getContent().flammability.get(getSubtype(state)).orElse(0) * 3; // 300 is 100%
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        IBlockState state = world.getBlockState(pos);
        return getContent().fireSpreadSpeed.get(getSubtype(state)).orElse(0);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        IBlockState state = world.getBlockState(pos);
        return getContent().isFireSource.get(getSubtype(state)).orElse(false);
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return getContent().isWood.get(getSubtype(state)).orElse(false);
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return getContent().canSustainLeaves.get(getSubtype(state)).orElse(false);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon)
    {
        IBlockState state = world.getBlockState(pos);
        return getContent().isBeaconBase.get(getSubtype(state)).orElse(false);
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return getContent().enchantPowerBonus.get(getSubtype(state)).orElse(0f);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        return getContent().expDrop.get(getSubtype(state)).orElse(IntRange.ZERO).getRandomValue();
    }

    @Override
    public MapColor getMapColor(IBlockState state)
    {
        return getContent().mapColor.get(getSubtype(state)).orElse(getMaterial(state).getMaterialMapColor());
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        String[] lines = getContent().information.get(stack.getMetadata()).orElse(new String[0]);
        tooltip.addAll(Arrays.asList(lines));
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        list.addAll(ItemHelper.createSubItems(itemIn, tab, getContent().creativeTab, getSubtypes()));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return getContent().tileEntity.hasEntry(getSubtype(state));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        if (hasTileEntity(state))
        {
            Optional<ResourceLocation> optional = getContent().tileEntity.get(getSubtype(state));
            if (optional.isPresent())
            {
                return TileEntityRegistry.createTileEntity(optional.get());
            } else
            {
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        Optional<ContentGuiBase> gui = getGui(state);

        if (worldIn.isRemote)
        {
            return gui.isPresent();
        } else
        {
            if (gui.isPresent())
            {
                playerIn.openGui(CustomStuff4.INSTANCE, gui.get().getGuiId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
            }

            return gui.isPresent();
        }
    }

    private Optional<ContentGuiBase> getGui(IBlockState state)
    {
        Optional<ResourceLocation> location = getContent().gui.get(getSubtype(state));
        if (location.isPresent())
        {
            return Optional.ofNullable(GuiRegistry.get(location.get()));
        }
        return Optional.empty();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ContentBlockBaseWithSubtypes.insertSubtype(getProperties()));
    }
}
