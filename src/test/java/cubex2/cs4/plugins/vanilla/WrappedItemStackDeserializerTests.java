package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WrappedItemStackDeserializerTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGsonBuilder().create();

        Bootstrap.register();
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
    public void test_fromStringWithMeta()
    {
        Map<String, WrappedItemStack> map = gson.fromJson("{ \"stack\":\"minecraft:stone@13\" }", new TypeToken<Map<String, WrappedItemStack>>() {}.getType());

        assertTrue(map.get("stack") instanceof WrappedItemStackImpl);
        WrappedItemStackImpl stack = (WrappedItemStackImpl) map.get("stack");
        assertEquals(new ResourceLocation("minecraft:stone"), stack.item);
        assertEquals(13, stack.metadata);
    }

    @Test
    public void test_fromStringWithWildcardMeta()
    {
        Map<String, WrappedItemStack> map = gson.fromJson("{ \"stack\":\"minecraft:stone@all\" }", new TypeToken<Map<String, WrappedItemStack>>() {}.getType());

        assertTrue(map.get("stack") instanceof WrappedItemStackImpl);
        WrappedItemStackImpl stack = (WrappedItemStackImpl) map.get("stack");
        assertEquals(new ResourceLocation("minecraft:stone"), stack.item);
        assertEquals(OreDictionary.WILDCARD_VALUE, stack.metadata);
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

    @Test
    public void test_nbt()
    {
        WrappedItemStackImpl stack = (WrappedItemStackImpl) gson.fromJson("{ \"item\":\"minecraft:coal\", \"nbt\": \"{AByte:1b, AString:\\\"Test\\\"}\" }", WrappedItemStack.class);

        ItemStack itemStack = stack.createItemStack();
        NBTTagCompound nbt = itemStack.getTagCompound();

        assertNotNull(nbt);
        assertTrue(nbt.hasKey("AByte", Constants.NBT.TAG_BYTE));
        assertTrue(nbt.hasKey("AString", Constants.NBT.TAG_STRING));
        assertEquals((byte) 1, nbt.getByte("AByte"));
        assertEquals("Test", nbt.getString("AString"));
    }

    @Test
    public void test_no_nbt()
    {
        WrappedItemStackImpl stack = (WrappedItemStackImpl) gson.fromJson("{ \"item\": \"minecraft:coal\" }", WrappedItemStack.class);

        ItemStack itemStack = stack.createItemStack();
        NBTTagCompound nbt = itemStack.getTagCompound();

        assertNull(nbt);
    }
}
