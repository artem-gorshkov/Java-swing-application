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

public class SwingApp {
    ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");

    public static void main(String[] args) {
        ModelFrame modelFrame = new ModelFrame();
        SwingUtilities.invokeLater(() -> {
            JFrame gerframe = new RegFrame("Lab8", modelFrame);
        });
    }
}
