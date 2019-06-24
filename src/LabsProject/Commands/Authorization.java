package LabsProject.Commands;

import LabsProject.GeneratorPassword;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
        if(hashpswd.equals(pswd[0])){
            List<Human> Color = new LinkedList<>();
            Human hum = new Human("color");
            hum.setColor(dbReciver.getColor(this.getNickname()));
            Color.add(hum);
            return new Result("login_successful");
        }
        else return new Result("incorrect");
    }
}
