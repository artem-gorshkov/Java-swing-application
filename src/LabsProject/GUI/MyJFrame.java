package LabsProject.GUI;

import LabsProject.Commands.Load;
import LabsProject.Commands.Show;
import LabsProject.Nature.CanFruit;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.ConnectionClient;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.NetworkInteraction.Result;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

public class MyJFrame extends JFrame{
    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    private Config config;
    private MyModelTable modelTable;
    private MyCanvas canvas;
    private JPanel userPanel;
    private ConnectionClient conn;

    public MyJFrame(String str, Config config) {
        super(str);
        this.config = config;
        conn = new ConnectionClient(config.getAddress(), config.getPort());
        Result res1 = conn.sendAndGetAnswer(new Show());
        //System.out.println(res1.getHumans());
        this.setFont(new Font("Consolas", Font.PLAIN, 15));
        getContentPane().setLayout(new GridBagLayout());
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        this.modelTable = new MyModelTable(res1.getHumans());
        JTable table = new JTable(modelTable);
        JScrollPane scrollpane = new JScrollPane(table);
        GridBagConstraints CfTH = new GridBagConstraints();
        CfTH.weightx = 100; CfTH.weighty = 100;
        CfTH.gridx = 1; CfTH.gridy = 0;
        CfTH.gridwidth = 2; CfTH.gridheight = 1;
        JLabel tableheader = new JLabel(resource.getString("tableheader"));
        tableheader.setLabelFor(scrollpane);
        getContentPane().add(tableheader, CfTH);

        GridBagConstraints CfT = new GridBagConstraints();
        CfT.weightx = 100; CfT.weighty = 100;
        CfT.gridx = 0; CfT.gridy = 1;
        CfT.gridwidth = 4; CfT.gridheight = 12;
        scrollpane.setBorder(BorderFactory.createLineBorder(Color.black));
        getContentPane().add(scrollpane, CfT);

        GridBagConstraints CfUL = new GridBagConstraints();
        CfUL.weightx = 100; CfUL.weighty = 100;
        CfUL.gridx = 5; CfUL.gridy = 0;
        CfUL.gridwidth = 2; CfUL.gridheight = 1;
        JLabel UserHeader = new JLabel(resource.getString("userheader"));
        getContentPane().add(UserHeader, CfUL);

        GridBagConstraints CfU = new GridBagConstraints();
        CfU.weightx = 100; CfU.weighty = 100;
        CfU.gridx = 4; CfU.gridy = 1;
        CfU.gridwidth = 4; CfU.gridheight = 3;
        this.userPanel = new UserPanel(config, this);
        userPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        getContentPane().add(userPanel, CfU);


        GridBagConstraints mapHeader = new GridBagConstraints();
        mapHeader.weightx = 100; mapHeader.weighty = 100;
        mapHeader.gridx = 5; mapHeader.gridy = 4;
        mapHeader.gridwidth = 2; mapHeader.gridheight =1;
        JLabel hed = new JLabel(resource.getString("map"));
        getContentPane().add(hed, mapHeader);

        GridBagConstraints map = new GridBagConstraints();
        map.weightx = 100; map.weighty = 100;
        map.gridx = 4; map.gridy = 5;
        map.gridwidth = 4; map.gridheight =8;
        JPanel panelForMap = new JPanel();
        this.canvas = new MyCanvas();
        panelForMap.add(canvas);
        panelForMap.setBorder(BorderFactory.createLineBorder(Color.black));
        getContentPane().add(panelForMap, map);
        pack();
    }
     public void updateColl(Result res) {
        modelTable.setHumans(res.getHumans());
        modelTable.fireTableDataChanged();
        canvas.setHumans(res.getHumans());
     }

    public ConnectionClient getConn() {
        return conn;
    }

}
