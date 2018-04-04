package cubex2.cs4.script.runtime;

import cubex2.cs4.CustomStuff4;
import frontrider.repack.delight.nashornsandbox.NashornSandbox;
import frontrider.repack.delight.nashornsandbox.internal.Binding;

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
    private Binding bindings;
    private final Binding binding;
    private File folder;

    public IncludeFunction(NashornSandbox sandbox, Binding binding) {
        this.sandbox = sandbox;
        bindings = new Binding(sandbox.createBindings(),ScriptHandler.forbiddenNames);
        this.binding = binding;
    }

    void setFolder(File folder) {
        this.folder = folder;
    }

    //includes the file, by running required script when called.
    public void includeMethod(String file) {
        loadFile(file,CustomStuff4.scriptHandler.binding);
    }

    public Object requireMethod(String file) {
        try {
            bindings = CustomStuff4.scriptHandler.setupBindings();
            loadFile(file,bindings);

            return bindings.getOnPath("export");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadFile(String file,Binding bindings) {
        if (!file.endsWith(".js"))
            file = file + ".js";
        try {
            File readFile = new File(folder,file);
            String script = new Scanner(readFile).useDelimiter("\n").next();
            sandbox.eval(script, bindings);

        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }

    }
}
