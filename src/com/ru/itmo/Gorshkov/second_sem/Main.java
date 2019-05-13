package com.ru.itmo.Gorshkov.second_sem;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.*;
import java.util.Scanner;


public class Main {
    private static final String path = "PathLab5";
    private static long timeOfStartProg;

    public static void main(String args[]) {
        //Check environment variable
        try {
            Path path1 = Paths.get(System.getenv(path));
        } catch (Throwable e) {
            System.err.println("Can't find environment variable\nPlease write way to file in \"PathLab5\"");
            System.exit(3);
        }
        //Create new collection handler
        ManagerCollection coll = new ManagerCollection(path);
        coll.exportfromfile(path);
        timeOfStartProg = System.currentTimeMillis();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                coll.saveToFile();
                System.out.println("Collection saved to " + System.getenv(path));
            } catch (Throwable e) {
                System.err.println("An error occurred while file saving :(");
            }
        }));
        //create server socket, start waiting for connection
        int port = 8189;
//        try {
//            port = Integer.parseInt(args[0]);
//        } catch (NumberFormatException | IndexOutOfBoundsException e) {
//            System.err.println("Port must be number");
//            Scanner scan = new Scanner(System.in);
//            if (scan.hasNextInt()) port = scan.nextInt();
//            else port = 8189;
//        }
        try (ServerSocket s = new ServerSocket(port)) {
            while (true) {
                Socket incoming = s.accept();
                System.out.println("One more user in");
                Runnable r = new Connection(incoming, coll);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException e) {
            System.err.println("Broken");
        }
    }
}
