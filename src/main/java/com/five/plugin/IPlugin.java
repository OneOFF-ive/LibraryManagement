package com.five.plugin;

import com.five.Application;

public interface IPlugin {

    void apply(Application application);
    void close();
}
