package com.five.manager;

import com.five.Book;
import com.five.data.DataAccess;

import java.util.List;

public interface BookManager {
    void insertBook(Book book);
    void removeBook(String isbn);
    void removeBooks(String isbn, int amount);
    List<Book> seekBook(String prompt);
    void returnBook(String isbn);
    void rentBook(String isbn);
    void startup();
    void shutdown();
    DataAccess getDataAccess();
    void setDataAccess(DataAccess dataAccess);
}
