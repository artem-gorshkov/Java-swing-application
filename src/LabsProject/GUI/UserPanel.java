package LabsProject.GUI;

import LabsProject.Commands.Command;
import LabsProject.NetworkInteraction.ConnectionClient;
import LabsProject.NetworkInteraction.Console;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.NetworkInteraction.Result;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class UserPanel extends JPanel {
    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    private Config config;
    private final Font arial = new Font("Arial", Font.PLAIN, 14);
    private final Font serif = new Font("Serif", Font.BOLD, 16);

    private Command command;
    private MyJFrame frame;

    public UserPanel(Config config, MyJFrame frame) {
        this.frame = frame;
        this.config = config;
        GridLayout layout = new GridLayout(4, 2);
        layout.setHgap(10);
        layout.setVgap(5);
        setLayout(layout);
        JLabel NameHeader = new JLabel(resource.getString("name"));
        NameHeader.setHorizontalAlignment(JLabel.CENTER);
        add(NameHeader);
        JLabel Name = new JLabel(config.getNickname());
        Name.setFont(serif);
        Name.setHorizontalAlignment(JLabel.CENTER);
        add(Name);
        JLabel colorHeader  = new JLabel(resource.getString("yourColor"));
        colorHeader.setHorizontalAlignment(JLabel.CENTER);
        add(colorHeader);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(config.getColor()));
        add(panel);
        TextField console = new TextField();
        console.setFont(arial);
        add(console);
        JButton changeLanguage = new JButton(resource.getString("changeLanguage"));
        changeLanguage.addActionListener((event) -> {
            JFrame change = new ChangeLanguageFrame("Change language");
        });
        changeLanguage.setFont(arial);
        add(changeLanguage);
        JButton sendCommand = new JButton(resource.getString("sendCommand"));
        sendCommand.setFont(arial);
        sendCommand.addActionListener((event) -> {
            if (!console.getText().equals("")) {
                this.command = Console.getCommandFromGUI(console.getText());
                console.setText("");
                if (command !=  null) {
                    command.addNick(config.getNickname());
                    Result res = frame.getConn().sendAndGetAnswer(command);
                    frame.updateColl(res.getHumans());
                }
            }
        });
        add(sendCommand);
        JButton exitButton = new JButton(resource.getString("exit"));
        exitButton.addActionListener((event) -> {
            System.exit(0);
        });
        exitButton.setFont(arial);
        add(exitButton);
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
