package com.ru.itmo.Gorshkov.second_sem;

import com.alibaba.fastjson.JSONException;
import com.ru.itmo.Gorshkov.first_sem.Human;
import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper;


import java.io.*;

import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handle one connection with one user
 */

public class Connection implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket recievePacket;
    private ManagerCollection managerCollection;
    private byte[] commandByte;
    private DatagramPacket answerPacket;

    /**
     * Construct new handler
     */
    Connection(DatagramSocket socket, DatagramPacket packet, ManagerCollection managerCollection) {
        this.socket = socket;
        this.recievePacket = packet;
        this.managerCollection = managerCollection;
    }

    public void run() {
        try {
            commandByte = recievePacket.getData();
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(commandByte);
            ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream);
            Command command = (Command) objectInputStream.readObject();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            switch (command.getCommands().getText()) {
                case "request":
                    answerPacket = new DatagramPacket(arrayOutputStream.toByteArray(), arrayOutputStream.toByteArray().length, recievePacket.getAddress(), recievePacket.getPort());
                    socket.send(answerPacket);
                case "exit":
                    try {
                        managerCollection.saveToFile();
                        String str = "Collection saved to " + System.getenv(Main.path);
                        objectOutputStream.writeObject(str);
                        answerPacket = new DatagramPacket(arrayOutputStream.toByteArray(), arrayOutputStream.toByteArray().length, recievePacket.getAddress(), recievePacket.getPort());
                        socket.send(answerPacket);
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
                case "import":
                    ConcurrentSkipListMap<String, Human> incoll =
                            ((ConcurrentSkipListMap<String, Human>) objectInputStream.readObject());
                    incoll.forEach((s, h) -> managerCollection.getCollection().put(s, h));
                case "load":
                    load(command.getArg());
                case "save":
                    save(command.getArg());
                default:
                    doCommand(command);
                    managerCollection.outCollection();
                    if(!managerCollection.getCollection().isEmpty())
                        objectOutputStream.writeObject(managerCollection.getCollection());
                     else objectOutputStream.writeObject("No elements in collection");
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
        try {
            String key = arg.substring(0, arg.indexOf(' '));
            String element = arg.substring(arg.indexOf(' ') + 1);
            Human hum = managerCollection.parseHuman(element);
            if (!key.equals(hum.getName())) hum.setName(key);
            System.out.println(hum);
            //Human hum = managerCollection.put(key, managerCollection.parseHuman(element));
            TreeMap<String, Human> one = new TreeMap<>();
            one.put(key, hum);
            Set<Human> set = Stream.concat(one.values().stream(), managerCollection.getCollection().values().stream()).collect(Collectors.toSet());
            managerCollection.getCollection().clear();
            set.forEach(managerCollection::put);
            //System.out.println("Added " + key + " successfully");
        } catch (Throwable ee) {
            System.err.println("Can't insert Human");
        }
    }

    /**
     * Add new element to collection, if his value bigger then max value in collection.
     *
     * @param arg must be: {element} (element in JSON format)
     */
    private void add_if_max(String arg) {
        try {
            Human hum = managerCollection.parseHuman(arg);
            TreeMap<String, Human> one = new TreeMap<>();
            one.put(hum.getName(), hum);
            if (Stream.concat(one.keySet().stream(), managerCollection.getCollection().keySet().stream()).max(String::compareTo).equals(hum.getName()) && !(hum == null)) {
                Set<Human> set = Stream.concat(one.values().stream(), managerCollection.getCollection().values().stream()).collect(Collectors.toSet());
                managerCollection.getCollection().clear();
                set.forEach(managerCollection::put);
            } //else System.out.println("Not bigger then max"); //OUT TO CLIENT
        } catch(Throwable e) {

        }
    }

    /**
     * Delete form collection all elements whose key larger than arg.
     *
     * @param arg must be: {String key}
     */
    private void remove_greater_key(String arg) {
        Set<String> newSet = managerCollection.getCollection().keySet().stream().filter(s -> s.compareTo(arg) < 0).collect(Collectors.toSet());
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
        try {
            managerCollection.exportfromfile(arg);
        } catch (IOException e) {
            System.err.println("File not found");
        }
    }

    private void save(String arg) {
        managerCollection.saveToFile(arg);
    }
}