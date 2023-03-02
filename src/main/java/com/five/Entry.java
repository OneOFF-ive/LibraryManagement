package com.five;

import com.five.plugin.PluginsManager;
import org.apache.commons.cli.*;

import java.net.URISyntaxException;


public class Entry {
    public static void main(String[] args) throws URISyntaxException {

        var pluginsManager = new PluginsManager();
        pluginsManager.loadPlugins(pluginsManager.getOrCreatePluginsFolder().listFiles());
        var pluginList = pluginsManager.getPluginList();

        Application application = new Application();
        try {
            application.initMyCli();

            for (var plugin : pluginList) {
                plugin.server(application);
            }

            if (args.length == 0) {
                args = new String[]{"-e"};
            }

            application.run(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
