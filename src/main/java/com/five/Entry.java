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

        MyCli myCli = MyCli.getInstance();
        JsonHandle<Book> jsonHandle = new JsonHandle<>("library.json", new TypeReference<List<Book>>() {
        });

        try {
            jsonHandle.readData();

            MyCliHandle myCliHandle = new MyCliHandle(myCli, jsonHandle.getDataList());
            myCliHandle.initMyCli();

            for (var plugin : pluginList) {
                plugin.server(myCliHandle);
            }

            if (args.length == 0) {
                args = new String[]{"-e"};
            }
            myCliHandle.parseAllOptions(args);

            jsonHandle.saveData();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }


}
