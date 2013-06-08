package com.bm.model.server.conf;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.mmorts.core.shared.conf.ScriptContext;
import com.mmorts.core.shared.conf.ScriptUtils;

public class ScriptUtilsRhino implements ScriptUtils {

    Scriptable scope;
    Context context;
    ScriptContext sc;

    public ScriptUtilsRhino(Context context, Scriptable scope, ScriptContext sc) {
        this.scope = scope;
        this.context = context;
        this.sc = sc;
    }

    @Override
    public Object wrap(int id, String clazz) {
        Scriptable pair = context.newObject(scope);
        ScriptableObject.defineProperty(pair, "id", id, 0);
        ScriptableObject.defineProperty(pair, "type", clazz, 0);
        return pair;
    }

    @Override
    public int unwrapInt(Object obj) {
        Scriptable pair = (Scriptable) obj;
        return (Integer) ScriptableObject.getProperty(pair, "id");
    }

    @Override
    public String getType(Object obj) {
        Scriptable pair = (Scriptable) obj;
        return ScriptableObject.getProperty(pair, "type").toString();
    }

    Object something;

    @Override
    public String getString() {
        if (something instanceof String) return (String) something;
        sc.addError("String expected");
        return "";
    }

    @Override
    public int getInt() {
        if (something instanceof Double) return (int) Math.round((Double) something);
        if (something instanceof Integer) return (Integer) something;
        sc.addError("Integer expected");
        return -1;
    }

    @Override
    public Object[] getArray() {
        if (something instanceof NativeArray) {
            NativeArray arr = (NativeArray) something;
            int len = (int) arr.getLength();
            if (len < 0 || len > 100) {
                sc.addError("Too big array");
                return new Object[] {};
            }
            Object[] res = new Object[len];
            for (int i = 0; i < len; i++) {
                res[i] = arr.get(i, null);
            }
            return res;
        }
        return new Object[] {};
    }

    @Override
    public Object getObject() {
        if (something instanceof ScriptableObject) return something;
        sc.addError("Object expected");
        return context.newObject(scope);
    }

    @Override
    public boolean getField(Object obj, String field) {
        Scriptable arg = (Scriptable) obj;
        if (ScriptableObject.hasProperty(arg, field)) {
            something = ScriptableObject.getProperty(arg, field);
            return true;
        }
        return false;
    }

}
