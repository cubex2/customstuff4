package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NamedMapDeserializerTest
{
    @Test
    public void test()
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, TestClass>>() {}.getType(), new NamedMapDeserializer<>(TestClass.class)).create();

        Map<String, Map<String, TestClass>> map1 = gson.fromJson("{\"entries\": [ { \"name\":\"first\", \"i\":5 },{ \"name\":\"second\", \"i\":10 } ] }", new TypeToken<Map<String, Map<String, TestClass>>>() {}.getType());
        Map<String, TestClass> map = map1.get("entries");

        TestClass first = map.get("first");
        TestClass second = map.get("second");

        assertNotNull(first);
        assertNotNull(second);
        assertEquals(5, first.i);
        assertEquals(10, second.i);
    }

    private static class TestClass
    {
        public int i;
    }
}