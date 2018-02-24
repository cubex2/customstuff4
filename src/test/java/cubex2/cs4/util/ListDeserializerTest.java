package cubex2.cs4.util;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ListDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setUp()
    {
        gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<Integer>>() {}.getType(), new ListDeserializer<>(Integer.class))
                .create();
    }

    @Test
    public void test_singleValue()
    {
        Map<String, List<Integer>> map = gson.fromJson("{ \"list\": 1 }", new TypeToken<Map<String, List<Integer>>>() {}.getType());

        assertEquals(1, map.size());
        List<Integer> list = map.get("list");
        assertEquals(Lists.newArrayList(1), list);
    }

    @Test
    public void test_array()
    {
        List<Integer> list = gson.fromJson("[1, 2]", new TypeToken<List<Integer>>() {}.getType());

        assertEquals(Lists.newArrayList(1, 2), list);
    }
}