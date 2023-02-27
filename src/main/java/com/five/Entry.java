package com.five;

import com.OneFive.MyCli;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;


public class Entry {
    public static void main(String[] args) {

        MyCli myCli = MyCli.getInstance();
        JsonHandle jsonHandle = new JsonHandle("library.json");

        try {
            jsonHandle.readData();

            MyCliHandle myCliHandle = new MyCliHandle(myCli, jsonHandle.getDataList());
            myCliHandle.initMyCli();
            if (args.length == 0) {
                args = new String[]{"-e"};
            }

            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {new URL("file:" + "D:\\code\\java_learn\\LibraryManagement\\plugins\\LibraryManagement_plugins-1.0.0.jar")});
            Class<?> clazz = urlClassLoader.loadClass("com.five.LibraryManagement_plugins.AddOptionPlugin");
            Object instance = clazz.getDeclaredConstructor().newInstance();
            AddOptionAble plugin = (AddOptionAble) instance;
            plugin.addOption(myCliHandle);

            myCliHandle.parseAllOptions(args);

            jsonHandle.saveData();
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

}
