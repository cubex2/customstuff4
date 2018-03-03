package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

import static com.google.common.base.Preconditions.checkState;

class ArmorMaterial extends SimpleContent
{
    String id;
    String textureName = "leather";
    int durability = 5;
    int enchantability = 15;
    int armorHelmet = 1;
    int armorChest = 3;
    int armorLegs = 2;
    int armorBoots = 1;
    SoundEvent equipSound = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    float toughness = 0f;
    WrappedItemStack repairItem;

    private transient ItemArmor.ArmorMaterial material;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        checkState(id != null && id.matches("[_a-zA-Z][_a-zA-Z0-9]*"), "Invalid id: " + id);

        int[] damageReduction = new int[] {
                armorBoots,
                armorLegs,
                armorChest,
                armorHelmet
        };

        material = EnumHelper.addArmorMaterial(id, textureName, durability, damageReduction, enchantability, equipSound, toughness);
        if (repairItem != null)
        {
            material.setRepairItem(repairItem.getItemStack().copy());
        }
    }

    @Override
    protected boolean isReady()
    {
        return repairItem == null || repairItem.isItemLoaded();
    }
}
