package frontrider.repack.delight.nashornsandbox.internal;

import frontrider.repack.delight.nashornsandbox.exceptions.NotScriptedException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.lang3.ArrayUtils;

import javax.script.Bindings;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This object is used to give greater control over the evaluation environment.
 * Duties:
 * -prevents the removal of certain default names, used by the api.
 */
public class Binding implements Bindings {

    private final Bindings core;
    private String[] forbiddenNames;
    public Binding(Bindings core, String[] forbiddenNames) {
        this.core = core;
        this.forbiddenNames = forbiddenNames;
    }

    public Object put(String name, Object value, boolean override) {
        if (!override)
            if (ArrayUtils.contains(forbiddenNames, name)) {
                return false;
            }
        return core.put(name, value);
    }

    public void addBlackListedName(String name){
        forbiddenNames = ArrayUtils.add(forbiddenNames,name);
    }

    @Override
    public Object put(String name, Object value) {
        if (ArrayUtils.contains(forbiddenNames, name)) {
            return false;
        }
        return core.put(name, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> toMerge) {

        for (String key : forbiddenNames) {
            if (toMerge.keySet().contains(key)) {
                toMerge.remove(key);
            }
        }
        core.putAll(toMerge);
    }

    @Override
    public void clear() {
        core.clear();
    }

    @Override
    public Set<String> keySet() {
        return core.keySet();
    }

    @Override
    public Collection<Object> values() {
        return core.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return core.entrySet();
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return core.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Object> action) {
        core.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super Object, ?> function) {
        core.replaceAll(function);
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        return core.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (ArrayUtils.contains(forbiddenNames, key)) {
            return false;
        }

        return core.remove(key, value);
    }

    @Override
    public boolean replace(String key, Object oldValue, Object newValue) {
        if (ArrayUtils.contains(forbiddenNames, key)) {
            return false;
        }
        return core.replace(key, oldValue, newValue);
    }

    @Override
    public Object replace(String key, Object value) {
        if (ArrayUtils.contains(forbiddenNames, key)) {
            return false;
        }
        return core.replace(key, value);
    }

    @Override
    public Object computeIfAbsent(String key, Function<? super String, ?> mappingFunction) {
        return core.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return core.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return core.compute(key, remappingFunction);
    }

    @Override
    public Object merge(String key, Object value, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        if (ArrayUtils.contains(forbiddenNames, key)) {
            return false;
        }
        return core.merge(key, value, remappingFunction);
    }

    @Override
    public int size() {
        return core.size();
    }

    @Override
    public boolean isEmpty() {
        return core.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return core.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return core.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return core.get(key);
    }

    @Override
    public Object remove(Object key) {
        if (ArrayUtils.contains(forbiddenNames, key)) {
            return false;
        }
        return core.remove(key);
    }

    //helper functions for quick access

    public boolean containspath(String path) {
        try {
            getOnPath(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("CollectionAddedToSelf")
    public Object execute(String path, Object... args) throws NotScriptedException, RuntimeException {
        ScriptObjectMirror thiz = ((ScriptObjectMirror) getOnPath(path));
        return thiz.call(thiz, args);
    }

    public Object getOnPath(String path) throws NotScriptedException {
        ScriptObjectMirror global = (ScriptObjectMirror) core.get("nashorn.global");
        Object fun;
        String[] splitPath = null;
        if (path.contains(".")) {
            splitPath = path.split("\\.");
            fun = global.get(splitPath[0]);
        } else
            fun = global.get(path);
        if (fun instanceof ScriptObjectMirror) {
            if (splitPath == null) {
                return fun;
            } else {
                return getMember(splitPath, path, global);
            }
        } else {
            throw new NotScriptedException(path + " is not scripted!");
        }
    }

    private ScriptObjectMirror getMember(String[] path, String originalPath, ScriptObjectMirror parent) throws NotScriptedException {
        Object obj = parent.get(path[0]);
        if (obj instanceof ScriptObjectMirror) {
            if (path.length == 1) {
                return (ScriptObjectMirror) obj;
            } else {
                String[] newPath = new String[path.length - 1];
                System.arraycopy(path, 1, newPath, 0, path.length - 1);
                return getMember(newPath, originalPath, (ScriptObjectMirror) obj);
            }
        } else
            throw new NotScriptedException(path[0] + " on path " + originalPath + " is not scripted!");
    }


}
