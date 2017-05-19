package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.api.WrappedPotionEffect;
import cubex2.cs4.plugins.vanilla.item.ItemFactory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;

public class ContentItemFood extends ContentItemWithSubtypes<Item>
{
    public Attribute<Integer> healAmount = Attribute.constant(2);
    public Attribute<Float> saturation = Attribute.constant(0.6F);
    public Attribute<Boolean> alwaysEdible = Attribute.constant(false);
    public Attribute<WrappedPotionEffect> potionEffect = Attribute.constant(WrappedPotionEffect.of(null));
    public Attribute<Float> potionEffectProbability = Attribute.constant(1f);
    public Attribute<WrappedItemStack> result = Attribute.constant(null);
    public Attribute<EnumAction> useAction = Attribute.constant(EnumAction.EAT);

    public boolean isWolfFood = false;

    @Override
    protected Item createItem()
    {
        return ItemFactory.createFood(this);
    }
}
