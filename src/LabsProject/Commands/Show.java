package LabsProject.Commands;

import LabsProject.Nature.Homosapiens.Human;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;


public class Show implements Command, Serializable {
    private String nickname;

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        List<Human> humans = ((DataBaseReciver) reciver).getAllHuman();
        if (humans.isEmpty())
            return new Result("No element in collection");
        else
            return new Result(humans);
    }

    public void addNick(String nickname) {
        this.nickname = nickname;
    }

}
