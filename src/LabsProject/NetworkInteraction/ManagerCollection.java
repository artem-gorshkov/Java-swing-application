package LabsProject.NetworkInteraction;

import LabsProject.Nature.Homosapiens.Condition;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.Nature.Homosapiens.Propetyies.MaterialProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.time.ZonedDateTime;
import java.util.*;

public class ManagerCollection {

    ManagerCollection() {

    }

    public static List<Human> exportfromfile(String path) throws Exception {
        try (Scanner scan = new Scanner(Paths.get(path))) {
            List<Human> list = new LinkedList<>();
            if (scan.hasNextLine()) {
                String str = scan.nextLine();
                if (!str.equals("{\"Humans\" : [")) throw new JSONException();
            }
            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                if (str.charAt(str.length() - 1) == ',') {
                    str = str.substring(0, str.length() - 1);
                    list.add(parseHuman(str));
                } else {
                    if (!str.equals("]}")) {
                        list.add(parseHuman(str.substring(0, str.length() - 2)));
                    }
                    if (!str.substring(str.length() - 2).equals("]}")) throw new JSONException();
                }
            }
            return list;
        } catch (IOException e) {
            System.err.println("File not found");
            throw e;
        } catch (java.lang.StringIndexOutOfBoundsException | JSONException e) {
            System.err.println("Invalid JSON Format");
            throw e;
        } catch (Throwable e) {
            System.err.println("error");
            throw e;
        }
    }

    public static Human parseHuman(String str) throws JSONException {
        try {
            Human human = helpParse(str);
            JSONObject jsonObject = (JSONObject) JSON.parse(str);
            Object allProperty = jsonObject.get("allProperty");
            for (Object property : (JSONArray) allProperty) {
                Object propertyName = ((JSONObject) property).get("name");
                Object propertyOwner = ((JSONObject) property).get("owner");
                if (!(propertyOwner == null)) {
                    Human propertyhuman = helpParse(propertyOwner.toString());
                    human.addProperty(new MaterialProperty((String) propertyName, propertyhuman));
                } else {
                    human.addProperty(new MaterialProperty((String) propertyName));
                }
            }
            return human;
        } catch (JSONException | NullPointerException e) {
            System.err.println("Can't parse Human\nInvalid syntax\nTry to use \"help\" for more information");
            throw new JSONException();
        }
    }

    public static Human helpParse(String str) throws JSONException {
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
        Object birhday = jsonObject.get("birthday");
        if (birhday != null) human.setBirthday(ZonedDateTime.parse((String) birhday));
        else human.setBirthday(ZonedDateTime.now());
        return human;
    }

    public static String saveToFile(String path, List<Human> list) {
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.println("{\"Humans\" : [");
            boolean i = true;
            for (Human human : list) {
                if (i) {
                    i = false;
                } else {
                    writer.println(",");
                }
                writer.print(JSON.toJSONString(human));
            }
            writer.print("]}");
            return "Save to: " + path;
        } catch (FileNotFoundException e) {
            return "Can't save information in file\nFile not found";
        } catch (JSONException | NullPointerException e) {
            return "Can't save Human to file\nInvalid syntax";
        }
    }

    public static void outCollection(List<Human> humans) {
        humans.forEach(human -> {
            System.out.println(human.toString());
        });
    }
}
