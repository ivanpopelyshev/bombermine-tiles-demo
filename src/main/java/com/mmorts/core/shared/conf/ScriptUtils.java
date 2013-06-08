package com.mmorts.core.shared.conf;

public interface ScriptUtils {
    public Object wrap(int id, String clazz);

    public int unwrapInt(Object obj);

    public String getType(Object obj);

    public String getString();

    public int getInt();

    public Object[] getArray();

    public Object getObject();

    public boolean getField(Object obj, String field);
}
