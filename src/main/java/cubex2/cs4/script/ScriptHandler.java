package cubex2.cs4.script;

import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import jdk.nashorn.api.scripting.JSObject;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author <a href="mailto:kisandrasgabor@gmail.com">András Kis</a>
 */

public class ScriptHandler {

    private final JSObject eventHandler;
    public NashornSandbox sandbox;
    public Binding binding;
    private IncludeFunction includeFunction;
    public static final String api = "assets/customstuff4/scriptAPI/api.js";
    public static final String[] forbiddenNames = {
            "EventHander","EventHandlerObject","include","includer","require"
    };

    /**
     * @throws ScriptException if thrown here, than there's an api error.
     */
    public ScriptHandler() throws ScriptException, FileNotFoundException {
        sandbox = NashornSandboxes.create();
        sandbox.disallowAllClasses();

        //should be set from the config
        sandbox.setMaxCPUTime(200);

        //used for the non-standard "include" function.
        //should not be called directly, so the name is random.
        String includeID = "includer";
        binding = new Binding(sandbox.createBindings());
        includeFunction = new IncludeFunction(sandbox,binding);
        binding.put(includeID, includeFunction,true);

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(api).getFile());
        String api = new Scanner(file).useDelimiter("\n").next();
        api = api.replaceAll("\\$\\{include}",includeID);
        sandbox.eval(api);
        this.eventHandler = (JSObject)sandbox.get("eventHandler");
    }

    public void load(File folder) {
        File mainFile = new File(folder,"main.js");
        try {
            String script = new Scanner(mainFile).useDelimiter("\n").next();
            sandbox.eval(script);
        }catch (FileNotFoundException ignored){

        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
