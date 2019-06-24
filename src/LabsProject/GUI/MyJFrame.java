package LabsProject.GUI;

import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.ManagerCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

public class MyJFrame extends JFrame{
    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    private Config config;
    private ModelFrame modelFrame;
    public MyJFrame(String str, Config config, ModelFrame modelFrame) {
        super(str);
        this.config = config;
        this.modelFrame = modelFrame;
        getContentPane().setLayout(new GridBagLayout());
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        JTable table = new JTable(modelFrame.getTable());
        JScrollPane scrollpane = new JScrollPane(table);
        GridBagConstraints CfTH = new GridBagConstraints();
        CfTH.weightx = 100; CfTH.weighty = 100;
        CfTH.gridx = 2; CfTH.gridy = 1;
        CfTH.gridwidth = 4; CfTH.gridheight = 1;
        JLabel tableheader = new JLabel(resource.getString("tableheader"));
        tableheader.setLabelFor(scrollpane);
        getContentPane().add(tableheader, CfTH);

        GridBagConstraints CfT = new GridBagConstraints();
        CfT.weightx = 100; CfT.weighty = 100;
        CfT.gridx = 0; CfT.gridy = 3;
        CfT.gridwidth = 8; CfT.gridheight = 12;
        getContentPane().add(scrollpane, CfT);

        GridBagConstraints CfUL = new GridBagConstraints();
        CfUL.weightx = 100; CfUL.weighty = 100;
        CfUL.gridx = 12; CfUL.gridy = 1;
        CfUL.gridwidth = 4; CfUL.gridheight = 1;
        JLabel UserHeader = new JLabel(resource.getString("userheader"));
        getContentPane().add(UserHeader, CfUL);

        GridBagConstraints CfU = new GridBagConstraints();
        CfU.weightx = 100; CfU.weighty = 100;
        CfU.gridx = 11; CfU.gridy = 3;
        CfU.gridwidth = 6; CfU.gridheight = 4;
        JPanel userPanel = modelFrame.getUserPanel();
        getContentPane().add(userPanel, CfU);

        GridBagConstraints mapHeader = new GridBagConstraints();
        mapHeader.weightx = 100; mapHeader.weighty = 100;
        mapHeader.gridx = 12; mapHeader.gridy = 8;
        mapHeader.gridwidth = 4; mapHeader.gridheight =1;
        JLabel hed = new JLabel(resource.getString("map"));
        getContentPane().add(hed, mapHeader);

        GridBagConstraints map = new GridBagConstraints();
        map.weightx = 100; map.weighty = 100;
        map.gridx = 11; map.gridy = 10;
        map.gridwidth = 6; map.gridheight =5;
        MyCanvas canvas = modelFrame.getCanvas();
        getContentPane().add(canvas, map);

        pack();
    }
}
