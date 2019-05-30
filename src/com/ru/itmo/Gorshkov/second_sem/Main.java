package com.ru.itmo.Gorshkov.second_sem;



import java.io.IOException;
import java.nio.file.Paths;
import java.net.*;
import java.util.Scanner;


public class Main {
    public static final String path = "PathLab6";
    private static Integer port;
    static private Scanner scan = new Scanner(System.in);

    public static void main(String args[]) {
        try {
            Paths.get(System.getenv(path));
        } catch (Throwable e) {
            System.err.println("Can't find environment variable\nPlease write way to file in \"PathLab6\"");
            System.exit(3);
        }
        ManagerCollection coll = new ManagerCollection(path);
        try {
            coll.exportfromfile(System.getenv(path));
        } catch (Throwable e) {
            System.err.println("Can't parse collection");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                coll.saveToFile();
                System.out.println("Collection saved to " + System.getenv(path));
            } catch (Throwable e) {
                System.err.println("An error occurred while file saving");
            }
        }));
        try {
            port = Integer.parseInt(args[0]);
        } catch (Throwable e) {
            System.err.println("Port must be number");
            enterPort();
        }
        try {
            byte b[] = new byte[Integer.MAX_VALUE/32];
            DatagramSocket socket = new DatagramSocket(port);
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(b, b.length);
                    socket.receive(packet);
                    Runnable r = new Connection(socket, packet, coll);
                    Thread t = new Thread(r);
                    t.start();
                } catch (IOException e) {
                    System.err.println("Can't take datagram");
                }
            }
        } catch (Exception e) {
            System.err.println("can't create socket");
        }
    }
    private static void enterPort() {
        System.out.println();
        System.out.println("Enter port");
        boolean k = true;
        while (k) {
            if (scan.hasNextInt()) {
                port = scan.nextInt();
                k = false;
            } else {
                System.err.println("Port must be number");
                scan.nextLine();
            }
        }
    }
}
