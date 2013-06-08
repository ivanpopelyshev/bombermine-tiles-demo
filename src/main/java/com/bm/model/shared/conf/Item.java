package com.bm.model.shared.conf;

import java.util.ArrayList;

import com.mmorts.core.shared.conf.ScriptClass;

public class Item extends ScriptClass {
    public boolean all = false;
    public int image = -1, slot = -1, effect = -1;

    public static class Variant {
        public int p = 1, slot = -1, count = -1, effect = -1;
    }

    public int totalP = 0;
    public ArrayList<Variant> variants = new ArrayList<Variant>();
}
