package com.bm.model.shared.conf;

import com.mmorts.core.shared.conf.ScriptClassTable;
import com.mmorts.core.shared.conf.ScriptContext;
import com.mmorts.core.shared.conf.ScriptUtils;

public class TileGroupTable extends ScriptClassTable<TileGroup> {
    TileConf conf;

    public TileGroupTable(TileConf conf) {
        this.conf = conf;
    }

    @Override
    public TileGroup create() {
        return new TileGroup();
    }

    // TODO: больно уж группа напоминает прототип. Можно будет это использовать
    public Object newGroup(String name, Object desc) {
        TileGroup group = create();
        ScriptContext context = conf.context;
        group.name = name;
        ScriptUtils utils = context.utils;
        int image = -1, background = -1, type = -1, onDamage = -1, onAtomic = -1, onBomb = -1, onPremiumDamage = -1, subground = -1;
        if (utils.getField(desc, "image")) {
            image = utils.getInt();
        }
        if (utils.getField(desc, "subground")) {
            subground = utils.getInt();
        }
        if (utils.getField(desc, "type")) {
            type = TileConf.tileTypeId(utils.getString());
        }
        if (utils.getField(desc, "oriented")) group.oriented = true;
        if (utils.getField(desc, "background")) background = utils.getInt();
        if (utils.getField(desc, "onDamage")) onDamage = conf.unwrapChangeOrTile(utils.getObject());
        if (utils.getField(desc, "onAtomic")) onAtomic = conf.unwrapChangeOrTile(utils.getObject());
        if (utils.getField(desc, "onBomb")) onBomb = conf.unwrapChangeOrTile(utils.getObject());
        if (utils.getField(desc, "onPremiumDamage")) onPremiumDamage = conf.unwrapChangeOrTile(utils.getObject());
        if (!utils.getField(desc, "subTiles")) {
            context.addError("TileGroup must have subTiles");
        }
        Object[] tiles = utils.getArray();
        boolean first = true;
        Tile prevTile = null;
        for (Object tileDesc : tiles) {
            Tile tile = conf.tiles.byId(conf.unwrapTile(tileDesc));
            if (tile == null) context.addError("Can't add this tile to TileGroup");
            else {
                if (!tile.defined) context.addError("Tile '" + tile.name + "' is not defined yet");
                else {
                    if (tile.group != -1) context.addError("Tile '" + tile.name + "' already has a group");
                    else {
                        group.tiles.add(tile.id);
                    }
                }
            }
            // type
            if (type != -1) {
                if (tile.type != -1 && tile.type != type) {
                    context.addError("Types of group '" + group.name + "' and tile '" + tile.name + "' are in conflict");
                } else
                    tile.type = type;
            }
            // image
            if (tile.image != -1 && !tile.useOffset) {
                image = tile.image;
            } else {
                if (image != -1) {
                    if (tile.image == -1) tile.image = first ? 0 : 1;
                    image += tile.image;
                    tile.image = image;
                }
            }
            if (tile.subground == -1) tile.subground = subground;
            if (tile.background == -1) tile.background = background;
            first = false;
            // TODO: проверять oriented у группы и direction у подтайла
            if (tile.onDamage == -1) tile.onDamage = onDamage;
            if (tile.onAtomic == -1) tile.onAtomic = onAtomic;
            if (tile.onBomb == -1) tile.onBomb = onBomb;
            if (tile.onPremiumDamage == -1) tile.onPremiumDamage = onPremiumDamage;
            // а это специально для next()
            if (prevTile != null) {
                prevTile.next = tile.id;
            }
            prevTile = tile;
        }
        newInstance(context, group);
        for (int i = 0; i < group.tiles.size(); i++) {
            Tile tile = conf.tiles.byId(group.tiles.get(i));
            tile.group = group.id;
            tile.indexInGroup = i;
        }
        return utils.wrap(group.id, className);
    }
}
