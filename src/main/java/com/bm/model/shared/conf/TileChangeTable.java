package com.bm.model.shared.conf;

import com.bm.model.shared.conf.TileChange.Variant;
import com.mmorts.core.shared.conf.ScriptClassTable;
import com.mmorts.core.shared.conf.ScriptContext;
import com.mmorts.core.shared.conf.ScriptUtils;

public class TileChangeTable extends ScriptClassTable<TileChange> {
    TileConf conf;

    public TileChangeTable(TileConf conf) {
        this.conf = conf;
    }

    @Override
    public TileChange create() {
        return new TileChange();
    }

    Variant parseVariant(ScriptUtils utils, Object desc) {
        Variant main = new Variant();
        if (utils.getField(desc, "p")) main.p = utils.getInt();
        if (utils.getField(desc, "item")) main.item = conf.unwrapSlotOrItem(utils.getObject());
        if (utils.getField(desc, "result")) main.result = conf.unwrapChangeOrTile(utils.getObject());
        if (utils.getField(desc, "effect")) main.effect = utils.getInt();
        return main;
    }

    public Object newTileChange(String name, Object desc) {
        TileChange tc = create();
        ScriptContext context = conf.context;
        tc.name = name;
        ScriptUtils utils = context.utils;
        Variant main = parseVariant(utils, desc);
        if (utils.getField(desc, "variants")) {
            Object[] vars = utils.getArray();
            for (Object varDesc : vars) {
                Variant v = parseVariant(utils, varDesc);
                if (v.p == 0) {
                    context.addError("Variant must have non-zero 'p'");
                }
                if (v.effect == -1) v.effect = main.effect;
                if (v.item == -1) v.item = main.item;
                if (v.result == -1) v.result = main.result;
                tc.totalP += v.p;
                tc.variants.add(v);
            }
        } else {
            tc.totalP = 1;
            main.p = 1;
            tc.variants.add(main);
        }
        return utils.wrap(newInstance(context, tc).id, className);
    }
};