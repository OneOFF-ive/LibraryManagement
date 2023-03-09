package com.five.plugin;

import com.five.Fs;
import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PluginManager implements IPluginManager {
    private final Map<String, IPlugin> plugins;
    private final File pluginsFolder;
    private final File configFolder;

    @Override
    public Map<String, IPlugin> getPlugins() {
        return plugins;
    }

    public List<IPlugin> getPluginList() {
        return new ArrayList<>(plugins.values());
    }

    @Override
    public File getPluginsFolder() {
        return pluginsFolder;
    }

    @Override
    public File resolvePluginConfig(String name) {
        return new File(configFolder, name);
    }

    public PluginManager() throws URISyntaxException {
        plugins = new HashMap<>();
        pluginsFolder = Fs.getOrCreatePluginsFolder();
        configFolder = Fs.getOrCreateConfigFolder();
        System.out.println("Plugin folder is " + pluginsFolder);
    }

    public void loadPlugins(File[] files) {

        Gson gson = new Gson();
        if (files == null) return;
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
                IPlugin plugin = null;
                try {
                    var ctor = clazz.getDeclaredConstructor(PluginContext.class);
                    var config = resolvePluginConfig(info.configFileName);
                    var ctx = new PluginContext(info, config, this);
                    plugin = (IPlugin) ctor.newInstance(ctx);
                } catch (NoSuchMethodException e) {
                    try {
                        var ctor = clazz.getDeclaredConstructor();
                        plugin = (IPlugin) ctor.newInstance();
                    } catch (NoSuchMethodException ignored) {}
                }
                if (plugin != null) {
                    plugins.put(info.name, plugin);
                    System.out.println("Loaded plugin [" + info.name + "]");
                } else {
                    System.out.println("Plugin [" + info.name + "] constructor failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

