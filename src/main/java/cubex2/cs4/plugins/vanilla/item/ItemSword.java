package cubex2.cs4.plugins.vanilla.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cubex2.cs4.plugins.vanilla.ContentItemSword;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemSword extends net.minecraft.item.ItemSword implements ItemTool
{
    private final ContentItemSword content;

    private final float defaultAttackDamage;
    private final double defaultAttackSpeed = -2.4D;
    private Float attackDamage = null;
    private Float attackSpeed = null;

    public ItemSword(ToolMaterial material, ContentItemSword content)
    {
        super(material);
        this.content = content;

        defaultAttackDamage = 3.0F + material.getDamageVsEntity();
    }

    @Override
    public void setDamage(float damage)
    {
        attackDamage = damage;
    }

    @Override
    public void setAttackSpeed(float attackSpeed)
    {
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.addAll(Arrays.asList(content.information));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            double damage = attackDamage != null ? attackDamage : defaultAttackDamage;
            double speed = attackSpeed != null ? attackSpeed : defaultAttackSpeed;
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speed, 0));
        }
        return multimap;
    }


}
