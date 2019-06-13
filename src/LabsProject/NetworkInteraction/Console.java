package LabsProject.NetworkInteraction;


import LabsProject.Commands.*;
import com.alibaba.fastjson.JSONException;

import java.util.Scanner;
import java.util.regex.Pattern;


public class Console {
    private final Scanner scan = new Scanner(System.in);
    private String nick;
    private String passwd;
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
    public static final String AskLogin = "You need to log in (or sing in). Enter Nickname or write \"signin\"";
    public static final String emailIn = "Enter your email: ";
    public static final String enterNick = "Enter nick: ";

    public Command authorization() {
        System.out.println(AskLogin);
        System.out.print("Nick: ");
        String nick = scan.nextLine();
        if(nick.equals("signin")) {
            System.out.print(emailIn);
            String email = scan.nextLine();
//            while(!Pattern.matches("[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{3}", email)){
//                System.err.println("Inccorrect email format");
//                System.out.print(emailIn);
//                email = scan.nextLine();
//            }
            System.out.print(enterNick);
            String nickname = scan.nextLine();
            Registration reg = new Registration(email);
            reg.addNick(nickname);
            return reg;
        }
        System.out.print("Password: ");
        //char[] passwd = System.console().readPassword();
        String ps = scan.nextLine();
        this.nick = nick;
        this.passwd = ps;//new String(passwd);
        Authorization command = new Authorization(this.passwd);
        command.addNick(this.nick);
        return command;
    }

    public Command giveCommand() {
        boolean k = true;
        while (k) {
            Command command = getCommand();
            if (!(command == null)) {
                k = false;
                command.addNick(nick);
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
                            try {
                                ManagerCollection.parseHuman(arg);
                                command = new Insert(arg);
                            } catch (JSONException e) {
                                System.err.println(invalidSyntax);
                            }
                        break;
                    case "add_if_max":
                        try {
                            ManagerCollection.parseHuman(arg);
                            command = new Add_If_Max(arg);
                        } catch (JSONException e) {
                            System.err.println(invalidSyntax);
                        }
                        break;
                    case "remove_greater_key":
                        command = new Remove_greater_key(arg);
                        break;
                    case "remove":
                        command = new Remove(arg);
                        break;
                    case "import":
                        command = new Import(arg);
                        break;
                    case "load":
                        command = new Load(arg);
                        break;
                    case "save":
                        command = new Save(arg);
                        break;
                    default:
                        NotFoundCmnd(cmnd);
                }
            }
        } else {
            cmnd = callcmnd;
            switch (cmnd) {
                case "show":
                    command = new Show();
                    break;
                case "exit":
                    System.exit(8);
                    break;
                case "info":
                    command = new Info();
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