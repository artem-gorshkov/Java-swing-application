package LabsProject.Commands;

import LabsProject.GeneratorPassword;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;

public class Authorization extends AbstractCommand {
    public Authorization() {

    }

    public Authorization(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DataBaseReciver dbReciver = (DataBaseReciver) reciver;
        String[] pswd = dbReciver.getPswd(this.getNickname());
        String hashpswd = GeneratorPassword.hash(this.getArguments(), pswd[1]);
        if(hashpswd.equals(pswd[0]))
        return new Result("login successful");
        else return new Result("incorrect. Try again");
    }
}
