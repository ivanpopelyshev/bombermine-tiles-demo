package com.mmorts.core.shared.conf;

import java.util.ArrayList;
import java.util.HashMap;

public class ScriptContext {
    public static class Location {
        public String filename;
        public int line, pos;
    }

    public static class Error {
        public String msg;
        public Location loc;

        public Error(String msg, Location loc) {
            super();
            this.msg = msg;
            this.loc = loc;
        }

        @Override
        public String toString() {
            return msg;
        }
    }

    public ArrayList<Error> errors = new ArrayList<Error>();
    public HashMap<ScriptClass, Location> whereDefined = new HashMap<ScriptClass, Location>();
    private Location location;
    public ScriptUtils utils;

    public void addError(String msg) {
        errors.add(new Error(msg, location));
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public void define(ScriptClass clazz, ScriptClass undefined, String tableName) {
        if (undefined != null) {
            clazz.id = undefined.id;
            if (undefined.defined) {
                addError(tableName + " '" + clazz.name + "' is already created");
                return;
            }
            whereDefined.remove(undefined);
        }
        whereDefined.put(clazz, location);
    }

    public void checkDefines() {
        for (ScriptClass cl : whereDefined.keySet()) {
            if (!cl.defined) {
                errors.add(new Error("'" + cl.name + "' is not created", whereDefined.get(cl)));
            }
        }
    }
}
