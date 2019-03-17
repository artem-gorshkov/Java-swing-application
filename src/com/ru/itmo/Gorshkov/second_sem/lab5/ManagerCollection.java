package com.ru.itmo.Gorshkov.second_sem.lab5;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ru.itmo.Gorshkov.first_sem.Condition;
import com.ru.itmo.Gorshkov.first_sem.Human;
import com.ru.itmo.Gorshkov.first_sem.MaterialProperty;
import com.ru.itmo.Gorshkov.first_sem.Property;
import com.sun.corba.se.spi.ior.ObjectKey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.security.SecureRandom;
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
        saveToFile();
        return collection.put(key, value);
    }

    public Human put(Human value) {
        if (value == null) {
            return null;
        } else {
            saveToFile();
            return collection.put(value.getName(), value);
        }
    }

    public Human remove(String key) {
        Human hum = collection.remove(key);
        saveToFile();
        return hum;
    }

    public void exportfromfile(String path) {
        try (Scanner scan = new Scanner(new File(System.getenv(path)).toPath())) {
            if (scan.hasNextLine()) scan.nextLine();
            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                if (str.charAt(str.length() - 1) == ',')
                    str = str.substring(0, str.length() - 1);
                if (!str.equals("]}"))
                    this.put(parseHuman(str));
            }
        } catch (IOException e) {
            System.err.println("File not found");
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            System.err.println("Invalid JSON Format");
        }
    }

    public Human parseHuman(String str) {
        try {
            JSONObject jsonObject = (JSONObject) JSON.parse(str);
            Object name = jsonObject.get("name");
            Human human = new Human((String) name);
            try {
                Object cordX = jsonObject.get("cordX");
                Object cordY = jsonObject.get("cordY");
                human.setCords(Double.parseDouble(cordX.toString()), Double.parseDouble(cordY.toString()));
            } catch (java.lang.ClassCastException e) {
                System.err.println("cords must be digital");
            }
            Object condition = jsonObject.get("condition");
            human.setCondition(Condition.valueOf((String) condition));
            Object allProperty = jsonObject.get("allProperty");
            for (Object property : (JSONArray) allProperty) {
                Object propertyName = ((JSONObject) property).get("name");
                human.addProperty(new MaterialProperty((String) propertyName));
            }
            return human;
        } catch (JSONException e) {
            System.err.println("Can't parse Human\nInvalid syntax\nTry to use \"help\" for more information");
            return null;
        }
    }
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(this.path)) {
            writer.println("{\"Humans\" : [");
//            for(collection.)
//            collection.forEach((String key, Human value) -> {
//                writer.println(JSON.toJSONString(collection.get(key)) + ",");
//            });
        } catch (FileNotFoundException e) {
            System.err.println("Can't save information in file\nFile not found");
        } catch (JSONException e) {
            System.err.println("Can't save Human to file\nInvalid syntax");
        }
    }
}
