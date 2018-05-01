package cubex2.cs4.asm;

import cubex2.cs4.util.JsonHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

@DisplayName("ModInfo")
public class ModInfoTests {

    @Test
    @DisplayName("deserializes properly")
    public void testJsonDeserialization() {
        ModInfo info = JsonHelper.deserialize("{\"id\":\"theid\"}", ModInfo.class);

        assertEquals("theid", info.id);
        assertNotNull(info.name);
        assertNotNull(info.version);
        assertNotNull(info.dependencies);

    }

    @Nested
    @DisplayName("is not valid if ")
    class ModinfoInvalid {
        ModInfo info;

        @BeforeEach
        void beforeEach() {
            info = new ModInfo();
        }

        @Test
        @DisplayName("id is null")
        public void testIsValid_nullId_shouldReturnFalse() {

            info.id = null;

            assertFalse(info.isValid());
        }

        @Test
        @DisplayName("id is empty")
        public void testIsValid_emptyId_shouldReturnFalse() {
            info.id = "";

            assertFalse(info.isValid());
        }

        @Test
        @DisplayName("id is null")
        public void testIsValid_nullName_shouldReturnFalse() {
            ModInfo info = new ModInfo();
            info.name = null;

            assertFalse(info.isValid());
        }

        @Test
        @DisplayName("version is null")
        public void testIsValid_nullVersion_shouldReturnFalse() {
            info.version = null;
            assertFalse(info.isValid());
        }

        @Test
        @DisplayName("dependencies is null")
        public void testIsValid_nullDependency_shouldReturnFalse() {
            info.dependencies = null;
            assertFalse(info.isValid());
        }

        @Test
        @DisplayName("id contains uppercase letters")
        public void testIsValid_uppercaseId_shouldReturnFalse() {
            info.id = "SomeId";
            assertFalse(info.isValid());
        }

        @Test
        @DisplayName("id contains space")
        public void testIsValid_spaceInId_shouldReturnFalse() {
            ModInfo info = new ModInfo();
            info.id = "some id";

            assertFalse(info.isValid());
        }
    }

    @Nested
    @DisplayName("is valid if")
    class ModinfoValid {
        @Test
        @DisplayName("id is lovercase")
        public void testIsValid_lowercaseId_shouldReturnTrue() {
            ModInfo info = new ModInfo();
            info.id = "someid";

            assertTrue(info.isValid());
        }

        @Test
        @DisplayName("id contains underscore")
        public void testIsValid_underscoreId_shouldReturnTrue() {
            ModInfo info = new ModInfo();
            info.id = "some_id";

            assertTrue(info.isValid());
        }

        @Test
        @DisplayName("id contains a number")
        public void testIsValid_numberId_shouldReturnTrue() {
            ModInfo info = new ModInfo();
            info.id = "some_id_1";

            assertTrue(info.isValid());
        }

    }


}
