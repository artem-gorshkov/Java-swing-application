package LabsProject.GUI;

import LabsProject.Commands.Command;
import LabsProject.Commands.Remove;
import LabsProject.Commands.SendAndAdd;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.Result;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class RemoveFrame extends JFrame {
    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    private Config config;
    private MyJFrame frame;
    private final Font arial = new Font("Arial", Font.PLAIN, 14);
    private final Font serif = new Font("Serif", Font.PLAIN, 14);
    public RemoveFrame(String str, Config config, MyJFrame frame) {
        super(str);
        this.config = config;
        this.frame = frame;
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        TextField removeF = new TextField();
        add(removeF, BorderLayout.NORTH);

        JPanel panelForButton = new JPanel();
        panelForButton.setFont(arial);
        panelForButton.setLayout(new GridLayout(1,2));
        JButton removeB = new JButton(resource.getString("enter"));
        JButton exitB = new JButton(resource.getString("inexit"));
        removeB.addActionListener(e->{
            Command command = new Remove(removeF.getText());
            command.addNick(config.getNickname());
            Result res = frame.getConn().sendAndGetAnswer(command);
            frame.updateColl(res.getHumans());
            setVisible(false);
        });
        exitB.addActionListener(e -> {
            setVisible(false);
        });
        panelForButton.add(removeB);
        panelForButton.add(exitB);
        add(panelForButton, BorderLayout.SOUTH);
        pack();
    }
}
