package com.bm.model.shared.conf;

import com.mmorts.core.shared.conf.ScriptClass;

public class Slot extends ScriptClass {
    public int type = 1, image = -1, def = 0, max = -1;

    public Slot() {

    }

    public Slot(String name, int type, int def, int max) {
        this.name = name;
        this.type = type;
        this.def = def;
        this.max = max;
    }
}
