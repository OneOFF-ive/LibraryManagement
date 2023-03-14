package com.five.logger;

public class Logger {
    static public void warn(String content) {
        System.out.println("warn: " + content);
    }

    static public void info(String content) {
        System.out.println("info: " + content);
    }

    static public void error(String content) {
        System.out.println("error: " + content);
    }
}
