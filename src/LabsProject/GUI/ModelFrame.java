package LabsProject.GUI;

import LabsProject.Commands.Authorization;
import LabsProject.Commands.Show;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.ConnectionClient;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.NetworkInteraction.Result;

import javax.swing.*;
import java.util.List;

public class ModelFrame {
    private MyJFrame mainFrame;
    private Config config;
    private ConnectionClient conn;
    private MyModelTable table;

    public MyCanvas getCanvas() {
        return canvas;
    }

    private MyCanvas canvas;

    public UserPanel getUserPanel() {
        return userPanel;
    }

    private UserPanel userPanel;

    public MyModelTable getTable() {
        return table;
    }



    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }


    public void PaintMainFrame(Config config) {
        this.config = config;
        this.conn = new ConnectionClient(config.getAddress(), config.getPort());
        Show command = new Show();
        command.addNick(config.getNickname());
        Result res = conn.sendAndGetAnswer(command);
        List<Human> humans = res.getHumans();
        table = new MyModelTable(humans);
        userPanel = new UserPanel(config);
        canvas = new MyCanvas();
        SwingUtilities.invokeLater(() ->{
            mainFrame = new MyJFrame("Lab8", config, this);
        });

    }


}
