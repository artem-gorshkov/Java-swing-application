package com.ru.itmo.Gorshkov.second_sem.lab5;

import com.ru.itmo.Gorshkov.first_sem.Human;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Console {
    private Scanner scan = new Scanner(System.in);
    private boolean run = false;
    private ManagerCollection managerCollection;
    Console(ManagerCollection managerCollection) {
        this.managerCollection = managerCollection;
    }
    public void exec() {
        if (run) {
            throw new RuntimeException("console running already");
        }
        run = true;
        while(run) {
            if(!scan.hasNextLine()) {
                break;
            }
            String cmnd = scan.next();
//            String cmnd = callcmnd.substring(0, callcmnd.indexOf(' '));
//            String arg = callcmnd.substring(callcmnd.indexOf(' ') + 1);
            switch (cmnd) {
                case "insert":
                    if(!scan.hasNext()) {
                        System.err.println("Not found argument\nTry to use \"help\" to get more information");
                    }
                    else {
                        String str = scan.next();
                        if(!str.matches("\\{.*\\}")) {
                          System.err.println("Invalid syntax\nTry to use \"help\" to get more information");
                        }
                        else {
                            String key = str.substring(1, str.indexOf('}') - 1);
                            String element = scan.next();
                            Human hum = managerCollection.parseHuman(element);
                            managerCollection.put(key, hum);
                        }
                    }
                case "show":
                    System.out.println(managerCollection.getCollection());
                case "add_if_max":
                    if(!scan.hasNext()) {
                    System.err.println("Not found argument\nTry to use \"help\" to get more information");
                    }
                    else {
                        String arg = scan.next();
                        Human hum = managerCollection.parseHuman(arg);
                        if (hum.getName().compareTo(managerCollection.getCollection().lastKey()) > 0) {
                            managerCollection.put(hum);
                        }
                    }
                case "remove_greater_key":
                    if(!scan.hasNext()) {
                        System.err.println("Not found argument\nTry to use \"help\" to get more information");
                    }
                    else {
                        String str = scan.next();
                        if(!str.matches("\\{.*\\}")) {
                            System.err.println("Invalid syntax\nTry to use \"help\" to get more information");
                        }
                        else {
                            String key = str.substring(1, str.indexOf('}') - 1);
                            while(managerCollection.getCollection().higherKey(key)!=null)
                                managerCollection.remove(managerCollection.getCollection().higherKey(key));
                        }
                    }
                case "remove":
                    if(!scan.hasNext()) {
                        System.err.println("Not found argument\nTry to use \"help\" to get more information");
                    }
                    else {
                        String str = scan.next();
                        if(!str.matches("\\{.*\\}")) {
                            System.err.println("Invalid syntax\nTry to use \"help\" to get more information");
                        }
                        else {
                            String key = str.substring(1, str.indexOf('}') - 1);
                            managerCollection.remove(key);
                        }
                    }
                default:
                    System.err.println("Not found " + cmnd + "\nTry to use \"help\" to get more information");
            }
        }
    }
}
