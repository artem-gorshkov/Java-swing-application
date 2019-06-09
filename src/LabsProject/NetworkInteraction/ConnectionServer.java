package LabsProject.NetworkInteraction;

import LabsProject.Nature.Homosapiens.Human;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectionServer implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket recievePacket;
    private ManagerCollection managerCollection;
    private byte[] commandByte;
    private DatagramPacket answerPacket;

    ConnectionServer(DatagramSocket socket, DatagramPacket packet, ManagerCollection managerCollection) {
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
            String feedback;
            switch (command.getCommands().getText()) {
                case "request":
                    answerPacket = new DatagramPacket(arrayOutputStream.toByteArray(), arrayOutputStream.toByteArray().length, recievePacket.getAddress(), recievePacket.getPort());
                    socket.send(answerPacket);
                    break;
                case "exit":
                    try {
                        managerCollection.saveToFile(MainServer.path);
                        String str = "Collection saved to " + System.getenv(MainServer.path);
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
                default:
                    feedback = doCommand(command);
                    //managerCollection.outCollection();
                    if (managerCollection.getCollection().isEmpty())
                        feedback = "No elements in collection";
                    if (feedback != null)
                        objectOutputStream.writeObject(feedback);
                    objectOutputStream.writeObject(managerCollection.getCollection());
                    answerPacket = new DatagramPacket(arrayOutputStream.toByteArray(), arrayOutputStream.toByteArray().length, recievePacket.getAddress(), recievePacket.getPort());
                    socket.send(answerPacket);
            }
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    private String doCommand(Command command) {
        switch (command.getCommands().getText()) {
            case "add_if_max":
                return add_if_max(command.getArg());
            case "remove":
                return remove(command.getArg());
            case "load":
                return load(command.getArg());
            case "save":
                return save(command.getArg());
            case "insert":
                insert(command.getArg());
                return null;
            case "remove_greater_key":
                remove_greater_key(command.getArg());
                return null;
            default:
                return null;
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
            TreeMap<String, Human> one = new TreeMap<>();
            one.put(key, hum);
            Set<Human> set = Stream.concat(one.values().stream(), managerCollection.getCollection().values().stream()).collect(Collectors.toSet());
            managerCollection.getCollection().clear();
            set.forEach(managerCollection::put);
        } catch (Throwable e) {
            System.err.println("Can't insert Human");
        }
    }

    /**
     * Add new element to collection, if his value bigger then max value in collection.
     *
     * @param arg must be: {element} (element in JSON format)
     */
    private String add_if_max(String arg) {
        try {
            Human hum = managerCollection.parseHuman(arg);
            TreeMap<String, Human> one = new TreeMap<>();
            one.put(hum.getName(), hum);
            if (managerCollection.getCollection().values().stream().sorted(Comparator.reverseOrder()).findFirst().get().compareTo(hum) < 0 && !(hum == null)) {
                Set<Human> set = Stream.concat(one.values().stream(), managerCollection.getCollection().values().stream()).collect(Collectors.toSet());
                managerCollection.getCollection().clear();
                set.forEach(managerCollection::put);
                return null;
            } else return "not max";
        } catch (Throwable e) {
            return null;
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
    private String remove(String arg) {
        try {
            String key = managerCollection.getCollection().keySet().stream().filter(k -> k.equals(arg)).findFirst().get();
            managerCollection.getCollection().remove(key);
            String str = "Remove " + arg + " succesfully";
            return str;
        } catch (NoSuchElementException e) {
            return "No such key";
        }
    }

    /**
     * Load Human form file
     *
     * @param arg - path to file
     * @return information for client
     */
    private String load(String arg) {
        try {
            managerCollection.exportfromfile(arg);
            return ("load from file succesfully");
        } catch (IOException e) {
            return ("File not found");
        }
    }

    /**
     * Save collection to file
     *
     * @param arg- path to file
     * @return information for client
     */
    private String save(String arg) {
        try {
            managerCollection.saveToFile(arg);
            return ("File saved succesfully");
        } catch (Exception e) {
            return ("File not found");
        }
    }
}
