package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.mixin.Mixin;
import cubex2.cs4.plugins.vanilla.ContentItemBase;
import cubex2.cs4.plugins.vanilla.ContentItemFood;
import cubex2.cs4.plugins.vanilla.ContentItemSimple;
import net.minecraft.item.Item;

public class ItemFactory
{
    private static Class<? extends Item> foodClass;
    private static Class<? extends Item> simpleClass;

    public static Item createFood(ContentItemFood content)
    {
        return newInstance(foodClass, content);
    }

    public static Item createSimple(ContentItemSimple content)
    {
        return newInstance(simpleClass, content);
    }

    private static <T extends ContentItemBase<?>> Item newInstance(Class<? extends Item> itemClass, T content)
    {
        try
        {
            return itemClass.getConstructor(content.getClass()).newInstance(content);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    static
    {
        foodClass = createClass(ItemFood.class, ItemWithSubtypesMixin.class);
        simpleClass = createClass(ItemSimple.class, ItemWithSubtypesMixin.class);
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Item> createClass(Class<?> baseClass, Class<?>... mixins)
    {
        return (Class<? extends Item>) Mixin.create(baseClass.getName().replace('.', '/') + "_created", baseClass, mixins);
    }
}
