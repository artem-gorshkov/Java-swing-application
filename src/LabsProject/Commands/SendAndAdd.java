package LabsProject.Commands;

import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.Result;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;

import java.sql.SQLException;

public class SendAndAdd  extends AbstractCommand {
    private Human human;
    public SendAndAdd(String arguments, Human human) {
        super(arguments);this.human = human;
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DataBaseReciver DBreciver = (DataBaseReciver) reciver;
        DBreciver.addHuman(human,getNickname());
        return new Result(DBreciver.getAllHuman());
    }
}