package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.TestContentHelper;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class ContentBlockStairsTest
{
    private final ContentHelper helper = new TestContentHelper();

    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_createBlock_noSubtypes()
    {
        ContentBlockStairs stairs = new ContentBlockStairs();
        stairs.modelState = Blocks.DIRT::getDefaultState;
        stairs.createBlock();
    }

    @Test
    public void canUseModelFromBlockOfSameMod()
    {
        ContentBlockSimple modelContent = new ContentBlockSimple();
        modelContent.id = "stairs_same_mod_model_1";
        ContentBlockStairs stairsContent = new ContentBlockStairs();
        stairsContent.id = "some_stairs_1";
        stairsContent.modelState = new WrappedBlockStateImpl(new ResourceLocation("minecraft", modelContent.id), Collections.emptyList());

        modelContent.init(InitPhase.PRE_INIT, helper);
        stairsContent.init(InitPhase.PRE_INIT, helper);

        modelContent.init(InitPhase.REGISTER_BLOCKS, helper);
        stairsContent.init(InitPhase.REGISTER_BLOCKS, helper);
    }

    @Test
    public void canUseModelFromBlockOfSameModThatIsLoadedAfterStairs()
    {
        ContentBlockSimple modelContent = new ContentBlockSimple();
        modelContent.id = "stairs_same_mod_model_2";
        ContentBlockStairs stairsContent = new ContentBlockStairs();
        stairsContent.id = "some_stairs_2";
        stairsContent.modelState = new WrappedBlockStateImpl(new ResourceLocation("minecraft", modelContent.id), Collections.emptyList());

        stairsContent.init(InitPhase.PRE_INIT, helper);
        modelContent.init(InitPhase.PRE_INIT, helper);

        stairsContent.init(InitPhase.REGISTER_BLOCKS, helper);
        modelContent.init(InitPhase.REGISTER_BLOCKS, helper);
    }
}