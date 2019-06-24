package LabsProject.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class UserPanel extends JPanel {
    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");

    public UserPanel(Config config) {
        setLayout(new GridLayout(3, 2));
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
    }
}
