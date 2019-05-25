package com.ru.itmo.Gorshkov.second_sem;

import com.alibaba.fastjson.JSONException;
import com.ru.itmo.Gorshkov.first_sem.Human;


import java.io.*;

import java.net.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handle one connection with one user
 */

public class Connection implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket recievePacket;
    private ManagerCollection managerCollection;
    private ByteArrayInputStream arrayInputStream;
    private ObjectInputStream objectInputStream;
    private ByteArrayOutputStream arrayOutputStream;
    private ObjectOutputStream objectOutputStream;
    private byte[] commandByte;
    private DatagramPacket answerPacket;

    /**
     * Construct new handler
     */
    Connection(DatagramSocket socket, DatagramPacket packet, ManagerCollection managerCollection) {
        this.socket = socket;
        this.recievePacket = packet;
        this.managerCollection = managerCollection;
        try {
            arrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(arrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            commandByte = recievePacket.getData();
            arrayInputStream = new ByteArrayInputStream(commandByte);
            objectInputStream = new ObjectInputStream(arrayInputStream);
            Command command = (Command) objectInputStream.readObject();
            arrayOutputStream.reset();
            switch (command.getCommands().getText()) {
                case "exit":
                    try {
                        managerCollection.saveToFile();
                        String str = "Collection saved to " + System.getenv(Main.path);
                        objectOutputStream.writeObject(str);
                        answerPacket = new DatagramPacket(arrayOutputStream.toByteArray(), arrayOutputStream.toByteArray().length, recievePacket.getAddress(), recievePacket.getPort());
                        socket.send(answerPacket);
                        System.exit(0);
                    } catch (Throwable e) {
                        System.err.println("An error occurred while file saving");
                    }
                    break;
                case "info":
                    String str = "Type: " + managerCollection.getCollection().getClass().getSimpleName() + ", Number of element: " + managerCollection.getCollection().size();
                    objectOutputStream.writeObject(str);
                    answerPacket = new DatagramPacket(arrayOutputStream.toByteArray(), arrayOutputStream.toByteArray().length, recievePacket.getAddress(), recievePacket.getPort());
                    socket.send(answerPacket);
                    break;
                case "load":
                    load(command.getArg());
                    break;
                case "save":
                    save(command.getArg());
                    break;
                default:
                    doCommand(command);
                    objectOutputStream.writeObject(managerCollection.getCollection());
                    answerPacket = new DatagramPacket(arrayOutputStream.toByteArray(), arrayOutputStream.toByteArray().length, recievePacket.getAddress(), recievePacket.getPort());
                    socket.send(answerPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ee) {
            ee.printStackTrace();
        }
    }

    private void doCommand(Command command) {
        switch (command.getCommands().getText()) {
            case "insert":
                insert(command.getArg());
                break;
            case "add_if_max":
                add_if_max(command.getArg());
                break;
            case "remove_greater_key":
                remove_greater_key(command.getArg());
                break;
            case "remove":
                remove(command.getArg());
                break;
        }
    }

    /**
     * Add to collection one element with specific key.
     *
     * @param arg must be: {String key} {element} (element in JSON format)
     */
    private void insert(String arg) {
        String key = arg.substring(0, arg.indexOf(' '));
        String element = arg.substring(arg.indexOf(' ') + 1);
        Human hum = managerCollection.parseHuman(element);
        //Human hum = managerCollection.put(key, managerCollection.parseHuman(element));
        TreeMap<String, Human> one = new TreeMap<>();
        one.put(key, hum);
        Set<Human> set = Stream.concat(one.values().stream(), managerCollection.getCollection().values().stream()).collect(Collectors.toSet());
        managerCollection.getCollection().clear();
        set.forEach(managerCollection::put);
        //System.out.println("Added " + key + " successfully");
    }

    /**
     * Add new element to collection, if his value bigger then max value in collection.
     *
     * @param arg must be: {element} (element in JSON format)
     */
    private void add_if_max(String arg) {
        Human hum = managerCollection.parseHuman(arg);
        TreeMap<String, Human> one = new TreeMap<>();
        one.put(hum.getName(), hum);
        if (Stream.concat(one.keySet().stream(), managerCollection.getCollection().keySet().stream()).max(String::compareTo).equals(hum.getName()) && !(hum == null)) {
            Set<Human> set = Stream.concat(one.values().stream(), managerCollection.getCollection().values().stream()).collect(Collectors.toSet());
            managerCollection.getCollection().clear();
            set.forEach(managerCollection::put);
        } //else System.out.println("Not bigger then max"); //OUT TO CLIENT
    }

    /**
     * Delete form collection all elements whose key larger than arg.
     *
     * @param arg must be: {String key}
     */
    private void remove_greater_key(String arg) {
        Set<String> newSet = managerCollection.getCollection().keySet().stream().filter(s -> s.compareTo(arg) > 0).collect(Collectors.toSet());
        for (Map.Entry<String, Human> entry : managerCollection.getCollection().entrySet()) {
            if (newSet.contains(entry.getKey())) {
                managerCollection.getCollection().remove(entry.getKey());
            }
        }
    }

    /**
     * Delete element with specific key(arg) from collection.
     *
     * @param arg must be: {String key}
     */
    private void remove(String arg) {
        try {
            String key = managerCollection.getCollection().keySet().stream().filter(k -> k.equals(arg)).findFirst().get();
            managerCollection.getCollection().remove(key);
            //System.out.println("Remove " + arg + " succesfully");
        } catch (NoSuchElementException e) {
            //System.out.println("No such key");
        }
    }

    private void load(String arg) {

    }

    private void save(String arg) {

    }
}