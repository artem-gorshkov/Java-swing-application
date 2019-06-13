package LabsProject.Commands;


import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;


public class Add_If_Max extends AbstractCommand {

    public Add_If_Max(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DataBaseReciver DBreciver = (DataBaseReciver) reciver;
        Human max_hum = DBreciver.return_max();
        Human in_hum = ManagerCollection.parseHuman(this.getArguments());
        if(in_hum.compareTo(max_hum) > 0) {
            int count = DBreciver.addHuman(in_hum, this.getNickname());
            if (count == 1) {
                return new Result("Insert " + in_hum.getName() + " succesfully", DBreciver.getAllHuman());
            }
            else {
                return new Result("Can't insert " + in_hum.getName(), DBreciver.getAllHuman());
            }
        }
        else {
            return new Result("Not bigger than max", DBreciver.getAllHuman());
        }
    }
}
