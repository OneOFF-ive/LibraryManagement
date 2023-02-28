package com.five;

import com.OneFive.MyCli;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import org.apache.commons.cli.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Entry {
    public static void main(String[] args) throws URISyntaxException {
        var pluginsFolder = getOrCreatePluginsFolder();
        System.out.println("Plugin folder is " + pluginsFolder);
        var plugins = loadPlugins(pluginsFolder.listFiles());
        MyCli myCli = MyCli.getInstance();
        JsonHandle<Book> jsonHandle = new JsonHandle<>("library.json", new TypeReference<List<Book>>() {
        });

        try {
            jsonHandle.readData();

            MyCliHandle myCliHandle = new MyCliHandle(myCli, jsonHandle.getDataList());
            myCliHandle.initMyCli();
            for (var plugin : plugins) {
                plugin.addOption(myCliHandle);
            }
            if (args.length == 0) {
                args = new String[]{"-e"};
            }
            myCliHandle.parseAllOptions(args);

            jsonHandle.saveData();
        } catch (IOException |
                 ParseException e) {
            System.out.println(e.getMessage());
        }

    }

    static List<AddOptionAble> loadPlugins(File[] files) {
        var gson = new Gson();
        var list = new ArrayList<AddOptionAble>();
        if (files == null) return list;
        for (var pluginFile : files) {
            System.out.println("Loading plugin " + pluginFile);
            try {
                var clzLoader = URLClassLoader.newInstance(new URL[]{
                        pluginFile.toURI().toURL()
                });
                var pluginInfoStream = clzLoader.getResourceAsStream("plugin.json");
                Objects.requireNonNull(pluginInfoStream);
                var pluginInfoContent = readAsString(pluginInfoStream);
                var info = gson.fromJson(pluginInfoContent, PluginInfo.class);
                Class<?> clazz = clzLoader.loadClass(info.mainClass);
                var plugin = (AddOptionAble) clazz.getDeclaredConstructor().newInstance();
                list.add(plugin);
                System.out.println("Loaded plugin[" + info.name + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    static String readAsString(InputStream stream) throws IOException {
        char[] buffer = new char[1024];
        StringBuilder out = new StringBuilder();
        var in = new InputStreamReader(stream, StandardCharsets.UTF_8);
        for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
            out.append(buffer, 0, numRead);
        }
        return out.toString();
    }

    static File getOrCreatePluginsFolder() throws URISyntaxException {
        var domain = Entry.class.getProtectionDomain();
        var selfPath = new File(domain.getCodeSource().getLocation().toURI().getPath());
        var pluginFolder = new File(selfPath.getParent(), "plugins");
        pluginFolder.mkdirs();
        return pluginFolder;
    }
}
