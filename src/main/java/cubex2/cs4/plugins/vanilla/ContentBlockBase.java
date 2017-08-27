package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.*;
import cubex2.cs4.compat.waila.WailaData;
import cubex2.cs4.plugins.vanilla.block.BlockMixin;
import cubex2.cs4.util.IntRange;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nullable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

public abstract class ContentBlockBase implements Content
{
    public String id;
    public Material material = Material.GROUND;

    public Attribute<Float> slipperiness = Attribute.constant(0.6f);
    public Attribute<String> creativeTab = Attribute.constant("anonexistingtabtoreturnnull");
    public Attribute<Float> hardness = Attribute.constant(1f);
    public Attribute<Float> resistance = Attribute.constant(0f);
    public Attribute<SoundType> soundType = Attribute.constant(SoundType.STONE);
    public Attribute<Integer> maxStack = Attribute.constant(64);
    public Attribute<Integer> opacity = Attribute.constant(255);
    public Attribute<Integer> light = Attribute.constant(0);
    public Attribute<Integer> flammability = Attribute.constant(0);
    public Attribute<Integer> fireSpreadSpeed = Attribute.constant(0);
    public Attribute<Boolean> isFireSource = Attribute.constant(false);
    public Attribute<Boolean> isWood = Attribute.constant(false);
    public Attribute<Boolean> canSustainLeaves = Attribute.constant(false);
    public Attribute<Boolean> isBeaconBase = Attribute.constant(false);
    public Attribute<Float> enchantPowerBonus = Attribute.constant(0f);
    public Attribute<IntRange> expDrop = Attribute.constant(IntRange.create(0, 0));
    public Attribute<String[]> information = Attribute.constant(new String[0]);
    public Attribute<MapColor> mapColor = Attribute.constant(null);
    public Attribute<ResourceLocation> tileEntity = Attribute.constant(null);
    public Attribute<ResourceLocation> gui = Attribute.constant(null);
    public Attribute<BlockDrop[]> drop = Attribute.constant(null);
    public Attribute<Boolean> isFullCube = Attribute.constant(true);
    public Attribute<Boolean> isOpaqueCube = Attribute.constant(true);
    public Attribute<Boolean> canInteractWithFluidItem = Attribute.constant(true);
    public Attribute<Boolean> isBurning = Attribute.constant(false);
    public Attribute<AxisAlignedBB> bounds = Attribute.constant(BlockMixin.DEFAULT_AABB_MARKER);
    public Attribute<AxisAlignedBB> selectionBounds = Attribute.constant(BlockMixin.DEFAULT_AABB_MARKER);
    public Attribute<AxisAlignedBB> collisionBounds = Attribute.constant(BlockMixin.DEFAULT_AABB_MARKER);
    public Attribute<BlockTint> tint = null;
    public Attribute<Color> itemTint = null;
    public BlockRenderLayer renderLayer = null;
    public Attribute<Boolean> canSilkHarvest = Attribute.constant(true);
    public Attribute<String> harvestTool = Attribute.constant(null);
    public Attribute<Integer> harvestLevel = Attribute.constant(-1);
    public boolean canPlaceOnFloor = true;
    public boolean canPlaceOnCeiling = true;
    public boolean canPlaceOnSides = true;
    public Attribute<EnumPlantType[]> sustainedPlants = Attribute.constant(null);
    public Attribute<Integer> burnTime = Attribute.constant(-1);

    Attribute<ResourceLocation> itemModel = Attribute.constant(null);

    protected transient Block block;
    @Nullable
    protected transient Item item;

    @Override
    public final void init(InitPhase phase, ContentHelper helper)
    {
        if (phase == InitPhase.PRE_INIT)
        {
            checkState(isReady());

            block = createBlock();
            block.setUnlocalizedName(Loader.instance().activeModContainer().getModId() + "." + id);
            block.setRegistryName(id);

            initBlock();

            createItem().ifPresent(this::initItem);

            WailaData.registerStackProviderBlock(block.getClass());
        } else if (phase == InitPhase.REGISTER_MODELS)
        {
            registerModels();
        } else if (phase == InitPhase.REGISTER_BLOCKS)
        {
            checkState(isReady());

            ForgeRegistry<Block> registry = RegistryManager.ACTIVE.getRegistry(GameData.BLOCKS);
            registry.register(block);
        } else if (phase == InitPhase.REGISTER_ITEMS)
        {
            if (item != null)
            {
                ForgeRegistry<Item> registry = RegistryManager.ACTIVE.getRegistry(GameData.ITEMS);
                registry.register(item);
            }
        } else if (phase == InitPhase.INIT)
        {
            if (tint != null)
            {
                CustomStuff4.proxy.setBlockBiomeTint(block, subtype -> tint.get(subtype).orElse(BlockTint.WHITE));
            }

            if (item != null && itemTint != null)
            {
                CustomStuff4.proxy.setItemTint(item, subtype -> itemTint.get(subtype).orElse(new ColorImpl(0xffffffff)).getRGB());
            }
        }
    }

    protected void initItem(Item item)
    {
        this.item = item;

        item.setUnlocalizedName(Loader.instance().activeModContainer().getModId() + "." + id);
        item.setRegistryName(id);
    }

    protected void initBlock()
    {

    }

    protected void registerModels()
    {

    }

    public abstract Block createBlock();

    protected Optional<Item> createItem()
    {
        return Optional.empty();
    }

    protected boolean isReady()
    {
        return true;
    }
}
