package com.bm.gwt.client.conf;

import com.google.gwt.core.client.JavaScriptObject;
import com.mmorts.core.shared.conf.ScriptUtils;

public final class ScriptUtilsGWT extends JavaScriptObject implements ScriptUtils {

    protected ScriptUtilsGWT() {
    }

    public static native ScriptUtilsGWT create() /*-{
		return {
			val : null
		}
    }-*/;

    @Override
    public native Object wrap(int id, String clazz) /*-{
		return {
			id : id,
			type : clazz
		};
    }-*/;

    @Override
    public native int unwrapInt(Object obj) /*-{
		return obj.id;
    }-*/;

    @Override
    public native String getType(Object obj) /*-{
		return obj.type;
    }-*/;

    @Override
    public native String getString() /*-{
		return this.val;
    }-*/;

    @Override
    public native int getInt() /*-{
		return this.val;
    }-*/;

    @Override
    public Object[] getArray() {
        Object[] arr = new Object[getLen()];
        for (int i = 0; i < getLen(); i++)
            arr[i] = get(i);
        return arr;
    }

    private native int getLen() /*-{
		return this.val.length;
    }-*/;

    private native Object get(int index) /*-{
		return this.val[index];
    }-*/;

    @Override
    public native Object getObject() /*-{
		return this.val;
    }-*/;

    @Override
    public native boolean getField(Object obj, String field) /*-{
		if (obj[field] === undefined)
			return false;
		this.val = obj[field];
		return true;
    }-*/;
}
