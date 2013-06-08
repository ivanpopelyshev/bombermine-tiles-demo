package com.bm.model.shared.conf;

import java.util.ArrayList;

import com.mmorts.core.shared.conf.ScriptClass;

public class TileChange extends ScriptClass {
    public static class Variant {
        public int p = 1, item = -1, effect = -1, result = -1;
    }

    public int totalP = 0;
    public ArrayList<Variant> variants = new ArrayList<Variant>();
}
