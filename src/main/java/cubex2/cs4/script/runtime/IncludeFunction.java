package cubex2.cs4.script.runtime;

import frontrider.repack.delight.nashornsandbox.NashornSandbox;
import frontrider.repack.delight.nashornsandbox.internal.Binding;

import javax.script.Bindings;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author <a href="mailto:kisandrasgabor@gmail.com">András Kis</a>
 */
@SuppressWarnings("unused")
public class IncludeFunction {

    private static final String root = "./mods/";
    private final NashornSandbox sandbox;
    private final Bindings bindings;
    private final Binding binding;
    private String folder;

    public IncludeFunction(NashornSandbox sandbox,Binding binding) {
        this.sandbox = sandbox;
        bindings = sandbox.createBindings();
        this.binding = binding;
    }

    void setFolder(String folder) {
        this.folder = root + folder;
    }

    //includes the file, by running required script when called.
    public void includeMethod(String file) {
        if (!file.endsWith(".js"))
            file = file + ".js";
        file = root + folder + file;
        try {

            File readFile = new File(file);
            String script = new Scanner(readFile).useDelimiter("\n").next();
            sandbox.eval(script,binding);

        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }
    }

    public Object requireMethod(String file) {
        bindings.clear();
        Object exported = null;
        if (!file.endsWith(".js"))
            file = file + ".js";
        file = root + folder + file;
        bindings.clear();
        try {
            File readFile = new File(file);
            String script = new Scanner(readFile).useDelimiter("\n").next();
            sandbox.eval(script, bindings);
            exported = bindings.get("export");

        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }
        return exported;
    }
}
