package LabsProject.Commands;

import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;
import java.util.List;

public class Save extends AbstractCommand {
    public Save(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DataBaseReciver DBreciver = (DataBaseReciver) reciver;
        List<Human> humans= DBreciver.getAllHuman();
        String answer = ManagerCollection.saveToFile(this.getArguments(), humans);
        return new Result(answer);
    }
}
