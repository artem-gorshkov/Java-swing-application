package LabsProject.Commands;

import LabsProject.Recivers.DBReciverWithImport;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;

public class Import extends AbstractCommand {

    public Import(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DBReciverWithImport DBI = (DBReciverWithImport) reciver;
        DBI.ImportHumans(this.getNickname());
        return new Result(DBI.getAllHuman());
    }
}