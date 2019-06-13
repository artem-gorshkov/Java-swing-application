package LabsProject.Commands;

import LabsProject.Nature.Homosapiens.Human;
import LabsProject.NetworkInteraction.ManagerCollection;
import LabsProject.Recivers.DBReciverWithImport;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;
import java.util.List;


public class Load extends AbstractCommand {
    public Load(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException
    {
        try {
            List<Human> humans = ManagerCollection.exportfromfile(this.getArguments());
            DBReciverWithImport DBI = new DBReciverWithImport(((DataBaseReciver) reciver), humans);
            DBI.ImportHumans(this.getNickname());
            return new Result("Load succesfully", DBI.getAllHuman());
        } catch (Exception e) {
            return new Result("Can't load", ((DataBaseReciver) reciver).getAllHuman());
        }
    }
}