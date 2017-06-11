package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import net.minecraft.init.Items;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class IMCDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();
    }

    @Test
    public void test_string()
    {
        IMCBase imc = gson.fromJson("{ \"type\":\"string\", \"modId\":\"cs4\", \"key\": \"key\", \"value\":\"value\" }", IMCBase.class);

        assertTrue(imc instanceof IMCString);

        IMCString string = (IMCString) imc;

        assertEquals("cs4", string.modId);
        assertEquals("key", string.key);
        assertEquals("value", string.value);
    }

    @Test
    public void test_resource()
    {
        IMCBase imc = gson.fromJson("{ \"type\":\"resource\", \"modId\":\"cs4\", \"key\": \"key\", \"value\":\"cs4:resource\" }", IMCBase.class);

        assertTrue(imc instanceof IMCResourceLocation);

        IMCResourceLocation string = (IMCResourceLocation) imc;

        assertEquals("cs4", string.modId);
        assertEquals("key", string.key);
        assertEquals("cs4:resource", string.value.toString());
    }

    @Test
    public void test_nbt()
    {
        IMCBase imc = gson.fromJson("{ \"type\":\"nbt\", \"modId\":\"cs4\", \"key\": \"key\", \"value\":\"{AByte:1b}\" }", IMCBase.class);

        assertTrue(imc instanceof IMCNBT);

        IMCNBT string = (IMCNBT) imc;

        assertEquals("cs4", string.modId);
        assertEquals("key", string.key);
        assertEquals(1, string.value.getByte("AByte"));
    }

    @Test
    public void test_itemstack()
    {
        IMCBase imc = gson.fromJson("{ \"type\":\"itemstack\", \"modId\":\"cs4\", \"key\": \"key\", \"value\":\"minecraft:stick\" }", IMCBase.class);

        assertTrue(imc instanceof IMCItemStack);

        IMCItemStack string = (IMCItemStack) imc;

        assertEquals("cs4", string.modId);
        assertEquals("key", string.key);
        assertSame(Items.STICK, string.value.getItemStack().getItem());
    }

    @Test
    public void test_function()
    {
        IMCBase imc = gson.fromJson("{ \"type\":\"function\", \"modId\":\"cs4\", \"key\": \"key\", \"value\":\"function\" }", IMCBase.class);

        assertTrue(imc instanceof IMCFunction);

        IMCFunction string = (IMCFunction) imc;

        assertEquals("cs4", string.modId);
        assertEquals("key", string.key);
        assertEquals("function", string.value);
    }
}