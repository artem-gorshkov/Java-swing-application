package LabsProject.Commands;

import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.io.Serializable;
import java.sql.SQLException;


public class Show implements Command, Serializable {
    @Override
    public Result execute(Reciver reciver) throws SQLException {
        return new Result(((DataBaseReciver) reciver).getAllHuman());
    }
}
