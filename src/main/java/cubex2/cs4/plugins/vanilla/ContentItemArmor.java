package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.item.ItemArmor;
import net.minecraft.util.ResourceLocation;

public abstract class ContentItemArmor extends ContentItemNoSubtypes<ItemArmor>
{
    public net.minecraft.item.ItemArmor.ArmorMaterial material = net.minecraft.item.ItemArmor.ArmorMaterial.LEATHER;
    public ResourceLocation armorTexture = null;

    public ContentItemArmor()
    {
        creativeTab = "combat";
    }
}
