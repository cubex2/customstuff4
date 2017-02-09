package cubex2.cs4.data;

import cubex2.cs4.api.Content;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ContentLoaderTests
{
    @Test
    public void testLoadContent()
    {
        List<TestContent> list = ContentLoader.loadContent("{\"list1\": [ {\"name\":\"a\"},{\"name\":\"b\"} ], \"list2\": [ {\"name\":\"c\"},{\"name\":\"d\"} ] }", TestContent.class);

        assertEquals(4, list.size());
        assertEquals("a", list.get(0).name);
        assertEquals("b", list.get(1).name);
        assertEquals("c", list.get(2).name);
        assertEquals("d", list.get(3).name);
    }

    @Test
    public void testLoadContent_empty()
    {
        List<TestContent> list = ContentLoader.loadContent("{ }", TestContent.class);

        assertEquals(0, list.size());
    }

    @Test
    public void testLoadContent_emptyList()
    {
        List<TestContent> list = ContentLoader.loadContent("{\"list\":[] }", TestContent.class);

        assertEquals(0, list.size());
    }

    @Test
    public void testLoadContent_emptyListAndNonEmptyList()
    {
        List<TestContent> list = ContentLoader.loadContent("{\"list\":[],\"list2\": [ {\"name\":\"c\"},{\"name\":\"d\"} ] }", TestContent.class);

        assertEquals(2, list.size());
        assertEquals("c", list.get(0).name);
        assertEquals("d", list.get(1).name);
    }

    @Test
    public void testLoadContent_objectInsteadOfList()
    {
        List<TestContent> list = ContentLoader.loadContent("{\"list\": { \"name\":\"a\" } }", TestContent.class);

        assertEquals(1, list.size());
        assertEquals("a", list.get(0).name);
    }

    public static class TestContent implements Content
    {
        public String name;
    }
}
