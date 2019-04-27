package com.ru.itmo.Gorshkov.second_sem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Handle one connection with one user
 */
public class Connection implements Runnable {
    private Socket socket;
    private ManagerCollection managerCollection;
    /**
     * Construct new handler
     *
     * @param socket - incoming socket
     */
    Connection(Socket socket, ManagerCollection managerCollection) {
        this.socket = socket;
        this.managerCollection = managerCollection;
    }

    public void run() {
        try (OutputStream outStream = socket.getOutputStream();
             InputStream inStream = socket.getInputStream()) {
            Console console = new Console(managerCollection, outStream, inStream); //create console for communication with client
            console.exec();
        } catch (IOException e) {
            System.out.println("Broken(((((((((((");
        }

    }
}
