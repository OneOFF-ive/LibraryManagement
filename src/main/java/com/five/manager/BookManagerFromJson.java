package com.five.manager;

import com.five.Book;
import com.five.data.DataAccess;
import com.five.data.JsonDataAccessImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookManagerFromJson implements BookManager{
    private DataAccess dataAccess;

    public BookManagerFromJson() {
        dataAccess = new JsonDataAccessImpl("library.json");
    }

    @Override
    public DataAccess getDataAccess() {
        return dataAccess;
    }
    @Override
    public void setDataAccess(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public void insertBook(Book book) {
        var res = dataAccess.getDataBy("isbn", book.getIsbn());
        if (!res.isEmpty()) {
            Book oldBook = res.get(0);
            oldBook.setTotalAmount(book.getTotalAmount() + oldBook.getTotalAmount());
            oldBook.setCurrentAmount(book.getTotalAmount() + oldBook.getCurrentAmount());
            dataAccess.updateData(oldBook);
        }
        else {
            dataAccess.insertData(book);
        }
    }

    @Override
    public void removeBook(String isbn) {
        var res = dataAccess.getDataBy("isbn", isbn);
        if (!res.isEmpty()) {
            Book oldBook = res.get(0);
            if (!oldBook.isRented()) dataAccess.removeData(isbn);
        }
    }

    @Override
    public List<Book> seekBook(String prompt) {
        Set<Book> res = new HashSet<>();
        res.addAll(dataAccess.getDataBy("isbn", prompt));
        res.addAll(dataAccess.getDataBy("title", prompt));
        return res.stream().toList();
    }

    @Override
    public void returnBook(String isbn) {
        var res = dataAccess.getDataBy("isbn", isbn);
        if (!res.isEmpty()) {
            Book oldBook = res.get(0);
            if (oldBook.isRented()) {
                oldBook.setCurrentAmount(oldBook.getTotalAmount() + 1);
                dataAccess.updateData(oldBook);
            }
        }
    }

    @Override
    public void rentBook(String isbn) {
        var res = dataAccess.getDataBy("isbn", isbn);
        if (!res.isEmpty()) {
            Book oldBook = res.get(0);
            if (oldBook.getCurrentAmount() > 0) {
                oldBook.setCurrentAmount(oldBook.getCurrentAmount() - 1);
                dataAccess.updateData(oldBook);
            }
        }
    }

    @Override
    public void startup() {
        dataAccess.readData();
    }

    @Override
    public void shutdown() {
        dataAccess.saveData();
    }
}
