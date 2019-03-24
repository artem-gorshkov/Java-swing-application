package com.ru.itmo.Gorshkov.second_sem.lab5;

import com.alibaba.fastjson.JSON;
import com.ru.itmo.Gorshkov.first_sem.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    private static final String path = "PathLab5";
    public static void main(String args[]) {
        ManagerCollection coll = new ManagerCollection(path);
        coll.exportfromfile(path);
        Console console = new Console(coll);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                coll.saveToFile();
                System.out.println("Collection saved to " + System.getenv(path));
            } catch (Throwable e) {
                System.err.println("An error occurred while file saving :(");
            }
        }));
        console.exec();
    }

}
