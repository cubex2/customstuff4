package cubex2.cs4.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class JsonHelperTests
{
    @Test
    public void testArrayFromElement_singleValue()
    {
        JsonElement element = new JsonPrimitive("abc");
        String[] strings = JsonHelper.arrayFromElement(element);

        assertArrayEquals(new String[] {"abc"}, strings);
    }

    @Test
    public void testArrayFromElement_array()
    {
        JsonArray element = new JsonArray();
        element.add(new JsonPrimitive("abc"));
        element.add(new JsonPrimitive("def"));

        String[] strings = JsonHelper.arrayFromElement(element);

        assertArrayEquals(new String[] {"abc", "def"}, strings);
    }
}
