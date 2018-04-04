package cubex2.cs4.script.runtime;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.CustomStuffConfiguration;
import cubex2.cs4.asm.ModInfo;
import cubex2.cs4.util.JsonHelper;
import frontrider.repack.delight.nashornsandbox.NashornSandbox;
import frontrider.repack.delight.nashornsandbox.NashornSandboxes;
import frontrider.repack.delight.nashornsandbox.exceptions.NotScriptedException;
import frontrider.repack.delight.nashornsandbox.internal.Binding;
import frontrider.repack.delight.nashornsandbox.internal.InterruptTest;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.minecraft.util.text.TextComponentString;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author <a href="mailto:kisandrasgabor@gmail.com">András Kis</a>
 */

public class ScriptHandler {

    public ScriptObjectMirror eventHandler;
    public NashornSandbox sandbox;
    public Binding binding;
    private IncludeFunction includeFunction;
    private final String ASSETS_CUSTOMSTUFF4_SCRIPT_API_API_JS = "assets/customstuff4/scriptAPI/api.js";
    private final String api;
    public static final String[] forbiddenNames = {
            "EventHander",
            "EventHandlerObject",
            "include",
            "includer",
            "require",
            "$",
            "__it"
            //,"__if"
    };

    public ScriptHandler() throws IOException, ScriptException, NotScriptedException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(ASSETS_CUSTOMSTUFF4_SCRIPT_API_API_JS).getFile());
        api = new Scanner(new FileInputStream(file)).useDelimiter("\\A").next();
        initSandbox();
    }

    private void initSandbox() throws ScriptException, NotScriptedException {
        sandbox = NashornSandboxes.create();
        sandbox.disallowAllClasses();
        setupSandbox(sandbox);
        //used for the non-standard "include" function.
        //should not be called directly, so the name is random.
        String includeID = "includer";
        binding = new Binding(sandbox.createBindings(), new String[]{});
        includeFunction = new IncludeFunction(sandbox, binding);
        binding.put(includeID, includeFunction, true);
        binding.put("console", new ScriptConsole(), true);

        //dirty hack at the javascript sandbox library.
        binding.put("__it", new InterruptTest(), true);
        String extrajs = "function __if(){}";
        sandbox.eval(extrajs, binding);
        binding.addBlackListedName("__if");
        //remove above this line after the initial testing phase finished

        sandbox.eval(api, binding);
        for (String s : forbiddenNames) {
            binding.addBlackListedName(s);
        }
        this.eventHandler = (ScriptObjectMirror) binding.getOnPath("EventHandler");
    }

    public void load(File modsDir) {

        File[] modFolders = modsDir.listFiles(File::isDirectory);
        if (modFolders != null) {
            for (File folder : modFolders) {
                File infoJsonFile = new File(folder, "cs4mod.json");
                if (infoJsonFile.exists() && infoJsonFile.isFile()) {
                    ModInfo info = JsonHelper.deserialize(infoJsonFile, ModInfo.class);
                    if (info != null) {
                        if (CustomStuffConfiguration.ScriptEnabled) {
                            try {
                                File scriptDir = new File(modsDir, "script");
                                if (!scriptDir.exists())
                                    scriptDir.mkdir();

                                String script = "";
                                File mainFile = new File(folder, "script/main.js");
                                Scanner scanner = new Scanner(mainFile).useDelimiter("\n");

                                while (scanner.hasNext()) {
                                    script = script + scanner.next();
                                }
                                sandbox.eval(script, binding);
                            } catch (FileNotFoundException | ScriptException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private void setupSandbox(NashornSandbox sandbox) {
        sandbox.allow(TextComponentString.class);
    }

    public void reload() {
        try {
            initSandbox();
            load(CustomStuff4.modsDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
