package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.ItemModuleSupplier;
import cubex2.cs4.plugins.vanilla.item.ItemModuleInventory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemModuleSupplierDeserializerTest
{
    private static Gson gson;

    @BeforeAll
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test()
    {
        ItemModuleSupplier module = gson.fromJson("{\"type\":\"inventory\",\"size\":10}", ItemModuleSupplier.class);

        assertTrue(module instanceof ItemModuleInventory.Supplier);
        assertEquals(10, ((ItemModuleInventory.Supplier) module).size);
    }
}