package LabsProject.GUI;

import LabsProject.Commands.Authorization;
import LabsProject.Commands.Info;
import LabsProject.Commands.Registration;
import LabsProject.NetworkInteraction.ConnectionClient;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.NetworkInteraction.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class RegFrame extends JFrame {

    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    final String yes = "login_successful";
    final String no = "incorrect";
    private final Font arial = new Font("Arial", Font.PLAIN, 14);
    private final Font serif = new Font("Serif", Font.BOLD, 16);
    private final Font serif2 = new Font("Serif", Font.PLAIN, 14);

    public RegFrame(String str) {
        super(str);
        setLocationRelativeTo(null);
        setGlobalFont(arial);
        setVisible(true);
        JPanel panel = new JPanel();
        JTextField innick = new JTextField(8);
        JTextField inpasswd = new JTextField(8);
        panel.setLayout(new GridLayout(2, 2));
        JLabel nick = new JLabel(resource.getString("nick"), SwingConstants.CENTER);
        nick.setFont(serif2);
        JLabel passwd = new JLabel(resource.getString("password"), SwingConstants.CENTER);
        passwd.setFont(serif2);
        panel.add(nick);
        panel.add(innick);
        panel.add(passwd);
        panel.add(inpasswd);
        getContentPane().add(panel, BorderLayout.NORTH);

        JPanel UDPanel = new JPanel();
        JTextField inserver = new JTextField();
        JTextField inport = new JTextField();
        panel.setLayout(new GridLayout(2, 2));
        JLabel server = new JLabel(resource.getString("server"), SwingConstants.CENTER);
        server.setFont(serif2);
        JLabel port = new JLabel(resource.getString("port"), SwingConstants.CENTER);
        port.setFont(serif2);
        panel.add(server);
        panel.add(inserver);
        panel.add(port);
        panel.add(inport);
        getContentPane().add(UDPanel, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        JLabel msg = new JLabel("");
        JButton insert = new JButton(resource.getString("login"));
        insert.addActionListener(event -> {
            try {
//                String password = inpasswd.getText();
//                String nickname = innick.getText();
//                InetAddress adr = InetAddress.getByName(inserver.getText());
//                int Port = Integer.parseInt(inport.getText());
                String password = "n!;WrXU;+3c4";
                String nickname = "Bob";
                InetAddress adr = InetAddress.getByName("localhost");
                int Port = 8189;
                Authorization command = new Authorization(password);
                command.addNick(nickname);
                ConnectionClient conn = new ConnectionClient(adr, Port);
                Result res = conn.sendAndGetAnswer(command);
                if (res.getAnswer().equals(yes)) {
                    msg.setText(resource.getString(res.getAnswer()));
                    Config config = new Config(nickname, password, adr, Port, res.getHumans().get(0).getColor());
                    SwingApp.setConfig(config);
                    SwingApp.regi = false;
                    synchronized (SwingApp.lock) {
                        SwingApp.lock.notifyAll();
                    }
                    setVisible(false);
                }
            } catch (UnknownHostException e) {
                msg.setText(resource.getString("hosterror"));
            } catch (NumberFormatException e) {
                msg.setText(resource.getString("porterror"));
            } catch (Throwable e) {
                e.printStackTrace();
                msg.setText(resource.getString("error"));
            }
        });
        JButton signin = new JButton(resource.getString("signin"));
        signin.addActionListener((event) -> {
            JFrame regi = new JFrame("Registration");
            regi.setVisible(true);
            regi.setLocationRelativeTo(null);
            JPanel panel1 = new JPanel();
            JTextField email = new JTextField(12);
            email.setFont(new Font("Arial", Font.PLAIN, 14));
            JTextField newnick = new JTextField(12);
            newnick.setFont(new Font("Arial", Font.PLAIN, 14));
            panel1.setLayout(new GridLayout(2, 2));
            JLabel emailLabel = new JLabel(resource.getString("email"), SwingConstants.CENTER);
            emailLabel.setFont(new Font("Serif", Font.PLAIN, 14));
            JLabel newnickLabel = new JLabel(resource.getString("nick"), SwingConstants.CENTER);
            newnickLabel.setFont(new Font("Serif", Font.PLAIN, 14));
            panel1.add(emailLabel);
            panel1.add(email);
            panel1.add(newnickLabel);
            panel1.add(newnick);
            regi.getContentPane().add(panel1, BorderLayout.NORTH);
            JButton regB = new JButton(resource.getString("reg"));
            regB.addActionListener((event1) -> {
                try {
                    String emailText = email.getText();
                    String nickText = newnick.getText();
                    InetAddress adr = InetAddress.getByName("localhost");
                    int Port = 8189;
                    Registration command = new Registration(emailText);
                    command.addNick(nickText);
                    ConnectionClient conn = new ConnectionClient(adr, Port);
                    Result res = conn.sendAndGetAnswer(command);
                    regi.setVisible(false);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
            regi.add(regB);
            regi.pack();
        });
        panel2.setLayout(new GridLayout(3, 1));
        panel2.add(msg);
        panel2.add(insert);
        panel2.add(signin);
        getContentPane().add(panel2, BorderLayout.SOUTH);
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
