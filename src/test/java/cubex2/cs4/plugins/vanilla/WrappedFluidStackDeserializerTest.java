package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.WrappedFluidStack;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WrappedFluidStackDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test_fromString()
    {
        Map<String, WrappedFluidStack> map = gson.fromJson("{ \"stack\":\"water\" }", new TypeToken<Map<String, WrappedFluidStack>>() {}.getType());

        assertTrue(map.get("stack") instanceof WrappedFluidStackImpl);
        WrappedFluidStackImpl stack = (WrappedFluidStackImpl) map.get("stack");
        assertEquals("water", stack.fluid);
        assertEquals(1000, stack.amount);
    }

    @Test
    public void test_fromEmptyString()
    {
        Map<String, WrappedFluidStack> map = gson.fromJson("{ \"stack\":\"\" }", new TypeToken<Map<String, WrappedFluidStack>>() {}.getType());

        WrappedFluidStack stack = map.get("stack");
        assertSame(WrappedFluidStack.EMPTY, stack);
    }

    @Test
    public void test_fromStringWithAmount()
    {
        Map<String, WrappedFluidStack> map = gson.fromJson("{ \"stack\":\"water@512\" }", new TypeToken<Map<String, WrappedFluidStack>>() {}.getType());

        assertTrue(map.get("stack") instanceof WrappedFluidStackImpl);
        WrappedFluidStackImpl stack = (WrappedFluidStackImpl) map.get("stack");
        assertEquals("water", stack.fluid);
        assertEquals(512, stack.amount);
    }

    @Test
    public void test_fromObject()
    {
        WrappedFluidStackImpl stack = (WrappedFluidStackImpl) gson.fromJson("{ \"fluid\":\"water\" }", WrappedFluidStack.class);

        assertEquals("water", stack.fluid);
        assertEquals(1000, stack.amount);
    }

    @Test
    public void test_fromObjectWithAmount()
    {
        WrappedFluidStackImpl stack = (WrappedFluidStackImpl) gson.fromJson("{ \"fluid\":\"water\",\"amount\":512 }", WrappedFluidStack.class);

        assertEquals("water", stack.fluid);
        assertEquals(512, stack.amount);
    }

    @Test
    public void test_fromObjectWithAmountInFluid()
    {
        WrappedFluidStackImpl stack = (WrappedFluidStackImpl) gson.fromJson("{ \"fluid\":\"water@512\" }", WrappedFluidStack.class);

        assertEquals("water", stack.fluid);
        assertEquals(512, stack.amount);
    }

    @Test
    public void test_fromObjectWithAmountInFluidOverridenInAmount()
    {
        WrappedFluidStackImpl stack = (WrappedFluidStackImpl) gson.fromJson("{ \"fluid\":\"water@512\",\"amount\":1024 }", WrappedFluidStack.class);

        assertEquals("water", stack.fluid);
        assertEquals(1024, stack.amount);
    }
}