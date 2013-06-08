package com.bm.model.server.conf;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.*;
import java.net.URL;

import org.apache.log4j.Logger;

import com.bm.model.shared.conf.TileConf;
import com.bm.model.shared.conf.TileConfJava2Js;
import com.mmorts.core.shared.conf.ScriptContext;

public class ScriptRunnerGroovy {
    public static Logger logger = Logger.getLogger(ScriptRunnerGroovy.class);

    public TileConf readTileConf(String fileName) {
        TileConf conf = new TileConf();
        ScriptContext context = new ScriptContext();
        conf.setContext(context);

        Binding binding = new Binding();
        binding.setVariable("tileConf", new TileConfJava2Js(conf));
        GroovyShell shell = new GroovyShell(binding);

        InputStreamReader fr = null;
        InputStream stream = null;
        try {
            if (!fileName.contains("://")) {
                stream = new FileInputStream(fileName);
            } else
                stream = new URL(fileName).openStream();
            fr = new InputStreamReader(stream);

            context.utils = new ScriptUtilsGroovy(context);
            Script script = shell.parse(fr);
            script.run();

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
        }
        return conf;
    }
}
