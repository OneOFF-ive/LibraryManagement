package com.five;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class JsonHandle<T> {
    private final File jsonFile;
    private List<T> dataList;

    public JsonHandle(String filePath) {
        jsonFile = new File(filePath);
    }

    public void readData() throws IOException {
        if (jsonFile.exists() || jsonFile.createNewFile()) {
            ObjectMapper objectMapper = new ObjectMapper();
            dataList = objectMapper.readValue(jsonFile, new TypeReference<List<T>>() {
            });
        }
    }

    public void saveData() throws IOException {
        if (jsonFile.exists() || jsonFile.createNewFile()) {
            ObjectMapper objectMapper = new ObjectMapper();
            FileOutputStream fileOutputStream = new FileOutputStream(jsonFile, false);
            objectMapper.writeValue(fileOutputStream, dataList);
            fileOutputStream.close();
        }
    }

    public List<T> getDataList() {
        return dataList;
    }
}
