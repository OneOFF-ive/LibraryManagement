package com.five.manager;

import com.five.Book;

import java.util.List;

public interface BookManager {
    void insertBook(Book book);
    void removeBook(String isbn);
    List<Book> seekBook(String prompt);
    void returnBook(String isbn);
    void rentBook(String isbn);
    void startup();
    void shutdown();
}
