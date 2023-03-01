package com.five.data;

import com.five.Book;

import java.util.List;
import java.util.Set;

public interface DataAccess {
   void insertData(Book data);
   void removeData(String isbn);
   List<Book> getDataBy(String field, Object condition);
   void updateData(Book data);
   void saveData();
   void readData();
   Set<Book> getAllData();
}