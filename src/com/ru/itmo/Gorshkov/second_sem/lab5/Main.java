package com.ru.itmo.Gorshkov.second_sem.lab5;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final String path = "PathLab5";
    private static long timeOfStartProg;
    public static void main(String args[]) {
        try{
            Path path1 = Paths.get(System.getenv(path));
        } catch(Throwable e) {
            System.err.println("Can't find environment variable\nPlease write way to file in \"PathLab5\"");
            System.exit(3);
        }
        ManagerCollection coll = new ManagerCollection(path);
        coll.exportfromfile(path);
        timeOfStartProg = System.currentTimeMillis();
        System.out.println("Lab5, press \"help\" for more information, path to file in environment variable: PathLab5");
        if(coll.getCollection().isEmpty()) System.out.println("Added zero elements");
        else System.out.println("Added from file succesfully");
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
    static long getTimeOfStartProg() {
        return timeOfStartProg;
    }
    public static String getPath() {
        return path;
    }
}
