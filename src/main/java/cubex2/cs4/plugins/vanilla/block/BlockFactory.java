package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.mixin.Mixin;
import cubex2.cs4.plugins.vanilla.ContentBlockBase;
import cubex2.cs4.plugins.vanilla.ContentBlockFence;
import cubex2.cs4.plugins.vanilla.ContentBlockOrientable;
import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;


public class BlockFactory
{
    private static Class<? extends Block> simpleClass;
    private static Class<? extends Block> simpleSubtypeClass;
    private static Class<? extends Block> directionalClass;
    private static Class<? extends Block> directionalSubtypeClass;
    private static Class<? extends Block> verticalClass;
    private static Class<? extends Block> verticalSubtypeClass;
    private static Class<? extends Block> horizontalClass;
    private static Class<? extends Block> horizontalSubtypeClass;
    private static Class<? extends Block> fenceClass;
    private static Class<? extends Block> fenceSubtypeClass;

    public static Block createSimple(ContentBlockSimple content)
    {
        return newInstance(simpleClass, content);
    }

    public static Block createSimpleSubtype(ContentBlockSimple content)
    {
        return newInstance(simpleSubtypeClass, content);
    }

    public static Block createDirectional(ContentBlockOrientable content)
    {
        return newInstance(directionalClass, content);
    }

    public static Block createDirectionalSubtype(ContentBlockOrientable content)
    {
        return newInstance(directionalSubtypeClass, content);
    }

    public static Block createVertical(ContentBlockOrientable content)
    {
        return newInstance(verticalClass, content);
    }

    public static Block createVerticalSubtype(ContentBlockOrientable content)
    {
        return newInstance(verticalSubtypeClass, content);
    }

    public static Block createHorizontal(ContentBlockOrientable content)
    {
        return newInstance(horizontalClass, content);
    }

    public static Block createHorizontalSubtype(ContentBlockOrientable content)
    {
        return newInstance(horizontalSubtypeClass, content);
    }

    public static Block createFence(ContentBlockFence content)
    {
        return newInstance(fenceClass, content);
    }

    public static Block createFenceSubtype(ContentBlockFence content)
    {
        return newInstance(fenceSubtypeClass, content);
    }

    private static <T extends ContentBlockBase> Block newInstance(Class<? extends Block> blockClass, T content)
    {
        try
        {
            return blockClass.getConstructor(Material.class, content.getClass()).newInstance(content.material, content);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    static
    {
        simpleClass = createClass(BlockSimple.class, BlockMixin.class);
        simpleSubtypeClass = createClass(BlockSimpleWithSubtypes.class, BlockMixin.class);
        directionalClass = createClass(BlockOrientableDirectional.class, BlockMixin.class);
        directionalSubtypeClass = createClass(BlockOrientableDirectionalWithSubtypes.class, BlockMixin.class);
        verticalClass = createClass(BlockOrientableVertical.class, BlockMixin.class);
        verticalSubtypeClass = createClass(BlockOrientableVerticalWithSubtypes.class, BlockMixin.class);
        horizontalClass = createClass(BlockOrientableHorizontal.class, BlockMixin.class);
        horizontalSubtypeClass = createClass(BlockOrientableHorizontalWithSubtypes.class, BlockMixin.class);
        fenceClass = createClass(BlockFence.class, BlockMixin.class);
        fenceSubtypeClass = createClass(BlockFenceWithSubtypes.class, BlockMixin.class);
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Block> createClass(Class<?> baseClass, Class<?>... mixins)
    {
        return (Class<? extends Block>) Mixin.create(baseClass.getName().replace('.', '/') + "_created", baseClass, mixins);
    }
}
