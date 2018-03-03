package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import cubex2.cs4.api.WrappedArmorMaterial;
import net.minecraft.item.ItemArmor;

import javax.annotation.Nullable;
import java.util.Map;

public class WrappedArmorMaterialImpl implements WrappedArmorMaterial
{
    public final String name;
    private ItemArmor.ArmorMaterial material;

    public WrappedArmorMaterialImpl(String name)
    {
        this.name = name.toLowerCase();
    }

    @Nullable
    @Override
    public ItemArmor.ArmorMaterial getArmorMaterial()
    {
        if (material != null)
            return material;

        if (vanillaMaterials.containsKey(name))
        {
            material = vanillaMaterials.get(name);
        } else
        {
            for (ItemArmor.ArmorMaterial mat : ItemArmor.ArmorMaterial.values())
            {
                if (mat.name().equalsIgnoreCase(name))
                {
                    material = mat;
                    break;
                }
            }
        }

        return material;
    }

    private static final Map<String, ItemArmor.ArmorMaterial> vanillaMaterials = Maps.newHashMap();

    static
    {
        vanillaMaterials.put("leather", ItemArmor.ArmorMaterial.LEATHER);
        vanillaMaterials.put("chain", ItemArmor.ArmorMaterial.CHAIN);
        vanillaMaterials.put("iron", ItemArmor.ArmorMaterial.IRON);
        vanillaMaterials.put("gold", ItemArmor.ArmorMaterial.GOLD);
        vanillaMaterials.put("diamond", ItemArmor.ArmorMaterial.DIAMOND);
    }
}
