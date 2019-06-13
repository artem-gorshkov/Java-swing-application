package LabsProject.Commands;


import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;

public interface Command {
    Result execute(Reciver reciver) throws SQLException;
    public void addNick(String nick);
}
