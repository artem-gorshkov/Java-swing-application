package com.ru.itmo.Gorshkov.second_sem.lab5;

public class Main {
    private static final String path = "PathLab5";
    private static long timeOfStartProg;
    public static void main(String args[]) {
        ManagerCollection coll = new ManagerCollection(path);
        coll.exportfromfile(path);
        timeOfStartProg = System.currentTimeMillis();
        System.out.println("Lab5, press \"help\" for more information, path to file in environment variable: PathLab5");
        if(coll.getCollection().isEmpty()) System.out.println("File is empty");
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
