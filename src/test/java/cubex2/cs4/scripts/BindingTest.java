package cubex2.cs4.scripts;

import cubex2.cs4.script.Binding;
import cubex2.cs4.script.IncludeFunction;
import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import org.junit.jupiter.api.*;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Script environment test")
public class BindingTest {

    NashornSandbox sandbox;
    Binding binding;
    static String api;
    IncludeFunction includeFunction;

    @BeforeAll
    static void load() throws FileNotFoundException {
        api = "";
        ClassLoader classLoader = BindingTest.class.getClassLoader();
        File file = new File(classLoader.getResource("api.js").getFile());
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            api = api + scanner.next() + "\n";
        }
    }

    @BeforeEach
    void setup() throws ScriptException {
        sandbox = NashornSandboxes.create();
        sandbox.allowGlobalsObjects(false);
        binding = new Binding(sandbox.createBindings());

        includeFunction = mock(IncludeFunction.class);
        binding.put("includer", includeFunction, true);
        sandbox.eval(api, binding);
    }

    @Nested
    @DisplayName("The API is initialised correctly")
    class ApiIsCorrect {

        @Nested
        @DisplayName("all the required objects are included")
        class HasObjects {
            @Test
            @DisplayName("The 'includer' object is there")
            void hasIncluder() {
                assertTrue(binding.containsKey("includer"));
            }

            @Test
            @DisplayName("The 'EventHandlerObject' object is there")
            void hasEHO() {
                assertTrue(binding.containspath("EventHandlerObject"));
            }

            @Test
            @DisplayName("The 'EventHandler' object is there")
            void hasEH() {
                assertTrue(binding.containspath("EventHandler"));
            }

            @Test
            @DisplayName("The 'include' function is there")
            void hasInclude() {
                assertTrue(binding.containspath("include"));
            }

            @Test
            @DisplayName("The 'require' function is included")
            void hasRequire() {
                assertTrue(binding.containspath("require"));
            }
        }

        @Nested
        @DisplayName("The api works")
        class APIWorks {

            @Test
            @DisplayName("include can be called")
            void include() throws ScriptException {
                sandbox.eval("include(\"asd\")", binding);
                verify(includeFunction, times(1)).include("asd");
            }

            @Nested
            @DisplayName("require")
            class Require {
                Object result;

                @BeforeEach
                void setup() throws ScriptException {
                    when(includeFunction.require("asd")).thenReturn("required");
                    result = sandbox.eval("require(\"asd\")", binding);
                }

                @Test
                @DisplayName("can be called")
                void require() {
                    verify(includeFunction, times(1)).require("asd");
                }

                @Test
                @DisplayName("returns the correct value")
                void returnsCorrect() {
                    assertEquals("required", result);
                }

                @Test
                @DisplayName("can call eventFunctions")
                void canCallEventFunction(){

                }
            }


        }
    }
}
