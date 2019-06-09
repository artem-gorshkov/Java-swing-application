package LabsProject.NetworkInteraction;

import java.io.*;

public enum Commands implements Serializable {
    SHOW("show"), EXIT("exit"), HELP("help"), INFO("info"), INSERT("insert"),
    ADD_IF_MAX("add_if_max"), REMOVE_GREATER_KEY("remove_greater_key"), REMOVE("remove"),
    IMPORT("import"), LOAD("load"), SAVE("save"), REQUEST("request");

    public String getText() {
        return text;
    }

    private String text;
    Commands(String text) {
        this.text = text;
    }

}
