package com.five;

import com.OneFive.MyCli;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Arrays;


public class Entry {
    public static void main(String[] args){

        MyCli myCli = MyCli.getInstance();
        JsonHandle<Book> jsonHandle = new JsonHandle<>("library.json");

        try {
            jsonHandle.readData();

            MyCliHandle myCliHandle = new MyCliHandle(myCli, jsonHandle.getDataList());
            myCliHandle.initMyCli();
            myCliHandle.parseAllOptions(args);

            jsonHandle.saveData();
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }

    }

}
