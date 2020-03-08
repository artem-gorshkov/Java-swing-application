package LabsProject.NetworkInteraction;

import LabsProject.Recivers.DataBaseReciver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public final class MainServer {
    private static Integer port;
    private static Properties heliosProp;
    private static String heliosURL = "jdbc:postgresql://pg/studs";
    private static Properties myProp;
    private static String myURL = "jdbc:postgresql://localhost:5432/lab7";

    static {
        heliosProp = new Properties();
        heliosProp.put("user", "s265087");
        heliosProp.put("password", "bay825");
        myProp = new Properties();
        myProp.put("user", "artem");
        myProp.put("password", "password");
    }
    public static void main(String args[]) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // прописать завершение
            } catch (Throwable e) {
                System.err.println("An error occurred while file saving");
            }
        }));
        try {
            port = Integer.parseInt(args[0]);
        } catch (Throwable e) {
            port = 0;
        }
        DataBaseReciver dbr = null;
        try {
            //Connection connDB = DriverManager.getConnection(heliosURL, heliosProp);
            Connection connDB = DriverManager.getConnection(myURL, myProp);
            dbr = new DataBaseReciver(connDB);
            //dbr.dropTables();
            //dbr.createTables();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        try {
            byte b[] = new byte[Integer.MAX_VALUE/32];
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println(InetAddress.getLocalHost() + " : " + socket.getLocalPort());
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(b, b.length);
                    socket.receive(packet);
                    Runnable r = new ConnectionServer(socket, packet, dbr);
                    Thread t = new Thread(r);
                    t.start();
                } catch (IOException e) {
                    System.err.println("Can't take datagram");
                }
            }
        } catch (Exception e) {
            System.err.println("can't create socket");
            e.printStackTrace();
        }
    }
}
