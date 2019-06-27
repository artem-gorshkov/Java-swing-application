package LabsProject.GUI;


import LabsProject.Commands.Command;
import LabsProject.Commands.Load;
import LabsProject.Commands.Show;
import LabsProject.Nature.CanFruit;
import LabsProject.Nature.Homosapiens.Condition;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.ConnectionClient;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.NetworkInteraction.Result;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import static LabsProject.Nature.Homosapiens.Condition.*;
import static javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS;

public class MyJFrame extends JFrame {
    final private EmptyBorder emptyBorder = new EmptyBorder(5, 5, 5, 5);
    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    private Config config;
    private MyModelTable modelTable;
    private MyCanvas canvas;
    private JPanel userPanel;
    private ConnectionClient conn;
    private boolean flag = true;
    final private Timer timer = new Timer(500, (event) -> {
        Command show = new Show();
        List<Human> newHum = conn.sendAndGetAnswer(show).getHumans();
        if (modelTable.getHumans() != null && newHum != null)
            if (!(newHum.containsAll(modelTable.getHumans()) && modelTable.getHumans().containsAll(newHum))) {
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
        getContentPane().setLayout(new BorderLayout());
        //setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        this.modelTable = new MyModelTable(res1.getHumans(), this);
        JTable table = new JTable(modelTable);
        table.setFillsViewportHeight(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelTable);
        table.setRowSorter(sorter);

        sorter.setSortable(4, false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        JComboBox<String> condCombo = new JComboBox<>();
        condCombo.addItem("\uD83D\uDE12");
        condCombo.addItem("\uD83D\uDE1E");
        condCombo.addItem("\uD83D\uDE10");
        condCombo.addItem("\uD83D\uDE00");
        TableCellEditor condEditor = new DefaultCellEditor(condCombo);

        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(10);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 1:
                    column.setPreferredWidth(50);
                    break;
                case 2:
                    column.setPreferredWidth(150);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 3:
                    column.setPreferredWidth(75);
                    break;
                case 4:
                    column.setPreferredWidth(50);
                    column.setCellRenderer(new EmogiTableCellRenderer());
                    column.setCellEditor(condEditor);
                    break;
            }
        }


        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(700, 400));


        JLabel tableheader = new JLabel(resource.getString("tableheader"));
        tableheader.setFont(new Font("Serif", Font.BOLD, 16));
        tableheader.setHorizontalAlignment(JLabel.CENTER);
        tableheader.setLabelFor(scrollpane);


        scrollpane.setBorder(BorderFactory.createLineBorder(Color.black));


        JLabel UserHeader = new JLabel(resource.getString("userheader"));
        UserHeader.setFont(new Font("Serif", Font.BOLD, 16));
        UserHeader.setHorizontalAlignment(JLabel.CENTER);


        this.userPanel = new UserPanel(config, this);
        userPanel.setFont(new Font("Serif", Font.PLAIN, 14));
        userPanel.setBorder(emptyBorder);


        JLabel hed = new JLabel(resource.getString("map"));
        hed.setFont(new Font("Serif", Font.BOLD, 16));
        hed.setHorizontalAlignment(JLabel.CENTER);


        JPanel panelForMap = new JPanel();
        this.canvas = new MyCanvas(res1.getHumans());
        canvas.paint(getGraphics());
        JPanel panelForCanvas = new JPanel();
        panelForCanvas.setBorder(BorderFactory.createLineBorder(Color.black));
        panelForCanvas.add(canvas);
        panelForMap.setLayout(new BorderLayout());
        panelForMap.add(hed, BorderLayout.NORTH);
        panelForMap.add(panelForCanvas, BorderLayout.SOUTH);

        JPanel panelforheader = new JPanel();
        panelforheader.setLayout(new GridLayout(1, 2));
        panelforheader.add(tableheader);
        panelforheader.add(UserHeader);
        panelforheader.setBorder(emptyBorder);
        getContentPane().add(panelforheader, BorderLayout.NORTH);
        JPanel panelForLeftSide = new JPanel();
        panelForLeftSide.setLayout(new CardLayout());
        panelForLeftSide.add(scrollpane);
        panelForLeftSide.setBorder(emptyBorder);
        getContentPane().add(panelForLeftSide, BorderLayout.WEST);

        JPanel panelForRightSide = new JPanel();
        panelForRightSide.setLayout(new BoxLayout(panelForRightSide, BoxLayout.Y_AXIS));
        JPanel PanelForUserPanel = new JPanel();
        PanelForUserPanel.setLayout(new CardLayout());
        PanelForUserPanel.add(userPanel);
        PanelForUserPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        panelForRightSide.add(PanelForUserPanel);
        panelForRightSide.add(Box.createVerticalStrut(5));
        panelForRightSide.add(panelForMap);
        panelForRightSide.setBorder(emptyBorder);

        getContentPane().add(panelForRightSide, BorderLayout.EAST);
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

    public Config getConfig() {
        return config;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
