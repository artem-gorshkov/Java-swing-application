package com.ru.itmo.Gorshkov.second_sem;

import com.alibaba.fastjson.JSONException;
import com.ru.itmo.Gorshkov.first_sem.Human;


import java.io.*;

import java.net.Socket;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

/**
 * Handle one connection with one user
 */
public class Connection implements Runnable {
    private Socket socket;
    private DataOutputStream outStream;
    private DataInputStream inStream;
    private ManagerCollection managerCollection;
    private static final String help = "Usage:" +
            "\ninfo - Show info about type of collection, number of collection elements, time from star program, time of file creation" +
            "\ninsert {String key} {element} - Add to collection one element with specific key" +
            "\nremove {String key} - Delete element with specific key(arg) from collection" +
            "\nremove_greater_key {String key} - Delete form collection all elements whose key larger than arg" +
            "\nimport {String path} - Add to collection all element from specific(arg) file" +
            "\nadd_if_max {element} - Add new element to collection, if his value bigger then max value in collection" +
            "\nshow - Show all element from collection" +
            "\nexit - Exit from program";
    private static final String invalidSyntax = "Invalid syntax\nTry to use \"help\" to get more information";
    private static final String notFoundArgument = "Not found argument\nTry to use \"help\" to get more information";

    /**
     * Construct new handler
     *
     * @param socket - incoming socket
     */
    Connection(Socket socket, ManagerCollection managerCollection) {
        this.socket = socket;
        this.managerCollection = managerCollection;
        try {
            outStream = new DataOutputStream(socket.getOutputStream());
            inStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            Broken(e);
        }
    }

    public void run() {
        String line = null;
        try {
            outStream.writeUTF("Hello, Lab6, press \"help\" for more information");
            while (true) {
                String callcmnd = inStream.readUTF();
                String cmnd;
                String arg;
                if (callcmnd.contains(" ")) {
                    cmnd = callcmnd.substring(0, callcmnd.indexOf(' '));
                    arg = callcmnd.substring(callcmnd.indexOf(' ') + 1);
                    if (arg.length() == 0) {
                        switch (cmnd) {
                            case "show":
                            case "exit":
                            case "help":
                            case "info":
                                DontneedArg(cmnd);
                                break;
                            default:
                                NotFoundArgument();
                        }
                    } else {
                        managerCollection.updateColl();
                        switch (cmnd) {
                            case "insert":
                                insert(arg);
                                break;
                            case "add_if_max":
                                add_if_max(arg);
                                break;
                            case "remove_greater_key":
                                remove_greater_key(arg);
                                break;
                            case "remove":
                                remove(arg);
                                break;
                            case "import":
                                Import(arg);
                                break;
                            default:
                                NotFoundCmnd(cmnd);
                        }
                        managerCollection.saveToFile();
                    }
                } else {
                    cmnd = callcmnd;
                    switch (cmnd) {
                        case "show":
                            show();
                            break;
                        case "exit":
                            exit();
                            break;
                        case "help":
                            help();
                            break;
                        case "info":
                            info();
                            break;
                        case "insert":
                        case "add_if_max":
                        case "remove_greater_key":
                        case "remove":
                        case "import":
                            NotFoundArgument();
                            break;
                        default:
                            if (!cmnd.equals(""))
                                NotFoundCmnd(cmnd);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Broken");
        }
    }

    /**
     * Add to collection one element with specific key.
     *
     * @param arg must be: {String key} {element} (element in JSON format)
     */
    private void insert(String arg) {
        if (!arg.matches("\\w*\\s\\{[\\s\\S]*\\}")) {
            System.err.println(invalidSyntax);
        } else {
            String key = arg.substring(0, arg.indexOf(' '));
            String element = arg.substring(arg.indexOf(' ') + 1);
            Human hum = managerCollection.put(key, managerCollection.parseHuman(element));
            System.out.println("Added " + key + " successfully");
        }
    }

    /**
     * Add new element to collection, if his value bigger then max value in collection.
     *
     * @param arg must be: {element} (element in JSON format)
     */
    private void add_if_max(String arg) {
        Human hum = managerCollection.parseHuman(arg);
        if (!(hum == null) && hum.getName().compareTo(managerCollection.getCollection().lastKey()) < 0) {
            managerCollection.put(hum);
        } else System.out.println("Not bigger then max");
    }

    /**
     * Delete form collection all elements whose key larger than arg.
     *
     * @param arg must be: {String key}
     */
    private void remove_greater_key(String arg) {
        boolean a = true;
        for (Map.Entry<String, Human> entry : managerCollection.getCollection().entrySet()) {
            if (entry.getKey().equals(arg)) a = false;
        }
        if (!a) managerCollection.remove(arg);
        while (managerCollection.getCollection().higherKey(arg) != null && a)
            managerCollection.remove(managerCollection.getCollection().higherKey(arg));
    }

    /**
     * Delete element with specific key(arg) from collection.
     *
     * @param arg must be: {String key}
     */
    private void remove(String arg) {
        if (managerCollection.remove(arg) == null) System.out.println("No such key");
        else System.out.println("Remove " + arg + " succesfully");
    }

    /**
     * Add to collection all element from specific(arg) file.
     *
     * @param arg must be {String path}
     */
    private void Import(String arg) {
        try (Scanner scan = new Scanner(Paths.get(arg))) {
            if (scan.hasNextLine()) {
                String str = scan.nextLine();
                if (!str.equals("{\"Humans\" : [")) throw new JSONException();
            }
            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                if (str.charAt(str.length() - 1) == ',') {
                    str = str.substring(0, str.length() - 1);
                    managerCollection.put(managerCollection.parseHuman(str));
                } else {
                    if (!str.equals("]}")) {
                        managerCollection.put(managerCollection.parseHuman(str.substring(0, str.length() - 2)));
                    }
                    if (!str.substring(str.length() - 2).equals("]}")) throw new JSONException();
                }
            }
        } catch (java.lang.StringIndexOutOfBoundsException | JSONException e) {
            System.err.println("Invalid JSON Format");
        } catch (IOException e) {
            System.err.println("File not found");
        }
    }

    /**
     * Show all element from collection.
     */
    private void show() {
        try {
            outStream.writeUTF(managerCollection.getCollection().toString());
        } catch (IOException e) {
            Broken(e);
        }
    }

    /**
     * Exit from program.
     */
    private void exit() {
        System.exit(0);
    }

    /**
     * Show info about type of collection, number of collection elements, time from star program, time of file creation.
     */
    private void info() {
        try {
            outStream.writeUTF("Type: " + managerCollection.getCollection().getClass().getSimpleName() + ", Number of element: "
                    + managerCollection.getCollection().size());
        } catch (IOException e) {
            Broken(e);
        }
    }

    /**
     * Help show all command in program.
     */
    private void help() {
        try {
            outStream.writeUTF(help);
        } catch (IOException e) {
            Broken(e);
        }
    }

    private void NotFoundCmnd(String cmnd) {
        try {
            outStream.writeUTF("Not found \"" + cmnd + "\"\nTry to use \"help\" to get more information");
        } catch (IOException e) {
            Broken(e);
        }
    }

    private void DontneedArg(String cmnd) {
        try {
            outStream.writeUTF(cmnd + " don't need argument\nTry to use \"help\" to get more information");
        } catch (IOException e) {
            Broken(e);
        }
    }

    private void NotFoundArgument() {
        try {
            outStream.writeUTF(notFoundArgument);
        } catch (IOException e) {
            Broken(e);
        }
    }

    private void Broken(Throwable e) {
        e.printStackTrace();
    }
}