package com.five;

import com.OneFive.MyCli;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import org.apache.commons.cli.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.*;


public class Entry {
    public static void main(String[] args) throws URISyntaxException {

        var pluginsManager = new PluginsManager();
        pluginsManager.loadPlugins(pluginsManager.getOrCreatePluginsFolder().listFiles());
        var plugins = pluginsManager.getPlugins();

        MyCli myCli = MyCli.getInstance();
        JsonHandle<Book> jsonHandle = new JsonHandle<>("library.json", new TypeReference<List<Book>>() {
        });

        try {
            jsonHandle.readData();

            MyCliHandle myCliHandle = new MyCliHandle(myCli, jsonHandle.getDataList());
            myCliHandle.initMyCli();

            for (var plugin : plugins) {
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
