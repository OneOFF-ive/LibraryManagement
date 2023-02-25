package com.five;

import com.OneFive.MyCli;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Arrays;


public class Entry {
    public static void main(String[] args) throws ParseException, IOException {
        MyCli myCli = MyCli.getInstance();
        MyCliHandle myCliHandle = new MyCliHandle(myCli);
        myCliHandle.start();
        myCliHandle.initMyCli();
        myCli.parseAllOptions(args);
        myCliHandle.close();
    }

}
