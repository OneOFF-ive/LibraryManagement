package com.five;

import com.OneFive.MyCli;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.*;
import java.util.function.Consumer;

class MyCliHandle {
    private MyCli myCli;
    private final List<Book> dataList;
    private boolean shouldQuit;


    MyCliHandle(MyCli myCli, List<Book> dataList) {
        this.myCli = myCli;
        this.dataList = dataList;
        shouldQuit = false;
    }

    MyCli getMyCli() {
        return myCli;
    }

    void setMyCli(MyCli myCli) {
        this.myCli = myCli;
    }

    void parseAllOptions(String[] args) throws ParseException {
        myCli.parseAllOptions(args);
    }

    void initMyCli() {
        // -a/addBook <title> <isbn> <author> <amount>
        myCli.addOption(defineAddOpt(), (Object[] args) -> {
            System.out.printf("add a book %s%n", Arrays.toString(args));

            String isbn = args[1].toString();
            int amount = Integer.parseInt(args[3].toString());
            var iter = dataList.iterator();
            boolean isHasBook = false;
            while (iter.hasNext()) {
                Book book = iter.next();
                if (book.getIsbn().equals(isbn)) {
                    isHasBook = true;
                    book.setTotalAmount(book.getTotalAmount() + amount);
                    book.setCurrentAmount(book.getCurrentAmount() + amount);
                    break;
                }
            }
            if (!isHasBook) {
                Book book = new Book(args[0].toString(), args[1].toString(), args[2].toString(), amount, amount);
                dataList.add(book);
            }

        });

        // -d/delBook <isbn>
        myCli.addOption(defineDelOpt(), (Object[] args) -> {
            System.out.printf("del a book %s%n", Arrays.toString(args));

            int amount = (args.length == 2) ? Integer.parseInt(args[1].toString()) : 0;

            var iter = dataList.iterator();
            while (iter.hasNext()) {
                var book = iter.next();
                if (book.getIsbn().equals(args[0].toString())) {
                    if (amount == 0 && Objects.equals(book.getCurrentAmount(), book.getTotalAmount())) {
                        iter.remove();
                    } else if (book.getCurrentAmount() > amount) {
                        book.setTotalAmount(book.getTotalAmount() - amount);
                        book.setCurrentAmount(book.getCurrentAmount() - amount);
                    } else {
                        System.out.println("can't delete book");
                    }
                    break;
                }
            }
        });

        // -s/seekBook <title/isbn>
        myCli.addOption(defineSeekOpt(), (Object[] args) -> {
            System.out.printf("seek a book %s%n", Arrays.toString(args));

            String info = args[0].toString();
            for (var book : dataList) {
                if (book.getTitle().equals(info) || book.getIsbn().equals(info)) {
                    System.out.println(book);
                }
            }

        });

        // -rt/returnBook <isbn>
        myCli.addOption(defineReturnOpt(), (Object[] args) -> {
            System.out.printf("return a book %s%n", Arrays.toString(args));

            for (var book : dataList) {
                if (book.getIsbn().equals(args[0].toString()) && book.getCurrentAmount() < book.getTotalAmount()) {
                    book.setCurrentAmount(book.getCurrentAmount() + 1);
                    break;
                }
            }

        });

        // -r/rentBook <isbn>
        myCli.addOption(defineRentOpt(), (Object[] args) -> {
            System.out.printf("rent a book %s%n", Arrays.toString(args));

            for (var book : dataList) {
                if (book.getIsbn().equals(args[0].toString()) && book.getCurrentAmount() > 0) {
                    book.setCurrentAmount(book.getCurrentAmount() - 1);
                    break;
                }
            }

        });

        // -h/help
        myCli.addOption(defineHelpOpt(), (Object[] args) -> {
            HelpFormatter formatter = new HelpFormatter();
            Options options = myCli.getOptions();
            formatter.printHelp("ant", options);
        });

        myCli.addOption(defineEnterOpt(), (Object[] args) -> {
            Scanner scanner = new Scanner(System.in);

            while (!shouldQuit) {
                String input = scanner.nextLine();
                String[] newArgs = input.split(" ");
                try {
                    myCli.parseAllOptions(newArgs);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        myCli.addOption(defineQuitOpt(), (Object[] args) -> {
            shouldQuit = true;
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
                .argName("isbn [amount]")
                .hasArgs()
                .desc("del a book from library")
                .build();
    }

    public static Option defineSeekOpt() {
        return Option.builder("s")
                .longOpt("seekBook")
                .argName("title")
                .hasArg()
                .desc("Seek a book from library")
                .build();
    }

    public static Option defineRentOpt() {
        return Option.builder("r")
                .longOpt("rentBook")
                .argName("isbn")
                .hasArg()
                .desc("Rent a book from library")
                .build();
    }

    public static Option defineReturnOpt() {
        return Option.builder("rt")
                .longOpt("ReturnBook")
                .argName("isbn")
                .hasArg()
                .desc("Return a book for library")
                .build();
    }

    public static Option defineHelpOpt() {
        return new Option("h", "help", false, "print help message");
    }

    public static Option defineEnterOpt() {
        return new Option("e", "enter", false, "enter interactive interface");
    }

    public static Option defineQuitOpt() {
        return new Option("q", "quit", false, "quit interactive interface");
    }

    public void addOption(Option option, Consumer<Object[]> callback) {
        myCli.addOption(option, callback);
    }
}
