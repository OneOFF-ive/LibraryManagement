package com.five;

import com.OneFive.MyCli;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.*;

public class MyCliHandle {
    private MyCli myCli;
    private final List<Book> dataList;


    public MyCliHandle(MyCli myCli, List<Book> dataList) {
        this.myCli = myCli;
        this.dataList = dataList;
    }

    public MyCli getMyCli() {
        return myCli;
    }

    public void setMyCli(MyCli myCli) {
        this.myCli = myCli;
    }

    public void parseAllOptions(String[] args) throws ParseException {
        myCli.parseAllOptions(args);
    }

    void initMyCli() {
        // -a/addBook <title> <isbn> <author> <amount>
        myCli.addOption(defineAddOpt(), (values) -> {
            System.out.printf("add a book %s%n", Arrays.toString(values));
            Book book = new Book(values[0].toString(), values[1].toString(), values[2].toString(), Integer.parseInt(values[3].toString()));
            dataList.add(book);
        });

        // -d/delBook <isbn>
        myCli.addOption(defineDelOpt(), (values) -> {
            System.out.printf("del a book %s%n", Arrays.toString(values));
            var iter = dataList.iterator();
            while (iter.hasNext()) {
                var book = iter.next();
                if (book.getIsbn().equals(values[0].toString()));
                iter.remove();
            }
        });

        // -s/seekBook <title>
        myCli.addOption(defineSeekOpt(), (values) -> {
            System.out.printf("seek a book %s%n", Arrays.toString(values));
        });

        // -rt/returnBook <isbn>
        myCli.addOption(defineReturnOpt(), (values) -> {
            System.out.printf("return a book %s%n", Arrays.toString(values));
        });

        // -r/rentBook <isbn>
        myCli.addOption(defineRentOpt(), (values) -> {
            System.out.printf("rent a book %s%n", Arrays.toString(values));
        });

        // -h/help
        myCli.addOption(defineHelpOpt(), (values) -> {
            HelpFormatter formatter = new HelpFormatter();
            Options options = myCli.getOptions();
            formatter.printHelp("ant", options);
        });
    }

    public static Option defineAddOpt() {
        Option option = Option.builder("a")
                .longOpt("addBook")
                .hasArgs()
                .desc("add a book for library")
                .build();
        option.setArgs(4);
        option.setArgName("title> <isbn> <author> <amount");
        return option;
    }

    public static Option defineDelOpt() {
        return Option.builder("d")
                .longOpt("delBook")
                .argName("isbn>")
                .hasArg()
                .desc("del a book from library")
                .build();
    }

    public static Option defineSeekOpt() {
        return Option.builder("s")
                .longOpt("seekBook")
                .argName("title>")
                .hasArg()
                .desc("Seek a book from library")
                .build();
    }

    public static Option defineRentOpt() {
        return Option.builder("r")
                .longOpt("rentBook")
                .argName("isbn>")
                .hasArg()
                .desc("Rent a book from library")
                .build();
    }

    public static Option defineReturnOpt() {
        return Option.builder("rt")
                .longOpt("ReturnBook")
                .argName("isbn>")
                .hasArg()
                .desc("Return a book for library")
                .build();
    }

    public static Option defineHelpOpt() {
        return new Option("h", "help", false, "print help message");
    }
}
