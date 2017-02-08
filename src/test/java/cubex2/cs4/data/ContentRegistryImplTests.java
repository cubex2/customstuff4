package cubex2.cs4.data;

import cubex2.cs4.api.Content;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ContentRegistryImplTests
{
    @Test(expected = IllegalArgumentException.class)
    public void testRegister_duplicateType_shouldThrow()
    {
        ContentRegistryImpl registry = new ContentRegistryImpl();
        registry.registerContentType("type", TestContent.class);
        registry.registerContentType("type", TestContent.class);
    }

    @Test
    public void testRegister_sameClassDifferentNames()
    {
        ContentRegistryImpl registry = new ContentRegistryImpl();
        registry.registerContentType("type1", TestContent.class);
        registry.registerContentType("type2", TestContent.class);

        assertNotNull(registry.createInstance("type1"));
        assertNotNull(registry.createInstance("type2"));
    }

    public static class TestContent implements Content
    {

    }
}
