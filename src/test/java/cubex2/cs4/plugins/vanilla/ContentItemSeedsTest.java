package cubex2.cs4.plugins.vanilla;

import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class ContentItemSeedsTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createItem_noSubtypes()
    {
        ContentItemSeeds content = new ContentItemSeeds();
        content.plant = new WrappedBlockStateImpl(new ResourceLocation("mincraft:dirt"), Collections.emptyList());

        content.createItem();
    }
}