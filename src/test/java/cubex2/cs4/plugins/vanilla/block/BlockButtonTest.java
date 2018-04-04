package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockButton;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@DisplayName("Button Block Test")
public class BlockButtonTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    //todo describe what its supposed to do
    @Test
    public void testProperties()
    {
        ContentBlockButton content = new ContentBlockButton();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        Collection<IProperty<?>> properties = block.getBlockState().getProperties();
        assertEquals(2, properties.size());
    }
}