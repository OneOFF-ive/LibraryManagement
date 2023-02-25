package com.five;

import com.OneFive.MyCli;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;
import java.io.IOException;
import java.text.FieldPosition;
import java.util.*;

public class MyCliHandle {
    private MyCli myCli;
    private List<Book> books;
    private ObjectMapper objectMapper;
    public static final String filePath = "booksData.json";

    public MyCliHandle(MyCli myCli) {
        this.myCli = myCli;
        books = new ArrayList<>();
        objectMapper = new ObjectMapper();
    }

    public MyCli getMyCli() {
        return myCli;
    }

    public void setMyCli(MyCli myCli) {
        this.myCli = myCli;
    }

    void start() {
        File file = new File(filePath);
        try {
            if (file.exists() || file.createNewFile()) {
                books = objectMapper.readValue(file, new TypeReference<List<Book>>() {
                });
                System.out.println(books.size());
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    void close() throws IOException {
        System.out.println("end");
        objectMapper.writeValue(new File(filePath), books);
    }

    void initMyCli() {
        // -a/addBook <书名> 添加一本书
        myCli.addOption(defineAddOpt(), (values) -> {
            System.out.printf("add a book %s%n", Arrays.toString(values));
            Book book = new Book(values[0].toString(), values[1].toString(), values[2].toString(), Integer.parseInt(values[3].toString()));
            books.add(book);
        });

        // -d/delBook <书名> 删除一本书
        myCli.addOption(defineDelOpt(), (values) -> {
            System.out.printf("del a book %s%n", Arrays.toString(values));
        });

        // -s/seekBook <书名> 查找一本书
        myCli.addOption(defineSeekOpt(), (values) -> {
            System.out.printf("seek a book %s%n", Arrays.toString(values));
        });

        // -rt/returnBook <书名> 归还一本书
        myCli.addOption(defineReturnOpt(), (values) -> {
            System.out.printf("return a book %s%n", Arrays.toString(values));
        });

        // -r/rentBook <书名> 租借一本书
        myCli.addOption(defineRentOpt(), (values) -> {
            System.out.printf("rent a book %s%n", Arrays.toString(values));
        });

        // -h/help 打印指令信息
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
