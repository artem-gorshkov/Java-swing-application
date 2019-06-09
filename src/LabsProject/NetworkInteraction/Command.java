package LabsProject.NetworkInteraction;

import java.io.Serializable;

public class Command implements Serializable {
    public Commands getCommands() {
        return commands;
    }

    private Commands commands;

    public String getArg() {
        return arg;
    }

    private String arg;
    Command(Commands commands, String arg) {
        this.arg = arg;
        this.commands = commands;
    }
    Command(Commands commands) {
        this.commands = commands;
    }
}
