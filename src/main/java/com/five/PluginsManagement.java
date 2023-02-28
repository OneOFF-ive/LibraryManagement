package com.five;

class PluginsManagement {
}

class PluginInfo {
    String name;
    String jar;
    String className;

    public PluginInfo(String name, String jar, String className) {
        this.name = name;
        this.jar = jar;
        this.className = className;
    }

    public PluginInfo() {
    }
}