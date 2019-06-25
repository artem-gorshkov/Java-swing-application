package LabsProject.GUI;

import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.MainClient;
import LabsProject.NetworkInteraction.ManagerCollection;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.applet.Applet;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SwingApp {
    static ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    public static boolean regi = true;

    static private Config config;
    public static Object lock = new Object();

    public static void main(String[] args) {
        System.out.println(Color.RED.getRGB());
        SwingUtilities.invokeLater(() -> {
            new RegFrame("Lab8");
        });

        synchronized (lock) {
            while(regi) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }

        }
        SwingUtilities.invokeLater(() -> {
            MyJFrame mainFrame = new MyJFrame("Lab8", config);
        });

    }

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config config2) {
        config = config2;
    }
}
