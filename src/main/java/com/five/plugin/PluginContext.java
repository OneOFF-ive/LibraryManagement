package com.five.plugin;

import java.io.File;

public class PluginContext {
    public final PluginInfo info;
    public final File config;
    public final IPluginManager manager;

    public PluginContext(PluginInfo info, File config, IPluginManager manager) {
        this.info = info;
        this.config = config;
        this.manager = manager;
    }
}
