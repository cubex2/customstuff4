package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WrappedItemStackDeserializerTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = new GsonBuilder().registerTypeAdapter(WrappedItemStack.class, new WrappedItemStackDeserializer())
                                .registerTypeAdapter(ResourceLocation.class, new ResourceLocationDeserializer())
                                .create();
    }

    @Test
    public void test_fromString()
    {
        Map<String, WrappedItemStack> map = gson.fromJson("{ \"stack\":\"minecraft:stone\" }", new TypeToken<Map<String, WrappedItemStack>>() {}.getType());

        assertTrue(map.get("stack") instanceof WrappedItemStackImpl);
        WrappedItemStackImpl stack = (WrappedItemStackImpl) map.get("stack");
        assertEquals(new ResourceLocation("minecraft:stone"), stack.item);
        assertEquals(1, stack.amount);
        assertEquals(0, stack.metadata);
    }

    @Test
    public void test_fromObject()
    {
        WrappedItemStackImpl stack = (WrappedItemStackImpl) gson.fromJson("{ \"item\":\"minecraft:stone\",\"amount\":42,\"metadata\":10 }", WrappedItemStack.class);

        assertEquals(new ResourceLocation("minecraft:stone"), stack.item);
        assertEquals(42, stack.amount);
        assertEquals(10, stack.metadata);
    }

    @Test
    public void test_wildcard_meta()
    {
        WrappedItemStackImpl stack = (WrappedItemStackImpl) gson.fromJson("{ \"item\":\"minecraft:stone\",\"amount\":42,\"metadata\":\"all\" }", WrappedItemStack.class);

        assertEquals(new ResourceLocation("minecraft:stone"), stack.item);
        assertEquals(42, stack.amount);
        assertEquals(OreDictionary.WILDCARD_VALUE, stack.metadata);
    }
}
