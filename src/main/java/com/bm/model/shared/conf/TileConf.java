package com.bm.model.shared.conf;

import java.util.HashMap;

import com.mmorts.core.shared.conf.ScriptClass;
import com.mmorts.core.shared.conf.ScriptClassTable;
import com.mmorts.core.shared.conf.ScriptContext;
import com.mmorts.core.shared.conf.ScriptUtils;

public class TileConf {
    public static final int MAX_TILES = 256;

    public static final int BLOCK_FLOOR = 0;
    public static final int BLOCK_BUILDING = 1;
    public static final int BLOCK_ARROW = 2;
    public static final int BLOCK_HIDEOUT = 3;
    public static final int BLOCK_ABYSS = 4;
    public static final int BLOCK_TUNNEL = 5;
    public static final int BLOCK_SOLID = 6;
    public static final int BLOCK_BOX = 7;

    public static final int SLOT_STAT = 0;
    public static final int SLOT_STACK = 1;
    public static final int SLOT_HIDDEN = 2;

    public static final int BLOCK_EFFECT_SPAWN_BOMBS = 1;
    public static final int BLOCK_EFFECT_FORCE_REGEN = 2;
    public static final int BLOCK_EFFECT_DESTROY_BLOCK = 3;
    public static final int BLOCK_EFFECT_SPAWN_BOMBS_NY = 4;

    public static final int ITEM_EFFECT_CHANGE_COUNT_TO_BOMBS_PLUS_TWO = 5;
    public static final int ITEM_EFFECT_TELEPORT_FROM_STRUCTURE = 6;
    public static final int ITEM_EFFECT_RANDOM_DESEASE = 7;
    public static final int ITEM_EFFECT_XOR = 8;
    public static final int ITEM_EFFECT_BALL = 9;
    public static final int ITEM_EFFECT_SANTA_CANT_TAKE = 10;

    public static final String[] BLOCK_NAMES = new String[] { "floor", "building", "arrow", "hideout", "abyss",
            "tunnel", "solid", "box" };
    public static final String[] SLOT_NAMES = new String[] { "stat", "stack", "hidden" };

    public static final int FUN_NEXT = -2;

    public static int tileTypeId(String type) {
        for (int i = 0; i < BLOCK_NAMES.length; i++)
            if (BLOCK_NAMES[i].equals(type)) return i;
        return -1;
    }

    public static int slotTypeId(String type) {
        for (int i = 0; i < SLOT_NAMES.length; i++)
            if (SLOT_NAMES[i].equals(type)) return i;
        return -1;
    }

    public TileTable tiles = new TileTable(this);
    public TileGroupTable groups = new TileGroupTable(this);
    public TileChangeTable changes = new TileChangeTable(this);
    public SlotTable slots = new SlotTable(this);
    public ItemTable items = new ItemTable(this);
    public HashMap<String, String> props = new HashMap<String, String>();

    public TileConf() {
        tiles.setClassName("Tile").setShift(0);
        groups.setClassName("TileGroup").setShift(512);
        changes.setClassName("TileChange").setShift(1024);
        slots.setClassName("Slot").setShift(0);
        items.setClassName("Item").setShift(512);
        TileChange next = new TileChange();
        next.id = -2;
        next.name = "next";
        changes.byName.put("next", next);
    }

    public void setContext(ScriptContext context) {
        this.context = context;
    }

    public ScriptContext getContext() {
        return context;
    }

    protected ScriptContext context;
    protected ScriptUtils sf;

    public int defaultTile, defaultGroup;

    public int getTilesCount() {
        return tiles.size();
    }

    public int unwrapChangeOrTile(Object wrapped) {
        String type = context.utils.getType(wrapped);
        if (!type.equals(tiles.className) && !type.equals(changes.className)) {
            context.addError("expected Tile or TileChange");
            return -1;
        }
        return context.utils.unwrapInt(wrapped);
    }

    public int unwrapSlotOrItem(Object wrapped) {
        String type = context.utils.getType(wrapped);
        if (!type.equals(items.className) && !type.equals(slots.className)) {
            context.addError("expected Slot or Item");
            return -1;
        }
        return context.utils.unwrapInt(wrapped);
    }

    public <T extends ScriptClass> int unwrap(Object wrapped, ScriptClassTable<T> table) {
        String type = context.utils.getType(wrapped);
        if (!type.equals(table.className)) {
            context.addError("expected " + table.className);
            return -1;
        }
        return context.utils.unwrapInt(wrapped);
    }

    public int unwrapTile(Object wrapped) {
        return unwrap(wrapped, tiles);
    }

    public int unwrapSlot(Object wrapped) {
        return unwrap(wrapped, slots);
    }

    public int unwrapItem(Object wrapped) {
        return unwrap(wrapped, items);
    }

    public void afterProcess() {
        defaultGroup = tiles.byId(defaultTile).group;
        int defaultImg = tiles.byId(defaultTile).image;
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.byId(i);
            if (tile.background == -1) tile.background = defaultImg;
        }
        tiles.afterProcess();
    }

    public int byName(String name) {
        Tile tile = tiles.byName.get(name);
        return tile != null ? tile.id : -1;
    }

    public int byName(String name, int index) {
        TileGroup group = groups.byName.get(name);
        if (group == null) return -1;
        return group.byIndex(index);
    }

    public int typeOfTile(int val) {
        return tiles.byId(val).type;
    }

    public int directionOfTile(int val) {
        return tiles.byId(val).direction;
    }

    public int tileGroup(int val) {
        return tiles.byId(val).group;
    }

    public int slotType(int val) {
        return slots.byId(val).type;
    }

    public String dumpString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tiles.dumpString());
        sb.append(groups.dumpString());
        sb.append(changes.dumpString());
        sb.append(items.dumpString());
        sb.append(slots.dumpString());
        return sb.toString();
    }
}
