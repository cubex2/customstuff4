package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.TileEntityModule;
import cubex2.cs4.api.TileEntityModuleSupplier;
import net.minecraft.tileentity.TileEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TileEntityModuleSupplierDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder()
                       .registerTypeAdapter(TileEntityModuleSupplier.class, new TileEntityModuleSupplierDeserializer(typename -> TestModule.class))
                       .create();
    }

    @Test
    public void test()
    {
        TileEntityModuleSupplier module = gson.fromJson("{\"type\":\"test\",\"prop\":\"someProp\"}", TileEntityModuleSupplier.class);

        assertTrue(module instanceof TestModule);
        assertEquals("someProp", ((TestModule) module).prop);
    }

    private static class TestModule implements TileEntityModuleSupplier
    {
        String prop;

        @Override
        public TileEntityModule createModule(TileEntity tileEntity)
        {
            return null;
        }
    }
}