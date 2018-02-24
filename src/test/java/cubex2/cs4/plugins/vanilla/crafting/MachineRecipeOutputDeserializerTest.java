package cubex2.cs4.plugins.vanilla.crafting;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.InitPhase;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MachineRecipeOutputDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void fromString()
    {
        Map<String, MachineRecipeOutputImpl> map = gson.fromJson("{ \"output\": \"minecraft:apple\" }", new TypeToken<Map<String, MachineRecipeOutputImpl>>() {}.getType());

        MachineRecipeOutputImpl output = map.get("output");
        output.doInit(InitPhase.PRE_INIT, null);

        assertEquals(1, output.getOutputItems().size());
        assertEquals(0, output.getOutputFluids().size());
        assertEquals(1, output.getWeight());
    }

    @Test
    public void fromObject()
    {
        MachineRecipeOutputImpl output = gson.fromJson("{ \"items\": \"minecraft:apple\", \"fluids\":\"water\",\"weight\":5 }", MachineRecipeOutputImpl.class);
        output.doInit(InitPhase.PRE_INIT, null);

        assertEquals(1, output.getOutputItems().size());
        assertEquals(1, output.getOutputFluids().size());
        assertEquals(5, output.getWeight());
    }

    @Test
    public void emptyStack()
    {
        MachineRecipeOutputImpl output = gson.fromJson("{ \"items\": [\"minecraft:air\",\"minecraft:apple\"] }", MachineRecipeOutputImpl.class);
        output.doInit(InitPhase.PRE_INIT, null);

        assertEquals(2, output.getOutputItems().size());
        assertEquals(0, output.getOutputFluids().size());
    }

    @Test
    public void emptyFluid()
    {
        MachineRecipeOutputImpl output = gson.fromJson("{ \"items\": [\"minecraft:air\",\"minecraft:apple\"] }", MachineRecipeOutputImpl.class);
        output.doInit(InitPhase.PRE_INIT, null);

        assertEquals(2, output.getOutputItems().size());
        assertEquals(0, output.getOutputFluids().size());
    }
}