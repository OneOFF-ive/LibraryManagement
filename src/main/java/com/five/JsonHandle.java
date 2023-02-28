package com.five;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JsonHandle<T> {
    private final File jsonFile;
    private List<T> dataList;
    private final TypeReference<List<T>> typeRef;

    public JsonHandle(String filePath, TypeReference<List<T>> typeRef) {
        this.jsonFile = new File(filePath);
        this.typeRef = typeRef;
    }

    public void readData() throws IOException {
        if (jsonFile.exists() || jsonFile.createNewFile()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                dataList = objectMapper.readValue(jsonFile, typeRef);
            } catch (IOException ignored) {
                if (dataList == null) {
                    dataList = new ArrayList<>();
                }
            }
        }
    }

    void saveData() throws IOException {
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
