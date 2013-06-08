package com.bm.model.server.conf;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bm.model.shared.conf.TileConf;

public class ScriptTest {
    @Before
    public void initLog4j() {
        Properties props = new Properties();
        props.put("log4j.rootLogger", "DEBUG, A1");
        props.put("log4j.appender.A1", "org.apache.log4j.ConsoleAppender");
        props.put("log4j.appender.A1.layout", "org.apache.log4j.PatternLayout");
        props.put("log4j.appender.A1.layout.ConversionPattern", "%d [%t] %-5p %c - %m%n");
        PropertyConfigurator.configure(props);
    }

    public void basicAssert(TileConf conf) {
        Assert.assertEquals(0, conf.getContext().errors.size());
        Assert.assertEquals(68, conf.tiles.size());
        Assert.assertEquals(13, conf.items.size());
        Assert.assertEquals(12, conf.groups.size());
        Assert.assertEquals(18, conf.changes.size());
        Assert.assertEquals(16, conf.slots.size());
    }

    @Test
    public void testRhino() {
        TileConf conf = new ScriptRunnerRhino().readTileConf("conf/tiles.js");
        basicAssert(conf);
    }

    @Test
    public void testGroovy() {
        TileConf conf = new ScriptRunnerGroovy().readTileConf("conf/tiles.groovy");
        basicAssert(conf);
        System.out.println(conf.dumpString());
    }
}
