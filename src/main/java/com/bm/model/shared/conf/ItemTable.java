package com.bm.model.shared.conf;

import com.bm.model.shared.conf.Item.Variant;
import com.mmorts.core.shared.conf.ScriptClassTable;
import com.mmorts.core.shared.conf.MyScriptContext;
import com.mmorts.core.shared.conf.ScriptUtils;

public class ItemTable extends ScriptClassTable<Item> {
    TileConf conf;

    public ItemTable(TileConf conf) {
        this.conf = conf;
    }

    @Override
    public Item create() {
        return new Item();
    }

    Variant parseVariant(ScriptUtils utils, Object desc) {
        Variant main = new Variant();
        if (utils.getField(desc, "p")) main.p = utils.getInt();
        if (utils.getField(desc, "slot")) main.slot = conf.unwrapSlotOrItem(utils.getObject());
        if (utils.getField(desc, "count")) main.count = utils.getInt();
        if (utils.getField(desc, "effect")) main.effect = utils.getInt();
        return main;
    }

    public Object newItem(String name, Object desc) {
        Item item = create();
        MyScriptContext context = conf.context;
        item.name = name;
        ScriptUtils utils = context.utils;
        if (utils.getField(desc, "all")) item.all = true;
        if (utils.getField(desc, "image")) item.image = utils.getInt();
        Variant main = parseVariant(utils, desc);
        item.slot = main.slot;
        if (main.count == -1) main.count = 1;
        item.effect = main.effect;
        if (utils.getField(desc, "variants")) {
            Object[] vars = utils.getArray();
            for (Object varDesc : vars) {
                Variant v = parseVariant(utils, varDesc);
                if (v.slot == -1) v.slot = main.slot;
                if (v.count == -1) v.count = main.count;
                if (v.effect == -1) v.effect = main.effect;
                item.totalP += v.p;
                item.variants.add(v);
            }
        } else {
            item.totalP = 1;
            main.p = 1;
            item.variants.add(main);
        }
        return utils.wrap(newInstance(context, item).id, className);
    }
}
