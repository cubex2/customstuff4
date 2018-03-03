package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemArmor;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemArmor extends net.minecraft.item.ItemArmor
{
    private final ContentItemArmor content;

    public ItemArmor(ContentItemArmor content, EntityEquipmentSlot equipmentSlotIn)
    {
        super(content.material.getArmorMaterial(), 0, equipmentSlotIn);
        this.content = content;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        if (content.armorTexture != null)
            return content.armorTexture.toString();

        return null;
    }
}
