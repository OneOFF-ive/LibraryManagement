package com.five.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.five.Book;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class JsonDataAccessImpl implements DataAccess {
    final private Set<Book> dataList;
    private final String filePath;
    private final JsonHandle<Book> jsonHandle;

    public JsonDataAccessImpl(String filePath) {
        this.filePath = filePath;
        dataList = new HashSet<>();
        jsonHandle = new JsonHandle<>(filePath, new TypeReference<List<Book>>() {});
    }

    public List<Book> getDataList() {
        return dataList.stream().toList();
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public void readData() {
        try {
            dataList.addAll(jsonHandle.readFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveData() {
        try {
            jsonHandle.writeFile(dataList.stream().toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertData(Book data) {
        dataList.add(data);
    }

    @Override
    public void removeData(String isbn) {
        dataList.removeIf(item -> Objects.equals(item.getIsbn(), isbn));
    }

    @Override
    public List<Book> getDataBy(String field, Object condition) {
        List<Book> resultList = new ArrayList<>();
        Class<?> cls = Book.class;
        Field f;
        try {
            f = cls.getDeclaredField(field);
            f.setAccessible(true);
            for (var book : dataList) {
                var value = f.get(book);
                if (Objects.equals(value, condition)) {
                    resultList.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public void updateData(Book book) {
        if (dataList.remove(book)) {
            dataList.add(book);
        }
    }
}
