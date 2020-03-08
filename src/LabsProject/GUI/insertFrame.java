package LabsProject.GUI;

import LabsProject.Commands.Insert;
import LabsProject.Commands.SendAndAdd;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.Result;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class insertFrame extends JFrame {
    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    private Config config;
    private MyJFrame frame;
    private final Font arial = new Font("Arial", Font.PLAIN, 14);
    private final Font serif = new Font("Serif", Font.PLAIN, 14);
    public insertFrame(String str, Config config, MyJFrame frame) {
        super(str);
        this.config = config;
        this.frame = frame;
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        //Paneeeeeel
        JPanel enPanel = new JPanel();
        enPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        enPanel.setFont(serif);

        GridLayout layout = new GridLayout(6,2);
        enPanel.setLayout(layout);

        JLabel nameL = new JLabel(resource.getString("name"));
        nameL.setHorizontalAlignment(SwingConstants.CENTER);
        enPanel.add(nameL);

        TextField nameT = new TextField(12);
        nameT.setFont(arial);
        enPanel.add(nameT);

        JLabel timeL = new JLabel(resource.getString("time"));
        timeL.setHorizontalAlignment(SwingConstants.CENTER);
        enPanel.add(timeL);

        TextField timeT = new TextField(12);
        timeT.setFont(arial);
        enPanel.add(timeT);

        JLabel zoneL = new JLabel(resource.getString("zone"));
        zoneL.setHorizontalAlignment(SwingConstants.CENTER);
        enPanel.add(zoneL);

        TextField zoneT = new TextField();
        zoneT.setFont(arial);
        enPanel.add(zoneT);

        JLabel cordxL = new JLabel(resource.getString("cordX"));
        cordxL.setHorizontalAlignment(SwingConstants.CENTER);
        enPanel.add(cordxL);

        TextField cordxT = new TextField();
        cordxT.setFont(arial);
        enPanel.add(cordxT);

        JLabel cordyL = new JLabel(resource.getString("cordY"));
        cordyL.setHorizontalAlignment(SwingConstants.CENTER);
        enPanel.add(cordyL);

        TextField cordyT = new TextField();
        cordyT.setFont(arial);
        enPanel.add(cordyT);

        JLabel condition = new JLabel(resource.getString("condition"));
        condition.setHorizontalAlignment(SwingConstants.CENTER);
        enPanel.add(condition);

        JComboBox<String> condCombo = new JComboBox<>();
        condCombo.addItem("\uD83D\uDE12");
        condCombo.addItem("\uD83D\uDE1E");
        condCombo.addItem("\uD83D\uDE10");
        condCombo.addItem("\uD83D\uDE00");
        enPanel.add(condCombo);

        //Paneeeeel
        add(enPanel, BorderLayout.NORTH);
        JPanel panelForButton = new JPanel();
        panelForButton.setFont(arial);
        panelForButton.setLayout(new GridLayout(1,2));
        JButton enterB = new JButton(resource.getString("enter"));
        JButton exitB = new JButton(resource.getString("inexit"));
        enterB.addActionListener(e->{
            Human human = new Human(nameT.getText());
            LocalDateTime time = LocalDateTime.parse(timeT.getText(), formatter);
            ZonedDateTime newtime = ZonedDateTime.of(time, ZoneId.of(zoneT.getText()));
            human.setBirthday(newtime);
            human.setCondition(MyModelTable.getCond((String) condCombo.getSelectedItem()));
            human.setCords(Double.parseDouble(cordxT.getText()), Double.parseDouble(cordyT.getText()));
            SendAndAdd send = new SendAndAdd(null,human);
            send.addNick(config.getNickname());
            Result res = frame.getConn().sendAndGetAnswer(send);
            frame.updateColl(res.getHumans());
            setVisible(false);
        });
        exitB.addActionListener(e -> {
        setVisible(false);
        });
        panelForButton.add(enterB);
        panelForButton.add(exitB);
        add(panelForButton, BorderLayout.SOUTH);
        pack();
    }
}
