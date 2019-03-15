package com.ru.itmo.Gorshkov.second_sem.lab5;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.ru.itmo.Gorshkov.first_sem.Human;
import com.ru.itmo.Gorshkov.first_sem.MaterialProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ManagerCollection {
    private TreeMap<String, Human> collection = new TreeMap<>();
    private String path;
    ManagerCollection(String path) {
        this.path = path;
    }
    public TreeMap<String, Human> getCollection() {
        return collection;
    }
    public Human put(String key, Human value) {
        saveToFile(value);
        return collection.put(key, value);
    }
    public Human put(Human value) {
        if(value==null) {
            System.err.println("Object is null");
            return null;
        }
        else {
            saveToFile(value);
            return collection.put(value.getName(), value);
        }
    }
    public Human remove(String key) {
        Human hum = collection.remove(key);
        saveToFile(collection);
        return hum;
    }
    public void exportfromfile(String path) {
        this.path = path;
        try (Scanner scan = new Scanner(new File(System.getenv(path)).toPath())) {
            while(scan.hasNextLine()) {
                String str = scan.nextLine();
                this.put(parseHuman(str));
            }
        } catch (IOException e) {
            System.err.println("File not found");
        }
    }
    public Human parseHuman(String str) {
        try {
            Human hum = JSON.parseObject(str, Human.class);
            if (str.contains("{\"name\":")) {
                String property = str.substring(str.indexOf("{\"name\":"), str.indexOf("\"}]") + 2);
                MaterialProperty prop = JSON.parseObject(property, MaterialProperty.class);
                hum.addProperty(prop);
            }
            return hum;
        } catch (JSONException e) {
            System.err.println("Can't parse Human \nInvalid syntax \nTry to use \"help\" for more information");
            return null;
        }

    }
    public void saveToFile(Human hum) {
        try(PrintWriter writer = new PrintWriter(this.path)) {
            String str = JSON.toJSONString(hum);
            writer.println();
            writer.append(str);
        } catch (FileNotFoundException e) {
            System.err.println("Can't save information in file\nFile not found");
        } catch (JSONException e) {
            System.err.println("Can't save Human to file\nInvalid syntax");
        }
    }
    public void saveToFile(TreeMap<String, Human> collection) {
        try(PrintWriter writer = new PrintWriter(this.path)) {
            collection.forEach((String key, Human value) -> {
                writer.println(JSON.toJSONString(collection.get(key)));
            });
        } catch (FileNotFoundException e) {
            System.err.println("Can't save information in file\nFile not found");
        } catch (JSONException e) {
            System.err.println("Can't save Human to file\nInvalid syntax");
        }
    }
}
