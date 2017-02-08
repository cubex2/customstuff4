package cubex2.cs4.data;

import com.google.gson.reflect.TypeToken;
import cubex2.cs4.util.JsonHelper;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class ContentListTests
{
    @Test
    public void testDeserialization()
    {
        Map<String, ContentList> map = JsonHelper.deserialize("{\"content\":[\n" +
                                                                 "{\n" +
                                                                 "\"type\":\"shapedRecipe\",\n" +
                                                                 "\"file\": \"recipe/myRecipe.json\"\n" +
                                                                 "},\n" +
                                                                 "{\n" +
                                                                 "\"type\":\"contentList\",\n" +
                                                                 "\"file\":\"anotherContentFile.json\"\n" +
                                                                 "}\n" +
                                                                 "]}"
                , new TypeToken<Map<String, ContentList>> (){}.getType());

        ContentList list = map.get("content");
        assertEquals(2, list.loaders.size());
    }
}
