package com.five;

import com.five.plugin.PluginsManager;
import com.five.utils.Timer;
import org.apache.commons.cli.*;

import java.net.URISyntaxException;


public class Entry {
    public static void main(String[] args) throws URISyntaxException {

        var pluginsManager = new PluginsManager();
        pluginsManager.loadPlugins(pluginsManager.getOrCreatePluginsFolder().listFiles());
        var plugins = pluginsManager.getPlugins();

        Application application = new Application();
        try {
            application.initMyCli();

            for (var entry : plugins.entrySet()) {
                var name = entry.getKey();
                var plugin = entry.getValue();
                var timer = new Timer();
                System.out.println("[" + name + "] starts initialization.");
                timer.start();
                plugin.server(application);
                timer.end();
                System.out.println("[" + name + "] initialized in " + timer.getPrettyOutput() + ".");
            }
            System.out.println("All plugins are initialized.");
            if (args.length == 0) {
                args = new String[]{"-e"};
            }

            application.run(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
