package LabsProject.Commands;

import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.io.Serializable;
import java.sql.SQLException;

public class Info implements Command, Serializable {

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DataBaseReciver DBreciver = (DataBaseReciver) reciver;
        String answer = "Number of element: " + DBreciver.numberOfElement() + ", Number of users: " + DBreciver.numberOfUser();
        return new Result(answer);
    }
}
