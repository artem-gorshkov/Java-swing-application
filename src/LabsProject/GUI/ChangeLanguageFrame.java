package LabsProject.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class ChangeLanguageFrame  extends JFrame {
    private final Font serif2 = new Font("Serif", Font.PLAIN, 14);
    public ChangeLanguageFrame(String str) {
        super(str);
        setVisible(true);
        setLocationRelativeTo(null);
        setGlobalFont(serif2);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        //русский, словенский, литовский и испанский (Мексика)
        JButton Russian = new JButton("Russian");
        JButton Slovenian = new JButton("Slovenian");
        JButton Lithuanian = new JButton("Lithuanian");
        JButton Spanish = new JButton("Spanish");
        panel.add(Russian);
        panel.add(Slovenian);
        panel.add(Lithuanian);
        panel.add(Spanish);
        getContentPane().add(panel);
        pack();
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
