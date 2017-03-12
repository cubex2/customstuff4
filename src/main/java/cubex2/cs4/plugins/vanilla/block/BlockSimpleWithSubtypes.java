package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;

public abstract class BlockSimpleWithSubtypes extends BlockSimple
{
    private PropertyEnum<EnumSubtype> subtype;

    public BlockSimpleWithSubtypes(Material material, ContentBlockSimple content)
    {
        super(material, content);

        subtype = BlockHelper.getSubtypeProperty(content.subtypes);

        setDefaultState(blockState.getBaseState()
                                  .withProperty(subtype, EnumSubtype.SUBTYPE0));
    }
}
