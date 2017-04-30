package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedToolMaterial;
import net.minecraft.item.Item;

import java.util.Arrays;

class WrappedToolMaterialImpl implements WrappedToolMaterial
{
    String material;

    WrappedToolMaterialImpl(String material)
    {
        this.material = material;
    }

    @Override
    public Item.ToolMaterial getToolMaterial()
    {
        return Arrays.stream(Item.ToolMaterial.values())
                     .filter(mat -> mat.name().equalsIgnoreCase(material))
                     .findFirst().orElse(null);

    }
}
