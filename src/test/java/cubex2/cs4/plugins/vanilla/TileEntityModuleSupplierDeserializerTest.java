package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.vanilla.tileentity.TileEntityModuleTank;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileEntityModuleSupplierDeserializerTest
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
        TileEntityModuleSupplier module = gson.fromJson("{\"type\":\"tank\",\"capacity\":10}", TileEntityModuleSupplier.class);

        assertTrue(module instanceof TileEntityModuleTank.Supplier);
        assertEquals(10, ((TileEntityModuleTank.Supplier) module).capacity);
    }
}