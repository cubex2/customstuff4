package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedArmorMaterial;
import cubex2.cs4.plugins.vanilla.item.ItemArmor;
import net.minecraft.util.ResourceLocation;

public abstract class ContentItemArmor extends ContentItemNoSubtypes<ItemArmor>
{
    public WrappedArmorMaterial material = WrappedArmorMaterial.of(net.minecraft.item.ItemArmor.ArmorMaterial.LEATHER);
    public ResourceLocation armorTexture = null;

    public ContentItemArmor()
    {
        creativeTab = "combat";
    }
}
