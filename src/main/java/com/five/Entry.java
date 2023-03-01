package com.five;

import com.OneFive.MyCli;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.cli.*;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;


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

//        MyCli myCli = MyCli.getInstance();
//
//        JsonHandle<Book> jsonHandle = new JsonHandle<>("library.json", new TypeReference<List<Book>>() {
//        });
//
//        try {
//            jsonHandle.readFile();
//
//            MyCliHandle myCliHandle = new MyCliHandle(myCli, jsonHandle.getDataList());
//            myCliHandle.initMyCli();
//
//            for (var plugin : pluginList) {
//                plugin.server(myCliHandle);
//            }
//
//            if (args.length == 0) {
//                args = new String[]{"-e"};
//            }
//            myCliHandle.parseAllOptions(args);
//
//            jsonHandle.writeFile();
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }

    }


}
