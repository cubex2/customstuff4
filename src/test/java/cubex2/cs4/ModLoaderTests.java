package cubex2.cs4;

import com.google.common.collect.Lists;
import cubex2.cs4.api.BlankContent;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.data.ContentLoader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModLoaderTests
{
    static int instanceCounter;
    static List<InitPhase> loadedPhases = Lists.newArrayList();

    @Test
    public void testInitAllPhases_ContentInstancesShouldBeTheSame()
    {
        instanceCounter = 0;
        loadedPhases.clear();

        TestMod mod = new TestMod();
        ModLoader.doPreInitMod(mod, ModLoaderTests::createHelper, new TestDeserializationRegistry());
        ModLoader.onRegisterBlocks(mod);
        ModLoader.onRegisterItems(mod);
        ModLoader.onRegisterModels(mod);
        ModLoader.onInitMod(mod);
        ModLoader.onPostInitMod(mod);

        assertEquals(1, instanceCounter);
        assertEquals(6, loadedPhases.size());
        assertTrue(loadedPhases.containsAll(Arrays.asList(InitPhase.values())));
    }

    private static ContentHelper createHelper(CS4Mod mod)
    {
        return new TestContentHelper("{}", TestContent.class)
                .withClass("contentLoader", ContentLoader.class)
                .withJson("main.json", "{\"list1\": [ {\"type\":\"test\", \"file\":\"someFile.json\"} ] }")
                .withJson("someFile.json", "{\"list1\": [ { \"name\":\"a\" } ] }");

    }

    public static class TestContent extends BlankContent
    {
        public String name;

        public TestContent()
        {
            instanceCounter++;
        }

        @Override
        public void init(InitPhase phase, ContentHelper helper)
        {
            loadedPhases.add(phase);
        }
    }

    private static class TestMod implements CS4Mod
    {

    }
}
