package com.five;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonHandle<T> {
    private final File jsonFile;
    private final TypeReference<List<T>> typeRef;

    public JsonHandle(String filePath, TypeReference<List<T>> typeRef) {
        this.jsonFile = new File(filePath);
        this.typeRef = typeRef;
    }

    public List<T> readFile() throws IOException {
        List<T> dataList = new ArrayList<>();
        if (jsonFile.exists() || jsonFile.createNewFile()) {
            ObjectMapper objectMapper = new ObjectMapper();
            if (jsonFile.length() == 0) {
                dataList.addAll(objectMapper.readValue("[]", typeRef));
            }
            else dataList.addAll(objectMapper.readValue(jsonFile, typeRef));
        }
        return dataList;
    }

    public void writeFile(List<T> dataList) throws IOException {
        if (jsonFile.exists() || jsonFile.createNewFile()) {
            ObjectMapper objectMapper = new ObjectMapper();
            FileOutputStream fileOutputStream = new FileOutputStream(jsonFile, false);
            objectMapper.writeValue(fileOutputStream, dataList);
            fileOutputStream.close();
        }
    }
}
