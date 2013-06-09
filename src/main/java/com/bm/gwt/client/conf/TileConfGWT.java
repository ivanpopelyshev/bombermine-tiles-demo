package com.bm.gwt.client.conf;

import com.bm.model.shared.conf.TileConf;
import com.bm.model.shared.conf.TileConfJava2Js;
import com.google.gwt.core.client.JavaScriptObject;
import com.mmorts.core.shared.conf.MyScriptContext;

public class TileConfGWT extends JavaScriptObject {
    protected TileConfGWT() {
    }

    public static native TileConfGWT create(TileConfJava2Js jj) /*-{
        return {
            getTile: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::getTile(Ljava/lang/String;)).bind(jj),
            newTile: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::newTile(Ljava/lang/String;Ljava/lang/Object;)).bind(jj),
            getSlot: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::getSlot(Ljava/lang/String;)).bind(jj),
            newSlot: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::newSlot(Ljava/lang/String;Ljava/lang/Object;)).bind(jj),
            getItem: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::getItem(Ljava/lang/String;)).bind(jj),
            newItem: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::newItem(Ljava/lang/String;Ljava/lang/Object;)).bind(jj),
            getChange: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::getChange(Ljava/lang/String;)).bind(jj),
            newChange: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::newChange(Ljava/lang/String;Ljava/lang/Object;)).bind(jj), 
            newGroup: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::newGroup(Ljava/lang/String;Ljava/lang/Object;)).bind(jj),
            defaultTile: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::defaultTile(Ljava/lang/Object;)).bind(jj), 
            next: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::next()).bind(jj),
            spawnBombs: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::spawnBombs()).bind(jj),
            forceRegen: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::forceRegen()).bind(jj),
            destroyBlock: $entry(jj.@com.bm.model.shared.conf.TileConfJava2Js::destroyBlock()).bind(jj)
        }
    }-*/;

    public final native void loadTiles() /*-{
        $wnd.tileConf = this;
        $wnd.loadTiles();
    }-*/;

    public static TileConf buildTileConf() {
        TileConf conf = new TileConf();
        MyScriptContext context = new MyScriptContext();
        conf.setContext(context);
        context.utils = ScriptUtilsGWT.create();
        TileConfJava2Js jj = new TileConfJava2Js(conf);
        TileConfGWT.create(jj).loadTiles();
        return conf;
    }
}
