package frontrider.repack.delight.nashornsandbox.internal;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import static frontrider.repack.delight.nashornsandbox.internal.NashornSandboxImpl.*;

public class EvaluateOperation implements ScriptEngineOperation {

    private final String js;
    private final ScriptContext scriptContext;
    private final Bindings bindings;

    public String getJs() {
        return js;
    }

    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    public Bindings getBindings() {
        return bindings;
    }

    public EvaluateOperation(String js, ScriptContext scriptContext, Bindings bindings) {
        this.js = js;
        this.scriptContext = scriptContext;
        this.bindings = bindings;
    }

    @Override
    public Object executeScriptEngineOperation(ScriptEngine scriptEngine) throws ScriptException {
        if (debugFlag) {
            System.out.println("--- Running JS ---");
            System.out.println(js);
            System.out.println("--- JS END ---");
        }

        if (bindings != null) {
            return scriptEngine.eval(js, bindings);
        } else if (scriptContext != null) {
            return scriptEngine.eval(js, scriptContext);
        } else {
            return scriptEngine.eval(js);
        }
    }
}
