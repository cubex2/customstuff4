package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedPotionEffect;
import cubex2.cs4.plugins.vanilla.item.ItemFactory;
import net.minecraft.item.Item;

public class ContentItemFood extends ContentItemWithSubtypes<Item>
{
    public MetadataAttribute<Integer> healAmount = MetadataAttribute.constant(2);
    public MetadataAttribute<Float> saturation = MetadataAttribute.constant(0.6F);
    public MetadataAttribute<Boolean> alwaysEdible = MetadataAttribute.constant(false);
    public MetadataAttribute<WrappedPotionEffect> potionEffect = MetadataAttribute.constant(WrappedPotionEffect.of(null));
    public MetadataAttribute<Float> potionEffectProbability = MetadataAttribute.constant(1f);

    public boolean isWolfFood = false;

    @Override
    protected Item createItem()
    {
        return ItemFactory.createFood(this);
    }
}
