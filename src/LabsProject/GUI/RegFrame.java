package LabsProject.GUI;

import LabsProject.Commands.Authorization;
import LabsProject.Commands.Info;
import LabsProject.NetworkInteraction.ConnectionClient;
import LabsProject.NetworkInteraction.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class RegFrame extends JFrame {

    final ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    final String yes = "login_successful";
    final String no = "incorrect";

    public RegFrame(String str) {
        super(str);
        setVisible(true);
        JPanel panel = new JPanel();
        JTextField innick = new JTextField();
        JTextField inpasswd = new JTextField();
        panel.setLayout(new GridLayout(2, 2));
        JLabel nick = new JLabel(resource.getString("nick"), SwingConstants.RIGHT);
        JLabel passwd = new JLabel(resource.getString("password"), SwingConstants.RIGHT);
        panel.add(nick);
        panel.add(innick);
        panel.add(passwd);
        panel.add(inpasswd);
        getContentPane().add(panel, BorderLayout.NORTH);

        JPanel UDPanel = new JPanel();
        JTextField inserver = new JTextField();
        JTextField inport = new JTextField();
        panel.setLayout(new GridLayout(2, 2));
        JLabel server = new JLabel(resource.getString("server"), SwingConstants.RIGHT);
        JLabel port = new JLabel(resource.getString("port"), SwingConstants.RIGHT);
        panel.add(server);
        panel.add(inserver);
        panel.add(port);
        panel.add(inport);
        getContentPane().add(UDPanel, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        JLabel msg = new JLabel("");
        JButton insert = new JButton(resource.getString("login"));
        insert.addActionListener(event -> {
//            try {
//                InetAddress adres =  InetAddress.getByName("localhost");
//                SwingUtilities.invokeLater(() -> new MyJFrame("Lab8", new Config("bob", "2", adres , 8189)));
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }
//        });
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
                msg.setText(resource.getString(res.getAnswer()));

                Config config = new Config(nickname, password, adr, Port, Color.RED.getRGB()); //res.getHumans().get(0).getColor());
                if(res.getAnswer().equals(yes)) {
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
        panel2.setLayout(new GridLayout(3, 1));
        panel2.add(msg);
        panel2.add(insert);
        panel2.add(signin);
        getContentPane().add(panel2, BorderLayout.SOUTH);
        pack();
    }
}
