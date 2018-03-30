package cubex2.cs4.asm;

import cubex2.cs4.util.JsonHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModInfoTests
{
    @Test
    public void testJsonDeserialization()
    {
        ModInfo info = JsonHelper.deserialize("{\"id\":\"theid\"}", ModInfo.class);

        assertEquals("theid", info.id);
        assertNotNull(info.name);
        assertNotNull(info.version);
        assertNotNull(info.dependencies);
    }

    @Test
    public void testIsValid_nullId_shouldReturnFalse()
    {
        ModInfo info = new ModInfo();
        info.id = null;

        assertFalse(info.isValid());
    }

    @Test
    public void testIsValid_emptyId_shouldReturnFalse()
    {
        ModInfo info = new ModInfo();
        info.id = "";

        assertFalse(info.isValid());
    }

    @Test
    public void testIsValid_nullName_shouldReturnFalse()
    {
        ModInfo info = new ModInfo();
        info.name = null;

        assertFalse(info.isValid());
    }

    @Test
    public void testIsValid_nullVersion_shouldReturnFalse()
    {
        ModInfo info = new ModInfo();
        info.version = null;

        assertFalse(info.isValid());
    }

    @Test
    public void testIsValid_nullDependency_shouldReturnFalse()
    {
        ModInfo info = new ModInfo();
        info.dependencies = null;

        assertFalse(info.isValid());
    }

    @Test
    public void testIsValid_uppercaseId_shouldReturnFalse()
    {
        ModInfo info = new ModInfo();
        info.id = "SomeId";

        assertFalse(info.isValid());
    }

    @Test
    public void testIsValid_spaceInId_shouldReturnFalse()
    {
        ModInfo info = new ModInfo();
        info.id = "some id";

        assertFalse(info.isValid());
    }

    @Test
    public void testIsValid_lowercaseId_shouldReturnTrue()
    {
        ModInfo info = new ModInfo();
        info.id = "someid";

        assertTrue(info.isValid());
    }

    @Test
    public void testIsValid_underscoreId_shouldReturnTrue()
    {
        ModInfo info = new ModInfo();
        info.id = "some_id";

        assertTrue(info.isValid());
    }

    @Test
    public void testIsValid_numberId_shouldReturnTrue()
    {
        ModInfo info = new ModInfo();
        info.id = "some_id_1";

        assertTrue(info.isValid());
    }
}
