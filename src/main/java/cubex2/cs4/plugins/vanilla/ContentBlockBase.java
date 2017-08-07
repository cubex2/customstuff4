package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.*;
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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Optional;

public abstract class ContentBlockBase implements Content
{
    public String id;
    public Material material = Material.GROUND;
    float slipperiness = 0.6f;

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
    public Attribute<BlockDrop> drop = Attribute.constant(null);
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

    Attribute<ResourceLocation> itemModel = Attribute.constant(null);

    protected transient Block block;
    private transient Item item;

    @Override
    public final void init(InitPhase phase, ContentHelper helper)
    {
        if (phase == InitPhase.INIT && block != null)
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

        if (phase == InitPhase.PRE_INIT && !isReady())
            return;
        if (phase == InitPhase.INIT && (block != null || !isReady()))
            return;
        if (phase == InitPhase.POST_INIT && (block != null || !isReady()))
            return;

        block = createBlock();
        block.setUnlocalizedName(Loader.instance().activeModContainer().getModId() + "." + id);
        block.setRegistryName(id);
        block.slipperiness = slipperiness;

        initBlock();

        GameRegistry.register(block);

        createItem().ifPresent(this::initItem);
    }

    protected void initItem(Item item)
    {
        this.item = item;

        item.setUnlocalizedName(Loader.instance().activeModContainer().getModId() + "." + id);
        item.setRegistryName(id);

        GameRegistry.register(item);
    }

    protected void initBlock()
    {

    }

    protected abstract Block createBlock();

    protected Optional<Item> createItem()
    {
        return Optional.empty();
    }

    protected boolean isReady()
    {
        return true;
    }
}
