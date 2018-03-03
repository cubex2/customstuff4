package cubex2.cs4.api;

import net.minecraft.item.ItemArmor;

import javax.annotation.Nullable;

/**
 * Contains the data to get a ArmorMaterial. Necessary as deserialization happens before custom materials are added.
 */
public interface WrappedArmorMaterial
{
    /**
     * Gets the material. Returns null if the material does not exist.
     */
    @Nullable
    ItemArmor.ArmorMaterial getArmorMaterial();

    static WrappedArmorMaterial of(ItemArmor.ArmorMaterial material)
    {
        return () -> material;
    }
}
