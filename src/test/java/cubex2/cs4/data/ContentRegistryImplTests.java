package cubex2.cs4.data;

import cubex2.cs4.api.BlankContent;
import cubex2.cs4.api.Content;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContentRegistryImplTests
{
    @Test
    public void testRegister_duplicateType_shouldThrow()
    {
        ContentRegistryImpl registry = new ContentRegistryImpl();
        registry.registerContentType("type", TestContent.class);
        assertThrows(IllegalArgumentException.class,
                     () -> registry.registerContentType("type", TestContent.class));
    }

    @Test
    public void testRegister_sameClassDifferentNames()
    {
        ContentRegistryImpl registry = new ContentRegistryImpl();
        registry.registerContentType("type1", TestContent.class);
        registry.registerContentType("type2", TestContent.class);

        Class<? extends Content> class1 = registry.getContentClass("type1");
        Class<? extends Content> class2 = registry.getContentClass("type2");
        assertNotNull(class1);
        assertNotNull(class2);
        assertSame(class1, class2);
    }

    @Test
    public void testRegisterPredicate_duplicateType_shouldThrow()
    {
        ContentRegistryImpl registry = new ContentRegistryImpl();
        registry.registerLoaderPredicate("type", arguments -> false);
        assertThrows(IllegalArgumentException.class,
                     () -> registry.registerLoaderPredicate("type", arguments -> true));
    }

    public static class TestContent extends BlankContent
    {

    }
}
