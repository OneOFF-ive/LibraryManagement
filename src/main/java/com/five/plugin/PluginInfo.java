package com.five.plugin;

public class PluginInfo {
    String name;
    String mainClass;
    String configFileName;

    public PluginInfo(String name, String mainClass, String configFileName) {
        this.name = name;
        this.mainClass = mainClass;
        this.configFileName = configFileName;
    }
}
