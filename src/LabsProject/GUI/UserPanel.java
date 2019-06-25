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

    private Command command;
    private MyJFrame frame;

    public UserPanel(Config config, MyJFrame frame) {
        this.frame = frame;
        this.config = config;
        setLayout(new GridLayout(4, 2));
        this.
        add(new JLabel(resource.getString("name")));
        add(new JLabel(config.getNickname()));
        add(new JLabel(resource.getString("yourColor")));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(config.getColor()));
        add(panel);
        TextField console = new TextField();
        add(console);
        JButton changeLanguage = new JButton(resource.getString("changeLanguage"));
        add(changeLanguage);
        JButton sendCommand = new JButton(resource.getString("sendCommand"));
        sendCommand.addActionListener((event) -> {
            if (!console.getText().equals("")) {
                this.command = Console.getCommandFromGUI(console.getText());
                console.setText("");
                if (command !=  null) {
                    command.addNick(config.getNickname());
                    Result res = frame.getConn().sendAndGetAnswer(command);
                    ManagerCollection.outCollection(res.getHumans());
                    System.out.println(res.getAnswer());
                    frame.updateColl(res);
                }
            }
        });
        add(sendCommand);
        JButton exitButton = new JButton("exit");
        add(exitButton);
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
