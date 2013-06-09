package com.bm.model.shared.conf;

import com.mmorts.core.shared.conf.ScriptClassTable;
import com.mmorts.core.shared.conf.MyScriptContext;
import com.mmorts.core.shared.conf.ScriptUtils;

public class SlotTable extends ScriptClassTable<Slot> {
    TileConf conf;

    public SlotTable(TileConf conf) {
        this.conf = conf;
    }

    @Override
    public Slot create() {
        return new Slot();
    }

    public Object newSlot(String name, Object desc) {
        Slot slot = create();
        MyScriptContext context = conf.context;
        slot.name = name;
        ScriptUtils utils = context.utils;
        if (utils.getField(desc, "image")) slot.image = utils.getInt();
        if (utils.getField(desc, "type")) slot.type = TileConf.slotTypeId(utils.getString());
        if (utils.getField(desc, "def")) slot.def = utils.getInt();
        if (utils.getField(desc, "max")) slot.max = utils.getInt();
        return utils.wrap(newInstance(context, slot).id, className);
    }
}
