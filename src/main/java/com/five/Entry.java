package com.five;

import com.five.plugin.PluginManager;
import org.apache.commons.cli.*;

import java.net.URISyntaxException;


public class Entry {
    public static void main(String[] args) throws URISyntaxException {

        var pluginsManager = new PluginManager();
        pluginsManager.loadPlugins(pluginsManager.getPluginsFolder().listFiles());

        Application application = new Application();
        try {
            application.initMyCli();
            pluginsManager.initPlugins(application);
            if (args.length == 0) {
                args = new String[]{"-e"};
            }
            application.run(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
