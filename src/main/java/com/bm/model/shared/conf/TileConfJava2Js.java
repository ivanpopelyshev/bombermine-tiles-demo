package com.bm.model.shared.conf;

public class TileConfJava2Js {
    TileConf conf;

    public TileConfJava2Js(TileConf conf) {
        this.conf = conf;
    }

    public Object getTile(String name) {
        return conf.tiles.getAndWrap(conf.context, name);
    }

    public Object newTile(String name, Object desc) {
        return conf.tiles.newTile(name, desc);
    }

    public Object getChange(String name) {
        return conf.changes.getAndWrap(conf.context, name);
    }

    public Object newChange(String name, Object desc) {
        return conf.changes.newTileChange(name, desc);
    }

    public Object newGroup(String name, Object desc) {
        return conf.groups.newGroup(name, desc);
    }
    
    public Object newItem(String name, Object desc) {
        return conf.items.newItem(name, desc);
    }

    public Object getItem(String name) {
        return conf.items.getAndWrap(conf.context, name);
    }

    public Object newSlot(String name, Object desc) {
        return conf.slots.newSlot(name, desc);
    }

    public Object getSlot(String name) {
        return conf.slots.getAndWrap(conf.context, name);
    }

    public void setGlobal(String key, String value) {
        conf.props.put(key, value);
    }

    public void defaultTile(Object desc) {
        conf.defaultTile = conf.unwrapTile(desc);
    }
}
