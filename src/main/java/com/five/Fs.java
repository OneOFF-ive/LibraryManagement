package com.five;

import java.io.File;
import java.net.URISyntaxException;
public class Fs {
    public static File getRootFolder() throws URISyntaxException {
        var domain = Entry.class.getProtectionDomain();
        var selfPath = new File(domain.getCodeSource().getLocation().toURI().getPath());
        return selfPath.getParentFile();
    }
    public static File getOrCreatePluginsFolder() throws URISyntaxException {
        var root = getRootFolder();
        var dir = new File(root, "plugins");
        dir.mkdirs();
        return dir;
    }
    public static File getOrCreateConfigFolder() throws URISyntaxException {
        var root = getRootFolder();
        var dir = new File(root, "config");
        dir.mkdirs();
        return dir;
    }
}
