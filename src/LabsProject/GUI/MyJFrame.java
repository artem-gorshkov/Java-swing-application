package LabsProject.GUI;


import LabsProject.Commands.Command;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import static javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS;

public class MyJFrame extends JFrame {
    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    private Config config;
    private MyModelTable modelTable;
    private MyCanvas canvas;
    private JPanel userPanel;
    private ConnectionClient conn;
    final private Timer timer = new Timer(500, (event) -> {
        Command show = new Show();
        List<Human> newHum = conn.sendAndGetAnswer(show).getHumans();
        if (modelTable.getHumans() != null && newHum != null)
        if(!(newHum.containsAll(modelTable.getHumans()) && modelTable.getHumans().containsAll(newHum))) {
            System.out.println("pererisvivau");
            updateColl(newHum);
        }
    });

    public MyJFrame(String str, Config config) {
        super(str);
        setLocationRelativeTo(null);
        setGlobalFont(new Font("Serif", Font.PLAIN, 14));
        this.config = config;
        conn = new ConnectionClient(config.getAddress(), config.getPort());
        Result res1 = conn.sendAndGetAnswer(new Show());
        getContentPane().setLayout(new GridBagLayout());
        //setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        this.modelTable = new MyModelTable(res1.getHumans());
        JTable table = new JTable(modelTable);
        table.setFillsViewportHeight(true);
        TableColumn column = null;
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        DefaultTableCellRenderer emojiRenderer = new DefaultTableCellRenderer();
        emojiRenderer.setHorizontalAlignment( JLabel.CENTER );
        emojiRenderer.setFont(new Font("Serif", Font.BOLD, 18));
        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(25);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 1:
                    column.setPreferredWidth(100);
                    break;
                case 2:
                    column.setPreferredWidth(150);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 3:
                    column.setPreferredWidth(50);
                    break;
                case 4:
                    column.setPreferredWidth(50);
                    column.setCellRenderer(emojiRenderer);
                    break;
            }
            column.setCellRenderer(emojiRenderer);

        }
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(700, 400));


        GridBagConstraints CfTH = new GridBagConstraints();
        CfTH.weightx = 100;
        CfTH.weighty = 100;
        CfTH.gridx = 1;
        CfTH.gridy = 0;
        CfTH.gridwidth = 2;
        CfTH.gridheight = 1;
        JLabel tableheader = new JLabel(resource.getString("tableheader"));
        tableheader.setFont(new Font("Serif", Font.BOLD, 16));
        tableheader.setHorizontalAlignment(JLabel.CENTER);
        tableheader.setLabelFor(scrollpane);
        CfTH.insets = new Insets(5, 5, 0, 5);
        getContentPane().add(tableheader, CfTH);

        GridBagConstraints CfT = new GridBagConstraints();
        CfT.weightx = 100;
        CfT.weighty = 100;
        CfT.gridx = 0;
        CfT.gridy = 1;
        CfT.gridwidth = 10;
        CfT.gridheight = 20;
        CfTH.insets = new Insets(0, 5, 5, 5);
        scrollpane.setBorder(BorderFactory.createLineBorder(Color.black));
        getContentPane().add(scrollpane, CfT);

        GridBagConstraints CfUL = new GridBagConstraints();
        CfUL.weightx = 100;
        CfUL.weighty = 100;
        CfUL.gridx = 10;
        CfUL.gridy = 0;
        CfUL.gridwidth = 10;
        CfUL.gridheight = 1;
        CfUL.insets = new Insets(5, 5, 5, 5);
        JLabel UserHeader = new JLabel(resource.getString("userheader"));
        UserHeader.setFont(new Font("Serif", Font.BOLD, 16));
        UserHeader.setHorizontalAlignment(JLabel.CENTER);
        getContentPane().add(UserHeader, CfUL);

        GridBagConstraints CfU = new GridBagConstraints();
        CfU.weightx = 100;
        CfU.weighty = 100;
        CfU.gridx = 10;
        CfU.gridy = 1;
        CfU.gridwidth = 10;
        CfU.gridheight = 5;
        CfU.insets = new Insets(5, 5, 5, 5);
        this.userPanel = new UserPanel(config, this);
        userPanel.setFont(new Font("Serif", Font.PLAIN, 14));
        userPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        getContentPane().add(userPanel, CfU);


        GridBagConstraints mapHeader = new GridBagConstraints();
        mapHeader.weightx = 100;
        mapHeader.weighty = 100;
        mapHeader.gridx = 10;
        mapHeader.gridy = 6;
        mapHeader.gridwidth = 10;
        mapHeader.gridheight = 1;
        mapHeader.insets = new Insets(5, 5, 5, 5);
        JLabel hed = new JLabel(resource.getString("map"));
        hed.setFont(new Font("Serif", Font.BOLD, 16));
        hed.setHorizontalAlignment(JLabel.CENTER);
        getContentPane().add(hed, mapHeader);

        GridBagConstraints map = new GridBagConstraints();
        map.weightx = 100;
        map.weighty = 100;
        map.gridx = 10;
        map.gridy = 7;
        map.gridwidth = 10;
        map.gridheight = 13;
        map.insets = new Insets(5, 5, 5, 5);
        JPanel panelForMap = new JPanel();
        this.canvas = new MyCanvas(res1.getHumans());
        canvas.paint(getGraphics());
        panelForMap.add(canvas);
        panelForMap.setBorder(BorderFactory.createLineBorder(Color.black));
        getContentPane().add(panelForMap, map);
        pack();
        timer.start();
    }

    public void updateColl(List<Human> humans) {
        modelTable.setHumans(humans);
        modelTable.fireTableDataChanged();
        canvas.setHumans(humans);
    }

    public ConnectionClient getConn() {
        return conn;
    }

    public static void setGlobalFont(Font font) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                UIManager.put(key, font);
            }
        }
    }
}
