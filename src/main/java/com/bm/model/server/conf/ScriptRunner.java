package com.bm.model.server.conf;

import java.io.*;
import java.net.URL;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;

import com.bm.model.shared.conf.TileConf;
import com.bm.model.shared.conf.TileConfJava2Js;
import com.mmorts.core.shared.conf.MyScriptContext;

public class ScriptRunner {
    public static Logger logger = Logger.getLogger(ScriptRunner.class);

    public TileConf readTileConf(String engine, String fileName) {
        TileConf conf = new TileConf();
        MyScriptContext context = new MyScriptContext();
        conf.setContext(context);

        ScriptEngineManager m = new ScriptEngineManager();
        ScriptEngine rubyEngine = m.getEngineByName(engine);
        ScriptContext scContext = rubyEngine.getContext();
        Bindings scope = scContext.getBindings(ScriptContext.ENGINE_SCOPE);
        scope.put("tileConf", new TileConfJava2Js(conf));

        InputStreamReader fr = null;
        InputStream stream = null;
        try {
            if (!fileName.contains("://")) {
                stream = new FileInputStream(fileName);
            } else
                stream = new URL(fileName).openStream();
            fr = new InputStreamReader(stream);
            context.utils = new ScriptUtilsImpl(context);
            rubyEngine.eval(fr, scope);

            conf.afterProcess();
            context.checkDefines();
            logger.info("Load configuration from '" + fileName + "' tiles = " + conf.tiles.size());
            for (com.mmorts.core.shared.conf.MyScriptContext.Error err : context.errors) {
                logger.warn(err.msg);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (javax.script.ScriptException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return conf;
    }
}
