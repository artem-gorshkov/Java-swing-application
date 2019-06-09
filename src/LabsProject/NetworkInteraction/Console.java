package LabsProject.NetworkInteraction;


import com.alibaba.fastjson.JSONException;

import java.util.Scanner;


public class Console {
    private final Scanner scan = new Scanner(System.in);
    private static final String help = "Usage:" +
            "\ninfo - Show info about type of collection, number of collection elements, time from star program, time of file creation" +
            "\ninsert {String key} {element} - Add to collection one element with specific key" +
            "\nremove {String key} - Delete element with specific key(arg) from collection" +
            "\nremove_greater_key {String key} - Delete form collection all elements whose key larger than arg" +
            "\nimport {String path} - Add to collection all element from specific(arg) file from client computer" +
            "\nadd_if_max {element} - Add new element to collection, if his value bigger then max value in collection" +
            "\nshow - Show all element from collection" +
            "\nexit - Exit from program" +
            "\nload {String path} -  Add to collection all element from specific(arg) file from server" +
            "\nsave {String path} - Save collection to specific(arg) file on server";
    private static final String invalidSyntax = "Invalid syntax\nTry to use \"help\" to get more information";
    private static final String notFoundArgument = "Not found argument\nTry to use \"help\" to get more information";

    public Command exec() {
        boolean k = true;
        while (k) {
            Command command = getCommand();
            if (!(command == null)) {
                k = false;
                return command;
            }
        }
        return null;
    }

    Command getCommand() {
        Command command = null;
        String callcmnd = scan.nextLine();
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
                switch (cmnd) {
                    case "insert":
                        if (!arg.matches("\\w*\\s\\{[\\s\\S]*\\}")) {
                            System.err.println(invalidSyntax);
                        } else {
                            try {
                                String element = arg.substring(arg.indexOf(' ') + 1);
                                ManagerCollection.parseHuman(element);
                                command = new Command(Commands.INSERT, arg);
                            } catch (JSONException e) {
                            }
                        }
                        break;
                    case "add_if_max":
                        try {
                            String element = arg.substring(arg.indexOf(' ') + 1);
                            ManagerCollection.parseHuman(element);
                            command = new Command(Commands.ADD_IF_MAX, arg);
                        } catch (JSONException e) {
                        }
                        break;
                    case "remove_greater_key":
                        command = new Command(Commands.REMOVE_GREATER_KEY, arg);
                        break;
                    case "remove":
                        command = new Command(Commands.REMOVE, arg);
                        break;
                    case "import":
                        command = new Command(Commands.IMPORT, arg);
                        break;
                    case "load":
                        command = new Command(Commands.LOAD, arg);
                        break;
                    case "save":
                        command = new Command(Commands.SAVE, arg);
                        break;
                    default:
                        NotFoundCmnd(cmnd);
                }
            }
        } else {
            cmnd = callcmnd;
            switch (cmnd) {
                case "show":
                    command = new Command(Commands.SHOW);
                    break;
                case "exit":
                    command = new Command(Commands.EXIT);
                    break;
                case "info":
                    command = new Command(Commands.INFO);
                    break;
                case "help":
                    help();
                    break;
                case "insert":
                case "add_if_max":
                case "remove_greater_key":
                case "remove":
                case "import":
                case "load":
                case "save":
                    NotFoundArgument();
                    break;
                default:
                    if (!cmnd.equals(""))
                        NotFoundCmnd(cmnd);
            }
        }
        return command;
    }

    /**
     * Help show all getCommand in program.
     */
    private void help() {
        System.out.println(help);
    }

    private void NotFoundCmnd(String cmnd) {

        System.out.println("Not found \"" + cmnd + "\"\nTry to use \"help\" to get more information");

    }

    private void DontneedArg(String cmnd) {
        System.out.println(cmnd + " don't need argument\nTry to use \"help\" to get more information");
    }

    private void NotFoundArgument() {
        System.out.println(notFoundArgument);
    }
}