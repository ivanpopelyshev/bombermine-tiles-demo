package com.bm.model.shared.conf;

import com.mmorts.core.shared.conf.ScriptClass;

public class Tile extends ScriptClass {
    public int group = -1, type = -1, image = -1, background = -1, onDamage = -1, onBomb = -1, onPremiumDamage = -1,
            onAtomic = -1, subground = -1;
    public int direction = -1, next = -1;
    public int indexInGroup = 0;
    // only while building configuration
    public boolean useOffset = false;
}
