package com.five;

import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.security.ProtectionDomain;
import java.util.*;

public class PluginsManager {
    private final Map<String, PluginService> plugins;
    private final File pluginsFolder;

    public Map<String, PluginService> getPlugins() {
        return plugins;
    }

    public List<PluginService> getPluginList() {
        return new ArrayList<PluginService>(plugins.values());
    }

    public File getPluginsFolder() {
        return pluginsFolder;
    }

    public PluginsManager() throws URISyntaxException {
        plugins = new HashMap<>();
        pluginsFolder = getOrCreatePluginsFolder();
        System.out.println("Plugin folder is " + pluginsFolder);
    }

    public void loadPlugins(File[] files) {

        Gson gson = new Gson();
        if (files == null) return ;
        for (var pluginFile : files) {
            System.out.println("Loading plugin " + pluginFile);
            try {
                var clzLoader = URLClassLoader.newInstance(new URL[]{
                        pluginFile.toURI().toURL()
                });

                InputStream inputStream = clzLoader.getResourceAsStream("pluginInfo.json");
                String pluginInfoContent = new String(Objects.requireNonNull(inputStream).readAllBytes(), StandardCharsets.UTF_8);

                var info = gson.fromJson(pluginInfoContent, PluginInfo.class);

                Class<?> clazz = clzLoader.loadClass(info.mainClass);
                var plugin = (PluginService) clazz.getDeclaredConstructor().newInstance();
                plugins.put(info.name, plugin);
                System.out.println("Loaded plugin[" + info.name + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    File getOrCreatePluginsFolder() throws URISyntaxException {
        ProtectionDomain domain = Entry.class.getProtectionDomain();
        File selfPath = new File(domain.getCodeSource().getLocation().toURI().getPath());
        File pluginFolder = new File(selfPath.getParent(), "plugins");
        pluginFolder.mkdirs();
        return pluginFolder;
    }
}

