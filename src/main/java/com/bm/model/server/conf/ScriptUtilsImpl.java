package com.bm.model.server.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mmorts.core.shared.conf.MyScriptContext;
import com.mmorts.core.shared.conf.ScriptUtils;

public class ScriptUtilsImpl implements ScriptUtils {

    MyScriptContext sc;

    public ScriptUtilsImpl(MyScriptContext sc) {
        this.sc = sc;
    }

    @Override
    public Object wrap(int id, String clazz) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("type", clazz);
        return map;
    }

    @Override
    public int unwrapInt(Object obj) {
        return (Integer) ((Map) obj).get("id");
    }

    @Override
    public String getType(Object obj) {
        return (String) ((Map) obj).get("type");
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
        if (something instanceof Long) return ((Long)something).intValue();
        sc.addError("Integer expected");
        return -1;
    }

    @Override
    public Object[] getArray() {
        if (something instanceof List) {
            List list = (List) something;
            int len = list.size();
            if (len < 0 || len > 100) {
                sc.addError("Too big array");
                return new Object[] {};
            }
            return list.toArray(new Object[] {});
        }
        return new Object[] {};
    }

    @Override
    public Object getObject() {
        if (something instanceof Map) return something;
        sc.addError("Object expected");
        return null;
    }

    @Override
    public boolean getField(Object obj, String field) {
        Map map = (Map) obj;
        if (map.containsKey(field)) {
            something = map.get(field);
            return true;
        }
        return false;
    }
}
