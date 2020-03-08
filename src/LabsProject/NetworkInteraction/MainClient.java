package LabsProject.NetworkInteraction;


import LabsProject.Commands.Authorization;
import LabsProject.Commands.Command;
import LabsProject.Commands.Registration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class MainClient {
    private static InetAddress serverAddress;
    private static Integer port;
    static private Scanner scan = new Scanner(System.in);

    public static void main(String args[]) {
        if (args == null) {
            enterHost();
            enterPort();
        } else {
            try {
                serverAddress = InetAddress.getByName(args[0]);
            } catch (Throwable e) {
                System.err.println("Unknown Host");
                enterHost();
            }
            try {
                port = Integer.parseInt(args[1]);
            } catch (Throwable e) {
                System.err.println("Port must be number");
                enterPort();
            }
        }
        System.out.println("Hello!");
        ConnectionClient conn = new ConnectionClient(serverAddress, port);
        Console console = new Console();
        Command inComm; Result answer;
        do {
            inComm = console.authorization();
            answer = conn.sendAndGetAnswer(inComm);
        }  while (!conn.handlePacket(answer, (Authorization) inComm));
        if(inComm instanceof Registration)
            do {
                inComm = console.authorization();
                answer = conn.sendAndGetAnswer(inComm);
            }  while (!conn.handlePacket(answer, (Authorization) inComm));
        System.out.println("Now you can enter command. Press \"help\" for more information");
        while (true) {
            Command command = console.giveCommand();
            Result result = conn.sendAndGetAnswer(command);
            conn.handlePacket(result, command);
        }

    }

    private static void enterHost() {
        InetAddress Address;
        boolean k = true;
        System.out.println("Enter host");
        while (k) {
            try {
                Address = InetAddress.getByName(scan.nextLine());
                k = false;
                serverAddress = Address;
            } catch (UnknownHostException e) {
                System.err.println("Unknown Host");
            }
        }
    }

    private static void enterPort() {
        System.out.println("Enter port");
        boolean k = true;
        while (k) {
            if (scan.hasNextInt()) {
                port = scan.nextInt();
                k = false;
            } else {
                System.err.println("Port must be number");
                scan.nextLine();
            }
        }
    }
}
