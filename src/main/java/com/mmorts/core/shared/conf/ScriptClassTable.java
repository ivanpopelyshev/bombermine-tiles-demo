package com.mmorts.core.shared.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ScriptClassTable<T extends ScriptClass> {
    public HashMap<String, T> byName = new HashMap<String, T>();
    public List<T> byIndex = new ArrayList<T>();
    public String className;
    public int idShift;

    public T newInstance(MyScriptContext context, T clazz) {
        context.define(clazz, byName.get(clazz.name), className);
        clazz.defined = true;
        if (!byName.containsKey(clazz.name)) {
            clazz.id = byIndex.size() + idShift;
            byIndex.add(clazz);
        } else {
            byIndex.set(clazz.id - idShift, clazz);
        }
        // TODO: возможно не во всех таблицах нужны анонимные классы
        if (clazz.name.length() > 0) byName.put(clazz.name, clazz);
        return clazz;
    }

    // TODO: что если name == ""
    public T getInstance(MyScriptContext context, String name) {
        if (byName.containsKey(name)) {
            return byName.get(name);
        }
        T clazz = create();
        clazz.name = name;
        clazz.id = byIndex.size() + idShift;
        byIndex.add(clazz);
        byName.put(name, clazz);
        context.define(clazz, null, className);
        return clazz;
    }

    public T byId(int id) {
        if (id < idShift || id >= idShift + byIndex.size()) return null;
        return byIndex.get(id - idShift);
    }

    public int size() {
        return byIndex.size();
    }

    public abstract T create();

    public ScriptClassTable<T> setShift(int shift) {
        this.idShift = shift;
        return this;
    }

    public ScriptClassTable<T> setClassName(String name) {
        this.className = name;
        return this;
    }

    public Object getAndWrap(MyScriptContext context, String name) {
        T instance = getInstance(context, name);
        return context.utils.wrap(instance.id, className);
    }

    public String dumpString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(className).append(" ===\n");
        for (int i = 0; i < byIndex.size(); i++)
            sb.append(byIndex.get(i).id).append(" ").append(byIndex.get(i).name).append("\n");
        sb.append("\n");
        return sb.toString();
    }
}
