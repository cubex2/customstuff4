package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cubex2.cs4.api.WrappedItemStack;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WrappedItemStackDeserializerTests
{
    @Test
    public void test_fromString()
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(WrappedItemStack.class, new WrappedItemStackDeserializer()).create();
        Map<String, WrappedItemStack> map = gson.fromJson("{ \"stack\":\"minecraft:stone\" }", new TypeToken<Map<String, WrappedItemStack>>() {}.getType());

        assertTrue(map.get("stack") instanceof WrappedItemStackImpl);
        WrappedItemStackImpl stack = (WrappedItemStackImpl) map.get("stack");
        assertEquals("minecraft:stone", stack.item);
        assertEquals(1, stack.amount);
        assertEquals(0, stack.metadata);
    }

    @Test
    public void test_fromObject()
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(WrappedItemStack.class, new WrappedItemStackDeserializer()).create();
        WrappedItemStackImpl stack = (WrappedItemStackImpl) gson.fromJson("{ \"item\":\"minecraft:stone\",\"amount\":42,\"metadata\":10 }", WrappedItemStack.class);

        assertEquals("minecraft:stone", stack.item);
        assertEquals(42, stack.amount);
        assertEquals(10, stack.metadata);
    }
}
