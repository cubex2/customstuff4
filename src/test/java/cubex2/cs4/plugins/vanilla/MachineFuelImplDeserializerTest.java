package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MachineFuelImplDeserializerTest
{
    private static Gson gson;

    @BeforeAll
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void itemsWithoutList()
    {
        MachineFuelImpl fuel = gson.fromJson("{ \"items\": \"minecraft:apple\" }", MachineFuelImpl.class);

        assertEquals(1, fuel.items.size());
    }
}
