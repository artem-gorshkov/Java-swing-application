package LabsProject.Commands;


import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;
import java.util.List;


public class Remove_greater_key extends AbstractCommand {
    public Remove_greater_key(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DataBaseReciver DBreciver = (DataBaseReciver) reciver;
        List<String> names = DBreciver.getGreaterName(this.getArguments());
        int count = 0;
        for (String name : names) {
            int k = DBreciver.removeHuman(name, this.getNickname());
            count += k;
        }
        return new Result("Removed " + count + " humans", DBreciver.getAllHuman());
    }
}