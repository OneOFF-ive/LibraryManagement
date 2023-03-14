package com.five;

import com.OneFive.MyCli;
import com.five.data.Book;
import com.five.manager.BookManager;
import com.five.manager.BookManagerFromJson;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.*;
import java.util.function.Consumer;

public class Application {
    private MyCli myCli;
    private boolean shouldQuit;
    private BookManager bookManager;

    public Application() {
        this.myCli = MyCli.getInstance();
        bookManager = new BookManagerFromJson();
        shouldQuit = false;
    }

    public MyCli getMyCli() {
        return myCli;
    }

    public void setMyCli(MyCli myCli) {
        this.myCli = myCli;
    }

    public BookManager getBookManager() {
        return bookManager;
    }

    public void setBookManager(BookManager bookManager) {
        this.bookManager = bookManager;
    }

    public void run(String[] args) throws ParseException {
        bookManager.startup();
        myCli.parseAllOptions(args);
        bookManager.shutdown();
    }

    public void initMyCli() {
        // -a/addBook <title> <isbn> <author> <amount>
        myCli.addOption(DefaultOptions.AddOpt(), (Object[] args) -> {
            System.out.printf("add a book %s%n", Arrays.toString(args));
            int amount = Integer.parseInt(args[3].toString());
            Book book = new Book(args[0].toString(), args[1].toString(), args[2].toString(), amount);

            bookManager.insertBook(book);
        });

        // -d/delBook <isbn>
        myCli.addOption(DefaultOptions.DelOpt(), (Object[] args) -> {
            System.out.printf("del a book %s%n", Arrays.toString(args));

            String isbn = args[0].toString();
            if (Objects.equals(args.length, 2)) {
                int amount = Integer.parseInt(args[1].toString());
                bookManager.removeBooks(isbn, amount);
            }
            else bookManager.removeBook(isbn);
        });

        // -s/seekBook <title/isbn>
        myCli.addOption(DefaultOptions.SeekOpt(), (Object[] args) -> {
            System.out.printf("seek a book %s%n", Arrays.toString(args));
            String info = args[0].toString();

            var res = bookManager.seekBook(info);
            for (var item : res) {
                System.out.println(item);
            }
        });

        // -rt/returnBook <isbn>
        myCli.addOption(DefaultOptions.ReturnOpt(), (Object[] args) -> {
            System.out.printf("return a book %s%n", Arrays.toString(args));

            String isbn = args[0].toString();
            bookManager.returnBook(isbn);
        });

        // -r/rentBook <isbn>
        myCli.addOption(DefaultOptions.RentOpt(), (Object[] args) -> {
            System.out.printf("rent a book %s%n", Arrays.toString(args));

            String isbn = args[0].toString();
            bookManager.rentBook(isbn);
        });

        // -h/help
        myCli.addOption(DefaultOptions.HelpOpt(), (Object[] args) -> {
            HelpFormatter formatter = new HelpFormatter();
            Options options = myCli.getOptions();
            formatter.printHelp("ant", options);
        });

        // -e/enter
        myCli.addOption(DefaultOptions.EnterOpt(), (Object[] args) -> {
            Scanner scanner = new Scanner(System.in);

            while (!shouldQuit) {
                String input = scanner.nextLine();
                String[] newArgs = input.split(" ");
                try {
                    myCli.parseAllOptions(newArgs);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });

        // -q/quit
        myCli.addOption(DefaultOptions.QuitOpt(), (Object[] args) -> {
            shouldQuit = true;
        });
    }

    public void addOption(Option option, Consumer<Object[]> callback) {
        myCli.addOption(option, callback);
    }
}
