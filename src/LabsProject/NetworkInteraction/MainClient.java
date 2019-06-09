package LabsProject.NetworkInteraction;



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
        System.out.println("Hello, Lab6, Try to use \"help\" to get more information");
        ConnectionClient connection = new ConnectionClient(serverAddress, port);
        Console console = new Console();
        while (true) {
            Command command = console.exec();
            connection.send(command);
            if (connection.getInterruptedCommand() != null) {
                connection.send(connection.getInterruptedCommand());
                connection.setInterruptedCommand(null);
            }
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
