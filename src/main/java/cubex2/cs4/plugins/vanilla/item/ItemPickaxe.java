package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemPickaxe;

public class ItemPickaxe extends net.minecraft.item.ItemPickaxe implements ItemTool
{
    public ItemPickaxe(ToolMaterial material, ContentItemPickaxe content)
    {
        super(material);
    }

    @Override
    public void setDamage(float damage)
    {
        damageVsEntity = damage;
    }

    @Override
    public void setAttackSpeed(float attackSpeed)
    {
        this.attackSpeed = attackSpeed;
    }
}
