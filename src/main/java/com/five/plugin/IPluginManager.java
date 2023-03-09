package com.five.plugin;

import java.io.File;
import java.util.Map;

public interface IPluginManager {
    Map<String, IPlugin> getPlugins();
    File getPluginsFolder();
    File resolvePluginConfig(String name);
}
