package com.bm.model.shared.conf;

import com.mmorts.core.shared.conf.ScriptClassTable;
import com.mmorts.core.shared.conf.MyScriptContext;
import com.mmorts.core.shared.conf.ScriptUtils;

public class TileTable extends ScriptClassTable<Tile> {
    TileConf conf;

    public TileTable(TileConf conf) {
        this.conf = conf;
    }

    @Override
    public Tile create() {
        return new Tile();
    }

    public Object newTile(String name, Object desc) {
        Tile tile = create();
        MyScriptContext context = conf.context;
        tile.name = name;
        ScriptUtils utils = context.utils;
        if (utils.getField(desc, "onDamage")) tile.onDamage = conf.unwrapChangeOrTile(utils.getObject());
        if (utils.getField(desc, "onAtomic")) tile.onAtomic = conf.unwrapChangeOrTile(utils.getObject());
        if (utils.getField(desc, "onBomb")) tile.onBomb = conf.unwrapChangeOrTile(utils.getObject());
        if (utils.getField(desc, "onPremiumDamage")) tile.onPremiumDamage = conf.unwrapChangeOrTile(utils.getObject());
        if (utils.getField(desc, "direction")) tile.direction = utils.getInt();
        if (utils.getField(desc, "offset")) {
            tile.image = utils.getInt();
            tile.useOffset = true;
        }
        if (utils.getField(desc, "subground")) tile.subground = utils.getInt();
        if (utils.getField(desc, "image")) {
            tile.image = utils.getInt();
            if (tile.useOffset) {
                tile.useOffset = false;
                context.addError("Fields 'image' and 'offset' are in conflict");
            }
        }
        if (utils.getField(desc, "background")) {
            tile.background = utils.getInt();
        }
        if (utils.getField(desc, "type")) {
            tile.type = TileConf.tileTypeId(utils.getString());
        }
        return utils.wrap(newInstance(context, tile).id, className);
    }

    public void afterProcess() {
        for (Tile tile : byIndex) {
            if (tile.image == -1) tile.image = 0;
            if (tile.type == -1) tile.type = 0;
            if (tile.next == -1) tile.next = tile.id;
            // TODO: проверка на возможные indexOutOfBounds, вдруг хаки были
        }
    }
}
