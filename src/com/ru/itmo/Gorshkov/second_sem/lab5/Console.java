package com.ru.itmo.Gorshkov.second_sem.lab5;

import com.ru.itmo.Gorshkov.first_sem.Human;

import java.util.Scanner;

class Console {
    private Scanner scan = new Scanner(System.in);
    private boolean run = false;
    private ManagerCollection managerCollection;
    private static final String help = "eto help";
    private static final String invalidSyntax = "Invalid syntax\nTry to use \"help\" to get more information";
    private static final String notFoundArgument = "Not found argument\nTry to use \"help\" to get more information";

    Console(ManagerCollection managerCollection) {
        this.managerCollection = managerCollection;
    }

    void exec() {
        if (run) {
            throw new RuntimeException("console running already");
        }
        run = true;
        while (run) {
            if (!scan.hasNextLine()) {
                break;
            }
            String callcmnd = scan.nextLine();
            String cmnd;
            String arg;
            if (callcmnd.contains(" ")) {
                cmnd = callcmnd.substring(0, callcmnd.indexOf(' '));
                arg = callcmnd.substring(callcmnd.indexOf(' ') + 1);
                if (arg.length() == 0) {
                    System.err.println(notFoundArgument);
                } else {
                    switch (cmnd) {
                        case "insert":
                            if (!arg.matches("\\w*\\s\\{[\\s\\S]*\\}")) {
                                System.err.println(invalidSyntax);
                            } else {
                                String key = arg.substring(1, arg.indexOf(' '));
                                String element = arg.substring(arg.indexOf(' ') + 1);
                                Human hum = managerCollection.parseHuman(element);
                                managerCollection.put(key, hum);
                            }
                            break;
                        case "add_if_max":
                            Human hum = managerCollection.parseHuman(arg);
                            if (hum.getName().compareTo(managerCollection.getCollection().lastKey()) > 0) {
                                managerCollection.put(hum);
                            }
                            break;
                        case "remove_greater_key":
                            while (managerCollection.getCollection().higherKey(arg) != null)
                                managerCollection.remove(managerCollection.getCollection().higherKey(arg));
                            break;
                        case "remove":
                            managerCollection.remove(arg);
                            break;
                        case "import":
                            managerCollection.exportfromfile(arg);
                            break;
                        case "show":
                        case "exit":
                        case "help":
                            System.err.println(cmnd + " don't need argument\nTry to use \"help\" to get more information");
                            break;
                        default:
                            System.err.println("Not found " + cmnd + "\nTry to use \"help\" to get more information");
                    }
                }
            } else {
                cmnd = callcmnd;
                arg = "";
                switch (cmnd) {
                    case "show":
                        System.out.println(managerCollection.getCollection());
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    case "help":
                        System.out.println(help);
                        break;
                    case "insert":
                    case "add_if_max":
                    case "remove_greater_key":
                    case "remove":
                    case "import":
                        System.err.println(notFoundArgument);
                        break;
                    default:
                        System.err.println("Not found \"" + cmnd + "\"help\nTry to use \"help\" to get more information");
                }
            }
        }
    }
}
