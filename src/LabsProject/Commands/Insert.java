package LabsProject.Commands;



import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;


public class Insert extends AbstractCommand {
    public Insert(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DataBaseReciver DBreciver = (DataBaseReciver) reciver;
        Human human = ManagerCollection.parseHuman(this.getArguments());
        int count = DBreciver.addHuman(human, this.getNickname());
        if (count == 1) {
            return new Result("Insert " + human.getName() + " succesfully", DBreciver.getAllHuman());
        }
        else {
            return new Result("Can't insert " + human.getName(), DBreciver.getAllHuman());
        }
    }
}
