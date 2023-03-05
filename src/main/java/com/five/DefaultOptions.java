package com.five;

import org.apache.commons.cli.Option;

public class DefaultOptions {
    public static Option AddOpt() {
        Option option = Option.builder("a")
                .longOpt("addBook")
                .hasArgs()
                .desc("add a book for library")
                .build();
        option.setArgs(4);
        option.setArgName("title> <isbn> <author> <amount");
        return option;
    }

    public static Option DelOpt() {
        return Option.builder("d")
                .longOpt("delBook")
                .argName("isbn [amount]")
                .hasArgs()
                .desc("del a book from library")
                .build();
    }

    public static Option SeekOpt() {
        return Option.builder("s")
                .longOpt("seekBook")
                .argName("title/isbn")
                .hasArg()
                .desc("Seek a book from library")
                .build();
    }

    public static Option RentOpt() {
        return Option.builder("r")
                .longOpt("rentBook")
                .argName("isbn")
                .hasArg()
                .desc("Rent a book from library")
                .build();
    }

    public static Option ReturnOpt() {
        return Option.builder("rt")
                .longOpt("ReturnBook")
                .argName("isbn")
                .hasArg()
                .desc("Return a book for library")
                .build();
    }

    public static Option HelpOpt() {
        return new Option("h", "help", false, "print help message");
    }

    public static Option EnterOpt() {
        return new Option("e", "enter", false, "enter interactive interface");
    }

    public static Option QuitOpt() {
        return new Option("q", "quit", false, "quit interactive interface");
    }
}
