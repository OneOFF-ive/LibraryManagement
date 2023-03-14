package com.five.data;

import com.five.Book;

import java.util.List;

public interface DataAccess {
   void insertData(Book data);
   void removeData(String isbn);
   List<Book> getDataBy(String field, Object condition);
   List<Book> getAllData();
   void updateData(Book data);
   void open();
   void close();
}