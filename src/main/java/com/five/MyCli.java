package com.five;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MyCli {
    private final Options options;
    private final CommandLineParser parser;
    private CommandLine line;
    private final Map<Option, Consumer<Object[]>> functionMap;

    private MyCli() {
        options = new Options();
        parser = new DefaultParser();
        functionMap = new HashMap<>();
    }

    private static final MyCli instance = new MyCli();

    public static MyCli getInstance() {
        return instance;
    }

    void addOption(Option option, Consumer<Object[]> callback) {
        options.addOption(option);
        functionMap.put(option, callback);
    }

    void parseCommandLine(String[] args) throws ParseException {
        line = parser.parse(options, args);
        functionMap.forEach((opt, callback) -> {
            if (line.hasOption(opt.getOpt())) {
                String[] values = line.getOptionValues(opt);
                callback.accept(values);
            }
        });
    }

}
