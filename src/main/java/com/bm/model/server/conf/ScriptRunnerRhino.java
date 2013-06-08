package com.bm.model.server.conf;

import java.io.*;
import java.net.URL;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.bm.model.shared.conf.TileConf;
import com.bm.model.shared.conf.TileConfJava2Js;
import com.mmorts.core.shared.conf.ScriptContext;

public class ScriptRunnerRhino {
    public static Logger logger = Logger.getLogger(ScriptRunnerRhino.class);

    public TileConf readTileConf(String fileName) {
        TileConf conf = new TileConf();
        ScriptContext context = new ScriptContext();
        conf.setContext(context);

        Context cx = Context.enter();
        InputStreamReader fr = null;
        InputStream stream = null;
        try {
            if (!fileName.contains("://")) {
                stream = new FileInputStream(fileName);
            } else
                stream = new URL(fileName).openStream();
            fr = new InputStreamReader(stream);
            Scriptable scope = cx.initStandardObjects();
            context.utils = new ScriptUtilsRhino(cx, scope, context);

            // Add a global variable "out" that is a JavaScript reflection
            // of System.out
            Object jsConf = Context.javaToJS(new TileConfJava2Js(conf), scope);
            ScriptableObject.putProperty(scope, "__conf", jsConf);
            ScriptableObject.putProperty(scope, "window", null);

            cx.evaluateReader(scope, fr, "tilesDef.js", 1, null);
            cx.evaluateString(scope, "loadTiles(__conf)", "tiles.js", 1, null);
            conf.afterProcess();
            context.checkDefines();
            logger.info("Load configuration from '" + fileName + "' tiles = " + conf.tiles.size());
            for (com.mmorts.core.shared.conf.ScriptContext.Error err : context.errors) {
                logger.warn(err.msg);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Context.exit();
        }
        return conf;
    }
}
