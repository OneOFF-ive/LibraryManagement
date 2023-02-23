package com.five;

import org.apache.commons.cli.*;

import java.util.Arrays;


public class Entry {
    public static void main(String[] args) throws ParseException {
        MyCli myCli = MyCli.getInstance();

        Option addBookOpt = defineAddOpt();
        Option delBookOpt = defineDelOpt();
        Option seekBookOpt = defineSeekOpt();
        Option rentBookOpt = defineRentOpt();
        Option returnBookOpt = defineReturnOpt();
        Option helpOpt = defineHelpOpt();

        myCli.addOption(addBookOpt, (values)->{
            System.out.println(Arrays.toString(values));
        });

        myCli.parseCommandLine(args);
    }

    void registerFunction(Option[] options, MyCli myCli) {
        myCli.addOption(options[0], (values) -> {
            System.out.println(Arrays.toString(values));
        });
    }

    public static void defineOptions() {
        Option addBookOpt = defineAddOpt();
        Option delBookOpt = defineDelOpt();
        Option seekBookOpt = defineSeekOpt();
        Option rentBookOpt = defineRentOpt();
        Option returnBookOpt = defineReturnOpt();
        Option helpOpt = defineHelpOpt();
    }

    public static Option defineAddOpt() {
        return Option.builder("a")
                .longOpt("addBook")
                .argName("bookName")
                .hasArgs()
                .desc("add a book for library")
                .build();
    }

    public static Option defineDelOpt() {
         return Option.builder("d")
                .longOpt("delBook")
                .argName("bookName")
                .hasArg()
                .desc("del a book from library")
                .build();
    }

    public static Option defineSeekOpt() {
         return Option.builder("s")
                .longOpt("seekBook")
                .argName("bookName")
                .hasArg()
                .desc("Seek a book from library")
                .build();
    }

    public static Option defineRentOpt() {
         return Option.builder("r")
                .longOpt("rentBook")
                .argName("bookName")
                .hasArg()
                .desc("Rent a book from library")
                .build();
    }

    public static Option defineReturnOpt() {
        return Option.builder("rt")
                .longOpt("ReturnBook")
                .argName("bookName")
                .hasArg()
                .desc("Return a book for library")
                .build();
    }

    public static Option defineHelpOpt() {
        return new Option("h", "help", false, "print help message");
    }
}
