package com.bm.model.shared.conf;

import java.util.ArrayList;

import com.mmorts.core.shared.conf.ScriptClass;

public class TileGroup extends ScriptClass {
    public ArrayList<Integer> tiles = new ArrayList<Integer>();
    public boolean oriented;

    public int byIndex(int index) {
        if (index < 0 || index >= tiles.size()) return -1;
        return tiles.get(index);
    }
}
